package disks.catalog.ui.view.table;

import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

import disks.catalog.model.tree.types.ITreeNode;


public class CustomTableModelEvent extends TableModelEvent {
	
	private ITreeNode treeNode;

	public CustomTableModelEvent(TableModel source, int firstRow, int lastRow,
			int column, int type, ITreeNode treeNode) {
		super(source, firstRow, lastRow, column, type);
		this.treeNode = treeNode;		
	}
	
	public ITreeNode getTreeNode() {
		return treeNode;
	}
}
