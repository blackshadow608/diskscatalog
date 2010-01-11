package al.catalog.ui.action.view;

import java.awt.event.ActionEvent;

import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.resource.ResourceManager;
import al.catalog.ui.view.ViewPanel;

public class SetListViewAction extends CustomAction {
	
	public static final String ACTION_NAME = "List";
	
	private static final String KEY = "setListViewAction.text";
	
	public SetListViewAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));		
	}
	
	public void actionPerformed(ActionEvent e) {
		ViewPanel viewPanel = actionManager.getMainFrame().getViewPanel();
		viewPanel.setListView();		
	}
}
