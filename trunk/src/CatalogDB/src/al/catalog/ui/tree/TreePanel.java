package al.catalog.ui.tree;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import al.catalog.MainEntity;
import al.catalog.ui.CatFrame;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.popup.PopupHelper;

public class TreePanel extends JScrollPane {

	private CatalogTree tree;

	public TreePanel(MainEntity mainEntity) {
		ActionManager aManager = mainEntity.getActionManager();

		tree = new CatalogTree(aManager.getModel());

		PopupHelper.createMenu(tree, aManager, PopupHelper.TYPE_TREE);

		JPanel treePanel = new JPanel();
		treePanel.setBorder(BorderFactory.createEmptyBorder(CatFrame.BORDER,
				CatFrame.BORDER, CatFrame.BORDER, CatFrame.BORDER));
		treePanel.setLayout(new BorderLayout());
		treePanel.add(tree, BorderLayout.CENTER);
		treePanel.setBackground(Color.WHITE);

		setBorder(BorderFactory.createEmptyBorder());

		getVerticalScrollBar().setUnitIncrement(10);
		getHorizontalScrollBar().setUnitIncrement(10);

		setViewportView(treePanel);
	}
	
	public CatalogTree getTree() {
		return tree;
	}
}
