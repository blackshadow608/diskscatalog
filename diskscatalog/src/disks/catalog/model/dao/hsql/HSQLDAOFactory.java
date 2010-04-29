package disks.catalog.model.dao.hsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import disks.catalog.model.connection.IConnectionProvider;
import disks.catalog.model.dao.DAOException;
import disks.catalog.model.dao.DAOFactory;
import disks.catalog.model.dao.IFileDAO;
import disks.catalog.model.dao.IFolderDAO;
import disks.catalog.model.dao.IImageDAO;
import disks.catalog.model.dao.IImgCategoryDAO;


/**
 * Реализация фабрики <b>DAOFactory</b> для конкретной базы данных, а именно
 * HSQL DB.
 * 
 * @author Alexander Levin
 */
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
	
	/**
	 * Возвращает уникальный идентификатор, который потребуется для вставки
	 * новых записей в таблицу.
	 * 
	 * @param connection -
	 *            соединение <b>Connection</b>, через которое метод
	 *            осуществляет получение идентификатора из таблицы.
	 * @param tableName -
	 *            строковое <b>String</b> имя таблицы, для которой требуется
	 *            получить уникальный идентификатор.
	 * 
	 * @return целый <b>int</b> уникальный идентификатор.
	 * 
	 * @throws DAOException
	 *             исключительная ситуация, которая выбрасывается, если что-то
	 *             не так.
	 */
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
