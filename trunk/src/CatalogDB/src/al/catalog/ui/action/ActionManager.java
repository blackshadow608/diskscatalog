package al.catalog.ui.action;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.Action;

import al.catalog.logger.Logger;
import al.catalog.model.DBAction;
import al.catalog.model.DBManager;
import al.catalog.model.IConnectionListener;
import al.catalog.model.IDBActionListener;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.IDBTreeModelListener;
import al.catalog.model.tree.IHistoryListener;
import al.catalog.model.tree.types.ITreeNode;
import al.catalog.model.tree.types.file.FileNode;
import al.catalog.model.tree.types.file.FolderNode;
import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.model.tree.types.images.ImgCategoryNode;
import al.catalog.ui.CatFrame;
import al.catalog.ui.action.edit.MoveAction;
import al.catalog.ui.action.edit.ShowMoveDialogAction;
import al.catalog.ui.action.edit.RedoAction;
import al.catalog.ui.action.edit.RemoveAction;
import al.catalog.ui.action.edit.RenameAction;
import al.catalog.ui.action.edit.ShowPropertiesDialogAction;
import al.catalog.ui.action.edit.ShowRenameDialogAction;
import al.catalog.ui.action.edit.UndoAction;
import al.catalog.ui.action.edit.create.CreateAction;
import al.catalog.ui.action.edit.create.CreateNewImageAction;
import al.catalog.ui.action.edit.create.CreateNewImgCategoryAction;
import al.catalog.ui.action.edit.image.RunAttachImageAction;
import al.catalog.ui.action.edit.image.ShowAttachDialogAction;
import al.catalog.ui.action.edit.image.RunDetachImageAction;
import al.catalog.ui.action.file.ExitAction;
import al.catalog.ui.action.file.OpenAction;
import al.catalog.ui.action.file.CloseAction;
import al.catalog.ui.action.file.SaveAction;
import al.catalog.ui.action.view.GoToAction;
import al.catalog.ui.action.view.SetIconsViewAction;
import al.catalog.ui.action.view.SetListViewAction;
import al.catalog.ui.action.view.SetTableViewAction;
import al.catalog.ui.action.view.UpOneLevelAction;
import al.catalog.ui.action.view.history.BackAction;
import al.catalog.ui.action.view.history.ClearHistoryAction;
import al.catalog.ui.action.view.history.ForwardAction;

/**
 * ActionManager управляет Swing Action'ами, которые используются в главном и контекстом меню.  
 * 
 * @author Alexander Levin
 */
public class ActionManager implements IConnectionListener, IDBActionListener, IHistoryListener, IDBTreeModelListener {
	
	private List<IErrorListener> listeners = new ArrayList<IErrorListener>();
	private Map<String, CustomAction> actions = new HashMap<String, CustomAction>();
	private Map<String, Boolean> actionsStates = new HashMap<String, Boolean>();
	private Map<String, Object> properties = new HashMap<String, Object>(); 
	
	private DBManager dbManager;
	private DBTreeModel dbModel;
	
	private CatFrame mainFrame;
	
