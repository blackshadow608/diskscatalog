package al.catalog.model.tree;

/**
 * Интерфейс, который должен реализрвать слушатель, желющий прослушивать события,
 * связанные с историей выбора или открытия какого-либо узла дерева.
 * 
 * @author Alexander Levin
 */
public interface IHistoryListener {
	
	/**
	 * Вызывается у слушателя, когда Back становится доступен, то есть возможно перейти
	 * к предыдущему открытому узлу дерева.
	 */
	public void backBecameEnabled();
	
	/**
	 * Вызывается у слушателя, когда Back становится недоступен, то есть нельзя перейти
	 * к предыдущиму открытому узлу дерева.
	 */
	public void backBecameDisabled();
	
	/**
	 * Вызывается у слушателя, когда Forward доступен.
	 */
	public void forwardBecameEnabled();
	
	/**
	 * Вызывается у слушателя, когда Forward не доступен.
	 */
	public void forwardBecameDisabled();
	
	/**
	 * Вызывается у слушателя, когда доступна операция очистки истории Clear.
	 */
	public void clearBecameEnabled();
	
	/**
	 * Вызывается у слушателя, когда операция очистки истории Clear
	 * становится недоступна.
	 */
	public void clearBecameDisabled();
}
