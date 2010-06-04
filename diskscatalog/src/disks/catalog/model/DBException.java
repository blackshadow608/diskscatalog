package disks.catalog.model;

/**
 * Представляет собой исключительную ситуацию или исключение, которое было
 * выброшено в процессе выполнения операций с БД.
 * 
 * @author Alexander Levin
 */
public class DBException extends Exception {

	/**
	 * Создает новый объект {@link DBException}, который будет содержать
	 * текстовое {@link String} сообщение.
	 * 
	 * @param message
	 *            - {@link String} сообщение, которое будет содержать объект.
	 */
	public DBException(String message) {
		super(message);
	}

	/**
	 * Создает новый объект {@link DBException} на основе родительского
	 * исключения.
	 * 
	 * @param exception
	 *            - исключение {@link Exception}, на основе которого создается
	 *            новый объект.
	 */
	public DBException(Exception exception) {
		super(exception);
	}
}