	public ActionManager(DBManager dbManager, DBTreeModel dbModel) {
		this.dbManager = dbManager;
		this.dbModel = dbModel;
		
		ActionManager actionManager = this;
		dbModel.addListener(this);
		
		dbManager.addConnectionListener(actionManager);
		dbManager.addActionListener(actionManager);
		dbModel.addHistoryListener(actionManager);
		
		CustomAction action = new CreateNewImgCategoryAction(actionManager);
		action.setEnabled(false);
		actions.put(CreateNewImgCategoryAction.ACTION_NAME, action);
		
		action = new CreateNewImageAction(actionManager);
		action.setEnabled(false);
		actions.put(CreateNewImageAction.ACTION_NAME, action);
		
		action = new RenameAction(actionManager);
		action.setEnabled(false);
		actions.put(RenameAction.ACTION_NAME, action);
		
		action = new RemoveAction(actionManager);
		action.setEnabled(false);
		actions.put(RemoveAction.ACTION_NAME, action);
		
		action = new OpenAction(actionManager);
		action.setEnabled(true);
		actions.put(OpenAction.ACTION_NAME, action);
		
		action = new CloseAction(actionManager);
		action.setEnabled(false);
		actions.put(CloseAction.ACTION_NAME, action);
		
		action = new UndoAction(actionManager);
		action.setEnabled(false);
		actions.put(UndoAction.ACTION_NAME, action);
		
		action = new RedoAction(actionManager);
		action.setEnabled(false);
		actions.put(RedoAction.ACTION_NAME, action);
		
		action = new SaveAction(actionManager);
		action.setEnabled(false);
		actions.put(SaveAction.ACTION_NAME, action);
		
		action = new CreateAction(actionManager);
		action.setEnabled(false);
		actions.put(CreateAction.ACTION_NAME, action);
		
		action = new ShowMoveDialogAction(actionManager);
		action.setEnabled(false);
		actions.put(ShowMoveDialogAction.ACTION_NAME, action);
		
		action = new ShowPropertiesDialogAction(actionManager);
		action.setEnabled(true);
		actions.put(ShowPropertiesDialogAction.ACTION_NAME, action);
		
		action = new MoveAction(actionManager);
		action.setEnabled(true);
		actions.put(MoveAction.ACTION_NAME, action);
		
		action = new ShowRenameDialogAction(actionManager);
		action.setEnabled(false);
		actions.put(ShowRenameDialogAction.ACTION_NAME, action);
		
		action = new SetIconsViewAction(actionManager);
		action.setEnabled(false);
		actions.put(SetIconsViewAction.ACTION_NAME, action);
		
		action = new SetListViewAction(actionManager);
		action.setEnabled(false);
		actions.put(SetListViewAction.ACTION_NAME, action);
		
		action = new SetTableViewAction(actionManager);
		action.setEnabled(false);
		actions.put(SetTableViewAction.ACTION_NAME, action);
		
		action = new BackAction(actionManager);
		action.setEnabled(false);
		actions.put(BackAction.ACTION_NAME, action);
		
		action = new ForwardAction(actionManager);
		action.setEnabled(false);
		actions.put(ForwardAction.ACTION_NAME, action);
		
		action = new ExitAction(actionManager);
		actions.put(ExitAction.ACTION_NAME, action);
		
		action = new UpOneLevelAction(actionManager);
		action.setEnabled(false);
		actions.put(UpOneLevelAction.ACTION_NAME, action);
		
		action = new GoToAction(actionManager);
		action.setEnabled(false);
		actions.put(GoToAction.ACTION_NAME, action);
		
		action = new ClearHistoryAction(actionManager);
		action.setEnabled(false);
		actions.put(ClearHistoryAction.ACTION_NAME, action);
		
		action = new ShowAttachDialogAction(actionManager);
		action.setEnabled(false);
		actions.put(ShowAttachDialogAction.ACTION_NAME, action);
		
		action = new RunAttachImageAction(actionManager);
		action.setEnabled(false);
		actions.put(RunAttachImageAction.ACTION_NAME, action);
		
		action = new RunDetachImageAction(actionManager);
		action.setEnabled(false);
		actions.put(RunDetachImageAction.ACTION_NAME, action);
	}
	
	public void freezeActions() {
		for (String key : actions.keySet()) {
			CustomAction action = actions.get(key);
			actionsStates.put(key, action.isEnabled());
			action.setEnabled(false);
		}
	}
	
	public void unfreezeActions() {
		for (String key : actions.keySet()) {
			CustomAction action = actions.get(key);
			Boolean state = actionsStates.get(key);
			if (action != null && state != null) {
				action.setEnabled(state);				
			}						
		}		
	}
	
