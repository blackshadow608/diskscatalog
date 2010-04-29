package disks.catalog.model.tree.action;

import disks.catalog.model.DBException;
import disks.catalog.model.DBManager;
import disks.catalog.model.action.DBAction;
import disks.catalog.ui.resource.ResourceManager;

public class SaveChangesAction extends DBAction {
	
	private Thread thread;
	private DBAction action;
	
	private static final String PROGRESS_TEXT = "saveChangesAction.progressText";
	
	public SaveChangesAction(DBManager dbManager) {
		super(dbManager);
		this.action = this;
	}

	public void execute() {
		thread = new Thread() {

			private boolean isInterrupted = false;

			public void run() {

				try {
					dbManager.save();
					dbManager.open();
				} catch (DBException e) {
					e.printStackTrace();
					isInterrupted = true;
					dbManager.fireAfterAction(action);
					dbManager.fireActionExecutingException(action, e);
				}

				if (!isInterrupted) {
					dbManager.fireRedoDisabled();
					dbManager.fireUndoDisabled();					
					dbManager.fireAfterAction(action);
				}
			}

			public void interrupt() {
				super.interrupt();
				isInterrupted = true;
			}
		};

		thread.start();
		dbManager.fireBeforeAction(action);
	}

	public void unexecute() {
		
	}
	
	public String getProgressText() {
		return ResourceManager.getString(PROGRESS_TEXT);
	}

	public boolean isCancelable() {
		return false;
	}
	
	public boolean isPausable() {
		return false;
	}
}
