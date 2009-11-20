package al.catalog.ui.util;

import java.util.Set;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

/**
 * Утилитный класс, основная задача которого сводится к показу ключей для Look
 * and feel из UIDefaults.
 * 
 * @author Alexander_Levin
 */
public class UIDefaultsKeysShower {
	
	/**
	 * Выводит ключи из UIDefaults для Look and feel.
	 */
	public static void showKeys() {
		UIDefaults uiDefaults = UIManager.getLookAndFeelDefaults();
		Set<?> keySet = uiDefaults.keySet();
		for(Object key : keySet) {
			System.out.println(key);			
		}
	}

}
