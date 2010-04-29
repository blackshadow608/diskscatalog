package disks.catalog.ui.tree;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import disks.catalog.logger.Logger;
import disks.catalog.model.connection.IConnectionListener;
import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.IDBTreeModelListener;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.model.tree.types.images.ImgCategoryNode;
import disks.catalog.ui.util.TreePathUtil;


import java.util.List;
import java.util.ArrayList;

public class CustomTreeModel implements TreeModel, IDBTreeModelListener, IConnectionListener {
	
	public static final int MODEL_CATALOG = 0;
	public static final int MODEL_IMAGES = 1;
		
	private List<TreeModelListener> listeners = new ArrayList<TreeModelListener>();
	private DBTreeModel dbModel;
	private ITreeNode root;
	
	private int state = MODEL_IMAGES;
	
	public CustomTreeModel(DBTreeModel dbModel) {
		this.dbModel = dbModel;
		this.dbModel.addListener(this);
	}
	
	public void setState(int state) {
		this.state = state;		
		nodeWasChanged(root);
	}
	
	public int getState() {
		return state;
	}

	public Object getChild(Object treeNodeObj, int index) {
		ITreeNode treeNode = (ITreeNode) treeNodeObj;
		if (!treeNode.wasRefreshed()) {
			refreshNode(treeNode);
		}
		List<ITreeNode> children = treeNode.getChildren();
		if (treeNode == root) {			
			for (ITreeNode child : children) {
				if (child instanceof ImgCategoryNode && state == MODEL_IMAGES) {
					return child;					
				}				
			}			
		}
		return children.get(index);
	}

	public int getChildCount(Object treeNodeObj) {
		ITreeNode treeNode = (ITreeNode) treeNodeObj;
		refreshNode(treeNode);		
		List<ITreeNode> children = treeNode.getChildren();
		if (treeNode == root) {
			return 1;
		}
		if (children != null) {
			return children.size();			
		}		
		return 0;
	}

	public int getIndexOfChild(Object treeNodeObj, Object childObj) {
		ITreeNode treeNode = (ITreeNode) treeNodeObj;
		treeNode.refresh();
		List<ITreeNode> children = treeNode.getChildren();
		return children.indexOf(childObj);
	}

	public Object getRoot() {
		root = dbModel.getRoot(); 
		return root;
	}

	public boolean isLeaf(Object treeNodeObj) {
		ITreeNode treeNode = (ITreeNode) treeNodeObj;
		refreshNode(treeNode);
		
		if (treeNode.hasChildren()) {
			return false;			
		}
		return true;		
	}
	
	public void addTreeModelListener(TreeModelListener listener) {
		listeners.add(listener);		
	}

	public void removeTreeModelListener(TreeModelListener listener) {
		listeners.remove(listener);		
	}

	public void valueForPathChanged(TreePath path, Object obj) {
			
	}
	
	public DBTreeModel getDBModel() {
		return dbModel;
	}
	
	public void setDBModel(DBTreeModel dbModel) {
		this.dbModel = dbModel;
	}
	
	public void refreshNode(ITreeNode node) {		
		if (!node.wasRefreshed()) {
			node.refresh();
		}		
	}
	
	public void nodeWasInserted(ITreeNode newChild, ITreeNode parent) {
		Logger.openStack();
		final ITreeNode parentNode = parent;
		final ITreeNode newChildNode = newChild;

		List<ITreeNode> children = parentNode.getChildren();

		int[] childIndexes = new int[1];
		childIndexes[0] = children.indexOf(newChildNode);
		Object[] path = TreePathUtil.getPathToRoot(parentNode);

		TreeModelEvent modelEvent = new TreeModelEvent(this, path, childIndexes, children.toArray());
		for (TreeModelListener listener : listeners) {
			listener.treeNodesInserted(modelEvent);
		}

		Logger.closeStack();
	}

	public void nodeWasRemoved(ITreeNode child, ITreeNode parent, int index) {
		Logger.openStack();
		final int removedIndex = index;
		final ITreeNode childNode = child;
		final ITreeNode parentNode = parent;
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				int[] childIndexes = new int[1];
				childIndexes[0] = removedIndex;
				
				Object[] removedArray = new Object[1];
				Object[] path = TreePathUtil.getPathToRoot(parentNode);
				removedArray[0] = childNode;
				
				TreeModelEvent modelEvent = new TreeModelEvent(this, path, childIndexes, removedArray);
				for (TreeModelListener listener : listeners) {
					listener.treeNodesRemoved(modelEvent);
				}				
			}
		});
		Logger.closeStack();
	}
	
	public void nodeWasChanged(ITreeNode node) {
		Logger.openStack();
		if (node != null) {
			if (node.isLogicalRoot()) {
				Object[] path = TreePathUtil.getPathToRoot(node);
				TreeModelEvent modelEvent = new TreeModelEvent(this, path);
				for (TreeModelListener listener : listeners) {
					listener.treeNodesChanged(modelEvent);
				}			
			} else if (!node.hasParent()) {
				Object[] path = TreePathUtil.getPathToRoot(node);
				TreeModelEvent modelEvent = new TreeModelEvent(this, path, null, null);
				for (TreeModelListener listener : listeners) {
					listener.treeStructureChanged(modelEvent);
				}			
			} else {
				List<ITreeNode> children = node.getParent().getChildren();
				int[] childIndexes = new int[children.size()];
				Object[] path = TreePathUtil.getPathToRoot(node.getParent());
				for (int i = 0; i < children.size(); i++) {
					childIndexes[i] = children.indexOf(children.get(i)); 
				}
				TreeModelEvent modelEvent = new TreeModelEvent(this, path, childIndexes, children.toArray());
				for (TreeModelListener listener : listeners) {
					listener.treeStructureChanged(modelEvent);
				}					
			}			
		}
		Logger.closeStack();
	}
	
	public void connectionWasClosed() {
		if (root != null) {
			if (root.hasChildren()) {
				root.getChildren().clear();				
			}									
		}
		nodeWasChanged(root);
	}

	public void connectionWasOpened() {		
		root.refresh();
		nodeWasChanged(root);
	}

	public void nodeStructureWasChanged(ITreeNode node) {
		Logger.openStack();
		List<ITreeNode> children = node.getChildren();
		Object[] path = TreePathUtil.getPathToRoot(node);
		int size = 0;
		Object[] childrenArray = null;
		if (children != null) {
			size = children.size();
			childrenArray = children.toArray();
		}
		int[] childIndexes = new int[size];
		for (int i = 0; i < size; i++) {
			childIndexes[i] = children.indexOf(children.get(i)); 
		}
		TreeModelEvent modelEvent = new TreeModelEvent(this, path, null, childrenArray);
		for (TreeModelListener listener : listeners) {
			listener.treeStructureChanged(modelEvent);
		}
		Logger.closeStack();
	}
	
	
	public void activeNodeWasChanged(ITreeNode node) {
		
	}
	
	public void activeNodeWasChanged(ITreeNode node, Object source) {
		
	}

	public void onAfterChangeNode(ITreeNode node) {
				
	}

	public void onBeforeChangeNode(ITreeNode node) {		
		
	}

	public void openedNodeWasChanged(ITreeNode node, Object source) {
		
	}
	
	public void openedNodeWasChanged(ITreeNode node) {
		
	}

	public void activeNodesWasChanged(List<ITreeNode> nodes) {
		
	}

	public void activeNodesWasChanged(List<ITreeNode> nodes, Object source) {
		
	}	
}
