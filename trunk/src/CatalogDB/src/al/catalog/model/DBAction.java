package al.catalog.model;

import java.sql.Savepoint;

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
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Возвращает значение метки.
	 * 
	 * @return - текст метки
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Возвращает Savepoint, который должен быть установлен перед началом выполнения действия, чтобы
	 * быда возможность сделать откат БД при отмене действия.
	 * 
	 * @return - Savepoint до выполнения действия
	 */
	public Savepoint getSavepoint() {
		return savepoint;
	}
	
	/**
	 * Дает возможность определить статус действия.
	 * 
	 * @return - true, если действие выполнено, false - если нет.
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
	abstract public void abort();
    
	/**
	 * Возвращает текст, который должен будет отображаться в прогресс-диалоге или, например,
	 * в логах приложения при выполнении данного action'а.
	 * 
	 * @return прогресс-текст
	 */
	abstract public String getProgressText();
}
