package al.catalog.ui.view.list;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JList;

import al.catalog.logger.Logger;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.ITreeNode;

public class ActiveListItemListener extends MouseAdapter {
	
	private DBTreeModel dbModel;
	private CustomList list;
	
	public ActiveListItemListener(DBTreeModel dbModel, CustomList list) {		
		this.dbModel = dbModel;
		this.list = list;
	}
	
	public void mousePressed(MouseEvent e) {
		Logger.openStack("ActiveListItemListener: mousePressed()");
		fireSelection();
		Logger.closeStack();
	}

	public void mouseReleased(MouseEvent e) {
		Logger.openStack("ActiveListItemListener: mouseReleased()");
		fireSelection();
		Logger.closeStack();
	}
	
	public void mouseClicked(MouseEvent e) {
		Logger.openStack("ActiveListItemListener: mouseClicked()");
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

	public void fireSelection() {
		Logger.openStack("ActiveListItemListener: valueChanged()");
		
		list.updateLastSelectedValue();
		list.updateLastSelectedIndex();
				
		List<ITreeNode> activeNodes = list.getSelectedNodes();
		
		if (activeNodes != null) {			
			dbModel.setActiveNodes(activeNodes);
			dbModel.fireChangeActiveNodes(activeNodes, list);
		}
		
		Logger.closeStack();
	}
}
