package disks.catalog.model;

/**
 * Утилитный класс, основное предназначение которого - это загрузить необходимый
 * драйвер для работы с базой данных.
 * 
 * @author Alexander Levin
 */
public class DriverLoader {
	
	private static boolean driverIsLoaded = false;
	
	/**
	 * Осуществляет загрузку дравейра базы данных.
	 * 
	 * @throws ClassNotFoundException если требуемый драйвер не найден.
	 */
	public static void loadDriver() throws ClassNotFoundException {
		if (!driverIsLoaded) {
			Class.forName("org.hsqldb.jdbcDriver");
		}		
	}
}
