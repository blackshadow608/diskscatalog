package disks.catalog.ui.action.file;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import disks.catalog.model.DBManager;
import disks.catalog.model.tree.action.CloseConnectionAction;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.resource.ResourceManager;


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
