package al.catalog.ui.view.table;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import al.catalog.logger.Logger;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.ITreeNode;

public class ActiveTableItemListener implements MouseListener {
	
	private DBTreeModel dbModel;
	private CustomTable table;
	
	public ActiveTableItemListener(DBTreeModel dbModel, CustomTable table) {		
		this.dbModel = dbModel;
		this.table = table;
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		Logger.openStack("ActiveTableItemListener: mousePressed()");
		fireSelection();
		Logger.closeStack();
	}

	public void mouseReleased(MouseEvent e) {
		Logger.openStack("ActiveTableItemListener: mouseReleased()");
		fireSelection();
		Logger.closeStack();
	}
	
	private void fireSelection() {		
		Logger.openStack("ActiveTableItemListener: fireSelection()");
		
		int index = table.getSelectedRow();
		if (index >= 0) {
			table.setLastSelectedIndex(index);
		}
		
		int[] selectedRows = table.getSelectedRows();
		List<ITreeNode> activeNodes = null;
		
		if (selectedRows.length > 0) {
			activeNodes = new ArrayList<ITreeNode>(selectedRows.length);
			for (int i = 0; i < selectedRows.length; i++) {
				Object selectedValue = table.getModel().getValueAt(selectedRows[i], CustomTableModel.TREE_NODE_COLUMN_INDEX);
				activeNodes.add((ITreeNode)selectedValue);
			}			
		}
		dbModel.setActiveNodes(activeNodes);
		dbModel.fireChangeActiveNodes(activeNodes, table);
		Logger.closeStack();
	}
}
