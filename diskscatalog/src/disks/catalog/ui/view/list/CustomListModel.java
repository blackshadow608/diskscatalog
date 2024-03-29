package disks.catalog.ui.view.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import disks.catalog.logger.Logger;
import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.IDBTreeModelListener;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.ui.view.list.event.CustomListDataEvent;


public class CustomListModel implements ListModel, IDBTreeModelListener {
	
	private List<ListDataListener> listeners = new ArrayList<ListDataListener>();
	private ITreeNode openedNode;
	private ITreeNode activeNode;
	
	public CustomListModel(DBTreeModel dbModel) {
		dbModel.addListener(this);		
	}

	public void addListDataListener(ListDataListener listener) {
		listeners.add(listener);		
	}

	public Object getElementAt(int index) {
		if(openedNode != null) {
			List<ITreeNode> children = openedNode.getChildren();
			if (children != null && index < children.size()) {
				return children.get(index);						
			}			
		}		
		return null;
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
	
	public List<ITreeNode> getElements() {
		if (openedNode != null) {
			return openedNode.getChildren();						
		}		
		return null;		
	}
	
	public ITreeNode getActiveNode() {
		return activeNode;
	}
	
	public ITreeNode getOpenedNode() {
		return openedNode;
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

	public void removeListDataListener(ListDataListener listener) {
		listeners.remove(listener);		
	}
	
	public void nodeWasChanged(ITreeNode node) {
		Logger.openStack();
		ITreeNode parent = node.getParent();
		List<ITreeNode> children = parent.getChildren();
		int index = children.indexOf(node);
		ListDataEvent event = new CustomListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index, index, node);
		for (ListDataListener listener : listeners) {
			listener.contentsChanged(event);
		}
		Logger.closeStack();
	}

	public void nodeWasInserted(ITreeNode node, ITreeNode parent) {
		Logger.openStack();
		if (openedNode == parent) {
			List<ITreeNode> children = parent.getChildren();
			final int index = children.indexOf(node);
			final ITreeNode treeNode = node;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Logger.openStack();
					Logger.logIntoStack("CustomListModel: nodeWasInserted() - SwingUtilities.invokeLater");
					ListDataEvent event = new CustomListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index, treeNode);
					for (ListDataListener listener : listeners) {						
						listener.intervalAdded(event);
					}					
					Logger.closeStack();
				}
			});
		}
		Logger.closeStack();
	}

	public void nodeWasRemoved(ITreeNode node, ITreeNode parent, int index) {
		Logger.openStack();		
		if(openedNode == parent) {
			final int removedIndex = index;
			final ITreeNode treeNode = node;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Logger.openStack();
					Logger.logIntoStack("CustomListModel: nodeWasRemoved() - SwingUtilities.invokeLater");
					ListDataEvent event = new CustomListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, removedIndex, removedIndex, treeNode);
					for (ListDataListener listener : listeners) {
						listener.intervalRemoved(event);
					}
					Logger.closeStack();
				}
			});			
		}
		Logger.closeStack();
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

	public void onAfterChangeNode(ITreeNode node) {
		
	}

	public void onBeforeChangeNode(ITreeNode node) {
		
	}

	public void openedNodeWasChanged(ITreeNode node) {
		Logger.openStack();
		openedNode = node;
		ListDataEvent listEvent = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, 0);		
		for (ListDataListener listener : listeners) {
			listener.contentsChanged(listEvent);
		}
		Logger.closeStack();
	}
		
	public void openedNodeWasChanged(ITreeNode node, Object source) {
		openedNodeWasChanged(node);						
	}

	public void nodeStructureWasChanged(ITreeNode node) {
		
	}			
}
