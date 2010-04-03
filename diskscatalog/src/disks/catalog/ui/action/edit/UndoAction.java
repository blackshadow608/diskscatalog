package disks.catalog.ui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import disks.catalog.model.DBManager;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.resource.ResourceManager;


public class UndoAction extends CustomAction {
	
	public static final String ACTION_NAME = "Undo";
	
	private static final String KEY = "undoAction.text";
	
	public UndoAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
	}

	public void actionPerformed(ActionEvent e) {
		final DBManager dbManager = aManager.getDBManager();		
		dbManager.undoAction();		
	}
}
