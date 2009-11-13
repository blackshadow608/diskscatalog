package al.catalog.ui.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreePath;

import al.catalog.model.tree.types.ITreeNode;

public class TreePathUtil {
	
	public static Object[] getPathToRoot(ITreeNode node) {
		List<ITreeNode> path = new ArrayList<ITreeNode>();
		
		ITreeNode ancestor = node;
		while (ancestor != null) {
			path.add(0, ancestor);
			ancestor = ancestor.getParent();
		}
		
		return path.toArray();
	}
	
	public static TreePath getTreePathToRoot(ITreeNode node) {
		List<ITreeNode> path = new ArrayList<ITreeNode>();
		
		ITreeNode ancestor = node;
		while (ancestor != null) {
			path.add(0, ancestor);
			ancestor = ancestor.getParent();
		}
		
		return new TreePath(path.toArray());
	}
}
