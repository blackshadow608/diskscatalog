package al.catalog.model.dao;

import java.util.List;

import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.model.tree.types.images.ImgCategoryNode;

public interface IImageDAO {
	
	public List<ImageNode> getAll() throws DAOException;

	public List<ImageNode> getChildren(ImgCategoryNode parentCategory) throws DAOException;

	public ImageNode get(int imageId) throws DAOException;

	public ImageNode add(ImageNode image) throws DAOException;

	public ImageNode remove(ImageNode image) throws DAOException;

	public ImageNode update(ImageNode image) throws DAOException;
	
	public ImgCategoryNode getParent(ImageNode image) throws DAOException;
}
