package disks.catalog.ui.action.edit.create;

import java.awt.event.ActionEvent;

import disks.catalog.model.action.IActionCallback;
import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.resource.ResourceManager;


/**
 * Нужен только для того, чтобы контролировать в интерфейсе пункт меню по созданию
 * новых узлов. Никаких действий не выполняет. Служит как заглушка. 
 * 
 * @author Alexander Levin
 */
public class CreateAction extends CustomAction {
	
	public static String ACTIVE_AND_OPENED_EQUAL = "Active and opened equal";
	public static String ACTIVE_AND_OPENED_NOT_EQUAL = "Active and opened not equal";
	
	public static final String ACTION_NAME = "Create";
	
	private static CreateNodeCallback callback = null;
	
	private static final String KEY = "createAction.text";
	
	public CreateAction(ActionManager actionManager) {		
		super(actionManager, ResourceManager.getString(KEY));		
	}

	public void actionPerformed(ActionEvent e) {
		
	}
	
	public static IActionCallback getCreateCallback(DBTreeModel dbModel, String actionCommand) {
		if(callback == null) {
			callback = new CreateNodeCallback(dbModel, actionCommand);			
		} else {
			callback.setActionCommand(actionCommand);			
		}
		return callback;
	}
}
