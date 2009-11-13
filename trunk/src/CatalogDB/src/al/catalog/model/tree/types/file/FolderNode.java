package al.catalog.model.tree.types.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import al.catalog.model.dao.DAOException;
import al.catalog.model.dao.DAOFactory;
import al.catalog.model.dao.IFileDAO;
import al.catalog.model.dao.IFolderDAO;
import al.catalog.model.tree.types.AbstractTreeNode;
import al.catalog.model.tree.types.ITreeNode;

public class FolderNode extends AbstractTreeNode {
	
	public static final int TYPE_DATA_BASE = 0;
	public static final int TYPE_FILE_SYSTEM = 1;
	
	private DAOFactory daoFactory;
	
	private File file;	
	private int type = TYPE_FILE_SYSTEM;
	private boolean isRoot = false;
	
	public FolderNode() {
		
	}
	
	public FolderNode(String name) {
		this.name = name;
	}
	
	public FolderNode(File file) {
		this.file = file;
		this.type = TYPE_FILE_SYSTEM;
		if (file.getName().equals("")) {
			this.name = file.getPath();			
		} else {
			this.name = file.getName();			
		}
	}
	
	public FolderNode(Integer id, String name, Integer parentId, int type) {
		super(id, name, parentId);
		this.type = type;
	}
	
	public FolderNode(Integer id, String name, Integer parentId, DAOFactory daoFactory) {
		super(id, name, parentId);
		this.type = TYPE_DATA_BASE;
		this.daoFactory = daoFactory;
	}
	
	public boolean canHaveChildren() {
		return true;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}
	
	public void refresh() {
		if (type == TYPE_FILE_SYSTEM) {
			if (file != null) {
				/*
				 * Проверяем, если это флоп, то не обновляем его для того, чтобы
				 * уменьшить время открывания Attach диалога.
				 */
				if (getName().equals("A:\\")) {
					return;
				}
				if (children != null) {
					children.clear();
				}
				if (file.isDirectory()) {
					File[] childFiles = file.listFiles();
					for (File childFile : childFiles) {
						if (!childFile.isHidden()) {
							if (childFile.isDirectory()) {
								FolderNode treeNode = new FolderNode(childFile);
								treeNode.setParent(this);
								addChild(treeNode);
							} else if (childFile.isFile()) {
								FileNode treeNode = new FileNode(childFile);
								treeNode.setParent(this);
								addChild(treeNode);
							}
						}
					}
				}
				sortChildren();
				setFirstRefresh(true);
			}
		} else if (type == TYPE_DATA_BASE) {
			IFolderDAO folderDAO = daoFactory.getFolderDAO();
			IFileDAO fileDAO = daoFactory.getFileDAO();
			List<ITreeNode> children = new ArrayList<ITreeNode>();
			if (hasChildren()) {
				getChildren().clear();
			}
			try {
				children.addAll(folderDAO.getChildren(this));
				children.addAll(fileDAO.getChildren(this));
				setChildren(children);
				sortChildren();
			} catch (DAOException e) {
				e.printStackTrace();
			}
			setFirstRefresh(true);
		}
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
	public boolean isRoot() {
		return isRoot;
	}
	
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	
	public int getPrioritet() {
		return 1;
	}

	public ITreeNode clone() {
		return null;
	}
}
