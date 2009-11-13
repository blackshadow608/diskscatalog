package al.catalog.ui.util;

import java.awt.Dimension;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;

public class MenuItemSizeUtil {
	
	private static final Dimension MENU_ITEM_SIZE = new Dimension(150, 22);
	
	public static void setMenuItemSize(JMenuBar menuBar) {
		for (int i = 0; i < menuBar.getMenuCount(); i++) {
			JMenu menu = menuBar.getMenu(i);
			int width = menu.getPreferredSize().width + 2;
			menu.setPreferredSize(new Dimension(width, MENU_ITEM_SIZE.height));			
			setMenuItemSize(menu);			
		}		
	}
	
	public static void setMenuItemSize(JPopupMenu popupMenu) {
		MenuElement[] subElements = popupMenu.getSubElements();
		if (subElements != null && subElements.length > 0) {
			for (int i = 0; i < subElements.length; i++) {
				MenuElement menuElement = subElements[i];
				if (menuElement != null) {
					JMenuItem menuItem = (JMenuItem) menuElement;
					menuItem.setPreferredSize(MENU_ITEM_SIZE);
					
					if (menuItem instanceof JMenu) {
						JMenu menu = (JMenu) menuItem;
						setMenuItemSize(menu);
					}					
				}
			}
		}				
	}
	
	public static void setMenuItemSize(JMenu parentMenu) {
		for (int i = 0; i < parentMenu.getItemCount(); i++) {
			JMenuItem menuItem = parentMenu.getItem(i);
			if (menuItem != null) {
				menuItem.setPreferredSize(MENU_ITEM_SIZE);				
				if (menuItem instanceof JMenu) {					
					JMenu menu = (JMenu) menuItem;
					setMenuItemSize(menu);
				}				
			}			
		}
	}
}
