package disks.catalog.model.dao.hsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import disks.catalog.model.IConnectionProvider;
import disks.catalog.model.dao.DAOException;
import disks.catalog.model.dao.IFileDAO;
import disks.catalog.model.dao.IFolderDAO;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.model.tree.types.file.FileNode;
import disks.catalog.model.tree.types.file.FolderNode;
import disks.catalog.model.tree.types.images.ImageNode;


public class HSQLFolderDAO implements IFolderDAO {
	
	private HSQLDAOFactory daoFactory;
	private IConnectionProvider conProvider;
	
	public HSQLFolderDAO(HSQLDAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		this.conProvider = daoFactory.getConnectionProvider();
	}

	public FolderNode add(FolderNode folder) throws DAOException {
		Connection connection = conProvider.getConnection();		
		int id = HSQLDAOFactory.getUniqueId(connection, "FOLDERS");
		folder.setId(id);
		
		String query = "INSERT INTO FOLDERS (ID, NAME, PARENT, IS_ROOT) VALUES (" +
			folder.getId() + ", '" +
			folder.getName().replaceAll("\'", "\'\'") + "', " +
			folder.getParentId() + ", " +
			folder.isRoot() + ")";
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return folder;
	}

	public FolderNode get(int folderId) throws DAOException {
		Connection connection = conProvider.getConnection();
		String query = "SELECT ID, NAME, PARENT, IS_ROOT FROM FOLDERS WHERE ID = " + folderId;
		FolderNode folder = null;
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			if(resultSet.next()) {
				Integer id = resultSet.getInt("ID");
				String name = resultSet.getString("NAME");
				Integer parent = resultSet.getInt("PARENT");
				boolean isRoot = resultSet.getBoolean("IS_ROOT"); 
				
				folder = new FolderNode(id, name, parent, daoFactory);
				folder.setRoot(isRoot);
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return folder;
	}

	public List<FolderNode> getAll() throws DAOException {
		List<FolderNode> folders = new ArrayList<FolderNode>();
		Connection connection = conProvider.getConnection();
		String query = "SELECT ID, NAME, PARENT, IS_ROOT FROM FOLDERS";
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				Integer id = resultSet.getInt("ID");
				String name = resultSet.getString("NAME");
				Integer parent = resultSet.getInt("PARENT");
				boolean isRoot = resultSet.getBoolean("IS_ROOT");
				
				FolderNode folder = new FolderNode(id, name, parent, daoFactory);
				folder.setRoot(isRoot);
				folders.add(folder);
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return folders;
	}

	public List<FolderNode> getChildren(ITreeNode parent) throws DAOException {
		List<FolderNode> folders = new ArrayList<FolderNode>();
		Connection connection = conProvider.getConnection();
		String query = "SELECT ID, NAME, PARENT, IS_ROOT FROM FOLDERS WHERE PARENT = " + parent.getId() +
			" AND NOT IS_ROOT ORDER BY NAME";
		if (parent instanceof ImageNode) {
			query = "SELECT ID, NAME, PARENT, IS_ROOT FROM FOLDERS WHERE PARENT = " + parent.getId() +
			" AND IS_ROOT ORDER BY NAME";						
		}
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				Integer id = resultSet.getInt("ID");
				String name = resultSet.getString("NAME");
				Integer parentId = resultSet.getInt("PARENT");
				boolean isRoot = resultSet.getBoolean("IS_ROOT");
				
				FolderNode folder = new FolderNode(id, name, parentId, daoFactory);
				folder.setRoot(isRoot);
				folder.setParent(parent);
				folders.add(folder);
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
			
		return folders;
	}

	public ITreeNode getParent(FolderNode folder) throws DAOException {
		ITreeNode parent = null;
		
		if (folder.getParentId() == null) {
			return parent;
		}
		
		Connection connection = conProvider.getConnection();
		String query = "SELECT ID, NAME, PARENT FROM FOLDERS WHERE ID = " + folder.getParentId();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			if(resultSet.next()) {
				Integer id = resultSet.getInt("ID");
				String name = resultSet.getString("NAME");
				Integer parentId = null;
				
				parent = new FolderNode(id, name, parentId, daoFactory);				
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return parent;
	}

	public FolderNode remove(FolderNode folder) throws DAOException {
		Connection connection = conProvider.getConnection();
		try {
			Statement statement = connection.createStatement();
			remove(folder, statement);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return folder;
	}
	
	private void remove(ITreeNode node, Statement statement) throws DAOException {
		IFileDAO fileDAO = daoFactory.getFileDAO();
		
		if (node instanceof FolderNode) {
			FolderNode folder = (FolderNode) node;
			
			List<ITreeNode> children = new ArrayList<ITreeNode>();
			List<FolderNode> folderChildren = getChildren(folder);
			List<FileNode> fileChildren = fileDAO.getChildren(folder);
			
			children.addAll(folderChildren);
			children.addAll(fileChildren);
			
			if (children != null) {
				for (ITreeNode child : children) {
					remove(child, statement);
				}				
			}
			
			String query = "DELETE FROM FOLDERS WHERE ID = " + folder.getId();
			try {
				statement.executeUpdate(query);
			} catch (SQLException e) {
				throw new DAOException(e);
			}			
		} else if (node instanceof FileNode) {			
			FileNode file = (FileNode) node;
			fileDAO.remove(file);
		}
	}

	public FolderNode update(FolderNode folder) throws DAOException {
		Connection connection = conProvider.getConnection();
		String name =  folder.getName();
		Integer parentId = folder.getParentId();
		String query = "UPDATE FOLDERS SET NAME = '" + name + "', PARENT = " + parentId + " WHERE ID = " + folder.getId();
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return folder;
	}
}
