package al.catalog.ui.view.list;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTree;

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
	
	private ActionManager actionManager;
	
	public ListFocusListener(ActionManager actionManager) {
		this.actionManager = actionManager;		
	}
	
	public void focusGained(FocusEvent e) {
		Logger.openStack("ListFocusListener: focusGained()");
		Object tree = actionManager.getProperty(ActionManager.PROPERTY_TREE);
		if (e.getOppositeComponent() == tree) {
			CustomList list = (CustomList) e.getSource();			
			list.selectLast();
		}
		Logger.closeStack();
	}
	
	public void focusLost(FocusEvent e) {
		Logger.openStack("ListFocusListener: focusLost()");
		Object tree = actionManager.getProperty(ActionManager.PROPERTY_TREE);
		if (e.getOppositeComponent() == tree) {
			CustomList list = (CustomList) e.getSource();
			list.clearSelection();
		}
		Logger.closeStack();
	}
}
