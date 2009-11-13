package al.catalog.model.tree.types.images;

import java.util.ArrayList;
import java.util.List;

import al.catalog.model.dao.DAOException;
import al.catalog.model.dao.DAOFactory;
import al.catalog.model.dao.IFileDAO;
import al.catalog.model.dao.IFolderDAO;
import al.catalog.model.tree.types.AbstractTreeNode;
import al.catalog.model.tree.types.ITreeNode;

public class ImageNode extends AbstractTreeNode {
	
	private DAOFactory daoFactory;
	
	private int type;
	
	private static ImageType[] types = {
		new ImageType(0, "imageType.folder"),
		new ImageType(1, "imageType.cd"),
		new ImageType(2, "imageType.dvd")
	};
	
	public ImageNode() {
		
	}
	
	public ImageNode(Integer id, String name, Integer parentId, DAOFactory daoFactory) {
		super(id, name, parentId);
		this.daoFactory = daoFactory;
	}

	public boolean canHaveChildren() {		
		return true;
	}
	
	public void refresh() {
		IFolderDAO folderDAO = daoFactory.getFolderDAO();
		IFileDAO fileDAO = daoFactory.getFileDAO();
		List<ITreeNode> children = new ArrayList<ITreeNode>();
		if (hasChildren()) {
			getChildren().clear();					
		}
		try {
			children.addAll(folderDAO.getChildren(this));
			children.addAll(fileDAO.getChildren(this));
			setChildren(children);
			sortChildren();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		setFirstRefresh(true);		
	}
	
	public void setDAOFactory(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public int getPrioritet() {
		return 0;
	}
	
	public static ImageType[] getTypes() {
		return types;		
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
	public ITreeNode clone() {
		ImageNode clone = new ImageNode();
		clone.setId(id);
		clone.setComments(comments);
		clone.setName(name);
		clone.setType(type);
		return clone;
	}
}
