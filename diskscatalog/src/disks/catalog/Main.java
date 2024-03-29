package disks.catalog;

import javax.swing.JDialog;
import javax.swing.JFrame;

import disks.catalog.logger.Logger;
import disks.catalog.model.DBManager;
import disks.catalog.model.IConstants;
import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.ui.CatFrame;
import disks.catalog.ui.action.ActionManager;


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
		
		DBManager dbManager = new DBManager();
		DBTreeModel dbModel = new DBTreeModel(dbManager);
		ActionManager aManager = new ActionManager(dbManager, dbModel);
		MainEntity mainEntity = new MainEntity(dbManager, aManager);		
		
		/*
		 * Читаем и проверяем параметры консоли.
		 */
		if (args != null && args.length > 0) {
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					String arg = args[i];
					if (SERVER_MODE.equals(arg)) {
						dbManager.setMode(IConstants.MODE_SERVER);						
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
