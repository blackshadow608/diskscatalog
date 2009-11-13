package al.catalog.ui.view.table;

import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import al.catalog.logger.Logger;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.IDBTreeModelListener;
import al.catalog.model.tree.types.ITreeNode;

/**
 * Класс <b>CustomTableModel</b> - модель таблицы.
 * 
 * @author Alexander Levin
 */
public class CustomTableModel extends DefaultTableModel implements IDBTreeModelListener {
	
	public static int TREE_NODE_COLUMN_INDEX = -1;
	
	private ITreeNode openedNode;
	private ITreeNode activeNode;
	private DBTreeModel dbModel;
	
	public CustomTableModel(DBTreeModel dbModel) {
		this.dbModel = dbModel;
		dbModel.addListener(this);
	}	

	public int getRowCount() {
		if(openedNode != null && openedNode.getChildren() != null) {
			return openedNode.getChildren().size();
		}
		return 0;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		/**
		 * Если индекс строки равен TREE_NODE_COLUMN_INDEX, то возвращаем объект ITreeNode. 
		 */
		if(columnIndex == TREE_NODE_COLUMN_INDEX) {
			return openedNode.getChildren().get(rowIndex);
		}
		return openedNode.getChildren().get(rowIndex);
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
				
	}
	
	public ITreeNode getActiveNode() {
		return activeNode;
	}
	
	public ITreeNode getOpenedNode() {
		return openedNode;
	}

	public void activeNodeWasChanged(ITreeNode node) {
		activeNode = node;		
	}

	public void activeNodeWasChanged(ITreeNode node, Object source) {
		activeNode = node;		
	}

	public void activeNodesWasChanged(List<ITreeNode> nodes) {
		if (nodes != null && nodes.size() == 1) {
			activeNode = nodes.get(0);
		} else {
			activeNode = null;
		}		
	}

	public void activeNodesWasChanged(List<ITreeNode> nodes, Object source) {
		activeNodesWasChanged(nodes);		
	}

	public void nodeStructureWasChanged(ITreeNode node) {
		
	}

	public void nodeWasChanged(ITreeNode node) {		
		Logger.openStack("CustomTableModel: nodeWasChanged()");
		ITreeNode parent = node.getParent();
		List<ITreeNode> children = parent.getChildren();
		int index = children.indexOf(node);
		
		CustomTableModelEvent event = new CustomTableModelEvent(this, index, index,
                TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE, node);
		
		fireTableChanged(event);
		
		Logger.closeStack();		
	}

	public void nodeWasInserted(ITreeNode node, ITreeNode parent) {
		Logger.openStack("CustomTabletModel: nodeWasInserted()");
		if (openedNode == parent) {
			List<ITreeNode> children = parent.getChildren();
			final int index = children.indexOf(node);
			final ITreeNode treeNode = node;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Logger.openStack("CustomTableModel: nodeWasInserted() - SwingUtilities.invokeLater");
					List<ITreeNode> activeNodes = dbModel.getActiveNodes();
					
					CustomTableModelEvent event = new CustomTableModelEvent(CustomTableModel.this, index, index,
			                TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT, treeNode);
					
					fireTableChanged(event);
					
					dbModel.setActiveNodes(activeNodes);
					dbModel.fireChangeActiveNodes(activeNodes);
					Logger.closeStack();
				}
			});
		}
		Logger.closeStack();
	}

	public void nodeWasRemoved(ITreeNode node, ITreeNode parent, int index) {
		Logger.openStack("CustomTableModel: nodeWasRemoved()");		
		if(openedNode == parent) {
			final int removedIndex = index;
			final ITreeNode treeNode = node;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Logger.openStack("CustomTableModel: nodeWasRemoved() - SwingUtilities.invokeLater");
					
					CustomTableModelEvent event = new CustomTableModelEvent(CustomTableModel.this, removedIndex, removedIndex,
			                TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE, treeNode);
					
					fireTableChanged(event);
					
					Logger.closeStack();
				}
			});			
		}
		Logger.closeStack();		
	}

	public void onAfterChangeNode(ITreeNode node) {
		
	}

	public void onBeforeChangeNode(ITreeNode node) {
		
	}

	public void openedNodeWasChanged(ITreeNode node) {
		openedNodeWasChanged(node, null);
	}

	public void openedNodeWasChanged(ITreeNode node, Object source) {
		openedNode = node;		
		this.fireTableDataChanged();
	}
	
	public int getIndexOf(ITreeNode node) {
		if (this.openedNode != null) {
			List<ITreeNode> children = this.openedNode.getChildren();
			if (children != null) {
				return children.indexOf(node);
			}			
		}		
		return -1;		
	}
	
	public int getSize() {
		if (openedNode != null) {
			List<ITreeNode> children = openedNode.getChildren();
			if (children != null) {
				return children.size();
			}			
		}		
		return 0;
	}
}
