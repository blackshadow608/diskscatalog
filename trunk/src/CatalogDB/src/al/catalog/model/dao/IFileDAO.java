package al.catalog.model.dao;

import java.util.List;

import al.catalog.model.tree.types.ITreeNode;
import al.catalog.model.tree.types.file.FileNode;
import al.catalog.model.tree.types.file.FolderNode;

/**
 * Интерфейс <b>IFileDAO</b> должны реализовать все DAO, которые обеспечивают
 * методы для доступа к файлам и папкам, расположенными в базе данных.
 * 
 * @author Alexander Levin
 */
public interface IFileDAO {
	
	/**
	 * Добавляет файл, который описывается с помощью <b>FileNode</b> в базу
	 * данных. За более подробным описанием читать <b>FileNode</b>.
	 * 
	 * @param file -
	 *            <b>FileNode</b>, который описывает добавляемый в базу данных
	 *            файл.
	 * 
	 * @return <b>FileNode</b>, который был добавлен в базу данных.
	 * 
	 * @throws DAOException
	 *             исключительная ситуация, которая выбрасывается, если что-то
	 *             пойдет не так.
	 */
	public FileNode add(FileNode file) throws DAOException;
	
	/**
	 * Удаляет из базы данных файл, который описывается с помощью <b>FileNode</b>,
	 * ссылка на который передается методу в качестве параметра.
	 * 
	 * @param file -
	 *            <b>FileNode</b>, описывающий файл, который требуется удалить
	 *            из базы данных.
	 * 
	 * @return <b>FieldNode</b>, который был удален из базы данных.
	 * 
	 * @throws DAOException
	 *             исключительная ситуация, которая выбрасывается, если что-то
	 *             пойдет не так.
	 */
	public FileNode remove(FileNode file) throws DAOException;
	
	/**
	 * Удаляет все дочерние узлы, у которых родительским является узел,
	 * передаваемый методу в качестве параметра.
	 * 
	 * @param parent -
	 *            <b>FolderNode</b> родительский узел, у которого необходимо
	 *            удалить все дочерние узлы.
	 * 
	 * @throws DAOException
	 *             исключительная ситуация, которая выбрасывается, если что-то
	 *             пойдет не так.
	 */
	public void removeByParent(FolderNode parent) throws DAOException;
	
	/**
	 * Обновляет информацию об узле <b>FileNode</b>, ссылка на который
	 * передается методу в качестве параметра.
	 * 
	 * @param file -
	 *            узел <b>FileNode</b>, информацию о котором необходимо
	 *            обновить в базе данных.
	 * 
	 * @return <b>FildNode</b> узел, информацию о котором была обновлена в базе
	 *         данных.
	 * 
	 * @throws DAOException
	 *             исключительная ситуация, которая выбрасывается, если что-то
	 *             пойдет не так.
	 */
	public FileNode update(FileNode file) throws DAOException;
	
	/**
	 * Возвращает список всех <b>FileNode</b> узлов, которые содержаться в базе
	 * данных.
	 * 
	 * @return список всех <b>FileNode</b> узлов.
	 * 
	 * @throws DAOException
	 *             исключительная ситуация, которая выбрасывается, если что-то
	 *             пойдет не так.
	 */
	public List<FileNode> getAll() throws DAOException;
	
	/**
	 * Возвращает список дочерних узлов узла <b>ITreeNode</b>, ссылка на которы
	 * йпередается методу в качестве параметра.
	 * 
	 * @param parent -
	 *            родительский <b>ITreeNode</b> узел, дочерние элементы
	 *            которого необходимо получить из базы данных.
	 * 
	 * @return список дочерних узлов <b>FileNode</b>.
	 * 
	 * @throws DAOException
	 *             исключительная ситуация, которая выбрасывается, если что-то
	 *             пойдет не так.
	 */
	public List<FileNode> getChildren(ITreeNode parent) throws DAOException;
	
	/**
	 * Возвращается из базы данных <b>FileNode</b>, уникальный идентификатор
	 * которого совпадает с передаваемым методу в качестве параметра.
	 * 
	 * @param id -
	 *            уникальный <b>int</b> идентификатор <b>FileNode</b> узла,
	 *            который необходимо получить из базы данных.
	 * 
	 * @return <b>FileNode</b>, который требовалось получить по уникальному
	 *         идентификатору.
	 * 
	 * @throws DAOException
	 *             исключительная ситуация, которая выбрасывается, если что-то
	 *             пойдет не так.
	 */
	public FileNode get(int id) throws DAOException;
}
