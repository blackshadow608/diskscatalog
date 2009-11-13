package al.catalog;

import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import al.catalog.logger.Logger;
import al.catalog.model.DBManager;
import al.catalog.ui.CatalogFrame;

public class Main {
	
	private static final String SERVER_MODE = "serverMode=true";
	
	
	public Main(String[] args) {
		Logger.enable();
		Logger.setShowLineNumbers(true);
		Logger.disable();
		
		DBManager dbManager = new DBManager();		
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
		
		new CatalogFrame(dbManager);
				
		/*UIDefaults uiDefaults = UIManager.getLookAndFeelDefaults();
		Set keySet = uiDefaults.keySet();
		for(Object key : keySet) {
			System.out.println(key);			
		}*/
	}
	
	public static void main(String[] args) {
		new Main(args);
	}
}
