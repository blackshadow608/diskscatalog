package al.catalog.ui.action.edit;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JFrame;

import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.ITreeNode;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.dialog.move.MoveDialog;
import al.catalog.ui.resource.ResourceManager;

public class ShowMoveDialogAction extends CustomAction {
	
	public static final String ACTION_NAME = "Show move dialog";	
	private static final String KEY = "showMoveDialogAction.text";
	
	private List<ITreeNode> treeNodes;
	
	public ShowMoveDialogAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));				
	}

	public void actionPerformed(ActionEvent e) {
		JFrame owner = actionManager.getMainFrame();		
		DBTreeModel dbModel = actionManager.getDBManager().getTreeModel();
		treeNodes = dbModel.getActiveNodes();
		MoveDialog dialog = new MoveDialog(owner, dbModel, actionManager);
		dialog.setVisible(true);				
	}
	
	public List<ITreeNode> getTreeNodes() {
		return treeNodes;
	}
}
