package al.catalog.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;

import al.catalog.MainEntity;
import al.catalog.model.DBManager;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.popup.PopupMenuHelper;
import al.catalog.ui.progress.ProgressActionListener;
import al.catalog.ui.tree.CatalogTree;
import al.catalog.ui.view.ViewPanel;

public class ContentPanel extends JPanel {
	
	private static final int DIVIDER_SIZE = 8;
	private static final int DIVIDER_POS = 250;
	
	private JTree tree;
	private ViewPanel viewPanel;
	
	public ContentPanel(MainEntity mainEntity, CatalogFrame mainFrame) {
		setLayout(new BorderLayout());
		
		DBManager dbManager = mainEntity.getDBManager();
		ActionManager actionManager = mainEntity.getActionManager();
		
		DBTreeModel dbModel = dbManager.getTreeModel();
				
		tree = new CatalogTree(dbManager);
		actionManager.setProperty(ActionManager.PROPERTY_TREE, tree);
				
		ProgressActionListener progressListener = new ProgressActionListener(mainFrame);
		dbManager.addDBActionListener(progressListener);
		
		PopupMenuHelper.createPopupMenu(tree, actionManager, PopupMenuHelper.TYPE_TREE);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		viewPanel = new ViewPanel(dbModel, actionManager, tree);
		viewPanel.setIconsView();
		actionManager.setProperty(ActionManager.PROPERTY_VIEW_PANEL, viewPanel);
		
		JPanel treePanel = new JPanel();
		treePanel.setBorder(BorderFactory.createEmptyBorder(CatalogFrame.BORDER, CatalogFrame.BORDER, CatalogFrame.BORDER, CatalogFrame.BORDER));
		treePanel.setLayout(new BorderLayout());
		treePanel.add(tree, BorderLayout.CENTER);
		treePanel.setBackground(Color.WHITE);
		
		JScrollPane treeScrollPane = new JScrollPane(treePanel);		
		treeScrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		treeScrollPane.getVerticalScrollBar().setUnitIncrement(10);
		treeScrollPane.getHorizontalScrollBar().setUnitIncrement(10);
		
		splitPane.setLeftComponent(treeScrollPane);
		splitPane.setRightComponent(viewPanel);
		splitPane.setDividerSize(DIVIDER_SIZE);
		splitPane.setDividerLocation(DIVIDER_POS);
		
		add(splitPane, BorderLayout.CENTER);
		
		CatalogToolBar toolBar = new CatalogToolBar(actionManager);
		
		add(toolBar, BorderLayout.PAGE_START);
	}
	
	/**
	 * Показывает в нижней части основного окна приложения панель прогресса
	 * выполнения, ссылка на которую передается методу в качестве параметра.
	 * 
	 * @param progressBar - ссылка на <b>JPanel</b> панель прогресса выполнения.
	 */
	public void showProgressBar(JPanel progressBar) {
		add(progressBar, BorderLayout.SOUTH);
		revalidate();
	}
	
	/**
	 * Скрывает панель прогресса выполнения, ссылка на которую передается методу
	 * в качестве параметра.
	 * 
	 * @param progressBar -
	 *            ссылка на <b>JPanel</b> прогресс панель, которую необходимо
	 *            скрыть.
	 */
	public void hideProgressBar(JPanel progressBar) {
		if(progressBar != null) {
			remove(progressBar);
			revalidate();			
		}		
	}

}
