package disks.catalog.ui.action.edit;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.dialog.RenameDialog;
import disks.catalog.ui.resource.ResourceManager;


public class ShowRenameDialogAction extends CustomAction {
	
	public static final String ACTION_NAME = "Show rename action";
	private static final String KEY = "showRenameDialog.text";
	
	private ITreeNode treeNode;
	
	public ShowRenameDialogAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
	}

	public void actionPerformed(ActionEvent e) {
		JFrame owner = aManager.getMainFrame();
		DBTreeModel dbModel = aManager.getModel();
		treeNode = dbModel.getActiveNodes().get(0);
		RenameDialog dialog = new RenameDialog(owner, dbModel, aManager);
		dialog.setVisible(true);		
	}
	
	public ITreeNode getTreeNode() {
		return treeNode;
	}
}
