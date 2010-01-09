package al.catalog;

import javax.swing.JDialog;
import javax.swing.JFrame;

import al.catalog.logger.Logger;
import al.catalog.model.DBManager;
import al.catalog.ui.CatFrame;
import al.catalog.ui.action.ActionManager;

/**
 * Точка входа приложения, с которой начинается выполнение приложения.
 * 
 * @author Alexander Levin
 */
public class Main {
	
	private static final String SERVER_MODE = "serverMode=true";
	
	
	public Main(String[] args) {
		/*
		 * Настраиваем логер.
		 */
		Logger.enable();
		Logger.setShowLineNumbers(true);
		Logger.disable();
		
		MainEntity mainEntity = new MainEntity();
		
		DBManager dbManager = new DBManager();
		mainEntity.setDBManager(dbManager);
		
		ActionManager actionManager = new ActionManager(dbManager);
		mainEntity.setActionManager(actionManager);
		
		/*
		 * Читаем и проверяем параметры консоли.
		 */
		if (args != null && args.length > 0) {
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					String arg = args[i];
					if (SERVER_MODE.equals(arg)) {
						dbManager.setMode(DBManager.MODE_SERVER);						
					}
				}									
			}
		}
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		
		new CatFrame(mainEntity);
	}
	
	/**
	 * Точка входа приложения.
	 * 
	 * @param args -
	 *            <b>String[]</b> параметры командной строки, передаваемые
	 *            приложению во время запуска.
	 */
	public static void main(String[] args) {
		new Main(args);
	}
}
