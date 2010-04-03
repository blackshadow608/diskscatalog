package disks.catalog.ui.action.view;

import java.awt.event.ActionEvent;

import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.resource.ResourceManager;
import disks.catalog.ui.view.ViewPanel;


public class SetListViewAction extends CustomAction {
	
	public static final String ACTION_NAME = "List";
	
	private static final String KEY = "setListViewAction.text";
	
	public SetListViewAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));		
	}
	
	public void actionPerformed(ActionEvent e) {
		ViewPanel viewPanel = aManager.getMainFrame().getViewPanel();
		viewPanel.setListView();		
	}
}
