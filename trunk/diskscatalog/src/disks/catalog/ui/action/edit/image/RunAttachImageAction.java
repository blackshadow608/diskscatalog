package disks.catalog.ui.action.edit.image;

import java.awt.event.ActionEvent;

import javax.swing.JDialog;

import disks.catalog.model.tree.action.AttachImageAction;
import disks.catalog.model.tree.types.file.FolderNode;
import disks.catalog.model.tree.types.images.ImageNode;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.resource.ResourceManager;


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
		AttachImageAction dbAction = new AttachImageAction(aManager.getModel(), folder, image);		
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
