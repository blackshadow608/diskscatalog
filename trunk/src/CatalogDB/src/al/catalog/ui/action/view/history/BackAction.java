package al.catalog.ui.action.view.history;

import java.awt.event.ActionEvent;

import javax.swing.JTree;

import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.resource.ResourceManager;

public class BackAction extends CustomAction {
	
	public static final String ACTION_NAME = "Back";
	
	private static final String KEY = "backAction.text";
	
	public BackAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
	}

	public void actionPerformed(ActionEvent e) {
		actionManager.getDBManager().getTreeModel().back();
		JTree tree = (JTree) actionManager.getProperty(ActionManager.PROPERTY_TREE);;
		tree.requestFocus();
	}
}
