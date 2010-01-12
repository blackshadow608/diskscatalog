package al.catalog.ui.action.edit;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import al.catalog.model.tree.DBTreeModel;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.dialog.PropertiesDialog;
import al.catalog.ui.resource.ResourceManager;

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
