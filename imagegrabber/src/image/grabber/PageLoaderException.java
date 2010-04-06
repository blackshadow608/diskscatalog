package image.grabber;

/**
 * Исключение, которое выбрасывается, если при загрузке страницы возникла
 * исключительная ситуация.
 * 
 * @author Alexander Levin
 */
public class PageLoaderException extends Exception {

	/**
	 * Создает новый объект {@link PageLoaderException} на основе текстового
	 * сообщения, которое передается конструктору в качестве параметра.
	 * 
	 * @param message
	 *            - текстовое сообщение, которое будет содержаться в новом
	 *            объекте {@link PageLoaderException}.
	 */
	public PageLoaderException(String message) {
		super(message);
	}

	/**
	 * Создает новый объект {@link PageLoaderException} на основе
	 * {@link Exception}.
	 * 
	 * @param e
	 *            - {@link Exception}, на основе которого создается новый объект
	 *            {@link PageLoaderException}.
	 */
	public PageLoaderException(Exception e) {
		super(e);
	}
}