	public CustomAction getAction(String key) {
		if (actions.containsKey(key)) {
			return actions.get(key);
		}
		return null;
	}
	
	public DBManager getDBManager() {
		return dbManager;
	}
	
	public DBTreeModel getModel() {
		return dbModel;
	}
	
	public void addErrorListener(IErrorListener listener) {
		listeners.add(listener);
	}
	
	public void removeErrorListener(IErrorListener listener) {
		listeners.remove(listener);
	}
	
	public void fireListeners(String error) {
		for (IErrorListener listener : listeners) {
			listener.showError(error);
		}
	}

	public void connectionWasClosed() {
		Action connectAction = actions.get(OpenAction.ACTION_NAME);
		connectAction.setEnabled(true);
		
		Action disconnectAction = actions.get(CloseAction.ACTION_NAME);
		disconnectAction.setEnabled(false);
		
		Action action = actions.get(CreateAction.ACTION_NAME);
		action.setEnabled(false);
		
		action = actions.get(RemoveAction.ACTION_NAME);
		action.setEnabled(false);
		
		action = actions.get(RenameAction.ACTION_NAME);
		action.setEnabled(false);
		
		action = actions.get(SetIconsViewAction.ACTION_NAME);
		action.setEnabled(false);
		
		action = actions.get(SetListViewAction.ACTION_NAME);
		action.setEnabled(false);
		
		action = actions.get(SetTableViewAction.ACTION_NAME);
		action.setEnabled(false);
		
		action = actions.get(GoToAction.ACTION_NAME);
		action.setEnabled(false);
		
		action = actions.get(SaveAction.ACTION_NAME);
		action.setEnabled(false);
		
		action = actions.get(UndoAction.ACTION_NAME);
		actionsStates.put(UndoAction.ACTION_NAME, false);
		action.setEnabled(false);
		
		action = actions.get(RedoAction.ACTION_NAME);
		actionsStates.put(UndoAction.ACTION_NAME, false);
		action.setEnabled(false);
		
		dbModel.clearHistory();
	}

	public void connectionWasOpened() {
		Action action = actions.get(OpenAction.ACTION_NAME);
		action.setEnabled(false);
		
		action = actions.get(CloseAction.ACTION_NAME);
		action.setEnabled(true);
		
		action = actions.get(SetIconsViewAction.ACTION_NAME);
		action.setEnabled(true);
		
		action = actions.get(SetListViewAction.ACTION_NAME);
		action.setEnabled(true);
		
		action = actions.get(SetTableViewAction.ACTION_NAME);
		action.setEnabled(true);
		
		action = actions.get(GoToAction.ACTION_NAME);
		action.setEnabled(true);
	}

	public void redoBecameDisabled() {
		Action action = actions.get(RedoAction.ACTION_NAME);
		actionsStates.put(RedoAction.ACTION_NAME, false);
		action.setEnabled(false);		
	}

	public void redoBecameEnabled() {
		Action action = actions.get(RedoAction.ACTION_NAME);
		actionsStates.put(RedoAction.ACTION_NAME, true);
		action.setEnabled(true);		
	}

	public void undoBecameDisabled() {
		Action action = actions.get(UndoAction.ACTION_NAME);
		actionsStates.put(UndoAction.ACTION_NAME, false);
		action.setEnabled(false);		
	}

	public void undoBecameEnabled() {
		Action action = actions.get(UndoAction.ACTION_NAME);
		actionsStates.put(UndoAction.ACTION_NAME, true);
		action.setEnabled(true);
	}

	public void someActionWasAdded() {
		Action action = actions.get(SaveAction.ACTION_NAME);
		actionsStates.put(SaveAction.ACTION_NAME, true);
		action.setEnabled(true);
	}
	
	public void setProperty(String key, Object value) {
		properties.put(key, value);
	}
	
	public Object getProperty(String key) {
		if  (properties.containsKey(key)) {
			return properties.get(key);
		}
		return null;
	}
	
