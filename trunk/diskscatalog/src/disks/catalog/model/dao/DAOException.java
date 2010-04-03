package disks.catalog.model.dao;

/**
 * Исключительная ситуация или исключение, которое может быть выброшено в ходе выполнения
 * методов DAO.
 * 
 * @author Alexander Levin
 */
public class DAOException extends Exception {

	/**
	 * Создает новый объект <b>DAOException</b> с текстовым сообщением об ошибке.
	 * 
	 * @param message - <b>String</b> строка с текстом сообщения об ошибке.
	 */
	public DAOException(String message) {
		super(message);		
	}
	
	/**
	 * Создает новый объект <b>DAOException</b> на основе другой исключительной
	 * ситуации.
	 * 
	 * @param e -
	 *            <b>Exception</b>, на основе которой создается новый объект
	 *            <b>DAOException</b>.
	 */
	public DAOException(Exception e) {
		super(e);
	}
}
