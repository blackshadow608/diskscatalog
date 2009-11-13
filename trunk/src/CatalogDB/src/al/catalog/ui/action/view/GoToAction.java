package al.catalog.ui.action.view;

import java.awt.event.ActionEvent;

import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.resource.ResourceManager;

public class GoToAction extends CustomAction {
	
	public static final String ACTION_NAME = "Go to action";
	
	private static final String KEY = "gotoAction.text";
	
	public GoToAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}
}
