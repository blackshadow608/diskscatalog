package al.catalog.model.tree.manager;

import al.catalog.model.dao.DAOException;
import al.catalog.model.dao.DAOFactory;
import al.catalog.model.dao.IImgCategoryDAO;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.ITreeNode;
import al.catalog.model.tree.types.images.ImgCategoryNode;
import al.catalog.ui.resource.ResourceManager;

public class ImgCategoryManager implements INodeManager {
	
	private static final String NEW_CATEGORY = "createNodeAction.newCategory";

	public ITreeNode createNode(ITreeNode treeNode, DBTreeModel dbModel) {
		ITreeNode parentNode = null;

		if (treeNode == null) {
			parentNode = dbModel.getOpenedNode();
		} else {
			parentNode = treeNode.getParent();
		}

		if (parentNode != null && parentNode instanceof ImgCategoryNode) {
			ImgCategoryNode parent = (ImgCategoryNode) parentNode;
			ImgCategoryNode imgCategory = null;

			String newCategory = ResourceManager.getString(NEW_CATEGORY);

			if (treeNode == null) {
				imgCategory = new ImgCategoryNode();
				String name = newCategory;

				for (int i = 1; i < Integer.MAX_VALUE; i++) {
					if (parent.hasChildWithName(name)) {
						name = newCategory + " " + i;
					} else {
						break;
					}
				}

				imgCategory.setName(name);
				imgCategory.setParent(parent);
				imgCategory.setParentId(parent.getId());
				imgCategory.setFirstRefresh(true);
			} else {
				imgCategory = (ImgCategoryNode) treeNode;
			}

			try {
				DAOFactory daoFactory = dbModel.getDAOFactory();
				IImgCategoryDAO imgCategoryDAO = daoFactory.getImgCategoryDAO();
				imgCategoryDAO.add(imgCategory);
			} catch (DAOException e) {
				e.printStackTrace();
			}

			parent.addChild(imgCategory);

			return imgCategory;
		} else {
			return null;
		}
	}

	public boolean removeNode(ITreeNode treeNode, DBTreeModel dbModel) {
		ImgCategoryNode imgCategory = (ImgCategoryNode) treeNode;
		try {
			DAOFactory daoFactory = dbModel.getDAOFactory();
			IImgCategoryDAO imgCategoryDAO = daoFactory.getImgCategoryDAO();
			imgCategoryDAO.remove(imgCategory);
		} catch (DAOException e) {
			e.printStackTrace();			
		}
		return false;
	}

	public boolean updateNode(ITreeNode treeNode, DBTreeModel dbModel) {
		// TODO Auto-generated method stub
		return false;
	}

}
