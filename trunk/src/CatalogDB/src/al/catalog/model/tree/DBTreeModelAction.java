package al.catalog.model.tree;

import java.util.List;

import al.catalog.model.DBAction;
import al.catalog.model.DBManager;
import al.catalog.model.tree.types.ITreeNode;

/**
 * Представляет собой DBAction для всех операций с деревьями. Любые action'ы, которые
 * выполняются с деревом зависят от того, какие узлы в данный момент активны - выбранны
 * пользователем в приложении, а какие открыты, потому что все действия происходят с
 * выбранными пользователем узлами, поэтому их знание необходимо. 
 * 
 * @author Alexander Levin
 */
public abstract class DBTreeModelAction extends DBAction {
	
	protected List<ITreeNode> activeNodes;
	protected List<ITreeNode> openedNodes;
	
	public DBTreeModelAction(DBManager dbManager) {
		super(dbManager);
	}
}
