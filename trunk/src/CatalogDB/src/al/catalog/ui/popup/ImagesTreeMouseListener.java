package al.catalog.ui.popup;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;

import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.action.edit.ShowMoveDialogAction;
import al.catalog.ui.action.edit.RemoveAction;
import al.catalog.ui.action.edit.ShowPropertiesDialogAction;
import al.catalog.ui.action.edit.ShowRenameDialogAction;
import al.catalog.ui.action.edit.create.CreateAction;
import al.catalog.ui.action.edit.create.CreateNewImageAction;
import al.catalog.ui.action.edit.create.CreateNewImgCategoryAction;
import al.catalog.ui.util.MenuItemSizeUtil;

public class ImagesTreeMouseListener extends AbstractTreeMouseListener {
	
	public ImagesTreeMouseListener(JTree tree, ActionManager actionManager) {
		super(tree, actionManager);
		createPopupMenu();		
	}

	protected void createPopupMenu() {
		popupMenu = new JPopupMenu();
		popupMenu.setInvoker(tree);
		
		JMenu menu = new JMenu(CREATE);
		CustomAction action = actionManager.getAction(CreateAction.ACTION_NAME);
		menu.setAction(action);
		popupMenu.add(menu);
		
		JMenuItem menuItem = new JMenuItem();
		action = actionManager.getAction(CreateNewImgCategoryAction.ACTION_NAME);		
		menuItem.setAction(action);
		menu.add(menuItem);
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(CreateNewImageAction.ACTION_NAME);		
		menuItem.setAction(action);
		menu.add(menuItem);
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(RemoveAction.ACTION_NAME);		
		menuItem.setAction(action);
		popupMenu.add(menuItem);
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(ShowMoveDialogAction.ACTION_NAME);		
		menuItem.setAction(action);
		popupMenu.add(menuItem);
		
		/*menuItem = new JMenuItem();
		popupMenu.add(menuItem);
		action = actionManager.getAction(RenameAction.ACTION_NAME);		
		menuItem.setAction(action);*/
		
		menuItem = new JMenuItem();
		popupMenu.add(menuItem);
		action = actionManager.getAction(ShowRenameDialogAction.ACTION_NAME);		
		menuItem.setAction(action);
		
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
	}
	
	/*public void mousePressed(MouseEvent e) {		
		int row = tree.getRowForLocation(e.getX(), e.getY());
		if (popupMenu != null) {
			popupMenu.setVisible(false);			
		}		
        tree.setSelectionRow(row);
        
        if (row < 0) {        	         	
        	updateTreeSelectionListeners();        	
        }
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == RIGHT_BUTTON && tree.getSelectionPath() != null) {
			int x = tree.getLocationOnScreen().x + e.getX();
			int y = tree.getLocationOnScreen().y + e.getY();						
			popupMenu.setLocation(x, y);
            popupMenu.setVisible(true);
        }		
	}
	
	public void mouseClicked(MouseEvent e) {
				
	}

	public void mouseEntered(MouseEvent e) {
				
	}

	public void mouseExited(MouseEvent e) {
				
	}
	
	private void updateTreeSelectionListeners() {
		TreeSelectionListener[] listeners = tree.getTreeSelectionListeners();
		TreeSelectionEvent event = new TreeSelectionEvent(tree, null, true, null, null);
		for (TreeSelectionListener listener : listeners) {
			listener.valueChanged(event);
		}
	}*/
}
