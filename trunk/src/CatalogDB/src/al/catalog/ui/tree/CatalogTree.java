package al.catalog.ui.tree;

import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import al.catalog.model.DBManager;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.ui.CatalogFrame;

public class CatalogTree extends JTree {
	
	protected DBManager dbManager;
	
	protected TreeCellRenderer renderer;
	
	protected ActiveTreeNodeListener treeActiveListener;
	protected NodesChangingListener nodesListener;
	protected TreeFocusListener focusListener;
	protected CustomTreeExpansionListener expListener;
	
	public CatalogTree(DBManager dbManager) {
		this.dbManager = dbManager;
		
		setFont(CatalogFrame.FONT);
		setScrollsOnExpand(false);
		
		setTreeModel();
		
		initRenderer();
		initListeners();
		
		setRenderer();
		setListeners();
		
		customizeSelectionModel();		
	}
	
	private void initListeners() {
		DBTreeModel dbModel = dbManager.getTreeModel();
		
		treeActiveListener = new ActiveTreeNodeListener(dbModel, this);
		nodesListener = new NodesChangingListener(dbModel, this);		
		focusListener = new TreeFocusListener(this, dbModel);
		expListener = new CustomTreeExpansionListener();		
		expListener.addModel(dbModel);
	}
	
	private void setListeners() {
		addTreeSelectionListener(treeActiveListener);
		setEditable(false);
		setRootVisible(false);
		addTreeExpansionListener(expListener);
		addFocusListener(focusListener);
		
		dbManager.getTreeModel().addListener(nodesListener);
	}
	
	private void initRenderer() {
		renderer = new CustomTreeCellRenderer();
	}
	
	private void setRenderer() {
		setCellRenderer(renderer);
	}
	
	private void setTreeModel() {
		CustomTreeModel treeModel = new CustomTreeModel(dbManager.getTreeModel());
		dbManager.addConnectionListener(treeModel);
		setModel(treeModel);
	}
	
	private void customizeSelectionModel() {
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);		
	}
}
