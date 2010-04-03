package disks.catalog.ui.action.edit;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.resource.ResourceManager;


public class MoveAction extends CustomAction {
	
	private JDialog owner;
	private DBTreeModel dbModel;
	private List<ITreeNode> treeNodes;
	
	private static final String KEY = "moveAction.text";
	
	public static final String ACTION_NAME = "MoveAction";
	
	public MoveAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));		
	}

	public void actionPerformed(ActionEvent e) {
		final ITreeNode newParent = dbModel.getActiveNodes().get(0);
		//final JList list = (JList) actionManager.getProperty(ActionManager.PROPERTY_FILE_LIST);
		
		for (ITreeNode treeNode : treeNodes) {
			if (treeNode == newParent) {
				JOptionPane.showMessageDialog(owner, "Cannot move node into itself!", "Move error", JOptionPane.ERROR_MESSAGE);
				return;
			}			
		}		
		
		if (treeNodes.size() == 1) {
			if (treeNodes.get(0).getParent() == newParent) {
				JOptionPane.showMessageDialog(owner, "This node already content moved node!", "Move error", JOptionPane.ERROR_MESSAGE);
				return;
			}			
		} else if (treeNodes.size() > 1) {
			for (int i = 0; i < treeNodes.size(); i++) {
				if (treeNodes.get(i).getParent() == newParent) {
					JOptionPane.showMessageDialog(owner,
							"This node already content some node from selected!",
							"Move error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				dbModel.moveNodes(treeNodes, newParent);
				
			}
		});		
		owner.setVisible(false);
	}
	
	public void setOwner(JDialog owner) {
		this.owner = owner;
	}
	
	public void setModel(DBTreeModel dbModel) {
		this.dbModel = dbModel;
	}
	
	public void setTreeNodes(List<ITreeNode> treeNodes) {
		if (treeNodes == null) {
			this.treeNodes = null;
		} else {
			this.treeNodes = new ArrayList<ITreeNode>(treeNodes);			
		}		
	}
}
