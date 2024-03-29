package disks.catalog.model.tree.action;

import java.util.ArrayList;
import java.util.List;

import disks.catalog.model.DBManager;
import disks.catalog.model.action.DBAction;
import disks.catalog.model.dao.DAOException;
import disks.catalog.model.dao.DAOFactory;
import disks.catalog.model.dao.IImageDAO;
import disks.catalog.model.dao.IImgCategoryDAO;
import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.model.tree.types.images.ImageNode;
import disks.catalog.model.tree.types.images.ImgCategoryNode;
import disks.catalog.ui.resource.ResourceManager;


public class MoveNodeAction extends DBAction {
	
	private DBTreeModel dbModel;
	private List<ITreeNode> treeNodes;
	private ITreeNode oldParent;
	private ITreeNode newParent;
	private DBManager dbManager;
	
	private static final String PROGRESS_TEXT = "moveNodeAction.progressText";
	
	public MoveNodeAction(DBTreeModel dbModel, List<ITreeNode> treeNodes, ITreeNode newParent) {
		super(dbModel.getDBManager());
		this.dbModel = dbModel;		
		if (treeNodes != null && treeNodes.size() > 0) {
			this.treeNodes = new ArrayList<ITreeNode>(treeNodes);
			this.oldParent = treeNodes.get(0).getParent();			
		}		
		this.newParent =  newParent;
		this.dbManager = dbModel.getDBManager();
	}

	public void execute() {
		savepoint = dbManager.setSavepoint();
		
		if (firstExecute) {
			dbManager.addAction(this);
		}
		
		if (treeNodes != null) {
			for (ITreeNode treeNode : treeNodes) {
				int index = oldParent.getChildren().indexOf(treeNode);		
				treeNode.setParent(newParent);
				newParent.addChild(treeNode);
				newParent.sortChildren();
				oldParent.removeChild(treeNode);		
				updateNode(treeNode);		
				dbManager.addAction(this);		
				dbModel.fireRemoveNode(treeNode, oldParent, index);
				dbModel.fireInsertNode(treeNode, newParent);				
			}						
		}		
		
		if (firstExecute) {
			dbModel.fireChangeOpenedNode(newParent);						
		}				
		
		isExecuted = true;
		firstExecute = false;
	}

	public void unexecute() {
		dbManager.rollback(savepoint);
		
		if (treeNodes != null) {
			for (ITreeNode treeNode : treeNodes) {
				int index = newParent.getChildren().indexOf(treeNode);		
				treeNode.setParent(oldParent);
				oldParent.addChild(treeNode);
				oldParent.sortChildren();
				newParent.removeChild(treeNode);
				
				dbModel.fireInsertNode(treeNode, oldParent);
				dbModel.fireRemoveNode(treeNode, newParent, index);				
			}						
		}
				
		isExecuted = false;		
	}
	
	private void updateNode(ITreeNode treeNode) {
		if (treeNode instanceof ImgCategoryNode) {
			updateImgCategory(treeNode);			
		} else if (treeNode instanceof ImageNode) {
			updateImage(treeNode);			
		}
	}
	
	private void updateImgCategory(ITreeNode node) {
		ImgCategoryNode imgCategory = (ImgCategoryNode) node;
		try {
			DAOFactory daoFactory = dbModel.getDAOFactory();
			IImgCategoryDAO imgCategoryDAO = daoFactory.getImgCategoryDAO();			
			imgCategoryDAO.update(imgCategory);
		} catch (DAOException e) {
			e.printStackTrace();			
		}						
	}
	
	private void updateImage(ITreeNode node) {
		ImageNode image = (ImageNode) node;
		try {
			DAOFactory daoFactory = dbModel.getDAOFactory();
			IImageDAO imageDAO = daoFactory.getImageDAO();
			imageDAO.update(image);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	public String getProgressText() {
		return ResourceManager.getString(PROGRESS_TEXT);
	}

	public boolean isCancelable() {
		return false;
	}
	
	public boolean isPausable() {
		return false;
	}
}
