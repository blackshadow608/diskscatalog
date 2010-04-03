package disks.catalog.ui.dialog.attach;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;

import disks.catalog.model.tree.types.file.FileNode;
import disks.catalog.model.tree.types.file.FolderNode;


public class FileSystemCellRenderer extends DefaultTreeCellRenderer {
	
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		String stringValue = tree.convertValueToText(value, sel, expanded, leaf, row, hasFocus);
		
		this.hasFocus = hasFocus;
		setText(" " + stringValue + " ");
		
		if (sel) {
			setForeground(getTextSelectionColor());			
		} else {
			setForeground(getTextNonSelectionColor());
		}
		
		if (value instanceof FolderNode) {
			FolderNode folder = (FolderNode) value;
			if (!folder.hasParent()) {
				setIcon(UIManager.getIcon("FileView.computerIcon"));								
			} else if (folder.hasParent()) {
				if (expanded) {
					setIcon(getOpenIcon());								
				} else {
					setIcon(getClosedIcon());
				}
				if (!folder.getParent().hasParent()) {
					if (folder.getName().equals("A:\\")) {
						setIcon(UIManager.getIcon("FileView.floppyDriveIcon"));						
					} else {
						setIcon(UIManager.getIcon("FileView.hardDriveIcon"));						
					}
				}
			}
		} else if (value instanceof FileNode) {
			setIcon(getLeafIcon());
		}
		
		setComponentOrientation(tree.getComponentOrientation());

		selected = sel;

		return this;
	}
}
