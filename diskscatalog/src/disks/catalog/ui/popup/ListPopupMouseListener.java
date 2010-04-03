package disks.catalog.ui.popup;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.model.tree.types.images.ImageNode;
import disks.catalog.model.tree.types.images.ImgCategoryNode;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.CustomAction;
import disks.catalog.ui.action.edit.RemoveAction;
import disks.catalog.ui.action.edit.ShowMoveDialogAction;
import disks.catalog.ui.action.edit.ShowPropertiesDialogAction;
import disks.catalog.ui.action.edit.ShowRenameDialogAction;
import disks.catalog.ui.action.edit.create.CreateAction;
import disks.catalog.ui.action.edit.create.CreateNewImageAction;
import disks.catalog.ui.action.edit.create.CreateNewImgCategoryAction;
import disks.catalog.ui.action.edit.image.RunDetachImageAction;
import disks.catalog.ui.action.edit.image.ShowAttachDialogAction;
import disks.catalog.ui.util.MenuItemSizeUtil;
import disks.catalog.ui.view.list.CustomListModel;


/**
 * 
 * Класс ListMouseListener. Предназначен для показа контекстного меню узла (открытого - opened
 * или активного - active). Кроме этого ранее была проблема при выделении узлов FileView (компонента JList):
 * если кликнуть на свободном месте  компонента выделялся последний элемент списка. Проблема была решена
 * путем удаления встроенных MouseListener'ов компонента JList (см. также al.catalog.ui.util.ListMouseUtil).
 * 
 * @author Alexander Levin
 */
public class ListPopupMouseListener implements MouseListener {
	
	private static final boolean FOCUSABLE = true;
	
	protected JList list;
	protected ActionManager actionManager;
	protected JPopupMenu popupMenu;
	protected static final int RIGHT_BUTTON = 3;
	
	//private int lastIndex = -1;
	
	public ListPopupMouseListener(JList list, ActionManager actionManager) {
		this.list = list;
		this.actionManager = actionManager;
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
				
	}

	public void mouseExited(MouseEvent e) {
				
	}

	public void mousePressed(MouseEvent e) {
		/*int index = -1;
		int x = e.getX();
		int y = e.getY();
		for (int i = list.getFirstVisibleIndex(); i <= list.getLastVisibleIndex(); i++) {
			Rectangle cellBounds = list.getCellBounds(i, i);
			if (cellBounds != null) {
				int minX = (int) cellBounds.getMinX();
				int maxX = (int) cellBounds.getMaxX();
				int minY = (int) cellBounds.getMinY();
				int maxY = (int) cellBounds.getMaxY();
				if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
					index = i;
				}
			}
		}

		if (index < 0) {
			list.transferFocus();
			list.clearSelection();
			CustomListModel listModel = (CustomListModel) list.getModel();
			listModel.activeNodeWasChanged(null);
		} else {
			list.requestFocus();
		}

		list.setSelectedIndex(index);*/
	}

	public void mouseReleased(MouseEvent e) {
		CustomListModel listModel = (CustomListModel) list.getModel();
		ITreeNode openedNode = listModel.getOpenedNode();
		if (e.getButton() == RIGHT_BUTTON) {
			int[] selIndices = list.getSelectedIndices();
			if (selIndices.length > 0) {
				ITreeNode firstSelected = (ITreeNode) list.getModel().getElementAt(0);
				if (firstSelected instanceof ImgCategoryNode || firstSelected instanceof ImageNode) {					
					createPopupForActiveImgNode(e);
				}				
			} else {
				if (openedNode != null) {
					if (openedNode instanceof ImgCategoryNode) {
						createPopupForOpenedImgNode(e);					
					}					
				}								
			}
		}	
	}
	
