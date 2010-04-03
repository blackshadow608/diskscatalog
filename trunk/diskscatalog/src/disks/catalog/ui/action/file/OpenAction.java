package disks.catalog.ui.action.file;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import disks.catalog.model.DBManager;
import disks.catalog.model.tree.action.OpenConnectionAction;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.resource.ResourceManager;


public class OpenAction extends CustomAction {

	public static final String ACTION_NAME = "Open";
	
	private static final String KEY = "openAction.text";
	
	public OpenAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
	}

	public void actionPerformed(ActionEvent e) {
		DBManager dbManager = aManager.getDBManager();		
		OpenConnectionAction action = new OpenConnectionAction(dbManager);
		action.execute();
	}
}
