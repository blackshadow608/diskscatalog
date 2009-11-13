package al.catalog.model.dao;

import java.util.List;

import al.catalog.model.tree.types.ITreeNode;
import al.catalog.model.tree.types.file.FileNode;
import al.catalog.model.tree.types.file.FolderNode;

public interface IFileDAO {
	
	public FileNode add(FileNode file) throws DAOException;
	
	public FileNode remove(FileNode file) throws DAOException;
	
	public void removeByParent(FolderNode parent) throws DAOException;
	
	public FileNode update(FileNode file) throws DAOException;
	
	public List<FileNode> getAll() throws DAOException;
	
	public List<FileNode> getChildren(ITreeNode parent) throws DAOException;
	
	public FileNode get(int id) throws DAOException;
}
