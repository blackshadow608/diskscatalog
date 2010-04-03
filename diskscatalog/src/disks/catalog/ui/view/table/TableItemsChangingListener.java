package disks.catalog.ui.view.table;

import java.util.List;

import javax.swing.JTable;

import disks.catalog.logger.Logger;
import disks.catalog.model.tree.IDBTreeModelListener;
import disks.catalog.model.tree.types.ITreeNode;


public class TableItemsChangingListener implements IDBTreeModelListener {
	
	private CatalogTable table;
	
	public TableItemsChangingListener(CatalogTable table) {
		this.table = table;		
	}

	public void activeNodeWasChanged(ITreeNode node) {
		Logger.openStack();
		final ITreeNode activeNode = node;
		CustomTableModel tableModel = (CustomTableModel) table.getModel();
		if (activeNode != null) {
			Logger.logIntoStack("activeNode is not null");
			int index = tableModel.getIndexOf(activeNode);
			if (index >= 0 && index < tableModel.getSize()) {
				Logger.logIntoStack("select activeNode in list");
				table.setLastSelectedIndex(index);
				table.setSelectedIndex(index);				
				table.requestFocus();				
			} else {
				Logger.logIntoStack("clear selection");
				table.clearSelection();
			}
		} else {
			Logger.logIntoStack("activeNode is null");
			table.clearSelection();
			table.transferFocus();
		}
		Logger.closeStack();
	}
	
	public void activeNodeWasChanged(ITreeNode node, Object source) {
		if (source != table) {
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
		final CustomTableModel tableModel = (CustomTableModel) table.getModel();
		final List<ITreeNode> nodesList = nodes;
		if (nodesList != null) {
			boolean haveSelected = false;
			table.getSelectionModel().removeSelectionInterval(0, tableModel.getSize() - 1);
			for (ITreeNode node : nodesList) {
				int index = tableModel.getIndexOf(node);
				table.getSelectionModel().addSelectionInterval(index, index);
				if (index >= 0) {
					haveSelected = true;
				}
			}
			if (!haveSelected) {
				table.clearSelection();
			}
		} else {
			table.clearSelection();
			table.transferFocus();
		}
		Logger.closeStack();
	}
	
	public void activeNodesWasChanged(List<ITreeNode> nodes, Object source) {
		if (!(source instanceof JTable)) {
			activeNodesWasChanged(nodes);			
		}		
	}	
}
