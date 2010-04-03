package disks.catalog.model.tree.action;

import java.util.List;

import javax.swing.SwingUtilities;

import disks.catalog.model.DBAction;
import disks.catalog.model.dao.DAOException;
import disks.catalog.model.dao.DAOFactory;
import disks.catalog.model.dao.IFileDAO;
import disks.catalog.model.dao.IFolderDAO;
import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.model.tree.types.file.FileNode;
import disks.catalog.model.tree.types.file.FolderNode;
import disks.catalog.model.tree.types.images.ImageNode;
import disks.catalog.ui.resource.ResourceManager;


public class DetachImageAction extends DBAction {
	
	private ImageNode image;
	private Thread thread;
	private DBAction action;
	
	private DBTreeModel dbModel;
	
	private List<ITreeNode> children;
	
	private static final String PROGRESS_TEXT = "detachImageAction.progressText";
	
	public DetachImageAction(DBTreeModel dbModel, ImageNode  image) {
		super(dbModel.getDBManager());
		this.dbModel = dbModel;
		this.image = image;
		this.action = this;
	}

	public void abort() {
		if (thread != null && !thread.isInterrupted()) {
			try {
				thread.interrupt();				
			} catch (Exception e) {
				e.printStackTrace();				
			}
			
			thread = null;
		}
		
		dbManager.rollback(savepoint);
		image.setChildren(children);		
		dbModel.fireChangeStructureNode(image);
		dbModel.fireChangeOpenedNode(image);
		
		runGarbageCollector();
		
		dbManager.fireAfterAction(action);		
	}

	public void execute() {
		thread = new Thread() {
			
			private boolean isInterrupted = false;
			
			public void run() {
				savepoint = dbManager.setSavepoint();
				
				children = image.getChildren();
				
				for (ITreeNode child : children) {					
					remove(child);
				}
				
				if (!isInterrupted) {
					image.setChildren(null);					
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							dbModel.fireChangeStructureNode(image);
							dbModel.fireChangeOpenedNode(image);
							
							firstExecute = false;
							isExecuted = true;												
						}
					});
					dbManager.fireAfterAction(action);
					dbManager.addAction(action);
				}				
			}
			
			private void remove(ITreeNode treeNode) {
				if (!isInterrupted) {
					DAOFactory daoFactory = dbModel.getDAOFactory();
					IFolderDAO folderDAO = daoFactory.getFolderDAO();
					IFileDAO fileDAO = daoFactory.getFileDAO();					
					if (treeNode instanceof FolderNode) {
						FolderNode folder = (FolderNode) treeNode;
						if (treeNode.hasChildren()) {
							for (ITreeNode child : treeNode.getChildren()) {
								remove(child);
							}
						}
						try {
							folderDAO.remove(folder);
						} catch (DAOException e) {
							e.printStackTrace();
						}
					} else if (treeNode instanceof FileNode) {
						FileNode file = (FileNode) treeNode;
						try {
							fileDAO.remove(file);
						} catch (DAOException e) {
							e.printStackTrace();
						}
					}					
				}												
			}
			
			public void interrupt() {
				super.interrupt();
				isInterrupted = true;
			}
		};
		
		thread.start();		
		dbManager.fireBeforeAction(action);
		runGarbageCollector();
	}
	
	public void unexecute() {
		dbManager.rollback(savepoint);		
		image.setChildren(children);		
		dbModel.fireChangeStructureNode(image);
		dbModel.fireChangeOpenedNode(image);
	}
	
	public void pause() {
		
	}
	
	public void runGarbageCollector() {
	      Runtime r = Runtime.getRuntime();
	      r.gc();
	}
	
	public String getProgressText() {
		return ResourceManager.getString(PROGRESS_TEXT);
	}

	public boolean isCancelable() {
		return true;
	}
	
	public boolean isPausable() {
		return true;
	}
}
