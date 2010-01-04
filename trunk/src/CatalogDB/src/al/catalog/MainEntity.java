package al.catalog;

import al.catalog.model.DBManager;
import al.catalog.ui.action.ActionManager;

public class MainEntity {
	
	private DBManager dbManager;
	private ActionManager actionManager;
	
	public MainEntity() {
		
	}
	
	public MainEntity(DBManager dbManager, ActionManager aManager) {
		this.dbManager = dbManager;
		this.actionManager = aManager;
	}
	
	public void setDBManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}
	
	public DBManager getDBManager() {
		return dbManager;
	}
	
	public void setActionManager(ActionManager actionManager) {
		this.actionManager = actionManager;
	}
	
	public ActionManager getActionManager() {
		return actionManager;
	}
}
