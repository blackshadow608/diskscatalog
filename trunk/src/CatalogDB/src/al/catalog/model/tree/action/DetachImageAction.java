package al.catalog.model.tree.action;

import java.util.List;

import javax.swing.SwingUtilities;

import al.catalog.model.DBAction;
import al.catalog.model.DBManager;
import al.catalog.model.dao.DAOException;
import al.catalog.model.dao.DAOFactory;
import al.catalog.model.dao.IFileDAO;
import al.catalog.model.dao.IFolderDAO;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.DBTreeModelAction;
import al.catalog.model.tree.types.ITreeNode;
import al.catalog.model.tree.types.file.FileNode;
import al.catalog.model.tree.types.file.FolderNode;
import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.ui.resource.ResourceManager;

public class DetachImageAction extends DBTreeModelAction {
	
	private ImageNode image;
	private Thread thread;
	private DBAction action;
	
	private List<ITreeNode> children;
	
	private static final String PROGRESS_TEXT = "detachImageAction.progressText";
	
	public DetachImageAction(DBManager dbManager, ImageNode  image) {
		super(dbManager);
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
		dbManager.getTreeModel().fireChangeStructureNode(image);
		dbManager.getTreeModel().fireChangeOpenedNode(image);
		
		runGarbageCollector();
		
		dbManager.fireAfterAction(action);		
	}

	public void execute() {
		thread = new Thread() {
			
			private boolean isInterrupted = false;
			
			public void run() {
				final DBTreeModel dbModel = dbManager.getTreeModel();				
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
					DAOFactory daoFactory = dbManager.getTreeModel().getDAOFactory();
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
		dbManager.getTreeModel().fireChangeStructureNode(image);
		dbManager.getTreeModel().fireChangeOpenedNode(image);
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
}
