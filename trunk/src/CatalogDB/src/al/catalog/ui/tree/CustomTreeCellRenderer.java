package al.catalog.ui.tree;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;

import al.catalog.model.tree.types.ITreeNode;
import al.catalog.model.tree.types.file.FileNode;
import al.catalog.model.tree.types.file.FolderNode;
import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.model.tree.types.images.ImgCategoryNode;

/**
 * Кастомизированный визуализатор узла дерева в компоненте JTree.
 * Здесь нужен для того, чтобы для каждого типа узла оторажать свою иконку,
 * а также для того, чтобы у всех узлов, в независимости от размера иконки,
 * была одинаковая высота строки в компоненте JTree - у визуализатора, который
 * используется по умолчанию высота разная.
 *  
 * @author Alexander Levin
 */
public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		String stringValue = tree.convertValueToText(value, sel, expanded, leaf, row, hasFocus);
		
		this.hasFocus = hasFocus;
		setText(" " + stringValue + " ");
		if (sel) {
			setForeground(getTextSelectionColor());			
		} else {
			setForeground(getTextNonSelectionColor());
		}
		
		ITreeNode treeNode = (ITreeNode) value;
		
		if (value instanceof ImgCategoryNode) {
			if (treeNode.isLogicalRoot()) {
				setIcon(UIManager.getIcon("FileView.floppyDriveIcon"));								
			} else if (expanded) {
				setIcon(getOpenIcon());								
			} else {
				setIcon(getClosedIcon());
			}			
		} else if (value instanceof ImageNode) {
			setIcon(UIManager.getIcon("FileView.hardDriveIcon"));						
		} else if (value instanceof FolderNode) {
			setIcon(getOpenIcon());									
		} else if (value instanceof FileNode) {
			setIcon(getLeafIcon());
		}
		
		setComponentOrientation(tree.getComponentOrientation());		
		
		selected = sel;

		return this;
	}
}
