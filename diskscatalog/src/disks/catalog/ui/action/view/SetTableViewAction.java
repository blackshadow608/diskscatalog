package disks.catalog.ui.action.view;

import java.awt.event.ActionEvent;

import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.resource.ResourceManager;
import disks.catalog.ui.view.ViewPanel;


public class SetTableViewAction extends CustomAction {
	
	public static final String ACTION_NAME = "Table";
	
	public static final String KEY = "setTableViewAction.text";
	
	public SetTableViewAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
	}

	public void actionPerformed(ActionEvent e) {
		ViewPanel viewPanel = aManager.getMainFrame().getViewPanel();
		viewPanel.setDetailedView();				
	}
}
