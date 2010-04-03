package disks.catalog.ui.action.view.history;

import java.awt.event.ActionEvent;

import javax.swing.JTree;

import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.resource.ResourceManager;


public class ForwardAction extends CustomAction {
	
	public static final String ACTION_NAME = "Forward";
	
	private static final String KEY = "forwardAction.text";
	
	public ForwardAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
	}
	
	public void actionPerformed(ActionEvent e) {
		aManager.getModel().forward();
		JTree tree = aManager.getMainFrame().getContentPanel().getTreePanel().getTree();
		tree.requestFocus();
	}
}
