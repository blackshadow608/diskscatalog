package disks.catalog.ui.view.table;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import disks.catalog.model.tree.IDBTreeModelListener;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.model.tree.types.images.ImgCategoryNode;


public class DBTreeModelListener implements IDBTreeModelListener {
	
	private JTable table;
	private TableColumnModel imagesColumns;
	private TableColumnModel filesColumns;
	
	private static final int COLUMN_WIDTH_NAME = 300;
	
	public static final int MODEL_INDEX_NAME = 0;
	public static final int MODEL_INDEX_TYPE = 1;
	public static final int MODEL_INDEX_SIZE = 2;
	
	private void createImagesColumns() {
		imagesColumns = new DefaultTableColumnModel();		
		imagesColumns.setColumnMargin(0);
		
		TableColumn column = new TableColumn();
		column.setHeaderValue("Название");
		column.setModelIndex(MODEL_INDEX_NAME);		
		column.setPreferredWidth(COLUMN_WIDTH_NAME);
		imagesColumns.addColumn(column);
		
		column = new TableColumn();
		column.setHeaderValue("Тип");
		column.setModelIndex(MODEL_INDEX_TYPE);
		imagesColumns.addColumn(column);
	}
	
	private void createFilesColumns() {
		filesColumns = new DefaultTableColumnModel();
		filesColumns.setColumnMargin(0);
		
		TableColumn column = new TableColumn();
		column.setHeaderValue("Название");
		column.setModelIndex(MODEL_INDEX_NAME);
		column.setPreferredWidth(COLUMN_WIDTH_NAME);
		filesColumns.addColumn(column);
		
		column = new TableColumn();
		column.setHeaderValue("Тип");
		column.setModelIndex(MODEL_INDEX_TYPE);
		filesColumns.addColumn(column);
		
		column = new TableColumn();
		column.setHeaderValue("Размер");
		column.setModelIndex(MODEL_INDEX_SIZE);
		filesColumns.addColumn(column);
	}
	
	public DBTreeModelListener(JTable table) {
		this.table = table;
		createImagesColumns();
		createFilesColumns();
	}

	public void activeNodeWasChanged(ITreeNode node) {
		
	}

	public void activeNodeWasChanged(ITreeNode node, Object source) {
		
	}

	public void activeNodesWasChanged(List<ITreeNode> nodes) {
		
	}

	public void activeNodesWasChanged(List<ITreeNode> nodes, Object source) {
		
	}

	public void nodeStructureWasChanged(ITreeNode node) {
		
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
		openedNodeWasChanged(node, null);				
	}

	public void openedNodeWasChanged(ITreeNode node, Object source) {		
		if(node instanceof ImgCategoryNode) {
			table.setColumnModel(imagesColumns);
		} else {
			table.setColumnModel(filesColumns);
		}
	}
}
