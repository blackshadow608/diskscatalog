package al.catalog.ui.action.view;

import java.awt.event.ActionEvent;

import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.resource.ResourceManager;
import al.catalog.ui.view.ViewPanel;

public class SetIconsViewAction extends CustomAction {
	
	public static final String ACTION_NAME = "Icons";
	
	private static final String KEY = "setIconsViewAction.text";
	
	public SetIconsViewAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));		
	}
	
	public void actionPerformed(ActionEvent e) {
		ViewPanel viewPanel = aManager.getMainFrame().getViewPanel();
		viewPanel.setIconsView();
	}
}
