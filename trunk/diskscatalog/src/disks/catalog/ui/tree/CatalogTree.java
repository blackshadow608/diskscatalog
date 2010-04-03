package disks.catalog.ui.tree;

import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.ui.CatFrame;


public class CatalogTree extends JTree {
	
	protected DBTreeModel dbModel;
	
	protected TreeCellRenderer renderer;
	
	protected ActiveTreeNodeListener treeActiveListener;
	protected NodesChangingListener nodesListener;
	protected TreeFocusListener focusListener;
	protected CustomTreeExpansionListener expListener;
	
	public CatalogTree(DBTreeModel dbModel) {
		this.dbModel = dbModel;
		
		setFont(CatFrame.FONT);
		setScrollsOnExpand(false);
		
		setTreeModel();
		
		initRenderer();
		initListeners();
		
		setRenderer();
		setListeners();
		
		customizeSelectionModel();
	}
	
	private void initListeners() {
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
		
		dbModel.addListener(nodesListener);
	}
	
	private void initRenderer() {
		renderer = new CustomTreeCellRenderer();
	}
	
	private void setRenderer() {
		setCellRenderer(renderer);
	}
	
	private void setTreeModel() {
		CustomTreeModel treeModel = new CustomTreeModel(dbModel);
		dbModel.getDBManager().addConnectionListener(treeModel);
		setModel(treeModel);
	}
	
	private void customizeSelectionModel() {
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);		
	}
}
