package al.catalog.ui.view.table;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import al.catalog.logger.Logger;
import al.catalog.ui.action.ActionManager;

public class TableFocusListener implements FocusListener {
	
private ActionManager aManager;
	
	public TableFocusListener(ActionManager actionManager) {
		this.aManager = actionManager;		
	}
	
	public void focusGained(FocusEvent e) {
		Logger.openStack();
		Object tree = aManager.getMainFrame().getContentPanel().getTreePanel().getTree();
		if (e.getOppositeComponent() == tree) {
			CatalogTable table = (CatalogTable) e.getSource();			
			table.selectLast();
		}
		Logger.closeStack();
	}
	
	public void focusLost(FocusEvent e) {
		Logger.openStack();
		Object tree = aManager.getMainFrame().getContentPanel().getTreePanel().getTree();
		if (e.getOppositeComponent() == tree) {
			CatalogTable table = (CatalogTable) e.getSource();
			table.clearSelection();
		}
		Logger.closeStack();
	}
}
