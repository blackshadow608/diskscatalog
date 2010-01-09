package al.catalog.ui.popup;

import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.JComponent;

import al.catalog.ui.action.ActionManager;


public class PopupHelper {
	
	public static final int TYPE_FILE_LIST = 1;
	public static final int TYPE_TREE = 2;
	
	public static void createMenu(JComponent component, ActionManager actionManager, int type) {
		switch (type) {			
			case TYPE_FILE_LIST: createFileViewPopup(component, actionManager); break;
			case TYPE_TREE: createTreePopup(component, actionManager); break;
		}				
	}
	
	private static final void createFileViewPopup(JComponent component, ActionManager actionManager) {
		if (component instanceof JList) {
			JList list = (JList) component;
			list.addMouseListener(new ListPopupMouseListener(list, actionManager));
		}
	}
	
	private static final void createTreePopup(JComponent component, ActionManager actionManager) {
		if (component instanceof JTree) {
			JTree tree = (JTree) component;
			tree.addMouseListener(new TreePopupMouseListener(tree, actionManager));
		}		
	}
}
