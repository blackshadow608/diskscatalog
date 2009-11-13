package al.catalog.ui.view.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import al.catalog.logger.Logger;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.ITreeNode;

public class ActiveListItemListener implements ListSelectionListener {
	
	private DBTreeModel dbModel;	
	
	public ActiveListItemListener(DBTreeModel dbModel) {		
		this.dbModel = dbModel;		
	}

	public void valueChanged(ListSelectionEvent e) {
		Logger.openStack("ActiveListItemListener: valueChanged()");
		
		CustomList list = (CustomList) e.getSource();
		list.setLastSelectedValue(list.getSelectedValue());
		int index = list.getSelectedIndex();
		
		if (index >= 0) {
			list.setLastSelectedIndex(index);
		}
		
		Object[] selectedValues = list.getSelectedValues();
		List<ITreeNode> activeNodes = null;
		if (selectedValues.length > 0) {
			activeNodes = new ArrayList<ITreeNode>(selectedValues.length);
			for (int i = 0; i < selectedValues.length; i++) {
				activeNodes.add((ITreeNode)selectedValues[i]);				
			}
			dbModel.setActiveNodes(activeNodes);
			dbModel.fireChangeActiveNodes(activeNodes, list);
		}		
		
		Logger.closeStack();
	}
}
