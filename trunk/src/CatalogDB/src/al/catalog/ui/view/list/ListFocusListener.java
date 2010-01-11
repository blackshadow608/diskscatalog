package al.catalog.ui.view.list;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import al.catalog.logger.Logger;
import al.catalog.ui.action.ActionManager;

/**
 * Слушает фокус у списка узлов. Как только список узлов получает фокус,
 * выделяется последний выделенный узел списка. При потере фокуса компонентом - 
 * очищает выделение списка узлов.
 * 
 * @author Alexander Levin
 */
public class ListFocusListener implements FocusListener {
	
	private ActionManager aManager;
	
	public ListFocusListener(ActionManager actionManager) {
		this.aManager = actionManager;		
	}
	
	public void focusGained(FocusEvent e) {
		Logger.openStack();
		Object tree = aManager.getMainFrame().getContentPanel().getTreePanel().getTree();
		if (e.getOppositeComponent() == tree) {
			CatalogList list = (CatalogList) e.getSource();			
			list.selectLast();
		}
		Logger.closeStack();
	}
	
	public void focusLost(FocusEvent e) {
		Logger.openStack();
		Object tree = aManager.getMainFrame().getContentPanel().getTreePanel().getTree();
		if (e.getOppositeComponent() == tree) {
			CatalogList list = (CatalogList) e.getSource();
			list.clearSelection();
		}
		Logger.closeStack();
	}
}
