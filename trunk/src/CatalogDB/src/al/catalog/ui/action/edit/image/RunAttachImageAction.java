package al.catalog.ui.action.edit.image;

import java.awt.event.ActionEvent;

import javax.swing.JDialog;

import al.catalog.model.tree.action.AttachImageAction;
import al.catalog.model.tree.types.file.FolderNode;
import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.resource.ResourceManager;

public class RunAttachImageAction extends CustomAction {
	
	public static final String ACTION_NAME = "Attach image action";
	
	private static final String KEY = "attachImageAction.text";
	
	private ImageNode image;
	private FolderNode folder;
	private JDialog owner;
	
	public RunAttachImageAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
	}

	public void actionPerformed(ActionEvent e) {		
		AttachImageAction dbAction = new AttachImageAction(aManager.getDBManager(), folder, image);		
		dbAction.execute();
		owner.setVisible(false);
	}
	
	public void setImage(ImageNode image) {
		this.image = image;		
	}
	
	public void setFolder(FolderNode folder) {
		this.folder = folder;
	}
	
	public void setOwner(JDialog owner) {
		this.owner = owner;
	}
}
