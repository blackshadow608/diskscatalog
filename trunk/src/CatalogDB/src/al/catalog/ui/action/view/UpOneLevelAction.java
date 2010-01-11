package al.catalog.ui.action.view;

import java.awt.event.ActionEvent;

import javax.swing.JTree;

import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.ITreeNode;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.resource.ResourceManager;

public class UpOneLevelAction extends CustomAction {

	public static final String ACTION_NAME = "Up one level action";

	private static final String KEY = "upOneLevelAction.text";

	public UpOneLevelAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
	}

	public void actionPerformed(ActionEvent e) {
		DBTreeModel dbModel = aManager.getDBManager().getTreeModel();
		ITreeNode openedNode = dbModel.getOpenedNode();

		if (openedNode != null && openedNode.hasParent()) {
			JTree tree = aManager.getMainFrame().getContentPanel().getTreePanel().getTree();
			tree.requestFocus();
			ITreeNode parentNode = openedNode.getParent();
			dbModel.setOpenedNode(parentNode);
			dbModel.fireChangeOpenedNode(parentNode);
		}
	}
}
