package al.catalog.model.tree.manager;

import al.catalog.model.dao.DAOException;
import al.catalog.model.dao.DAOFactory;
import al.catalog.model.dao.IImageDAO;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.ITreeNode;
import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.model.tree.types.images.ImgCategoryNode;
import al.catalog.ui.resource.ResourceManager;

public class ImageManager implements INodeManager {

	private static final String NEW_IMAGE = "createNodeAction.newImage";

	public ITreeNode createNode(ITreeNode treeNode, DBTreeModel dbModel) {
		ITreeNode parentNode = null;

		if (treeNode != null) {
			parentNode = treeNode.getParent();
		} else {
			parentNode = dbModel.getOpenedNode();
		}

		if (parentNode != null && parentNode instanceof ImgCategoryNode) {
			ImgCategoryNode parent = (ImgCategoryNode) parentNode;
			ImageNode image = null;

			String newImage = ResourceManager.getString(NEW_IMAGE);

			if (treeNode == null) {
				image = new ImageNode();
				String name = newImage;

				for (int i = 1; i < Integer.MAX_VALUE; i++) {
					if (parent.hasChildWithName(name)) {
						name = newImage + " " + i;
					} else {
						break;
					}
				}

				image.setName(name);
				image.setParent(parent);
				image.setParentId(parent.getId());
				image.setFirstRefresh(true);
			} else {
				image = (ImageNode) treeNode;
			}

			try {
				DAOFactory daoFactory = dbModel.getDAOFactory();
				IImageDAO imageDAO = daoFactory.getImageDAO();
				imageDAO.add(image);
			} catch (DAOException e) {
				e.printStackTrace();
			}

			parent.addChild(image);

			return image;
		} else {
			return null;
		}
	}

	public boolean removeNode(ITreeNode treeNode) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateNode(ITreeNode treeNode) {
		// TODO Auto-generated method stub
		return false;
	}

}