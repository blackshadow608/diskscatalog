package al.catalog.model.tree.manager;

import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.ITreeNode;

public interface INodeManager {
	
	public ITreeNode createNode(ITreeNode treeNode, DBTreeModel dbModel);
	
	public boolean updateNode(ITreeNode treeNode);
	
	public boolean removeNode(ITreeNode treeNode);

}
