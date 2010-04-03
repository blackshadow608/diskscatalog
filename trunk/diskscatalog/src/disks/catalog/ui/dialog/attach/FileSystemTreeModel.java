package disks.catalog.ui.dialog.attach;

import java.io.File;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.model.tree.types.file.FolderNode;


public class FileSystemTreeModel implements TreeModel {	
	
	private ITreeNode rootFolder;	

	public Object getChild(Object parent, int index) {		
		ITreeNode parentNode = (ITreeNode) parent;
		if (!parentNode.wasRefreshed()) {
			parentNode.refresh();					
		}
		List<ITreeNode> children = parentNode.getChildren();
		if (index < children.size()) {
			return children.get(index);
		}
		return null;
	}

	public int getChildCount(Object parent) {		
		if (parent != null) {
			ITreeNode treeNode = (ITreeNode) parent;
			if (parent != rootFolder) {
				if (!treeNode.wasRefreshed()) {
					treeNode.refresh();					
				}				
			}			
			List<ITreeNode> children =  treeNode.getChildren();
			if (children != null) {
				int count = 0;
				for (ITreeNode child : children) {
					if (child instanceof FolderNode) {
						count++;
					}
				}
				return count;
			}
		}
		return 0;
	}

	public int getIndexOfChild(Object parent, Object child) {
		ITreeNode parentNode = (ITreeNode) parent;
		if (!parentNode.wasRefreshed()) {
			parentNode.refresh();					
		}
		List<ITreeNode> children = parentNode.getChildren();
		if (children.contains(child)) {
			return children.indexOf(child);
		}
		return 0;
	}

	public Object getRoot() {
		rootFolder = new FolderNode("My computer");
		File[] roots = File.listRoots();
		for (File file : roots) {
			ITreeNode treeNode = new FolderNode(file);
			treeNode.setParent(rootFolder);			
			rootFolder.addChild(treeNode);
		}
		return rootFolder;
	}

	public boolean isLeaf(Object node) {		
		if (node != null) {
			ITreeNode treeNode = (ITreeNode) node;
			if (treeNode != rootFolder) {				
				if (treeNode instanceof FolderNode) {
					return false;
				}
			}
			if (treeNode.hasChildren()) {
				return false;
			}
		}		
		return true;
	}
	
	public void addTreeModelListener(TreeModelListener l) {
				
	}

	public void removeTreeModelListener(TreeModelListener l) {
		
	}

	public void valueForPathChanged(TreePath path, Object newValue) {
					
	}
}
