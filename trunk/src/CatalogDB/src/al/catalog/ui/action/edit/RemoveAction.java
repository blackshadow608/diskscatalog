package al.catalog.ui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import al.catalog.model.tree.DBTreeModel;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.resource.ResourceManager;

public class RemoveAction extends CustomAction {

	public static final String ACTION_NAME = "Delete";
	
	private static final String KEY = "removeAction.text";
	private static final String MESSAGE = "TreeActionManager must have DBModel";
	
	private static final String QUESTION = "removeAction.question";
	private static final String YES = "global.yes";
	private static final String NO = "global.no";
	
	public RemoveAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));		
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_DELETE, 0));
	}

	public void actionPerformed(ActionEvent e) {
		DBTreeModel dbModel = actionManager.getDBManager().getTreeModel();
		JFrame frame = (JFrame) actionManager.getProperty(ActionManager.PROPERTY_OWNER_FRAME);
		String  question = ResourceManager.getString(QUESTION);
		Object[] options = {ResourceManager.getString(YES), ResourceManager.getString(NO)};
		int n = JOptionPane.showOptionDialog(frame, question, ResourceManager.getString(KEY), JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (n == JOptionPane.YES_OPTION) {
			if (dbModel != null) {
				dbModel.removeNode();
			} else {
				throw new NullPointerException(MESSAGE);
			}			
		}		
	}
}
