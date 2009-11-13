package al.catalog.model.dao.hsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import al.catalog.model.IConnectionProvider;
import al.catalog.model.dao.DAOException;
import al.catalog.model.dao.IImageDAO;
import al.catalog.model.dao.IImgCategoryDAO;
import al.catalog.model.tree.types.ITreeNode;
import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.model.tree.types.images.ImgCategoryNode;

public class HSQLImgCategoryDAO implements IImgCategoryDAO {
	
	private HSQLDAOFactory daoFactory;
	private IConnectionProvider conProvider;
	
	public HSQLImgCategoryDAO(HSQLDAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		this.conProvider = daoFactory.getConnectionProvider();
	}

	public ImgCategoryNode add(ImgCategoryNode imgCategory) throws DAOException {
		Connection connection = conProvider.getConnection();		
		int id = HSQLDAOFactory.getUniqueId(connection, "IMG_CATEGORIES");
		imgCategory.setId(id);
		
		String query = "INSERT INTO IMG_CATEGORIES (ID, NAME, PARENT, COMMENTS) VALUES (" +
			imgCategory.getId() + ", '" +
			imgCategory.getName() + "', " +
			imgCategory.getParentId() + ", '" +
			imgCategory.getComments() + "')";
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return imgCategory;
	}

	public ImgCategoryNode get(int categoryId) throws DAOException {
		Connection connection = conProvider.getConnection();
		String query = "SELECT ID, NAME, PARENT, COMMENTS FROM IMG_CATEGORIES WHERE ID = " + categoryId;
		ImgCategoryNode imgCategory = null;
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			if(resultSet.next()) {
				Integer id = resultSet.getInt("ID");
				String name = resultSet.getString("NAME");
				Integer parent = resultSet.getInt("PARENT");
				String comments = resultSet.getString("COMMENTS");
				
				imgCategory = new ImgCategoryNode(id, name, parent, daoFactory);
				imgCategory.setComments(comments);
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return imgCategory;
	}

	public List<ImgCategoryNode> getAll() throws DAOException {
		List<ImgCategoryNode> imgCategories = new ArrayList<ImgCategoryNode>();
		Connection connection = conProvider.getConnection();
		String query = "SELECT ID, NAME, PARENT, COMMENTS FROM IMG_CATEGORIES";
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				Integer id = resultSet.getInt("ID");
				String name = resultSet.getString("NAME");
				Integer parent = resultSet.getInt("PARENT");
				String comments = resultSet.getString("COMMENTS");
				
				ImgCategoryNode imgCategory = new ImgCategoryNode(id, name, parent, daoFactory);
				imgCategory.setComments(comments);
				imgCategories.add(imgCategory);
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return imgCategories;
	}

	public List<ImgCategoryNode> getChildren(ImgCategoryNode parentCategory) throws DAOException {
		List<ImgCategoryNode> imgCategories = new ArrayList<ImgCategoryNode>();
		Connection connection = conProvider.getConnection();
		String query = "SELECT ID, NAME, PARENT, COMMENTS FROM IMG_CATEGORIES WHERE PARENT = " + parentCategory.getId() +
			" ORDER BY NAME";
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				Integer id = resultSet.getInt("ID");
				String name = resultSet.getString("NAME");
				Integer parent = resultSet.getInt("PARENT");
				String comments = resultSet.getString("COMMENTS");
				
				ImgCategoryNode imgCategory = new ImgCategoryNode(id, name, parent, daoFactory);
				imgCategory.setParent(parentCategory);
				imgCategory.setComments(comments);
				imgCategories.add(imgCategory);
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
			
		return imgCategories;		
	}

	public ImgCategoryNode getParent(ImgCategoryNode imgCategory) throws DAOException {
		ImgCategoryNode parent = null;
		
		if (imgCategory.getParentId() == null) {
			return parent;
		}
		
		Connection connection = conProvider.getConnection();
		String query = "SELECT ID, NAME, PARENT, COMMENTS FROM IMG_CATEGORIES WHERE ID = " + imgCategory.getParentId();
		
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

	public ImgCategoryNode getRoot() throws DAOException {
		ImgCategoryNode imgCategory = null;
		Connection connection = conProvider.getConnection();
		
		try {
			if (connection == null || connection.isClosed()) {
				return null;
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		String query = "SELECT ID, NAME, PARENT, COMMENTS FROM IMG_CATEGORIES WHERE PARENT IS NULL";
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			if(resultSet.next()) {
				Integer id = resultSet.getInt("ID");
				String name = resultSet.getString("NAME");
				String comments = resultSet.getString("COMMENTS");
				Integer parent = null;
				
				imgCategory = new ImgCategoryNode(id, name, parent, daoFactory);
				imgCategory.setComments(comments);
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return imgCategory;
	}

	public ImgCategoryNode remove(ImgCategoryNode imgCategory) throws DAOException {
		Connection connection = conProvider.getConnection();
		try {
			Statement statement = connection.createStatement();
			remove(imgCategory, statement);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return imgCategory;
	}
	
	private void remove(ITreeNode node, Statement statement) throws DAOException {
		IImageDAO imageDAO = daoFactory.getImageDAO();
		if (node instanceof ImgCategoryNode) {
			ImgCategoryNode category = (ImgCategoryNode) node;
			List<ITreeNode> children = new ArrayList<ITreeNode>();
			List<ImageNode> images = imageDAO.getChildren(category);
			List<ImgCategoryNode> categories = getChildren(category);
			
			children.addAll(categories);
			children.addAll(images);
			
			if (children.size() > 0) {
				for (ITreeNode child : children) {
					remove(child, statement);
				}				
			}
			String query = "DELETE FROM IMG_CATEGORIES WHERE ID = " + category.getId();
			try {				
				statement.executeUpdate(query);
			} catch (SQLException e) {
				throw new DAOException(e);
			}		
			
		} else if (node instanceof ImageNode) {			
			ImageNode image = (ImageNode) node;
			imageDAO.remove(image);
		}
	}

	public ImgCategoryNode update(ImgCategoryNode imgCategory) throws DAOException {
		Connection connection = conProvider.getConnection();
		String name = imgCategory.getName();
		Integer parentId = imgCategory.getParentId();
		String query = "UPDATE IMG_CATEGORIES SET NAME = '" + name + "', PARENT = " + parentId + ", COMMENTS = '" + imgCategory.getComments() + "' WHERE ID = " + imgCategory.getId();
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return imgCategory;
	}
}
