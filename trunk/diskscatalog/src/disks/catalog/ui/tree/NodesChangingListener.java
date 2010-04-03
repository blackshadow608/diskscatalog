package disks.catalog.ui.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import disks.catalog.logger.Logger;
import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.IDBTreeModelListener;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.ui.util.TreePathUtil;


/**
 * Данный слушатель выполняет действия обратные ActiveTreeNodeListener - слушает
 * соответсвующий DBModel и как только в DBModel изменяется активный узел, то
 * сразу же отражает это изменение на соответствующий JTree. Кроме того, данный слушатель
 * используется при выполнении переименования узла дерева с дальнейшим упорядочением (сортировкой)
 * без сворачивания распахнутых узлов. Перед тем, как узел будет переименован выполняется метод onBeforeChangeNode.
 * Данный метод здесь запоминает все развернутые пути (paths). Далее происходит переименование узла,
 * и система автоматом сворачивает все узлы дочерние. После переименования узла выполняется метод
 * onAfterChange. Здесь происходит восстановление свернутых путей из запомненных ранее в методе onBeforeChange.
 * 
 * @author Alexander Levin
 * 
 */
public class NodesChangingListener implements IDBTreeModelListener {
	
	private JTree tree;
	private Enumeration<TreePath> paths;
	private TreePath selectedPath;
	private DBTreeModel dbModel; 
	
	public NodesChangingListener(DBTreeModel dbModel, JTree tree) {
		this.tree = tree;
		this.dbModel = dbModel;
	}
	
	public void onBeforeChangeNode(ITreeNode node) {
		Logger.openStack();
		if (node.hasParent()) {
			TreePath parentPath = TreePathUtil.getTreePathToRoot(node.getParent());
			paths = tree.getExpandedDescendants(parentPath);			
		}
		ITreeNode openedNode = dbModel.getOpenedNode();
		selectedPath = null;
		if (openedNode == node) {
			selectedPath = TreePathUtil.getTreePathToRoot(node);
		}
		Logger.closeStack();
	}

	public void onAfterChangeNode(ITreeNode node) {
		Logger.openStack();
		if (node.hasParent() && paths != null) {
			while(paths.hasMoreElements()) {
				TreePath path = paths.nextElement();
				tree.expandPath(path);
			}			
		}
		if (selectedPath != null) {
			tree.setSelectionPath(selectedPath);
		}
		Logger.closeStack();
	}

	public void openedNodeWasChanged(ITreeNode node) {
		Logger.openStack();
		if (node != null) {
			List<ITreeNode> pathList = new ArrayList<ITreeNode>();

			ITreeNode ancestor = node;
			while (ancestor != null) {
				pathList.add(0, ancestor);
				ancestor = ancestor.getParent();
			}
			
			TreePath path = new TreePath(pathList.toArray());
			
			tree.scrollPathToVisible(path);
			tree.setSelectionPath(path);			
		} else {
			tree.clearSelection();
		}
		Logger.closeStack();
	}
	
	public void openedNodeWasChanged(ITreeNode node, Object source) {
		if (source != tree) {
			openedNodeWasChanged(node);			
		}
	}
	
	public void activeNodeWasChanged(ITreeNode node) {
		
	}
	
	public void activeNodeWasChanged(ITreeNode node, Object source) {
		
	}

	public void nodeWasChanged(ITreeNode node) {
						
	}

	public void nodeWasInserted(ITreeNode node, ITreeNode parent) {
				
	}

	public void nodeWasRemoved(ITreeNode node, ITreeNode parent, int index) {
		
	}

	public void nodeStructureWasChanged(ITreeNode node) {
		
	}

	public void activeNodesWasChanged(List<ITreeNode> nodes) {
				
	}

	public void activeNodesWasChanged(List<ITreeNode> nodes, Object source) {
		
	}		
}
