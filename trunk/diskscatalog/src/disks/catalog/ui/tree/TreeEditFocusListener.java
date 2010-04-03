package disks.catalog.ui.tree;

import javax.swing.JTree;
import javax.swing.event.ChangeEvent;

import disks.catalog.ui.action.ActionManager;


public class TreeEditFocusListener implements ITreeEditFocusListener {
	
	private ActionManager actionManager;
	private JTree tree;
	
	public TreeEditFocusListener(ActionManager actionManager, JTree tree) {
		this.actionManager = actionManager;
		this.tree = tree;		
	}

	public void editingStarted() {		
		actionManager.freezeActions();
	}

	public void editingCanceled(ChangeEvent e) {		
		actionManager.unfreezeActions();
		tree.setEditable(false);
	}

	public void editingStopped(ChangeEvent e) {		
		actionManager.unfreezeActions();
		tree.setEditable(false);
	}
}
