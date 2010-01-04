package al.catalog.ui;

import java.util.List;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import al.catalog.MainEntity;
import al.catalog.model.DBManager;
import al.catalog.model.tree.IDBTreeModelListener;
import al.catalog.model.tree.types.ITreeNode;
import al.catalog.model.tree.types.images.ImgCategoryNode;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.edit.ShowMoveDialogAction;
import al.catalog.ui.action.edit.RedoAction;
import al.catalog.ui.action.edit.RemoveAction;
import al.catalog.ui.action.edit.ShowPropertiesDialogAction;
import al.catalog.ui.action.edit.ShowRenameDialogAction;
import al.catalog.ui.action.edit.UndoAction;
import al.catalog.ui.action.edit.create.CreateAction;
import al.catalog.ui.action.edit.create.CreateNewImageAction;
import al.catalog.ui.action.edit.create.CreateNewImgCategoryAction;
import al.catalog.ui.action.edit.image.ShowAttachDialogAction;
import al.catalog.ui.action.edit.image.RunDetachImageAction;
import al.catalog.ui.action.file.ExitAction;
import al.catalog.ui.action.file.OpenAction;
import al.catalog.ui.action.file.CloseAction;
import al.catalog.ui.action.file.SaveAction;
import al.catalog.ui.action.view.GoToAction;
import al.catalog.ui.action.view.SetIconsViewAction;
import al.catalog.ui.action.view.SetListViewAction;
import al.catalog.ui.action.view.SetTableViewAction;
import al.catalog.ui.action.view.UpOneLevelAction;
import al.catalog.ui.action.view.history.BackAction;
import al.catalog.ui.action.view.history.ClearHistoryAction;
import al.catalog.ui.action.view.history.ForwardAction;
import al.catalog.ui.resource.ResourceManager;
import al.catalog.ui.util.MenuItemSizeUtil;

public class CatalogMenuBar extends JMenuBar implements IDBTreeModelListener {
	
	private static final String FILE = "mainMenu.file";
	private static final String IMPORT = "mainMenu.import";
	private static final String EXPORT = "mainMenu.export";	
	private static final String EDIT = "mainMenu.edit";
	private static final String VIEW = "mainMenu.view";
	private static final String WINDOW = "mainMenu.window";
	private static final String HELP = "mainMenu.help";
	
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu viewMenu;
	private JMenu windowMenu;
	private JMenu helpMenu;
	
	private JMenu createMenu;
	
	private MainEntity mainEntity;

