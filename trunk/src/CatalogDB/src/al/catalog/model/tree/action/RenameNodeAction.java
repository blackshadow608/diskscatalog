package al.catalog.model.tree.action;

import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.ITreeNode;
import al.catalog.ui.resource.ResourceManager;

public class RenameNodeAction extends UpdateNodeAction {

	private static final String PROGRESS_TEXT = "renameNodeAction.progressText";
	
	public RenameNodeAction(DBTreeModel dbModel, ITreeNode treeNode) {
		super(dbModel, treeNode);		
	}
	
	public String getProgressText() {
		return ResourceManager.getString(PROGRESS_TEXT);
	}

	public boolean isCancelable() {
		return false;
	}
	
	public boolean isPausable() {
		return false;
	}
}
