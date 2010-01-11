package al.catalog.ui.action.view.history;

import java.awt.event.ActionEvent;

import al.catalog.model.tree.DBTreeModel;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.resource.ResourceManager;

public class ClearHistoryAction extends CustomAction {
	
	public static final String ACTION_NAME = "Clear history action";
	
	private static final String KEY = "clearHistoryAction.text";
	
	public ClearHistoryAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));		
	}
	
	public void actionPerformed(ActionEvent e) {
		DBTreeModel treeModel = aManager.getDBManager().getTreeModel();
		treeModel.setOpenedNode(null);
		treeModel.setActiveNode(null);
		treeModel.fireChangeOpenedNode(null);
		treeModel.fireChangeActiveNode(null);
		treeModel.clearHistory();
	}
}
