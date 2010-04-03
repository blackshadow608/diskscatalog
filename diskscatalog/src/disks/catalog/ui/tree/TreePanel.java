package disks.catalog.ui.tree;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import disks.catalog.MainEntity;
import disks.catalog.ui.CatFrame;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.popup.PopupHelper;


public class TreePanel extends JScrollPane {

	private CatalogTree tree;

	public TreePanel(MainEntity mainEntity) {
		ActionManager aManager = mainEntity.getActionManager();

		tree = new CatalogTree(aManager.getModel());

		PopupHelper.createMenu(tree, aManager, PopupHelper.TYPE_TREE);

		JPanel treePanel = new JPanel();
		Border border = createEmptyBorder(CatFrame.BORDER);
		treePanel.setBorder(border);
		treePanel.setLayout(new BorderLayout());
		treePanel.add(tree, BorderLayout.CENTER);
		treePanel.setBackground(Color.WHITE);

		setBorder(BorderFactory.createEmptyBorder());

		getVerticalScrollBar().setUnitIncrement(10);
		getHorizontalScrollBar().setUnitIncrement(10);

		setViewportView(treePanel);
	}
	
	private Border createEmptyBorder(int border) {
		return BorderFactory.createEmptyBorder(border, border, border, border);		
	}
	
	public CatalogTree getTree() {
		return tree;
	}
}
