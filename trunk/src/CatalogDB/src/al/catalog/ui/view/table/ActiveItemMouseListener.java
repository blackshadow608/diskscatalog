package al.catalog.ui.view.table;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import al.catalog.logger.Logger;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.ITreeNode;

/**
 * Класс ActiveItemMouseListener - является MouseListener'ом и обрабатывает двойной клик,
 * то есть открытие узла. 
 *  
 * @author Alexander Levin
 */
public class ActiveItemMouseListener implements MouseListener {

	private DBTreeModel dbModel;

	public ActiveItemMouseListener(DBTreeModel dbModel) {		
		this.dbModel = dbModel;
	}
	
	public void mouseClicked(MouseEvent e) {
		Logger.openStack("ActiveItemMouseListener: mouseClicked()");
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

	public void mouseEntered(MouseEvent e) {
						
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {		
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}
}
