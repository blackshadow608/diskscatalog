package al.catalog.ui.action.edit.image;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.ITreeNode;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.dialog.attach.AttachDialog;
import al.catalog.ui.resource.ResourceManager;

public class ShowAttachDialogAction extends CustomAction {
	
	public static final String ACTION_NAME = "Show attach dialog action";
	
	private static final String KEY = "showAttachDialogAction.text";
	
	private ITreeNode treeNode;
	
	public ShowAttachDialogAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));		
	}
	
	public void actionPerformed(ActionEvent e) {
		JFrame owner = (JFrame) actionManager.getProperty(ActionManager.PROPERTY_OWNER_FRAME);
		DBTreeModel dbModel = actionManager.getDBManager().getTreeModel();
		treeNode = dbModel.getActiveNodes().get(0);
		AttachDialog dialog = new AttachDialog(owner, dbModel, actionManager);
		dialog.setVisible(true);
	}
	
	public ITreeNode getTreeNode() {
		return treeNode;
	}
}
