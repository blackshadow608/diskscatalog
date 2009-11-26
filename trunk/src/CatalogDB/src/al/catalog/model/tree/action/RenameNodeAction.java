package al.catalog.model.tree.action;

import al.catalog.model.dao.DAOException;
import al.catalog.model.dao.DAOFactory;
import al.catalog.model.dao.IImageDAO;
import al.catalog.model.dao.IImgCategoryDAO;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.DBTreeModelAction;
import al.catalog.model.tree.types.ITreeNode;
import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.model.tree.types.images.ImgCategoryNode;
import al.catalog.ui.resource.ResourceManager;

public class RenameNodeAction extends DBTreeModelAction {
	
	private DBTreeModel dbModel;
	private ITreeNode treeNode;
	
	private String oldName;
	private String newName;
	
	private static final String PROGRESS_TEXT = "renameNodeAction.progressText";
	
	public RenameNodeAction(DBTreeModel dbModel, ITreeNode treeNode) {
		super(dbModel.getDBManager());
		this.dbModel = dbModel;
		this.treeNode = treeNode;
		this.dbManager = dbModel.getDBManager();
		this.newName = treeNode.getName();
	}

	public void execute() {
		dbModel.fireBeforeChangeNode(treeNode);
		
		savepoint = dbManager.setSavepoint();				
		
		treeNode.setName(newName);
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
		treeNode.setName(oldName);
		if (treeNode.hasParent()) {
			treeNode.getParent().sortChildren();
		}
		dbModel.fireChangeNode(treeNode);		
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
			oldName = imgCategoryDAO.get(imgCategory.getId()).getName();
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
			oldName = imageDAO.get(image.getId()).getName();
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
