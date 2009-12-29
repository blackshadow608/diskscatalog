package al.catalog.ui.tree;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import al.catalog.logger.Logger;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.ITreeNode;

/**
 * Слушает фокус у дерева узлов. Как только дерево получает фокус, проверяет выделенный
 * узел и отражает это состояние на модель, то есть тот узел, который выделен должен быть
 * установлен в модели как активный active и как открытый opened.
 * 
 * @author Alexander Levin
 */
public class TreeFocusListener implements FocusListener {
	
	private JTree tree;
	private DBTreeModel dbModel;
	
	public TreeFocusListener(JTree tree, DBTreeModel dbModel) {
		this.tree = tree;
		this.dbModel = dbModel;
	}

	public void focusGained(FocusEvent e) {
		Logger.openStack();
		TreePath path = tree.getSelectionPath();
		ITreeNode selectedNode = null;
		
		if (path != null && !tree.isSelectionEmpty()) {
			selectedNode = (ITreeNode) path.getLastPathComponent();
		}
		
		dbModel.setOpenedNode(selectedNode);
		dbModel.setActiveNode(selectedNode);		
		
		dbModel.fireChangeOpenedNode(selectedNode, tree);		
		dbModel.fireChangeActiveNode(selectedNode, tree);
		Logger.closeStack();
	}

	public void focusLost(FocusEvent e) {
				
	}
}