	public void removeProperty(String key) {
		if (properties.containsKey(key)) {
			properties.remove(key);			
		}
	}
	
	private void checkActions() {		
        Action createAction = getAction(CreateAction.ACTION_NAME);
        Action removeAction = getAction(RemoveAction.ACTION_NAME);        
        Action showRenameDialogAction = getAction(ShowRenameDialogAction.ACTION_NAME);
        Action createNewImgCatAction = getAction(CreateNewImgCategoryAction.ACTION_NAME);
        Action createNewImageAction = getAction(CreateNewImageAction.ACTION_NAME);
        Action moveAction = getAction(ShowMoveDialogAction.ACTION_NAME);
        Action upOneLevelAction = getAction(UpOneLevelAction.ACTION_NAME);
        Action attachImageAction = getAction(ShowAttachDialogAction.ACTION_NAME);
        Action detachImageAction = getAction(RunDetachImageAction.ACTION_NAME);
        Action showPropertiesAction = getAction(ShowPropertiesDialogAction.ACTION_NAME);
                        
        List<ITreeNode> nodes = dbModel.getActiveNodes();
        
        if (nodes == null || nodes.size() == 0) {
        	Logger.logIntoStack("Nodes is empty");
        	createAction.setEnabled(false);        	
        	createNewImgCatAction.setEnabled(false);
        	createNewImageAction.setEnabled(false);
        	removeAction.setEnabled(false);
        	showRenameDialogAction.setEnabled(false);
        	moveAction.setEnabled(false);
        	upOneLevelAction.setEnabled(false);
        	attachImageAction.setEnabled(false);
        	detachImageAction.setEnabled(false);
        	showPropertiesAction.setEnabled(false);
        } else if (nodes.size() == 1) {
        	ITreeNode node = nodes.get(0);        	
        	if (node instanceof ImageNode) {        	
            	createAction.setEnabled(false);            	
            	createNewImgCatAction.setEnabled(false);
            	createNewImageAction.setEnabled(false);
            	removeAction.setEnabled(true);            	
            	showRenameDialogAction.setEnabled(true);
            	moveAction.setEnabled(true);
            	showPropertiesAction.setEnabled(true);
            	if (node instanceof ImageNode) {
            		ImageNode image = (ImageNode) node;
            		if (image.hasChildren()) {
            			attachImageAction.setEnabled(false);
            			detachImageAction.setEnabled(true);
            		} else {
            			attachImageAction.setEnabled(true);
            			detachImageAction.setEnabled(false);            			
            		}            		
            	} else {
            		attachImageAction.setEnabled(false);
            		detachImageAction.setEnabled(false);
            	}
            } else if (node instanceof ImgCategoryNode) {
            	if (node.isLogicalRoot()) {
            		removeAction.setEnabled(false);
            		moveAction.setEnabled(false);
            	} else {
            		removeAction.setEnabled(true);
            		moveAction.setEnabled(true);
            	}        	
            	createAction.setEnabled(true);            	
            	createNewImgCatAction.setEnabled(true);
            	createNewImageAction.setEnabled(true);          	
            	showRenameDialogAction.setEnabled(true);
            	attachImageAction.setEnabled(false);
            	detachImageAction.setEnabled(false);
            	showPropertiesAction.setEnabled(true);
            } else {
            	createAction.setEnabled(false);            	
            	createNewImgCatAction.setEnabled(false);
            	createNewImageAction.setEnabled(false);
            	removeAction.setEnabled(false);
            	showRenameDialogAction.setEnabled(false);
            	moveAction.setEnabled(false);
            	upOneLevelAction.setEnabled(false);
            	attachImageAction.setEnabled(false);
            	detachImageAction.setEnabled(false);
            	showPropertiesAction.setEnabled(false);
            }
        	
        	if (node != null && !node.isLogicalRoot() && node.hasParent() &&
        			!dbModel.getOpenedNode().isLogicalRoot()) {
        		upOneLevelAction.setEnabled(true);
        	} else {
        		upOneLevelAction.setEnabled(false);        		
        	}
        } else if (nodes != null) {
        	List<String> classes = new ArrayList<String>();
        	boolean containsRoot = false;
        	for (ITreeNode node : nodes) {
        		if (node != null) {
        			String className = node.getClass().getName();
            		if (!classes.contains(className)) {
            			classes.add(className);
            		}
            		if (node.isLogicalRoot() || !node.hasParent()) {
            			containsRoot = true;        			
            		}        			
        		}        		
        	}
        	
        	createAction.setEnabled(false);        	
        	createNewImgCatAction.setEnabled(false);
        	createNewImageAction.setEnabled(false);
        	showRenameDialogAction.setEnabled(false);
        	upOneLevelAction.setEnabled(false);        	
        	attachImageAction.setEnabled(false);
        	detachImageAction.setEnabled(false);
        	showPropertiesAction.setEnabled(false);
        	
        	if (!classes.contains(FolderNode.class.getName()) &&
        			!classes.contains(FileNode.class.getName()) && !containsRoot) {
        		moveAction.setEnabled(true);
            	removeAction.setEnabled(true);        		
        	} else {
        		moveAction.setEnabled(false);
            	removeAction.setEnabled(false);
        	}
        }		
	}
	
