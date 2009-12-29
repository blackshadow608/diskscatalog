package al.catalog.ui.view.table;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import al.catalog.logger.Logger;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.ITreeNode;

public class ActiveTableItemListener extends MouseAdapter {
	
	private DBTreeModel dbModel;
	private CustomTable table;
	
	public ActiveTableItemListener(DBTreeModel dbModel, CustomTable table) {		
		this.dbModel = dbModel;
		this.table = table;
	}

	public void mousePressed(MouseEvent e) {
		Logger.openStack();
		fireSelection();
		Logger.closeStack();
	}

	public void mouseReleased(MouseEvent e) {
		Logger.openStack();
		fireSelection();
		Logger.closeStack();
	}
	
	public void mouseClicked(MouseEvent e) {
		Logger.openStack();
		JTable table = (JTable) e.getSource();
		int count = e.getClickCount();
		int button = e.getButton();
		if (count == 2 && button == MouseEvent.BUTTON1) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				Object selectedValue = table.getModel().getValueAt(selectedRow, CustomTableModel.TREE_NODE_COLUMN_INDEX);
				ITreeNode node = (ITreeNode) selectedValue; 
				if (node.canHaveChildren()) {
					dbModel.setActiveNode(null);
					dbModel.setOpenedNode(node);
					dbModel.fireChangeOpenedNode(dbModel.getOpenedNode());
					dbModel.fireChangeActiveNodes(null, table);					
					table.transferFocus();
				}				
			}						
		}
		Logger.closeStack();
	}
	
	private void fireSelection() {		
		Logger.openStack();
		
		table.updateLastSelectedIndex();
		
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
