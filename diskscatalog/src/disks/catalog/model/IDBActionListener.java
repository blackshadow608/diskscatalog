package disks.catalog.model;

/**
 * Интерфейс, который должны реализовать слушатели, желающие прослушивать события, связанные
 * с выполнением какого-либо DBAction'а.
 * 
 * @author Alexander Levin
 */
public interface IDBActionListener {
	
	/**
	 * Вызывается у слушаетля, когда был выполнен хоть один action и возможно произвести его откат,
	 * то есть отменить его выполнение.
	 */
	public void undoBecameEnabled();
	
	/**
	 * Вызывается у слушаетля, когда некоторый action был отменен (Undo) и после этого никаких
	 * других action'ов не выполнялось,то есть возможно его выполнить снова.
	 */
	public void redoBecameEnabled();
	
	/**
	 * Вызывается у слушателей, когда становится недоступным отмена последнего
	 * выполненного действия.
	 */
	public void undoBecameDisabled();
	
	/**
	 * Вызывается у слушателя, когда становится недоступным произвести выполнение
	 * ранее откаченного action.
	 */
	public void redoBecameDisabled();
	
	/**
	 * Вызывается у слушателя, когда был добавлен новый action.
	 *
	 */
	public void someActionWasAdded();
	
	/**
	 * Вызывается у слушателя перед началом выполнения dbAction, чтобы перед началом
	 * выполнения действия слушатель успел выполнить все необходимые операции, например,
	 * открыть окно диалога прогресса выполнения действия.
	 * 
	 * @param dbAction - DBAction, который будет выполняться.
	 */
	public void beforeActionExecuting(DBAction dbAction);
	
	/**
	 * Вызывается у слушателя после выполнения dbAction, чтобы после выполненеия действия
	 * слушатель выполнил все необходимые действия, например, закрыл соответствующий диалог
	 * прогресса выполнения дйствия.
	 * 
	 * @param dbAction - DBAction, который выполнился.
	 */
	public void afterActionExecuting(DBAction dbAction);
	
	/**
	 * Вызывается у слушателя, когда выполнение dbAction было прервано и dbAction не выполнился
	 * до конца, например, когда мы жмем Cancel на диалоге прогресса выполнения действия.
	 * 
	 * @param dbAction - DBAction, выполнение которого было прервано.
	 */
	public void actionWasAborted(DBAction dbAction);
	
	/**
	 * Вызывается у слушателя в том случае, если в процессе выполнения action'а возникла
	 * исключительная ситуация.
	 * 
	 * @param dbAction - action, во время выполнения которого вознкила исключительная ситуация.
	 * @param exception - исключительная ситуация.
	 */
	public void actionThrowException(DBAction dbAction, Exception exception);
}
