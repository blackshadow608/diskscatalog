package disks.catalog.ui.action.file;

import java.awt.event.ActionEvent;

import disks.catalog.model.DBException;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.resource.ResourceManager;


public class ExitAction extends CustomAction {
	
	public static final String ACTION_NAME = "Exit action";
	
	private static final String KEY = "exitAction.text";
	
	public ExitAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			aManager.getDBManager().close();
		} catch (DBException ex) {
			ex.printStackTrace();
		}
		System.exit(0);		
	}
}