	public CatalogMenuBar(MainEntity mainEntity) {
		
		this.mainEntity = mainEntity;
		
		ActionManager actionManager = mainEntity.getActionManager();
		DBManager dbManager = mainEntity.getDBManager();
		
		setFocusable(false);
		dbManager.getTreeModel().addListener(this);
		
		String file = ResourceManager.getString(FILE);
		fileMenu = new JMenu(file);
		
		JMenuItem menuItem = new JMenuItem();				
		Action action = actionManager.getAction(OpenAction.ACTION_NAME);		
		menuItem.setAction(action);
		fileMenu.add(menuItem);
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(CloseAction.ACTION_NAME);
		menuItem.setAction(action);
		fileMenu.add(menuItem);
		
		fileMenu.addSeparator();
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(SaveAction.ACTION_NAME);
		menuItem.setAction(action);
		fileMenu.add(menuItem);
		
		fileMenu.addSeparator();
		
		String importStr = ResourceManager.getString(IMPORT);
		menuItem = new JMenuItem(importStr);
		menuItem.setEnabled(false);
		fileMenu.add(menuItem);
		
		String export = ResourceManager.getString(EXPORT);
		menuItem = new JMenuItem(export);
		menuItem.setEnabled(false);
		fileMenu.add(menuItem);
		
		fileMenu.addSeparator();
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(ExitAction.ACTION_NAME);
		menuItem.setAction(action);
		fileMenu.add(menuItem);
		
		this.add(fileMenu);
		
		String edit = ResourceManager.getString(EDIT);
		editMenu = new JMenu(edit);
		
		createMenu = new JMenu();
		action = actionManager.getAction(CreateAction.ACTION_NAME);
		createMenu.setAction(action);
		editMenu.add(createMenu);
		
		ITreeNode node = null;
		customizeEditMenu(node);
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(RemoveAction.ACTION_NAME);		
		menuItem.setAction(action);
		editMenu.add(menuItem);
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(ShowMoveDialogAction.ACTION_NAME);
		menuItem.setAction(action);
		editMenu.add(menuItem);
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(ShowRenameDialogAction.ACTION_NAME);		
		menuItem.setAction(action);
		editMenu.add(menuItem);
		
		editMenu.addSeparator();
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(ShowAttachDialogAction.ACTION_NAME);
		menuItem.setAction(action);
		editMenu.add(menuItem);
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(RunDetachImageAction.ACTION_NAME);
		menuItem.setAction(action);
		editMenu.add(menuItem);	
		
		
		editMenu.addSeparator();		
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(UndoAction.ACTION_NAME);
		menuItem.setAction(action);
		editMenu.add(menuItem);
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(RedoAction.ACTION_NAME);
		menuItem.setAction(action);
		editMenu.add(menuItem);
		
		editMenu.addSeparator();
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(ShowPropertiesDialogAction.ACTION_NAME);
		menuItem.setAction(action);
		editMenu.add(menuItem);
		
		this.add(editMenu);
		
		String view = ResourceManager.getString(VIEW);
		viewMenu = new JMenu(view);
		
		JMenu gotoMenu = new JMenu();
		action = actionManager.getAction(GoToAction.ACTION_NAME);
		gotoMenu.setAction(action);
		viewMenu.add(gotoMenu);		
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(BackAction.ACTION_NAME);
		menuItem.setAction(action);
		gotoMenu.add(menuItem);
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(ForwardAction.ACTION_NAME);
		menuItem.setAction(action);
		gotoMenu.add(menuItem);
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(UpOneLevelAction.ACTION_NAME);
		menuItem.setAction(action);
		gotoMenu.add(menuItem);
		
		menuItem = new JMenuItem();
		action = actionManager.getAction(ClearHistoryAction.ACTION_NAME);
		menuItem.setAction(action);
		viewMenu.add(menuItem);
		
		viewMenu.addSeparator();
		
		ButtonGroup group = new ButtonGroup();
				
		menuItem = new JRadioButtonMenuItem();
		action = actionManager.getAction(SetIconsViewAction.ACTION_NAME);
		menuItem.setAction(action);
		menuItem.setSelected(true);
		group.add(menuItem);
		viewMenu.add(menuItem);
				
		menuItem = new JRadioButtonMenuItem();
		action = actionManager.getAction(SetListViewAction.ACTION_NAME);
		menuItem.setAction(action);
		group.add(menuItem);
		viewMenu.add(menuItem);
		
		menuItem = new JRadioButtonMenuItem();
		action = actionManager.getAction(SetTableViewAction.ACTION_NAME);
		menuItem.setAction(action);
		group.add(menuItem);
		viewMenu.add(menuItem);
		
		this.add(viewMenu);
		
		String window = ResourceManager.getString(WINDOW);
		windowMenu = new JMenu(window);
		
		menuItem = new JMenuItem("Debtors...");
		windowMenu.add(menuItem);
		
		windowMenu.addSeparator();
		
		menuItem = new JMenuItem("Preferences...");
		windowMenu.add(menuItem);
		
		this.add(windowMenu);
		
		String help = ResourceManager.getString(HELP);
		helpMenu = new JMenu(help);
		
		menuItem = new JMenuItem("About...");
		helpMenu.add(menuItem);
		
		this.add(helpMenu);
		
		MenuItemSizeUtil.setMenuItemSize(this);
	}
	
	private void customizeEditMenu(ITreeNode node) {		
		if (node instanceof ImgCategoryNode) {
			createMenu.removeAll();
			
			JMenuItem menuItem = new JMenuItem();
			Action action = mainEntity.getActionManager().getAction(CreateNewImgCategoryAction.ACTION_NAME);
			menuItem.setAction(action);
			createMenu.add(menuItem);
			
			menuItem = new JMenuItem();
			action = mainEntity.getActionManager().getAction(CreateNewImageAction.ACTION_NAME);
			menuItem.setAction(action);
			createMenu.add(menuItem);
			
			MenuItemSizeUtil.setMenuItemSize(createMenu);						
		} 
		
		MenuItemSizeUtil.setMenuItemSize(createMenu);
	}
	
	private void customizeEditMenu(List<ITreeNode> nodes) {
				
	}
	
	public void activeNodeWasChanged(ITreeNode node) {
		customizeEditMenu(node);
	}
	
	public void activeNodeWasChanged(ITreeNode node, Object source) {
		customizeEditMenu(node);		
	}
	
	public void activeNodesWasChanged(List<ITreeNode> nodes) {
		if (nodes != null && nodes.size() == 1) {
			customizeEditMenu(nodes.get(0));			
		} else {
			customizeEditMenu(nodes);			
		}				
	}
	
	public void activeNodesWasChanged(List<ITreeNode> nodes, Object source) {
		activeNodesWasChanged(nodes);		
	}

	public void nodeWasChanged(ITreeNode node) {
		
	}

	public void nodeWasInserted(ITreeNode node, ITreeNode parent) {
		
	}

	public void nodeWasRemoved(ITreeNode node, ITreeNode parent, int index) {
		
	}

	public void onAfterChangeNode(ITreeNode node) {
		
	}

	public void onBeforeChangeNode(ITreeNode node) {
		
	}

	public void openedNodeWasChanged(ITreeNode node) {
		
	}

	public void nodeStructureWasChanged(ITreeNode node) {
		
	}

	public void openedNodeWasChanged(ITreeNode node, Object source) {
		
	}		
}
