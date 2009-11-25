package al.catalog.model.dao;

import java.util.List;

import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.model.tree.types.images.ImgCategoryNode;

/**
 * Интерфейс <b>IImageDAO</b> должны реализовать все DAO, которые обеспечивают
 * методы для доступа к образам, расположенными в базе данных.
 * 
 * @author Alexander Levin
 */
public interface IImageDAO {
	
	/**
	 * Возвращает список всех образов, которые имеются в базе дынных.
	 * 
	 * @return список всех образов.
	 * 
	 * @throws DAOException,
	 *             который выбрасывается, если в процессе работы метода возникла
	 *             исключительная ситуация.
	 */
	public List<ImageNode> getAll() throws DAOException;

	/**
	 * Возвращает все образы, у которых родителем является передаваемая в
	 * качестве параметра категория.
	 * 
	 * @param parentCategory -
	 *            родительская категория <b>ImgCategoryNode</b>.
	 * 
	 * @return список образов указанной категории.
	 * 
	 * @throws DAOException,
	 *             который выбрасывается, если в процессе работы метода возникла
	 *             исключительная ситуация.
	 */
	public List<ImageNode> getChildren(ImgCategoryNode parentCategory) throws DAOException;

	/**
	 * Возвращает образ, уникальный идентификатор которого совпадает с
	 * передаваемым методу в качестве параметра.
	 * 
	 * @param imageId -
	 *            уникальный <b>int</b> идентификатор.
	 * 
	 * @return <b>ImageNode</b> с указанным уникальным идентификатором.
	 * 
	 * @throws DAOException,
	 *             который выбрасывается, если в процессе работы метода возникла
	 *             исключительная ситуация.
	 */
	public ImageNode get(int imageId) throws DAOException;

	/**
	 * Добавляет в базу данных образ <b>ImageNode</b>, ссылка на который передается методу в качестве параметра.
	 * 
	 * @param image - образ <b>ImageNode</b>, который требуется добавить в базу данных.
	 * 
	 * @return <b>ImageNode</b> образ, который был успешно добавлен в базу данных.
	 * 
	 * @throws DAOException,
	 *             который выбрасывается, если в процессе работы метода возникла
	 *             исключительная ситуация.
	 */
	public ImageNode add(ImageNode image) throws DAOException;

	/**
	 * Удаляет из базы данных образ <b>ImageNode</b>, ссылка на который передается методу в качестве параметра.
	 * 
	 * @param image - образ <b>ImageNode</b>, который необходимо удалить из базы данных.
	 * 
	 * @return <b>ImageNode</b>, который был успешно удален из базы данных.
	 * 
	 * @throws DAOException,
	 *             который выбрасывается, если в процессе работы метода возникла
	 *             исключительная ситуация.
	 */
	public ImageNode remove(ImageNode image) throws DAOException;

	public ImageNode update(ImageNode image) throws DAOException;
	
	public ImgCategoryNode getParent(ImageNode image) throws DAOException;
}
