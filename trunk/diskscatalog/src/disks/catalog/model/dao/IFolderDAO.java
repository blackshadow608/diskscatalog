package disks.catalog.model.dao;

import java.util.List;

import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.model.tree.types.file.FolderNode;


public interface IFolderDAO {
	
	public List<FolderNode> getAll() throws DAOException;
	
	public List<FolderNode> getChildren(ITreeNode folder) throws DAOException;
	
	public FolderNode get(int folderId) throws DAOException;
	
	public FolderNode add(FolderNode folder) throws DAOException;
	
	public FolderNode remove(FolderNode folder) throws DAOException;
	
	public FolderNode update(FolderNode folder) throws DAOException;
	
	public ITreeNode getParent(FolderNode folder) throws DAOException;
}
