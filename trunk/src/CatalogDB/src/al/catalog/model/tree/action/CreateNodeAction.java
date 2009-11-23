package al.catalog.model.tree.action;

import java.util.List;

import al.catalog.model.DBAction;
import al.catalog.model.IActionCallback;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.manager.INodeManager;
import al.catalog.model.tree.manager.ManagerFactory;
import al.catalog.model.tree.types.ITreeNode;
import al.catalog.ui.resource.ResourceManager;

/**
 * Action, который создает новый узел.
 * 
 * @author Alexander Levin
 */
public class CreateNodeAction extends DBAction {
	
	private static final String PROGRESS_TEXT = "createNodeAction.progressText";
	
	private IActionCallback callback;
	
	private DBTreeModel dbModel;
	private ITreeNode treeNode;
	private Class<?> className;
	
	public CreateNodeAction(DBTreeModel dbModel, Class<?> className) {
		super(dbModel.getDBManager());
		this.dbModel = dbModel;		
		this.className = className;
	}
	
	public CreateNodeAction(DBTreeModel dbModel, Class<?> className, IActionCallback callback) {
		this(dbModel, className);
		this.callback = callback;
	}

	public void execute() {
		INodeManager nodeManager = ManagerFactory.getNodeManager(className);
		savepoint = dbManager.setSavepoint();
		
		treeNode = nodeManager.createNode(treeNode, dbModel);
		
		if (firstExecute) {
			dbManager.addAction(this);
		}
		
		dbModel.fireInsertNode(treeNode, treeNode.getParent());
		
		if(callback != null) {
			callback.onSuccess(treeNode, firstExecute);
		}
				
		firstExecute = false;
		isExecuted = true;
	}
	
	public void unexecute() {
		dbManager.rollback(savepoint);
		
		ITreeNode parent = treeNode.getParent();
		List<ITreeNode> children = parent.getChildren();		
		int index = 0;
		
		for (int i = 0; i < children.size(); i++) {
			if (treeNode == children.get(i)) {
				index = i;
				break;
			}
		}
				
		parent.removeChild(treeNode);
		dbModel.fireRemoveNode(treeNode, parent, index);
		
		isExecuted = false;
	}

	public void abort() {
		
	}
	
	public String getProgressText() {
		return ResourceManager.getString(PROGRESS_TEXT);
	}

	public boolean isCancelable() {
		return false;
	}
}
