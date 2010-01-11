package al.catalog.ui.action.edit.image;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import al.catalog.model.DBManager;
import al.catalog.model.tree.action.DetachImageAction;
import al.catalog.model.tree.types.ITreeNode;
import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.CustomAction;
import al.catalog.ui.resource.ResourceManager;

public class RunDetachImageAction extends CustomAction {
	
	public static final String ACTION_NAME = "Detach image action";
	
	private static final String KEY = "detachImageAction.text";
	private static final String QUESTION = "detachImageAction.question";
	private static final String YES = "global.yes";
	private static final String NO = "global.no";
	
	public RunDetachImageAction(ActionManager actionManager) {
		super(actionManager, ResourceManager.getString(KEY));
	}
	
	public void actionPerformed(ActionEvent e) {
		DBManager dbManager = actionManager.getDBManager();
		ITreeNode treeNode = dbManager.getTreeModel().getActiveNodes().get(0);
		if (treeNode instanceof ImageNode) {
			ImageNode image = (ImageNode) treeNode;
			JFrame frame = actionManager.getMainFrame();
			String question = ResourceManager.getString(QUESTION);
			Object[] options = {ResourceManager.getString(YES), ResourceManager.getString(NO)};
			int n = JOptionPane.showOptionDialog(frame, question, ResourceManager.getString(KEY), JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (n == JOptionPane.YES_OPTION) {
				DetachImageAction detachAction = new DetachImageAction(actionManager.getDBManager(), image);
				detachAction.execute();				
			}			
		}						
	}
}
