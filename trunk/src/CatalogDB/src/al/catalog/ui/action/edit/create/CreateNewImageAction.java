package al.catalog.ui.action.edit.create;

import java.awt.event.ActionEvent;

import al.catalog.model.IActionCallback;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.resource.ResourceManager;

public class CreateNewImageAction extends CustomAction {
	
	public static final String ACTION_NAME = "Create new image";
	
	private static final String KEY = "createNewImageAction.text";
	private static final String MESSAGE = "CreateNewImgame action must have DBModel";
	
	public CreateNewImageAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));		
	}

	public void actionPerformed(ActionEvent e) {		
		final DBTreeModel dbModel = actionManager.getDBManager().getTreeModel();
		if (dbModel != null) {
			IActionCallback callBack = CreateAction.getCreateCallback(dbModel, e.getActionCommand());
			dbModel.createNode(ImageNode.class, callBack);
		} else {
			throw new NullPointerException(MESSAGE);
		}		
	}
}
