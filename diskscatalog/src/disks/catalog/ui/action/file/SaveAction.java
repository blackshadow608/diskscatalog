package disks.catalog.ui.action.file;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import disks.catalog.model.DBManager;
import disks.catalog.model.tree.action.SaveChangesAction;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.resource.ResourceManager;


public class SaveAction extends CustomAction {
	
	public static final String ACTION_NAME = "Save";
	
	private static final String KEY = "saveAction.text";
	
	public SaveAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_S, ActionEvent.CTRL_MASK));
	}

	public void actionPerformed(ActionEvent e) {
		DBManager dbManager = aManager.getDBManager();		
		SaveChangesAction saveAction = new SaveChangesAction(dbManager);
		saveAction.execute();
		setEnabled(false);
	}	
}
