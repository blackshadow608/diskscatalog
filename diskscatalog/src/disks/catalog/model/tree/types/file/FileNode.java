package disks.catalog.model.tree.types.file;

import java.io.File;

import disks.catalog.model.tree.types.AbstractTreeNode;
import disks.catalog.model.tree.types.ITreeNode;


public class FileNode extends AbstractTreeNode {
	
	private File file;
	private boolean isRoot = false;
	
	public FileNode() {
		
	}
	
	public FileNode(String name) {
		this.name = name;
	}
	
	public FileNode(File file) {
		this.file = file;		
		if (file.getName().equals("")) {
			this.name = file.getPath();			
		} else {
			this.name = file.getName();			
		}
	}
	
	public FileNode(Integer id, String name, Integer parentId) {
		super(id, name, parentId);
	}

	public boolean canHaveChildren() {
		return false;
	}

	public void refresh() {
		
	}
	
	public boolean isRoot() {
		return isRoot;
	}
	
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	
	public File getFile() {
		return file;
	}
	
	public int getPrioritet() {
		return 0;
	}

	public ITreeNode clone() {
		return null;
	}
}
