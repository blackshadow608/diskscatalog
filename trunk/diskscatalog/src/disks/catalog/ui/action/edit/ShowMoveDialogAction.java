package disks.catalog.ui.action.edit;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JFrame;

import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.dialog.move.MoveDialog;
import disks.catalog.ui.resource.ResourceManager;


public class ShowMoveDialogAction extends CustomAction {
	
	public static final String ACTION_NAME = "Show move dialog";	
	private static final String KEY = "showMoveDialogAction.text";
	
	private List<ITreeNode> treeNodes;
	
	public ShowMoveDialogAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));				
	}

	public void actionPerformed(ActionEvent e) {
		JFrame owner = aManager.getMainFrame();		
		DBTreeModel dbModel = aManager.getModel();
		treeNodes = dbModel.getActiveNodes();
		MoveDialog dialog = new MoveDialog(owner, dbModel, aManager);
		dialog.setVisible(true);				
	}
	
	public List<ITreeNode> getTreeNodes() {
		return treeNodes;
	}
}
