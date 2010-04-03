package disks.catalog.ui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;

import disks.catalog.model.DBManager;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.resource.ResourceManager;


public class RedoAction extends CustomAction {
	
	public static final String ACTION_NAME = "Redo";
	
	private static final String KEY = "redoAction.text";
	
	public RedoAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
	}

	public void actionPerformed(ActionEvent e) {
		final DBManager dbManager = aManager.getDBManager();		
		dbManager.redoAction();
	}
}
