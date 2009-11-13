package al.catalog.model.dao;

import al.catalog.model.IConnectionProvider;

/**
 * Типичная DAOFactory в паттерне DAO.
 * 
 * @author Alexander Levin
 */
public abstract class DAOFactory {

	/**
	 * Возвращает IConnectionProvider, который используется для получения соединения
	 * Connection c БД. Нужен для того, чтобы каждая конкретная реализация DAO смогла
	 * получить Connection с БД и выполнить требуемые операции.
	 * 
	 * @return Объект, реализующий IConnectionProvider. 
	 */
	public abstract IConnectionProvider getConnectionProvider();
	
	/**
	 * Возвращает IImgCategoryDAO, используемую для операций с категориями
	 * образов (image category).
	 * 
	 * @return конкретная реализация IImgCategoryDAO.
	 */
	public abstract IImgCategoryDAO getImgCategoryDAO();
	
	/**
	 * Возвращает IImageDAO, используемую для операций с образами (image).
	 * 
	 * @return конкретная реализация IImageDAO.
	 */
	public abstract IImageDAO getImageDAO();
	
	/**
	 * Возвращает IFolderDAO, используемую для операций с директориями (folder).
	 * 
	 * @return конкретная реализация IFolderDAO.
	 */
	public abstract IFolderDAO getFolderDAO();
	
	/**
	 * Возвращает IFileDAO, используемую для операций с файлами (file).
	 * 
	 * @return конкретная реализация IFileDAO.
	 */
	public abstract IFileDAO getFileDAO(); 
}
