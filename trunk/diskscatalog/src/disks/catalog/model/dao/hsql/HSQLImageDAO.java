package disks.catalog.model.dao.hsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import disks.catalog.model.IConnectionProvider;
import disks.catalog.model.dao.DAOException;
import disks.catalog.model.dao.DAOFactory;
import disks.catalog.model.dao.IFileDAO;
import disks.catalog.model.dao.IFolderDAO;
import disks.catalog.model.dao.IImageDAO;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.model.tree.types.file.FileNode;
import disks.catalog.model.tree.types.file.FolderNode;
import disks.catalog.model.tree.types.images.ImageNode;
import disks.catalog.model.tree.types.images.ImgCategoryNode;


public class HSQLImageDAO implements IImageDAO {
	
	private DAOFactory daoFactory;
	private IConnectionProvider conProvider;
	
	public HSQLImageDAO(HSQLDAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		this.conProvider = daoFactory.getConnectionProvider();
	}

	public ImageNode add(ImageNode image) throws DAOException {
		Connection connection = conProvider.getConnection();		
		int id = HSQLDAOFactory.getUniqueId(connection, "IMAGES");
		image.setId(id);
		image.setDAOFactory(daoFactory);
		
		String query = "INSERT INTO IMAGES (ID, NAME, PARENT, IMAGE_TYPE, COMMENTS) VALUES (" +
			image.getId() + ", '" +
			image.getName() + "', " +
			image.getParentId() + ", " +
			image.getType() + ", '" +
			image.getComments() + "')";
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return image;
	}

	public ImageNode get(int imageId) throws DAOException {
		Connection connection = conProvider.getConnection();
		String query = "SELECT ID, NAME, PARENT, IMAGE_TYPE, COMMENTS FROM IMAGES WHERE ID = " + imageId;
		ImageNode image = null;
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			if(resultSet.next()) {
				Integer id = resultSet.getInt("ID");
				String name = resultSet.getString("NAME");
				Integer parent = resultSet.getInt("PARENT");
				Integer type = resultSet.getInt("IMAGE_TYPE");
				String comments = resultSet.getString("COMMENTS");
				
				image = new ImageNode(id, name, parent, daoFactory);
				image.setType(type);
				image.setComments(comments);
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return image;
	}

	public List<ImageNode> getAll() throws DAOException {
		List<ImageNode> images = new ArrayList<ImageNode>();
		Connection connection = conProvider.getConnection();
		String query = "SELECT ID, NAME, PARENT, IMAGE_TYPE, COMMENTS FROM IMAGES";
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				Integer id = resultSet.getInt("ID");
				String name = resultSet.getString("NAME");
				Integer parent = resultSet.getInt("PARENT");
				Integer type = resultSet.getInt("IMAGE_TYPE");
				String comments = resultSet.getString("COMMENTS");
				
				ImageNode image = new ImageNode(id, name, parent, daoFactory);
				image.setType(type);
				image.setComments(comments);
				images.add(image);
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return images;
	}

	public List<ImageNode> getChildren(ImgCategoryNode parentCategory) throws DAOException {
		List<ImageNode> images = new ArrayList<ImageNode>();
		Connection connection = conProvider.getConnection();
		String query = "SELECT ID, NAME, PARENT, IMAGE_TYPE, COMMENTS FROM IMAGES WHERE PARENT = " + parentCategory.getId();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				Integer id = resultSet.getInt("ID");
				String name = resultSet.getString("NAME");
				Integer parent = resultSet.getInt("PARENT");
				Integer type = resultSet.getInt("IMAGE_TYPE");
				String comments = resultSet.getString("COMMENTS");
				
				ImageNode image = new ImageNode(id, name, parent, daoFactory);
				image.setType(type);
				image.setComments(comments);
				image.setParent(parentCategory);
				images.add(image);
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return images;
	}

	public ImgCategoryNode getParent(ImageNode image) throws DAOException {
		ImgCategoryNode parent = null;
		
		if (image.getParentId() == null) {
			return parent;
		}
		
		Connection connection = conProvider.getConnection();
		String query = "SELECT ID, NAME, PARENT, COMMENTS FROM IMG_CATEGORIES WHERE ID = " + image.getParentId();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			if(resultSet.next()) {
				Integer id = resultSet.getInt("ID");
				String name = resultSet.getString("NAME");
				String comments = resultSet.getString("COMMENTS");
				Integer parentId = null;
				
				parent = new ImgCategoryNode(id, name, parentId, daoFactory);
				parent.setComments(comments);
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return parent;
	}

	public ImageNode remove(ImageNode image) throws DAOException {
		Connection connection = conProvider.getConnection();
		try {
			Statement statement = connection.createStatement();
			remove(image, statement);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return image;
	}
	
	private void remove(ITreeNode node, Statement statement) throws DAOException {
		IFolderDAO folderDAO = daoFactory.getFolderDAO();
		IFileDAO fileDAO = daoFactory.getFileDAO();

		ImageNode image = (ImageNode) node;
		String query = "DELETE FROM IMAGES WHERE ID = " + image.getId();

		try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		List<ITreeNode> children = new ArrayList<ITreeNode>();
		List<FolderNode> folders = folderDAO.getChildren(image);
		List<FileNode> files = fileDAO.getChildren(image);
		children.addAll(folders);
		children.addAll(files);

		if (children != null && children.size() > 0) {
			for (ITreeNode child : children) {
				if (child instanceof FolderNode) {
					FolderNode folder = (FolderNode) child;
					folderDAO.remove(folder);
				} else if (child instanceof FileNode) {
					FileNode file = (FileNode) child;
					fileDAO.remove(file);
				}
			}
		}

	}

	public ImageNode update(ImageNode image) throws DAOException {
		Connection connection = conProvider.getConnection();		
		String query = "UPDATE IMAGES SET NAME = '" + image.getName() + "', PARENT = " + image.getParentId() + ", IMAGE_TYPE = " + image.getType() + ", COMMENTS = '" + image.getComments() + "'" + " WHERE ID = " + image.getId();
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return image;
	}

}
