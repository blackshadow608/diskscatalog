package disks.catalog.ui.resource;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Класс <b>ResourceManager</b> представляет собой менеджер ресурсов. Все
 * текстовые сообщения и надписи, которые используются в приложении хранятся в
 * специальных ресурсных файлах. Данный класс предоставляет методы для получения
 * ресурсов из файлов и выполнения необходимых манипуляций с ними, если это
 * необходимо.
 * 
 * @author Alexander Levin
 */
public class ResourceManager {
	
	private static ResourceManager instance;
	
	private ResourceBundle resourceBundle;
	
	/**
	 * Закрытый конструктор. 
	 */
	private ResourceManager() {
		Locale locale = new Locale("ru", "RU");		
		resourceBundle = ResourceBundle.getBundle("messages", locale);		
	}
	
	/**
	 * Возвращает строку из ресурсного файла по ключу, который передается методу
	 * в качестве параметра. Каждая строка в ресурсном файле однозначно
	 * идентифицируется ключом.
	 * 
	 * @param key -
	 *            <b>String</b> ключ строки, которую необходимо получить из
	 *            ресурсного файла.
	 * 
	 * @return строка <b>String</b>, полученная из ресурсного файла.
	 */
	public static String getString(String key) {
		if (instance == null) {
			instance = new ResourceManager();
		}
		return instance.getMessage(key);
	}
	
	/**
	 * Возвращает строку из ресурсного файла по ключу и производит с ней
	 * некоторые модификации.
	 * 
	 * @param key - <b>String</b> ключ строки.
	 * 
	 * @return <b>String</b> строка, которая получается из ресурсного файла.
	 */
	protected String getMessage(String key) {
		String string = resourceBundle.getString(key);
		String message = "";
		
		/*
		 * После получения строки её необходимо преобразовать из одной кодировки
		 * в другую, сразу в UTF-8 почему-то читать не получается :(
		 */
		try {
			message = new String(string.getBytes("ISO-8859-1"), "UTF-8");			
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}	
		
		return message;
	}
}
