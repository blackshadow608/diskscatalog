package al.catalog.model.tree.types;

import al.catalog.model.dao.DAOException;
import al.catalog.model.dao.DAOFactory;
import al.catalog.model.dao.IImgCategoryDAO;

public class RootNode extends AbstractTreeNode {
	
	private DAOFactory daoFactory;
	
	public RootNode(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;		
	}

	public boolean canHaveChildren() {
		return true;
	}
	
	public void refresh() {
		ITreeNode imagesRoot = null;
		if (hasChildren()) {
			getChildren().clear();				
		}			
		try {
			IImgCategoryDAO imgCategoryDAO = daoFactory.getImgCategoryDAO();													
			imagesRoot = imgCategoryDAO.getRoot();
		} catch (DAOException e) {
			e.printStackTrace();		
		}	
				
		
		if (imagesRoot != null) {
			addChild(imagesRoot);			
			imagesRoot.setParent(this);
			imagesRoot.setLogicalRoot(true);					
		}		
	}
	
	public int getPrioritet() {
		return 2;
	}
	
	public ITreeNode clone() {		
		return null;
	}
}
