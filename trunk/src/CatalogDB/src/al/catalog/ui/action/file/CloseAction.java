package al.catalog.ui.action.file;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import al.catalog.model.DBManager;
import al.catalog.model.tree.action.CloseConnectionAction;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.resource.ResourceManager;

public class CloseAction extends CustomAction {
	
	public static final String ACTION_NAME = "Close";
	
	private static final String KEY = "closeAction.text";
	
	public CloseAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_W, ActionEvent.CTRL_MASK));
	}

	public void actionPerformed(ActionEvent e) {
		DBManager dbManager = aManager.getDBManager();		
		CloseConnectionAction closeAction = new CloseConnectionAction(dbManager);
		closeAction.execute();
	}
}
