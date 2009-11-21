package al.catalog.ui.resource;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceManager {
	
	private static ResourceManager instance;
	
	private ResourceBundle resourceBundle;
	
	private ResourceManager() {
		Locale locale = new Locale("ru", "RU");		
		resourceBundle = ResourceBundle.getBundle("messages", locale);		
	}
	
	public static String getString(String key) {
		if (instance == null) {
			instance = new ResourceManager();
		}
		return instance.getMessage(key);
	}
	
	protected String getMessage(String key) {
		String string = resourceBundle.getString(key);
		String message = "";
		
		try {
			message = new String(string.getBytes("ISO-8859-1"), "UTF-8");			
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}	
		
		return message;
	}
}
