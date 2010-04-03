package disks.catalog.ui.action.edit.image;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.dialog.attach.AttachDialog;
import disks.catalog.ui.resource.ResourceManager;


public class ShowAttachDialogAction extends CustomAction {
	
	public static final String ACTION_NAME = "Show attach dialog action";
	
	private static final String KEY = "showAttachDialogAction.text";
	
	private ITreeNode treeNode;
	
	public ShowAttachDialogAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));		
	}
	
	public void actionPerformed(ActionEvent e) {
		JFrame owner = aManager.getMainFrame();
		DBTreeModel dbModel = aManager.getModel();
		treeNode = dbModel.getActiveNodes().get(0);
		AttachDialog dialog = new AttachDialog(owner, dbModel, aManager);
		dialog.setVisible(true);
	}
	
	public ITreeNode getTreeNode() {
		return treeNode;
	}
}
