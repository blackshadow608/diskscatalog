package disks.catalog.ui.dialog.move;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.types.ITreeNode;


/**
 * 
 * Слушает соответствующее дерево JTree и как только у дерева меняется выбранный узел,
 * то сразу отражает это изменение в соответствующий DBModel. Из-за того, что для дерева
 * значение открытого узла (opened node) и активного узла (active node) совпадают,
 * то при выборе какого-либо узла в дереве этот узел становится активным и открытым.
 * Открытый узел - это тот узел, содержимое которого показывается в FileView (правая панель),
 * активный узел - выбранный в данный момент для выполнения какой-либо операции.
 * 
 * @author Alexander Levin
 */
public class ActiveTreeNodeListener implements TreeSelectionListener {
	
	private DBTreeModel dbModel;
	private JTree tree;
	
	public ActiveTreeNodeListener(DBTreeModel dbModel, JTree tree) {
		this.dbModel = dbModel;
		this.tree = tree;
	}
	
	public void valueChanged(TreeSelectionEvent e) {
		TreePath path = e.getPath();
		ITreeNode node = null;

		if (path != null && !tree.isSelectionEmpty()) {
			Object nodeObj = e.getPath().getLastPathComponent();
			node = (ITreeNode) nodeObj;
		}

		dbModel.setActiveNode(node);		
	}
}
