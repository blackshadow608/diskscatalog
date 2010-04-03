package disks.catalog.model;

/**
 * Представляет собой исключительную ситуацию или исключение, которое было выброшено в процессе
 * выполнения операций с БД.
 * 
 * @author Alexander Levin
 */
public class DBException extends Exception {

	public DBException(String message) {
		super(message);		
	}
	
	public DBException(Exception e) {
		super(e);
	}
}
