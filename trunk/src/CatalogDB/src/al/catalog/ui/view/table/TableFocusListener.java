package al.catalog.ui.view.table;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import al.catalog.logger.Logger;
import al.catalog.ui.action.ActionManager;

public class TableFocusListener implements FocusListener {
	
private ActionManager actionManager;
	
	public TableFocusListener(ActionManager actionManager) {
		this.actionManager = actionManager;		
	}
	
	public void focusGained(FocusEvent e) {
		Logger.openStack("TableFocusListener: focusGained()");
		Object tree = actionManager.getProperty(ActionManager.PROPERTY_TREE);
		if (e.getOppositeComponent() == tree) {
			CustomTable table = (CustomTable) e.getSource();			
			table.selectLast();
		}
		Logger.closeStack();
	}
	
	public void focusLost(FocusEvent e) {
		Logger.openStack("TableFocusListener: focusLost()");
		Object tree = actionManager.getProperty(ActionManager.PROPERTY_TREE);
		if (e.getOppositeComponent() == tree) {
			CustomTable table = (CustomTable) e.getSource();
			table.clearSelection();
		}
		Logger.closeStack();
	}
}
