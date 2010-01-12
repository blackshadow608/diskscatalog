package al.catalog.ui.popup;

import java.util.List;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;

import al.catalog.model.tree.types.ITreeNode;
import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.model.tree.types.images.ImgCategoryNode;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.action.edit.RemoveAction;
import al.catalog.ui.action.edit.ShowMoveDialogAction;
import al.catalog.ui.action.edit.ShowPropertiesDialogAction;
import al.catalog.ui.action.edit.ShowRenameDialogAction;
import al.catalog.ui.action.edit.create.CreateAction;
import al.catalog.ui.action.edit.create.CreateNewImageAction;
import al.catalog.ui.action.edit.create.CreateNewImgCategoryAction;
import al.catalog.ui.action.edit.image.ShowAttachDialogAction;
import al.catalog.ui.action.edit.image.RunDetachImageAction;
import al.catalog.ui.util.MenuItemSizeUtil;

public class TreePopupMouseListener extends AbstractTreeMouseListener {
	
	private static final boolean FOCUSABLE = true;
	
	public TreePopupMouseListener(JTree tree, ActionManager actionManager) {
		super(tree, actionManager);
	}
	
	protected void createPopupMenu() {
		//TODO Здесь надо составить список всех Class, которые имеют узлы из activeNodes и составить список доступных действий, которые отобразить в popupMenu
		/*ITreeNode node = actionManager.getDBManager().getTreeModel().getActiveNodes();*/
		ITreeNode node = null;
		List<ITreeNode> activeNodes = actionManager.getModel().getActiveNodes();		
		if (activeNodes != null) {
			node = activeNodes.get(0);			
		}
		if (node != null) {
			if (node instanceof ImgCategoryNode) {
				popupMenu = new JPopupMenu();
				popupMenu.setInvoker(tree);
				popupMenu.setFocusable(FOCUSABLE);
				
				JMenu menu = new JMenu(CREATE);
				menu.setFocusable(FOCUSABLE);
				CustomAction action = actionManager.getAction(CreateAction.ACTION_NAME);
				menu.setAction(action);
				popupMenu.add(menu);
				
				JMenuItem menuItem = new JMenuItem();
				action = actionManager.getAction(CreateNewImgCategoryAction.ACTION_NAME);		
				menuItem.setAction(action);
				menuItem.setActionCommand(CreateAction.ACTIVE_AND_OPENED_EQUAL);
				menu.add(menuItem);
				
				menuItem = new JMenuItem();
				action = actionManager.getAction(CreateNewImageAction.ACTION_NAME);		
				menuItem.setAction(action);
				menuItem.setActionCommand(CreateAction.ACTIVE_AND_OPENED_EQUAL);
				menu.add(menuItem);
				
				menuItem = new JMenuItem();
				action = actionManager.getAction(RemoveAction.ACTION_NAME);		
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
				action = actionManager.getAction(ShowPropertiesDialogAction.ACTION_NAME);
				menuItem.setAction(action);
				popupMenu.add(menuItem);		
				
				MenuItemSizeUtil.setMenuItemSize(popupMenu);				
			} else if (node instanceof ImageNode) {
				popupMenu = new JPopupMenu();
				popupMenu.setInvoker(tree);
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
			} else {
				popupMenu = null;
			}
		}
	}	
}
