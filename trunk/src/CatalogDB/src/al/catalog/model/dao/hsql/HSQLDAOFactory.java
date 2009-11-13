package al.catalog.model.dao.hsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import al.catalog.model.IConnectionProvider;
import al.catalog.model.dao.DAOException;
import al.catalog.model.dao.DAOFactory;
import al.catalog.model.dao.IFileDAO;
import al.catalog.model.dao.IFolderDAO;
import al.catalog.model.dao.IImageDAO;
import al.catalog.model.dao.IImgCategoryDAO;

public class HSQLDAOFactory extends DAOFactory {
	
	private IConnectionProvider conProvider;
	
	private HSQLImageDAO imageDAO;
	private HSQLImgCategoryDAO imgCategoryDAO;
	private HSQLFolderDAO folderDAO;
	private HSQLFileDAO fileDAO;
	
	public HSQLDAOFactory(IConnectionProvider conProvider) {
		this.conProvider = conProvider;
	}
	
	public IImageDAO getImageDAO() {
		if (imageDAO == null) {
			imageDAO = new HSQLImageDAO(this);
		}
		return imageDAO;
	}

	public IImgCategoryDAO getImgCategoryDAO() {
		if (imgCategoryDAO == null) {
			imgCategoryDAO = new HSQLImgCategoryDAO(this);
		}
		return imgCategoryDAO;
	}
	
	public IFileDAO getFileDAO() {
		if (fileDAO == null) {
			fileDAO = new HSQLFileDAO(this);
		}
		return fileDAO;
	}

	public IFolderDAO getFolderDAO() {
		if (folderDAO == null) {
			folderDAO = new HSQLFolderDAO(this);			
		}
		return folderDAO;
	}
	
	public IConnectionProvider getConnectionProvider() {
		return conProvider;
	}
	
	public static int getUniqueId(Connection connection, String tableName) throws DAOException {
		int id = 0;
		String query = "SELECT MAX(ID) AS MAX_ID FROM " + tableName;		
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			if(resultSet.next()) {
				id = resultSet.getInt("MAX_ID") + 1;				
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return id;
	}			
}
