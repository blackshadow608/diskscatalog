package al.catalog.ui.view.list;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;

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
		JList list = (JList) e.getSource();
		int count = e.getClickCount();
		int button = e.getButton();		
		if (count == 2 && button == MouseEvent.BUTTON1) {
			Object selectedValue = list.getSelectedValue();
			if (selectedValue != null) {
				ITreeNode node = (ITreeNode) selectedValue; 
				if (node.canHaveChildren()) {
					dbModel.setActiveNode(null);
					dbModel.setOpenedNode(node);
					dbModel.fireChangeOpenedNode(dbModel.getOpenedNode());
					dbModel.fireChangeActiveNodes(null, list);
					
					list.transferFocus();
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
		/*list = (JList) e.getSource();
		int index = -1;
		int x = e.getX();
		int y = e.getY();
		for (int i = list.getFirstVisibleIndex(); i <= list.getLastVisibleIndex(); i++) {
			Rectangle cellBounds = list.getCellBounds(i, i);
			if (cellBounds != null) {
				int minX = (int) cellBounds.getMinX();
				int maxX = (int) cellBounds.getMaxX();
				int minY = (int) cellBounds.getMinY();
				int maxY = (int) cellBounds.getMaxY();
				if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
					index = i;
				}
			}
		}

		if (index < 0) {									
			CustomListModel listModel = (CustomListModel) list.getModel();
			listModel.activeNodeWasChanged(null);			
			list.clearSelection();
			list.transferFocus();
		} else {			
			list.setSelectedIndex(index);
			list.requestFocus();
		}*/
		
		/* else {
			list.requestFocus();
		}*/

		/*if (ctrlIsPressed) {
			if (list.isSelectedIndex(index)) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					list.removeSelectionInterval(index, index);					
				}				
			} else {
				list.addSelectionInterval(index, index);
			}
		} else if (shiftIsPressed) {
			int prevSelection = list.getSelectedIndex();
			if (prevSelection < 0 || prevSelection >= list.getModel().getSize()) {
				list.setSelectedIndex(index);
			} else {
				list.setSelectionInterval(prevSelection, index);
			}
		} else {
			list.setSelectedIndex(index);
		}*/			
		
		/*Object selectedValue = list.getSelectedValue();
		if (selectedValue != null) {
			ITreeNode node = (ITreeNode) selectedValue;
			dbModel.setActiveNode(node);
			dbModel.fireChangeActiveNode(node, list);
		}*/		
	}

	public void mouseReleased(MouseEvent e) {
		
	}
}
