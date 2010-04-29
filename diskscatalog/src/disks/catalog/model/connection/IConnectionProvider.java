package disks.catalog.model.connection;

import java.sql.Connection;

/**
 * Интерфейс, который должны реализовать все объекты, предоставляющие коннект к БД.
 *
 * @author Alexander Levin
 */
public interface IConnectionProvider {
	
	/**
	 * Возвращает объект Connection - соединение с БД.
	 * 
	 * @return Connection срединение с БД.
	 */
	public Connection getConnection();
}
