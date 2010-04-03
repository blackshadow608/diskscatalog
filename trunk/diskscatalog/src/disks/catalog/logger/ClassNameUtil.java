package disks.catalog.logger;

/**
 * Утилитный класс для работы со строковыми именами классов.
 * 
 * @author Alexander Levin
 */
public class ClassNameUtil {

	/**
	 * Возвращает имя только самого класса из строки, которая представляет
	 * полное имя класса с указанием всех пакетво через точку.
	 * 
	 * @param fullName - полное имя класса с указанием всех пакетов.
	 * 
	 * @return имя класса без пакетов.
	 */
	public static String getClassName(String fullName) {
		String className = null;

		if (isNotEmpty(fullName)) {
			int index = fullName.lastIndexOf('.') + 1;

			if (indexValid(index, fullName)) {
				className = fullName.substring(index);
			}
		}

		return className;
	}

	private static boolean isNotEmpty(String string) {
		if (string == null) {
			return false;
		}

		if (string.equals("")) {
			return false;
		}

		return true;
	}

	private static boolean indexValid(int index, String string) {
		if (index < 0) {
			return false;
		}

		if (string == null) {
			return false;
		}

		if (string.equals("")) {
			return false;
		}

		if (index >= string.length()) {
			return false;
		}

		return true;
	}

}
