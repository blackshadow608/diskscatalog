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
	 *            родительская категория <b>ImgCategoryNode</b></b>.
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

	/**
	 * Обновляет информацию в базе данных об узле <b>ImageNode</b>, ссылка на
	 * который переадется методу в качестве параметра.
	 * 
	 * @param image -
	 *            <b>ImageNode</b> узел, информацию о котором необходимо
	 *            изменить в базе данных.
	 * 
	 * @return <b>ImageNode</b> узел, информация о котором было обновления в
	 *         базе данных.
	 * 
	 * @throws DAOException,
	 *             который выбрасывается, если в процессе работы метода возникла
	 *             исключительная ситуация.
	 */
	public ImageNode update(ImageNode image) throws DAOException;
	
	/**
	 * Возвращает из базы данных родительскую категорию <b>ImgCategory</b> узла
	 * <b>ImageNode</b>, который передается методу в качестве параметра.
	 * 
	 * @param image - <b>ImageNode</b> узел, родительскую категорию которого необходимо получить из базы данных.
	 * 
	 * @return родительская <b>ImgСategoryNode</b> категория. 
	 * 
	 * @throws DAOException,
	 *             который выбрасывается, если в процессе работы метода возникла
	 *             исключительная ситуация.
	 */
	public ImgCategoryNode getParent(ImageNode image) throws DAOException;
}
