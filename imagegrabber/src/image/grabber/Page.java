package image.grabber;

/**
 * Представляет собой страницу, из которой необходимо извлечь интересуемые
 * данные.
 * 
 * @author Alexander Levin
 */
public class Page {

	/*
	 * Весь HTML код страницы.
	 */
	private String content = "";

	/**
	 * Создает новый пустой объект {@link Page}.
	 */
	public Page() {

	}

	/**
	 * Создает новый объект {@link Page} с определенным HTML кодом. 
	 * 
	 * @param content - HTML код страницы.
	 */
	public Page(String content) {
		this.content = content;
	}

	/**
	 * Устанавливает новый HTML код страницы.
	 * 
	 * @param content - HTML код страницы в виде строки {@link String}.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Возвращает HTML код страницы.
	 * 
	 * @return HTML код страницы в виде {@link String} строки.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Возвращает позицию подстроки, которая встретилась в странице определенное
	 * количество раз согласно значению параметра метода.
	 * 
	 * @param str
	 *            - подстрока, позицию которой нужно найти.
	 * @param orderNum
	 *            - сколько раз должна встретиться.
	 * 
	 * @return Позиция подстроки, которая встретилась в странице определенное
	 *         количество раз.
	 */
	public int getPosition(String str, int orderNum) {
		return getPosition(str, orderNum, 0);
	}

	public int getPosition(String str, int orderNum, int beginPos) {
		String string = content.substring(beginPos);
		int index = 0;
		for (int i = 0; i < orderNum; i++) {
			index = string.indexOf(str, index + 1);
			if (index < 0) {
				return -1;
			}
		}
		return index + beginPos;
	}

	/**
	 * Возвращает позицию n-го тега рисунка &lt;img&gt;. Число n передается
	 * методу в качестве параметра.
	 * 
	 * @param orderNum
	 *            - какой именно по счету необходимо получить тег.
	 * @return позиция n-го тега.
	 */
	public int getImgPos(int orderNum) {
		return getPosition("<img", orderNum);
	}

	/**
	 * Возвращает позицию n-го тега &lt;a&gt;. Число n передается
	 * методу в качестве параметра.
	 * 
	 * @param orderNum
	 *            - какой именно по счету необходимо получить тег.
	 * @return позиция n-го тега.
	 */
	public int getAPos(int orderNum) {
		return getPosition("<a", orderNum);
	}

	public int getNextAPos(int fromIndex) {
		return content.indexOf("<a", fromIndex);
	}

	public int getAPos(int orderNum, int beginIndex) {
		return getPosition("<a", orderNum, beginIndex);
	}

}
