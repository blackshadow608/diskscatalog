package disks.catalog.ui.tree;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import disks.catalog.logger.Logger;
import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.types.ITreeNode;


/**
 * Слушает дерево JTree и при изменении активного узла, то есть когда пользователь
 * в дереве выбирает какой-то узел, отражает это изменение на модель.
 * 
 * @author Alexander Levin
 */
public class ActiveTreeNodeListener implements TreeSelectionListener {

	private DBTreeModel dbModel;
	private JTree tree;

	/**
	 * Конструктор для создания слушателя. При создании объекта требуется два
	 * параметра. Первый параметр это модель, которую нужно оповестить о том,
	 * что активной узел изменился, если пользователь выберет какой-то узел в
	 * дереве, ссылка на которое передается в качестве второго параметра.
	 * 
	 * @param dbModel
	 *            - <b>DBTreeModel</b> модель, которую требуется оповестить при
	 *            смене выделенного узла в дереве.
	 * @param tree
	 *            - <b>JTree</b> дерево, у которого требуется отслеживать
	 *            изменение выделенного узла.
	 */
	public ActiveTreeNodeListener(DBTreeModel dbModel, JTree tree) {
		this.dbModel = dbModel;
		this.tree = tree;
	}

	public void valueChanged(TreeSelectionEvent e) {
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
}
