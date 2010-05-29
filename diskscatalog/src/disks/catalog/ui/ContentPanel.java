package disks.catalog.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import disks.catalog.MainEntity;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.tree.TreePanel;
import disks.catalog.ui.view.ViewPanel;

/**
 * Представляет собой панель содержимого окна приложения. Все компоненты
 * приложения располагаются именно на этой панели.
 * 
 * @author Alexander Levin
 */
public class ContentPanel extends JPanel {

	private static final int DIVIDER_SIZE = 8;
	private static final int DIVIDER_POS = 250;
	
	private TreePanel treePanel;
	private ViewPanel viewPanel;

	public ContentPanel(MainEntity mainEntity) {
		setLayout(new BorderLayout());
		
		ActionManager aManager = mainEntity.getActionManager();
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		viewPanel = new ViewPanel(mainEntity);		

		treePanel = new TreePanel(mainEntity);
		
		splitPane.setLeftComponent(treePanel);
		splitPane.setRightComponent(viewPanel);
		splitPane.setDividerSize(DIVIDER_SIZE);
		splitPane.setDividerLocation(DIVIDER_POS);

		add(splitPane, BorderLayout.CENTER);

		CatalogToolBar toolBar = new CatalogToolBar(aManager);
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
	 * @param progressBar - ссылка на <b>JPanel</b> панель прогресса выполнения.
	 */
	public void hideProgressBar(JPanel progressBar) {
		if (progressBar != null) {
			remove(progressBar);
			revalidate();
		}
	}
	
	public ViewPanel getViewPanel() {
		return viewPanel;
	}
	
	public TreePanel getTreePanel() {
		return treePanel;
	}
}
