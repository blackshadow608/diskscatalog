package disks.catalog.model.dao.hsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import disks.catalog.model.connection.IConnectionProvider;
import disks.catalog.model.dao.DAOException;
import disks.catalog.model.dao.DAOFactory;
import disks.catalog.model.dao.IFileDAO;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.model.tree.types.file.FileNode;
import disks.catalog.model.tree.types.file.FolderNode;
import disks.catalog.model.tree.types.images.ImageNode;


public class HSQLFileDAO implements IFileDAO {
	
	private IConnectionProvider conProvider;
	
	public HSQLFileDAO(DAOFactory daoFactory) {
		this.conProvider = daoFactory.getConnectionProvider();
	}

	public FileNode remove(FileNode file) throws DAOException {
		Connection connection = conProvider.getConnection();
		String query = "DELETE FROM FILES WHERE ID = " + file.getId();
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return file;
	}

	public FileNode add(FileNode file) throws DAOException {
		Connection connection = conProvider.getConnection();		
		int id = HSQLDAOFactory.getUniqueId(connection, "FILES");
		file.setId(id);
		
		String query = "INSERT INTO FILES (ID, NAME, PARENT, IS_ROOT) VALUES (" +
			file.getId() + ", '" +
			file.getName().replaceAll("\'", "\'\'") + "', " +
			file.getParentId() + ", " +
			file.isRoot() + ")";
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return file;
	}

	public List<FileNode> getChildren(ITreeNode parent) throws DAOException {
		List<FileNode> files = new ArrayList<FileNode>();
		Connection connection = conProvider.getConnection();
		String query = "SELECT ID, NAME, PARENT, IS_ROOT FROM FILES WHERE PARENT = "
				+ parent.getId() + " AND NOT IS_ROOT ORDER BY NAME";
		if (parent instanceof ImageNode) {
			query = "SELECT ID, NAME, PARENT, IS_ROOT FROM FILES WHERE PARENT = "
					+ parent.getId() + " AND IS_ROOT ORDER BY NAME";
		}
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				Integer id = resultSet.getInt("ID");
				String name = resultSet.getString("NAME");
				Integer parentId = resultSet.getInt("PARENT");
				
				FileNode file = new FileNode(id, name, parentId);
				file.setParent(parent);
				files.add(file);
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return files;
	}

	public FileNode get(int id) throws DAOException {
		return null;
	}

	public List<FileNode> getAll() throws DAOException {
		return null;
	}

	public void removeByParent(FolderNode parent) throws DAOException {
		
	}

	public FileNode update(FileNode file) throws DAOException {
		return null;
	}
}
