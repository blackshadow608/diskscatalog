package al.catalog.ui.util;

import java.awt.Dimension;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;

/**
 * Утилитный класс <b>MenuItemSizeUtil</b>. По умолчанию размер меню в Java
 * Swing задается размерами самой длинной надписи, поэтому ширина всех меню
 * будет различна и зависеть от длины надписи. Для того, чтобы задать одинаковую
 * достаточную ширину для всех меню можно воспользоваться данным классом.
 * 
 * @author Alexander Levin
 */
public class MenuItemSizeUtil {

	private static final Dimension MENU_ITEM_SIZE = new Dimension(150, 22);

	/**
	 * Задает предустановленный размер каждому пункту меню для JMenuBar, ссылка
	 * на который передается методу в качестве параметра.
	 * 
	 * @param menuBar
	 *            - ссылка на <b>JMenuBar</b>, размеры пунктов меню которого
	 *            необходимо изменить.
	 */
	public static void setMenuItemSize(JMenuBar menuBar) {
		setMenuItemSize(menuBar, MENU_ITEM_SIZE);
	}

	/**
	 * Задает размер каждому пункту меню для JMenuBar.
	 * 
	 * @param menuBar
	 *            - ссылка на <b>JMenuBar</b>, размеры пунктов меню которого
	 *            необходимо изменить.
	 * @param size
	 *            - размер <b>Dimension</b>, который необходимо установить для
	 *            каждого пункта меню.
	 */
	public static void setMenuItemSize(JMenuBar menuBar, Dimension size) {
		for (int i = 0; i < menuBar.getMenuCount(); i++) {
			JMenu menu = menuBar.getMenu(i);
			int width = menu.getPreferredSize().width + 2;
			menu.setPreferredSize(new Dimension(width, size.height));
			setMenuItemSize(menu);
		}
	}

	/**
	 * Задает предустановленный размер каждому пункту меню для JPopupMenu,
	 * ссылка на который передается методу в качестве параметра.
	 * 
	 * @param poupMenu
	 *            - ссылка на <b>JPopupMenu</b>, размеры пунктов меню которого
	 *            необходимо изменить.
	 */
	public static void setMenuItemSize(JPopupMenu popupMenu) {
		setMenuItemSize(popupMenu, MENU_ITEM_SIZE);
	}

	/**
	 * Задает размер каждому пункту меню для JPopupMenu.
	 * 
	 * @param popupMenu
	 *            - ссылка на <b>JPopupMenu</b>, размеры пунктов меню которого
	 *            необходимо изменить.
	 * @param size
	 *            - размер <b>Dimension</b>, который необходимо установить для
	 *            каждого пункта меню.
	 */
	public static void setMenuItemSize(JPopupMenu popupMenu, Dimension size) {
		MenuElement[] subElements = popupMenu.getSubElements();
		if (subElements != null && subElements.length > 0) {
			for (int i = 0; i < subElements.length; i++) {
				MenuElement menuElement = subElements[i];
				if (menuElement != null) {
					JMenuItem menuItem = (JMenuItem) menuElement;
					menuItem.setPreferredSize(size);

					if (menuItem instanceof JMenu) {
						JMenu menu = (JMenu) menuItem;
						setMenuItemSize(menu);
					}
				}
			}
		}
	}

	/**
	 * Задает размер каждому пункту меню для JMenu.
	 * 
	 * @param parentMenu
	 *            - ссылка на <b>JMenu</b>, размеры пунктов меню которого
	 *            необходимо изменить.
	 * @param size
	 *            - размер <b>Dimension</b>, который необходимо установить для
	 *            каждого пункта меню.
	 */
	public static void setMenuItemSize(JMenu parentMenu, Dimension size) {
		for (int i = 0; i < parentMenu.getItemCount(); i++) {
			JMenuItem menuItem = parentMenu.getItem(i);
			if (menuItem != null) {
				menuItem.setPreferredSize(size);
				if (menuItem instanceof JMenu) {
					JMenu menu = (JMenu) menuItem;
					setMenuItemSize(menu);
				}
			}
		}
	}

	/**
	 * Задает предустановленный размер каждому пункту меню для JMenu, ссылка на
	 * который передается методу в качестве параметра.
	 * 
	 * @param menu
	 *            - ссылка на <b>JMenu</b>, размеры пунктов меню которого
	 *            необходимо изменить.
	 */
	public static void setMenuItemSize(JMenu menu) {
		setMenuItemSize(menu, MENU_ITEM_SIZE);
	}
}
