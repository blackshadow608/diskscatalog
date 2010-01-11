package al.catalog.ui.view.list;

import java.util.List;

import javax.swing.JList;

import al.catalog.logger.Logger;
import al.catalog.model.tree.IDBTreeModelListener;
import al.catalog.model.tree.types.ITreeNode;

public class ListItemsChangingListener implements IDBTreeModelListener {
	
	private CatalogList list;
	
	public ListItemsChangingListener(CatalogList list) {
		this.list = list;		
	}

	public void activeNodeWasChanged(ITreeNode node) {
		Logger.openStack();
		final ITreeNode activeNode = node;
		CustomListModel listModel = (CustomListModel) list.getModel();
		if (activeNode != null) {
			Logger.logIntoStack("activeNode is not null");
			int index = listModel.getIndexOf(activeNode);
			if (index >= 0 && index < listModel.getSize()) {
				Logger.logIntoStack("select activeNode in list");
				list.setLastSelectedIndex(index);
				list.setSelectedIndex(index);				
				list.requestFocus();				
			} else {
				Logger.logIntoStack("clear selection");
				list.clearSelection();
			}
		} else {
			Logger.logIntoStack("activeNode is null");
			list.clearSelection();
			list.transferFocus();
		}
		Logger.closeStack();
	}
	
	public void activeNodeWasChanged(ITreeNode node, Object source) {
		if (source != list) {
			activeNodeWasChanged(node);
		}
	}

	public void nodeWasChanged(ITreeNode node) {
		
	}

	public void nodeWasInserted(ITreeNode node, ITreeNode parent) {
				
	}

	public void nodeWasRemoved(ITreeNode node, ITreeNode parent, int index) {
												
	}

	public void onAfterChangeNode(ITreeNode node) {
				
	}

	public void onBeforeChangeNode(ITreeNode node) {
				
	}

	public void openedNodeWasChanged(ITreeNode node) {
		
	}

	public void nodeStructureWasChanged(ITreeNode node) {
		
	}

	public void openedNodeWasChanged(ITreeNode node, Object source) {
		
	}

	public void activeNodesWasChanged(List<ITreeNode> nodes) {
		Logger.openStack();
		final CustomListModel listModel = (CustomListModel) list.getModel();
		final List<ITreeNode> nodesList = nodes;
		if (nodesList != null) {
			boolean haveSelected = false;
			list.removeSelectionInterval(0, listModel.getSize() - 1);
			for (ITreeNode node : nodesList) {
				int index = listModel.getIndexOf(node);
				list.addSelectionInterval(index, index);
				if (index >= 0) {
					haveSelected = true;
				}
			}
			if (!haveSelected) {
				list.clearSelection();
			}
		} else {
			list.clearSelection();
			list.transferFocus();
		}
		Logger.closeStack();
	}
	
	public void activeNodesWasChanged(List<ITreeNode> nodes, Object source) {
		if (!(source instanceof JList)) {
			activeNodesWasChanged(nodes);			
		}		
	}	
}
