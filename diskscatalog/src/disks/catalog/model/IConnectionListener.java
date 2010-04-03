package disks.catalog.model;

/**
 * Интерфейс, который должен реализовать слушатель, желающий прослушивать события открытия
 * и закрытия соединения с БД. 
 * 
 * @author Alexander Levin
 */
public interface IConnectionListener {
	
	/**
	 * Вызывается у слушателя после того, как соединение было открыто.
	 */
	public void connectionWasOpened();
	
	/**
	 * Вызывается у слушателя после того, как соединение было закрыто.
	 */
	public void connectionWasClosed();
}
