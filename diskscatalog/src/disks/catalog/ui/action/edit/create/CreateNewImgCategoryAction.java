package disks.catalog.ui.action.edit.create;

import java.awt.event.ActionEvent;

import disks.catalog.model.IActionCallback;
import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.types.images.ImgCategoryNode;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.resource.ResourceManager;


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