	private void createPopupForActiveImgNode(MouseEvent e) {
		popupMenu = new JPopupMenu();
		popupMenu.setInvoker(list);
		popupMenu.setFocusable(FOCUSABLE);
		
		JMenuItem menuItem = new JMenuItem();
		Action action = actionManager.getAction(RemoveAction.ACTION_NAME);		
		menuItem.setAction(action);
		popupMenu.add(menuItem);
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(ShowMoveDialogAction.ACTION_NAME);		
		menuItem.setAction(action);
		popupMenu.add(menuItem);
		
		menuItem = new JMenuItem();
		popupMenu.add(menuItem);
		action = actionManager.getAction(ShowRenameDialogAction.ACTION_NAME);		
		menuItem.setAction(action);
		
		popupMenu.addSeparator();
		
		menuItem = new JMenuItem();
		popupMenu.add(menuItem);
		action = actionManager.getAction(ShowAttachDialogAction.ACTION_NAME);
		menuItem.setAction(action);
		
		menuItem = new JMenuItem();
		popupMenu.add(menuItem);
		action = actionManager.getAction(RunDetachImageAction.ACTION_NAME);
		menuItem.setAction(action);
		
		popupMenu.addSeparator();
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(ShowPropertiesDialogAction.ACTION_NAME);
		menuItem.setAction(action);
		popupMenu.add(menuItem);		
		
		MenuItemSizeUtil.setMenuItemSize(popupMenu);
		
		int x = list.getLocationOnScreen().x + e.getX();
		int y = list.getLocationOnScreen().y + e.getY();
		popupMenu.setLocation(x, y);
		popupMenu.setVisible(true);
	}
	
	private void createPopupForOpenedImgNode(MouseEvent e) {
		popupMenu = new JPopupMenu();
		popupMenu.setInvoker(list);
		popupMenu.setFocusable(FOCUSABLE);
		
		JMenu menu = new JMenu();
		menu.setFocusable(FOCUSABLE);
		CustomAction action = actionManager.getAction(CreateAction.ACTION_NAME);
		menu.setAction(action);
		popupMenu.add(menu);
		
		JMenuItem menuItem = new JMenuItem();
		action = actionManager.getAction(CreateNewImgCategoryAction.ACTION_NAME);		
		menuItem.setAction(action);
		menuItem.setActionCommand(CreateAction.ACTIVE_AND_OPENED_NOT_EQUAL);
		menu.add(menuItem);
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(CreateNewImageAction.ACTION_NAME);		
		menuItem.setAction(action);
		menuItem.setActionCommand(CreateAction.ACTIVE_AND_OPENED_NOT_EQUAL);
		menu.add(menuItem);
		
		/*menuItem = new JMenuItem();
		action = actionManager.getAction(RemoveAction.ACTION_NAME);		
		menuItem.setAction(action);
		popupMenu.add(menuItem);
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(ShowMoveDialogAction.ACTION_NAME);		
		menuItem.setAction(action);
		popupMenu.add(menuItem);*/
		
		/*menuItem = new JMenuItem();
		popupMenu.add(menuItem);
		action = actionManager.getAction(RenameAction.ACTION_NAME);		
		menuItem.setAction(action);*/
		
		/*menuItem = new JMenuItem();
		popupMenu.add(menuItem);
		action = actionManager.getAction(ShowRenameDialogAction.ACTION_NAME);		
		menuItem.setAction(action);*/
		
		/*
		popupMenu.addSeparator();
		
		menuItem = new JMenuItem();
		popupMenu.add(menuItem);
		action = actionManager.getAction(CollapseAction.ACTION_NAME);
		menuItem.setAction(action);
		
		menuItem = new JMenuItem();
		popupMenu.add(menuItem);		
		action = actionManager.getAction(ExpandAction.ACTION_NAME);
		menuItem.setAction(action);*/
		
		popupMenu.addSeparator();
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(ShowPropertiesDialogAction.ACTION_NAME);
		menuItem.setAction(action);
		popupMenu.add(menuItem);		
		
		MenuItemSizeUtil.setMenuItemSize(popupMenu);
		
		int x = list.getLocationOnScreen().x + e.getX();
		int y = list.getLocationOnScreen().y + e.getY();
		popupMenu.setLocation(x, y);
		popupMenu.setVisible(true);
	}
}
