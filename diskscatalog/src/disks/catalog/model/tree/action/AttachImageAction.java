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


/**
 * Action, который отвечает за сохранение образа, то есть просмотр всех
 * поддиректорий и файлов указанной директории и сохранении информации о них в
 * базе данных. Просмотр и сохранение выполняются в отдельном потоке.
 * 
 * @author Alexander Levin
 */
public class AttachImageAction extends DBAction {
	
	private FolderNode folder;
	private ImageNode image;
	private Thread thread;
	private DBAction action;
	
	private DBTreeModel dbModel;
	
	private static final String PROGRESS_TEXT = "attachImageAction.progressText";
	
	public AttachImageAction(DBTreeModel dbModel, FolderNode folder, ImageNode image) {
		super(dbModel.getDBManager());
		this.dbModel = dbModel;
		this.folder = folder;
		this.image = image;
		this.action = this;
	}

	public void execute() {
		savepoint = dbManager.setSavepoint();
		
		/*
		 * Поток, внутри которого проиходит просмотр и сохранеие всех файлов и поддиректорий
		 * указанной директории.  
		 */
		thread = new Thread() {
			
			private boolean isInterrupted = false;
			
			public void run() {
				/*
				 * Делаем refresh у folder'а, чтобы обновить список дочерних элементов 
				 */
				folder.refresh();
				List<ITreeNode> children = folder.getChildren();
				
				if (children != null) {
					for (ITreeNode child : children) {
						if (child instanceof FolderNode) {
							FolderNode childFolder = (FolderNode) child;
							childFolder.setRoot(true);
							childFolder.setParentId(image.getId());
							store(child);							
						} else if (child instanceof FileNode) {
							FileNode childFile = (FileNode) child;
							childFile.setRoot(true);
							childFile.setParentId(image.getId());
							store(child);
						}
					}					
				}
				
				if (!isInterrupted) {
					image.refresh();
					
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							if (firstExecute) {								
                                dbManager.addAction(action);
							}
							
							dbModel.fireChangeStructureNode(image);
							dbModel.fireChangeOpenedNode(image);
							
							dbModel.fireChangeStructureNode(image);
							
							firstExecute = false;
							isExecuted = true;												
						}
					});				
					
					dbManager.fireAfterAction(action);					
				}	
			}
			
			private void store(ITreeNode treeNode) {
				/**
				 * Каждый раз проверяется значение флажка isInterrupted, который показывает статус
				 * выпонения потока, если поток преостановлен, то ничего не просматривается
				 * и не сохраняется.
				 */
				if (!isInterrupted) {
					DAOFactory daoFactory = dbModel.getDAOFactory();
					if (treeNode instanceof FileNode) {			
						IFileDAO fileDAO = daoFactory.getFileDAO();
						FileNode file = (FileNode) treeNode;
						try {
							fileDAO.add(file);
						} catch (DAOException e) {
							e.printStackTrace();
						}
					} else if (treeNode instanceof FolderNode) {
						IFolderDAO folderDAO = daoFactory.getFolderDAO();
						FolderNode folder = (FolderNode) treeNode;			
						try {
							folderDAO.add(folder);
						} catch (DAOException e) {
							e.printStackTrace();
						}
						folder.refresh();
						List<ITreeNode> children = folder.getChildren();
						if (children != null) {
							for (ITreeNode child : children) {
								store(child);
							}
						}			
					}
					runGarbageCollector();
				}				
			}
			
			public void interrupt() {
				super.interrupt();
				isInterrupted = true;
			}
		};
		
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();		
		dbManager.fireBeforeAction(action);
	}
	
	public void unexecute() {
		dbManager.rollback(savepoint);
		
		List<ITreeNode> children = image.getChildren();
		if (children != null) {
			children.clear();
		}
		
		runGarbageCollector();
		
		dbModel.fireChangeStructureNode(image);
		dbModel.fireChangeOpenedNode(image);
	}
	
	public void abort() {
		if (thread != null && !thread.isInterrupted()) {
			try {
				thread.interrupt();				
			} catch (Exception e) {
				e.printStackTrace();				
			}
			
			thread = null;
			runGarbageCollector();
		}
		
		dbManager.rollback(savepoint);			
		dbManager.fireAfterAction(action);
		
		List<ITreeNode> children = image.getChildren();
		if (children != null) {
			children.clear();
		}
			    
		dbModel.fireChangeStructureNode(image);
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
