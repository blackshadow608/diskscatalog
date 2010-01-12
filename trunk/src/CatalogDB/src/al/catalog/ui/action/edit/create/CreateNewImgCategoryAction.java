package al.catalog.ui.action.edit.create;

import java.awt.event.ActionEvent;

import al.catalog.model.IActionCallback;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.images.ImgCategoryNode;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.resource.ResourceManager;

public class CreateNewImgCategoryAction extends CustomAction {
	
	public static final String ACTION_NAME = "Create new image category";
	
	private static final String KEY = "createNewImgCategoryAction.text";
	private static final String MESSAGE = "CreateNewImgCategory action must have DBModel";
	
	public CreateNewImgCategoryAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
	}

	public void actionPerformed(ActionEvent e) {
		final DBTreeModel dbModel = aManager.getModel();
		
		if (dbModel != null) {
			IActionCallback callBack = CreateAction.getCreateCallback(dbModel, e.getActionCommand());
			dbModel.createNode(ImgCategoryNode.class, callBack);
		} else {
			throw new NullPointerException(MESSAGE);
		}		
	}
}
