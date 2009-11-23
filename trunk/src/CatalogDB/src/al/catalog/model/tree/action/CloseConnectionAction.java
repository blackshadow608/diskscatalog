package al.catalog.model.tree.action;

import al.catalog.model.DBAction;
import al.catalog.model.DBException;
import al.catalog.model.DBManager;
import al.catalog.ui.resource.ResourceManager;

/**
 * Action, который закрывает соединение с базой данных.
 * 
 * @author Alexander Levin
 */
public class CloseConnectionAction extends DBAction {
	
	private Thread thread;
	private DBAction action;
	private static final String PROGRESS_TEXT = "closeConnectionAction.progressText";	
	
	public CloseConnectionAction(DBManager dbManager) {
		super(dbManager);
		this.action = this;
	}

	public void abort() {
		
	}

	public void execute() {
		thread = new Thread() {

			private boolean isInterrupted = false;

			public void run() {
				try {
					dbManager.close();
				} catch (DBException e) {
					e.printStackTrace();
					isInterrupted = true;
					dbManager.fireAfterAction(action);
					dbManager.fireActionExecutingException(action, e);
				}

				if (!isInterrupted) {
					dbManager.fireConnectionClosed();
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
}
