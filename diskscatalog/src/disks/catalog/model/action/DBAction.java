package disks.catalog.model.action;

import java.sql.Savepoint;

import disks.catalog.model.DBManager;

/**
 * Все действия с БД: открытие, закрытие, добавление, удаление и изменение узлов дерева, создание и
 * сохранение образов должны наследоваться от этого класса. Это сделано для того, чтобы можно было
 * отменить действие или повторить, если было отменено, а также прервать выполнение действия, если
 * его выполнение занимает определенный промежуток времени.
 * 
 * @author Alexander Levin
 */
public abstract class DBAction {
	
	protected DBManager dbManager;        
	protected Savepoint savepoint;
	protected boolean isExecuted;
	protected boolean firstExecute = true;
	protected String label;
	
	public DBAction(DBManager dbManager) {
		this.dbManager = dbManager;
	}
	
	/**
	 * Устанавливает значение метки, которую можно отображать в UI.
	 * 
	 * @param label - новая {@link String} строка для метки.
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Возвращает значение метки.
	 * 
	 * @return {@link String} строка текста метки.
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Возвращает Savepoint, который должен быть установлен перед началом
	 * выполнения действия, чтобы быда возможность сделать откат БД при отмене
	 * действия.
	 * 
	 * @return {@link Savepoint}, который был установлен до выполнения действия.
	 *         Данный {@link Savepoint} для возможности отката действия.
	 */
	public Savepoint getSavepoint() {
		return savepoint;
	}
	
	/**
	 * Дает возможность определить статус действия.
	 * 
	 * @return <code>true</code>, если действие выполнено, <code>false</code> - если нет.
	 */
	public boolean isExecuted() {
		return isExecuted;
	}
	
	/**
	 * Содержит код, который исполняется при выполнении действия.
	 */
	abstract public void execute();
	
	/**
	 * Содержит код, который исполняется при отмене действия.
	 */
	abstract public void unexecute();
	
	/**
	 * Содержит код, который исполняется при прерывании выполнения действия.
	 */
	public void abort() {
		
	}
	
	/**
	 * Содержит код, который вызывается при приостановке выполнения действия.
	 */
	public void pause() {
		
	}
    
	/**
	 * Возвращает текст, который должен будет отображаться в прогресс-диалоге или, например,
	 * в логах приложения при выполнении данного action'а.
	 * 
	 * @return {@link String} текст прогресс выполнения действия.
	 */
	abstract public String getProgressText();
	
	/**
	 * Возвращает флаг, который говорит о том, можно ли action прерывать или
	 * отменять в процессе его работы. Некоторые
	 * 
	 * @return <b>true</b>, если действие может быть отменено или прервано в
	 *         процессе его выполнения и <b>false</b>, если нет.
	 */
	abstract public boolean isCancelable();
	
	/**
	 * Возвращает флаг, который говорит о том, можно ли остановить выполнение
	 * action'а на время, чтобы далее прололжить его. Другими словами - можно ил
	 * поставить action на паузу или нет.
	 * 
	 * @return Возвращает <code>true</code>, если действие может быть поставлено на паузу и
	 *         <code>false</code>, если нет.
	 */
	abstract public boolean isPausable();
}
