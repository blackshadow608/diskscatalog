package al.catalog.model.tree.types.images;

import java.util.ArrayList;
import java.util.List;

import al.catalog.model.dao.DAOException;
import al.catalog.model.dao.DAOFactory;
import al.catalog.model.dao.IImageDAO;
import al.catalog.model.dao.IImgCategoryDAO;
import al.catalog.model.tree.types.AbstractTreeNode;
import al.catalog.model.tree.types.ITreeNode;

public class ImgCategoryNode extends AbstractTreeNode {
	
	private DAOFactory daoFactory;
	
	protected int prioritet = 1;
	
	public ImgCategoryNode() {
		
	}
	
	public ImgCategoryNode(Integer id, String name, Integer parentId, DAOFactory daoFactory) {
		super(id, name, parentId);
		this.daoFactory = daoFactory;
	}

	public boolean canHaveChildren() {
		return true;
	}
	
	public void refresh() {		
		IImgCategoryDAO imgCategoryDAO = daoFactory.getImgCategoryDAO();
		IImageDAO imageDAO = daoFactory.getImageDAO();
		List<ITreeNode> children = new ArrayList<ITreeNode>();
		if (hasChildren()) {
			getChildren().clear();					
		}
		try {
			children.addAll(imgCategoryDAO.getChildren(this));
			children.addAll(imageDAO.getChildren(this));
			setChildren(children);
			sortChildren();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		setFirstRefresh(true);
	}
	
	public int getPrioritet() {
		return 1;
	}

	public ITreeNode clone() {
		ImgCategoryNode clone = new ImgCategoryNode();
		clone.setId(id);
		clone.setComments(comments);
		clone.setName(name);		
		return clone;
	}
}
