package al.catalog.model.dao;

import java.util.List;

import al.catalog.model.tree.types.images.ImgCategoryNode;

public interface IImgCategoryDAO {
	
	public List<ImgCategoryNode> getAll() throws DAOException;

	public ImgCategoryNode getRoot() throws DAOException;

	public List<ImgCategoryNode> getChildren(ImgCategoryNode category) throws DAOException;

	public ImgCategoryNode get(int categoryId) throws DAOException;

	public ImgCategoryNode add(ImgCategoryNode category) throws DAOException;

	public ImgCategoryNode remove(ImgCategoryNode category) throws DAOException;

	public ImgCategoryNode update(ImgCategoryNode category) throws DAOException;
	
	public ImgCategoryNode getParent(ImgCategoryNode category) throws DAOException;
}
