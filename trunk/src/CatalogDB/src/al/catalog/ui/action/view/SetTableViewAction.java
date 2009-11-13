package al.catalog.ui.action.view;

import java.awt.event.ActionEvent;

import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.resource.ResourceManager;
import al.catalog.ui.view.ViewPanel;

public class SetTableViewAction extends CustomAction {
	
	public static final String ACTION_NAME = "Table";
	
	public static final String KEY = "setTableViewAction.text";
	
	public SetTableViewAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
	}

	public void actionPerformed(ActionEvent e) {
		ViewPanel viewPanel = (ViewPanel) actionManager.getProperty(ActionManager.PROPERTY_VIEW_PANEL);
		viewPanel.setDetailedView();				
	}
}
