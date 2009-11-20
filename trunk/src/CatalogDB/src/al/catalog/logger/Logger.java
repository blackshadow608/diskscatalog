package al.catalog.logger;

/**
 * Примитивный логер для внутреннего пользования.
 * 
 * @author Alexander_Levin
 */
public class Logger {
	
	private static int depth = 0;
	private static int lineNumber = 0;
	private static boolean isEnabled = false;
	private static boolean showLineNumbers = false;
	
	/**
	 * Выводит лог-сообщение, переданное методу в качестве параметра в консоль.
	 * 
	 * @param message -
	 *            <b>String</b> сообщение.
	 */
	public synchronized static void log(String message) {
		if(isEnabled) {
			if(showLineNumbers) {
				System.out.println(lineNumber++ + message);				
			} else {
				System.out.println(message);
			}
		}				
	}
	
	/**
	 * Открывает стек. Иногда требует проследить стек вызова какого-то метода.
	 * Поэтому для организации такого стека (лог-сообщения получают отступ, что
	 * существенно облегчает их чтение и понимание). В начале вызова метода
	 * делается openStack, далее идет вывод сообщений, а в конце метода
	 * closeStack.
	 */
	public synchronized static void openStack() {
		if(isEnabled) {
			depth++;						
		}		
	}
	
	/**
	 * Открывает стек лог-сообщений и при этом сразу же выводить какое-то
	 * сообщение.
	 * 
	 * @param message -
	 *            <b>String</b> сообщение.
	 */
	public synchronized static void openStack(String message) {
		if(isEnabled) {
			depth++;
			log(getTabs() + message);			
		}		
	}
	
	/**
	 * Закрывает стек и при этом сразу же выводит какое-то лог-сообщение.
	 * 
	 * @param message -
	 *            <b>String</b> сообщение.
	 */
	public synchronized static void closeStack(String message) {
		if(isEnabled) {
			log(getTabs() + message);
			depth--;			
		}		
	}
	
	/**
	 * Закрывает стек.
	 */
	public synchronized static void closeStack() {
		if(isEnabled) {			
			depth--;			
		}		
	}
	
	/**
	 * Выше было сказано про организацию стека,. открытие и закрытие стека
	 * отрганизуется при помощи методов openStack и closeStack. Данный метод
	 * выводит лог-сообщение внутри стека, чтобы логи сохраняли порядок внутри
	 * стека (определенный отступ слева).
	 * 
	 * @param message -
	 *            <b>String</b> сообщение, которое необходимо вывести в консоль
	 *            в рамках открытого стека.
	 */
	public synchronized static void logIntoStack(String message) {
		if(isEnabled) {
			depth++;
			log(getTabs() + message);
			depth--;
		}						
	}
	
	private static String getTabs() {
		String tabs = "";
		for(int i = 1; i <= depth; i++) {
			tabs += "\t";			
		}		
		return tabs;
	}
	
	/**
	 * Активизирует логер, то есть включает его - тогда все вызовы методов по
	 * выводу сообщений отображают сообщения в консоле. Если логер выключен, то
	 * в консоль ничего не выводится.
	 */
	public static void enable() {
		isEnabled = true;
	}
	
	/**
	 * Деактивирует логер - сообщения в консоль не выводятся.
	 */
	public static void disable() {
		isEnabled = false;
	}
	
	/**
	 * Говорит логеру, показывать ли номера строк или нет. Номер строки обычное
	 * целое число, которое призвано как-то однозначно идентифицировать строку
	 * сообщения. При просмотре логов очень часто сообщения здесь очень похожи,
	 * чтобы не путать одинаковые строки нужен идентификатор.
	 * 
	 * @param newValue -
	 *            <b>boolean</b> булевый флаг. <b>true</b> - показывать номера
	 *            строк и <b>false</b> - не показывать.
	 */
	public static void setShowLineNumbers(boolean newValue) {
		showLineNumbers = newValue;				
	}
}
