package al.catalog.ui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.ITreeNode;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.resource.ResourceManager;

/**
 * RenameAction - action для выполнения переименования узла.
 * 
 * @author Alexander Levin
 */
public class RenameAction extends CustomAction {
		
	public static final String ACTION_NAME = "Prepare to rename";	
	private static final String KEY = "renameAction.text";	
	private String name;
	
	/**
	 * Создает экземпляр класса RenameAction.
	 * 
	 * @param actionManager - ActionManager, который управляет действиями.
	 */
	public RenameAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_R, ActionEvent.CTRL_MASK));
	}

	public void actionPerformed(ActionEvent e) {		
		DBTreeModel dbModel = aManager.getModel();
		ITreeNode activeNode = dbModel.getActiveNodes().get(0);
		activeNode.setName(name);
		
		if (activeNode.hasParent()) {
			activeNode.getParent().sortChildren();
		}
		
		if (dbModel != null) {
			dbModel.renameNode(activeNode);			
		}		
	}
	
	/**
	 * Устанавливает новое имя, которое должен получить активный узел.
	 * 
	 * @param name - новое имя узла.
	 */
	public void setName(String name) {
		this.name = name;		
	}
}
