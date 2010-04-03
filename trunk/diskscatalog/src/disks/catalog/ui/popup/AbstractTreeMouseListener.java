package disks.catalog.ui.popup;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;
import javax.swing.JTree;

import disks.catalog.ui.action.ActionManager;


public abstract class AbstractTreeMouseListener implements MouseListener {
	
	protected JTree tree;
	protected ActionManager actionManager;
	protected JPopupMenu popupMenu;
	protected static final int RIGHT_BUTTON = 3;
	
	protected static final String CREATE = "Create";
	
	abstract protected void createPopupMenu();
	
	public AbstractTreeMouseListener(JTree tree, ActionManager actionManager) {
		this.tree = tree;
		this.actionManager = actionManager;
	}
	
	public void mousePressed(MouseEvent e) {
		tree.requestFocus();		
		if (e.getButton() == RIGHT_BUTTON) {
			int row = tree.getRowForLocation(e.getX(), e.getY());
			if (!tree.isRowSelected(row)) {
				tree.setSelectionRow(row);				
			}
        }
	}

	public void mouseReleased(MouseEvent e) {			
		if (e.getButton() == RIGHT_BUTTON && tree.getSelectionPath() != null) {
			if (tree.getSelectionPath() != null) {
				createPopupMenu();
				int x = tree.getLocationOnScreen().x + e.getX();
				int y = tree.getLocationOnScreen().y + e.getY();
				if (popupMenu != null) {
					popupMenu.setLocation(x, y);
		            popupMenu.setVisible(true);					
				}								
			}			
		}
	}
	
	public void mouseClicked(MouseEvent e) {		
		
	}

	public void mouseEntered(MouseEvent e) {
				
	}

	public void mouseExited(MouseEvent e) {
				
	}
}
