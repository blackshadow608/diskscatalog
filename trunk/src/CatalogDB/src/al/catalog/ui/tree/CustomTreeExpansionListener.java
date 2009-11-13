package al.catalog.ui.tree;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.TreePath;

import al.catalog.logger.Logger;
import al.catalog.model.tree.DBTreeModel;

public class CustomTreeExpansionListener implements TreeExpansionListener {
	
	private List<DBTreeModel> dbModels = new ArrayList<DBTreeModel>();
	
	public void treeCollapsed(TreeExpansionEvent event) {
		processEvent(event);		
	}

	public void treeExpanded(TreeExpansionEvent event) {
		processEvent(event);		
	}
	
	private void processEvent(TreeExpansionEvent event) {
		Logger.openStack("CustomTreeExpansionListener: processEvent()");
		TreePath path = event.getPath();
		if (path != null) {
			JTree tree = (JTree)event.getSource();
			tree.setSelectionPath(path);									
		}
		Logger.closeStack();
	}
	
	public void addModel(DBTreeModel dbModel) {
		dbModels.add(dbModel);
	}
	
	public void removeModel(DBTreeModel dbModel) {
		dbModels.remove(dbModel);
	}
}
