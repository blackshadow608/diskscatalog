package al.catalog.model.dao;

/**
 * Исключительная ситуация или исключение, которое может быть выброшено в ходе выполнения
 * методов DAO.
 * 
 * @author Alexander Levin
 */
public class DAOException extends Exception {

	public DAOException(String message) {
		super(message);		
	}
	
	public DAOException(Exception e) {
		super(e);
	}
}
