package disks.catalog.ui.action.view.history;

import java.awt.event.ActionEvent;

import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.resource.ResourceManager;


public class ClearHistoryAction extends CustomAction {
	
	public static final String ACTION_NAME = "Clear history action";
	
	private static final String KEY = "clearHistoryAction.text";
	
	public ClearHistoryAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));		
	}
	
	public void actionPerformed(ActionEvent e) {
		DBTreeModel treeModel = aManager.getModel();
		treeModel.setOpenedNode(null);
		treeModel.setActiveNode(null);
		treeModel.fireChangeOpenedNode(null);
		treeModel.fireChangeActiveNode(null);
		treeModel.clearHistory();
	}
}
