package disks.catalog.ui.action.edit;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.dialog.PropertiesDialog;
import disks.catalog.ui.resource.ResourceManager;


public class ShowPropertiesDialogAction extends CustomAction {
	
	public static final String ACTION_NAME = "Show properties";
	
	private static final String KEY = "showPropertiesDialog.text";
	
	public ShowPropertiesDialogAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
	}

	public void actionPerformed(ActionEvent e) {
		JFrame owner = aManager.getMainFrame();
		DBTreeModel dbModel = aManager.getModel();
		PropertiesDialog propertiesDailog = new PropertiesDialog(owner, dbModel, aManager);
		propertiesDailog.setVisible(true);
	}
}
