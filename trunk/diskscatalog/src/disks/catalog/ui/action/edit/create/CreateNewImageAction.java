package disks.catalog.ui.action.edit.create;

import java.awt.event.ActionEvent;

import disks.catalog.model.IActionCallback;
import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.types.images.ImageNode;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.resource.ResourceManager;


public class CreateNewImageAction extends CustomAction {
	
	public static final String ACTION_NAME = "Create new image";
	
	private static final String KEY = "createNewImageAction.text";
	private static final String MESSAGE = "CreateNewImgame action must have DBModel";
	
	public CreateNewImageAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));		
	}

	public void actionPerformed(ActionEvent e) {		
		final DBTreeModel dbModel = aManager.getModel();
		if (dbModel != null) {
			IActionCallback callBack = CreateAction.getCreateCallback(dbModel, e.getActionCommand());
			dbModel.createNode(ImageNode.class, callBack);
		} else {
			throw new NullPointerException(MESSAGE);
		}		
	}
}
