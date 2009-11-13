package al.catalog.ui.action.edit.create;

import al.catalog.model.IActionCallback;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.ITreeNode;

public class CreateNodeCallback implements IActionCallback {
		
	private DBTreeModel dbModel;
	private String actionCommand;
	
	public CreateNodeCallback(DBTreeModel dbModel, String actionCommand) {
		this.dbModel = dbModel;
		this.actionCommand = actionCommand;
	}

	public void onSuccess(Object result, boolean firstExecute) {
		if(!firstExecute) {
			return;
		}
		
		ITreeNode treeNode = (ITreeNode) result;
		
		if(actionCommand.equals(CreateAction.ACTIVE_AND_OPENED_EQUAL)) {
			dbModel.fireChangeOpenedNode(treeNode);			
		} else if(actionCommand.equals(CreateAction.ACTIVE_AND_OPENED_NOT_EQUAL)) {
			dbModel.fireChangeActiveNode(treeNode);			
		}
	}
	
	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}
}
