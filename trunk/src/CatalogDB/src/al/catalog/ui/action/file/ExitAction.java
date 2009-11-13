package al.catalog.ui.action.file;

import java.awt.event.ActionEvent;

import al.catalog.model.DBException;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.resource.ResourceManager;

public class ExitAction extends CustomAction {
	
	public static final String ACTION_NAME = "Exit action";
	
	private static final String KEY = "exitAction.text";
	
	public ExitAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			actionManager.getDBManager().close();
		} catch (DBException ex) {
			ex.printStackTrace();
		}
		System.exit(0);		
	}
}
