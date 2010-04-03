package disks.catalog.model.tree.action;

import java.util.ArrayList;
import java.util.List;

import disks.catalog.model.DBAction;
import disks.catalog.model.DBManager;
import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.manager.INodeManager;
import disks.catalog.model.tree.manager.ManagerFactory;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.ui.resource.ResourceManager;


public class RemoveNodeAction extends DBAction {
	
	private DBTreeModel dbModel;
	private List<ITreeNode> treeNodes;
	private DBManager dbManager;
	private Thread thread;
	private DBAction action;
	
	private static final String PROGRESS_TEXT = "removeNodeAction.progressText";
	
	public RemoveNodeAction(DBTreeModel dbModel, List<ITreeNode> treeNodes) {
		super(dbModel.getDBManager());
		this.dbModel = dbModel;
		this.dbManager = dbModel.getDBManager();
		this.treeNodes = new ArrayList<ITreeNode>(treeNodes);
		this.action = this;
	}

	public void execute() {
		thread = new Thread() {

			private boolean isInterrupted = false;

			public void run() {
				savepoint = dbManager.setSavepoint();
				
				if (firstExecute) {
					dbManager.addAction(action);
				}
				
				for (ITreeNode treeNode : treeNodes) {
					ITreeNode parent = treeNode.getParent();
					List<ITreeNode> children = parent.getChildren();
					int index = children.indexOf(treeNode);
					parent.removeChild(treeNode);
					
					Class<?> clazz = treeNode.getClass();
					INodeManager nodeManager = ManagerFactory.getNodeManager(clazz);
					nodeManager.removeNode(treeNode, dbModel);
					
					dbModel.fireRemoveNode(treeNode, treeNode.getParent(), index);
				}
				
				if (!isInterrupted) {
					isExecuted = true;
					firstExecute = false;					
					dbManager.fireAfterAction(action);
				}				
			}

			public void interrupt() {
				super.interrupt();
				isInterrupted = true;
			}
		};

		thread.start();
		dbManager.fireBeforeAction(action);
	}

	public void unexecute() {
		dbManager.rollback(savepoint);
		
		for (ITreeNode treeNode : treeNodes) {
			ITreeNode parent = treeNode.getParent();	
			parent.addChild(treeNode);
			dbModel.fireInsertNode(treeNode, parent);
		}
		
		isExecuted = false;		
	}
	
	public String getProgressText() {
		return ResourceManager.getString(PROGRESS_TEXT);
	}

	public boolean isCancelable() {
		return true;
	}
	
	public boolean isPausable() {
		return false;
	}
}
