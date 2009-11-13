package al.catalog.ui.action.file;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import al.catalog.model.DBManager;
import al.catalog.model.tree.action.OpenConnectionAction;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.resource.ResourceManager;

public class OpenAction extends CustomAction {

	public static final String ACTION_NAME = "Open";
	
	private static final String KEY = "openAction.text";
	
	public OpenAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
	}

	public void actionPerformed(ActionEvent e) {
		DBManager dbManager = actionManager.getDBManager();		
		OpenConnectionAction action = new OpenConnectionAction(dbManager);
		action.execute();
	}
}
