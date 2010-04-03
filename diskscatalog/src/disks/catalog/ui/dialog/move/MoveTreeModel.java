package disks.catalog.ui.dialog.move;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.model.tree.types.images.ImgCategoryNode;


public class MoveTreeModel implements TreeModel {
	
	private List<TreeModelListener> listeners = new ArrayList<TreeModelListener>();
	private DBTreeModel dbModel;
	private ITreeNode root;
	
	public MoveTreeModel(DBTreeModel dbModel) {
		this.dbModel = dbModel;		
	}

	public void addTreeModelListener(TreeModelListener listener) {
		listeners.add(listener);		
	}

	public Object getChild(Object treeNodeObj, int index) {
		ITreeNode treeNode = (ITreeNode) treeNodeObj;
		if (!treeNode.wasRefreshed()) {
			refreshNode(treeNode);			
		}
		List<ITreeNode> children = treeNode.getChildren();		
		if (treeNode == root) {			
			for (ITreeNode child : children) {
				return child;				
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
		
		int count = 0;
		if (treeNode instanceof ImgCategoryNode) {
			for (ITreeNode child : children) {
				if (child instanceof ImgCategoryNode) {
					count++;
				}
			}			
		}
		return count;
	}

	public int getIndexOfChild(Object treeNodeObj, Object child) {
		ITreeNode treeNode = (ITreeNode) treeNodeObj;
		refreshNode(treeNode);
		
		List<ITreeNode> children = treeNode.getChildren();
		int index = 0;
		if (treeNode instanceof ImgCategoryNode) {
			for (ITreeNode childNode : children) {
				if (child instanceof ImgCategoryNode) {
					index++;
					if (childNode == child) {
						return index;
					}
				}
			}			
		}		
		return -1;
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

	public void removeTreeModelListener(TreeModelListener listener) {
		listeners.remove(listener);		
	}

	public void valueForPathChanged(TreePath path, Object newValue) {
				
	}
	
	public void refreshNode(ITreeNode node) {		
		if (!node.wasRefreshed()) {
			node.refresh();
		}		
	}
}