	public CatFrame getMainFrame() {
		return mainFrame;
	}
	
	public void setMainFrame(CatFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void backBecameDisabled() {
		Action action = actions.get(BackAction.ACTION_NAME);
		action.setEnabled(false);
	}

	public void backBecameEnabled() {
		Action action = actions.get(BackAction.ACTION_NAME);
		action.setEnabled(true);		
	}

	public void forwardBecameDisabled() {
		Action action = actions.get(ForwardAction.ACTION_NAME);
		action.setEnabled(false);
	}

	public void forwardBecameEnabled() {
		Action action = actions.get(ForwardAction.ACTION_NAME);
		action.setEnabled(true);		
	}

	public void clearBecameDisabled() {
		Action action = actions.get(ClearHistoryAction.ACTION_NAME);
		action.setEnabled(false);
		
	}

	public void clearBecameEnabled() {
		Action action = actions.get(ClearHistoryAction.ACTION_NAME);
		action.setEnabled(true);		
	}

	public void afterActionExecuting(DBAction dbAction) {
		
	}

	public void beforeActionExecuting(DBAction dbAction) {
		
	}

	public void actionWasAborted(DBAction dbAction) {
		
	}

	public void actionThrowException(DBAction dbAction, Exception exception) {
		
	}

	public void activeNodeWasChanged(ITreeNode node) {
		Logger.openStack();
		checkActions();
		Logger.closeStack();		
	}

	public void activeNodeWasChanged(ITreeNode node, Object source) {
		Logger.openStack();
		checkActions();
		Logger.closeStack();
	}

	public void activeNodesWasChanged(List<ITreeNode> nodes) {
		Logger.openStack();
		checkActions();
		Logger.closeStack();
	}
	
	public void activeNodesWasChanged(List<ITreeNode> nodes, Object source) {
		Logger.openStack();
		checkActions();
		Logger.closeStack();		
	}

	public void nodeStructureWasChanged(ITreeNode node) {
		
	}

	public void nodeWasChanged(ITreeNode node) {
		
	}

	public void nodeWasInserted(ITreeNode node, ITreeNode parent) {
		
	}

	public void nodeWasRemoved(ITreeNode node, ITreeNode parent, int index) {
		
	}

	public void onAfterChangeNode(ITreeNode node) {
		
	}

	public void onBeforeChangeNode(ITreeNode node) {
		
	}

	public void openedNodeWasChanged(ITreeNode node) {
		
	}

	public void openedNodeWasChanged(ITreeNode node, Object source) {
		
	}	
}
