package al.catalog.model.tree.action;

import al.catalog.model.DBAction;
import al.catalog.model.dao.DAOException;
import al.catalog.model.dao.DAOFactory;
import al.catalog.model.dao.IImageDAO;
import al.catalog.model.dao.IImgCategoryDAO;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.ITreeNode;
import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.model.tree.types.images.ImgCategoryNode;
import al.catalog.ui.resource.ResourceManager;

public class UpdateNodeAction extends DBAction {
	
	private DBTreeModel dbModel;
	private ITreeNode treeNode;
	
	private ITreeNode oldTreeNode;
	private ITreeNode newTreeNode;
	
	private static final String PROGRESS_TEXT = "updateNodeAction.progressText";
	
	public UpdateNodeAction(DBTreeModel dbModel, ITreeNode treeNode) {
		super(dbModel.getDBManager());
		this.treeNode = treeNode;
		this.dbModel = dbModel;
	}
	
	public void execute() {
		dbModel.fireBeforeChangeNode(treeNode);
		
		savepoint = dbManager.setSavepoint();				
		
		if(firstExecute) {
			newTreeNode = treeNode.clone();
		}
		
		updateNode(treeNode);
		
		if (treeNode.hasParent()) {
			treeNode.getParent().sortChildren();
		}
		
		dbManager.addAction(this);
		
		dbModel.fireChangeNode(treeNode);
		
		isExecuted = true;
		
		dbModel.fireAfterChangeNode(treeNode);		
		
		firstExecute = false;
	}

	public void unexecute() {
		dbModel.fireBeforeChangeNode(treeNode);
		
		dbManager.rollback(savepoint);
		
		rollbackNode(treeNode);
		
		if (treeNode.hasParent()) {
			treeNode.getParent().sortChildren();
		}
		dbModel.fireChangeNode(treeNode);		
		isExecuted = false;	
	}
	
	private void rollbackNode(ITreeNode treeNode) {
		if (treeNode instanceof ImgCategoryNode) {
			rollbackImgCategory((ImgCategoryNode)treeNode);			
		} else if (treeNode instanceof ImageNode) {
			rollbackImage((ImageNode)treeNode);			
		}		
	}
	
	private void rollbackImgCategory(ImgCategoryNode treeNode) {
		ImgCategoryNode oldCategory = (ImgCategoryNode) oldTreeNode;
		treeNode.setName(oldCategory.getName());
		treeNode.setComments(oldCategory.getComments());
	}
	
	private void rollbackImage(ImageNode treeNode) {
		ImageNode oldImage = (ImageNode) oldTreeNode;
		treeNode.setName(oldImage.getName());
		treeNode.setComments(oldImage.getComments());
		treeNode.setType(oldImage.getType());
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
			
			if(firstExecute) {
				oldTreeNode = imgCategoryDAO.get(imgCategory.getId());				
			} else {
				ImgCategoryNode newImgCategory = (ImgCategoryNode) newTreeNode;
				imgCategory.setName(newImgCategory.getName());
				imgCategory.setComments(newImgCategory.getComments());				
			}
						
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
			
			if(firstExecute) {
				oldTreeNode = imageDAO.get(image.getId());				
			} else {
				ImageNode newImage = (ImageNode) newTreeNode;
				image.setName(newImage.getName());
				image.setComments(newImage.getComments());
				image.setType(newImage.getType());
			}
			
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
