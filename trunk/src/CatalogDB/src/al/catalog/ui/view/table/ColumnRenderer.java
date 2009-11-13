package al.catalog.ui.view.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import al.catalog.model.tree.types.ITreeNode;
import al.catalog.model.tree.types.file.FileNode;
import al.catalog.model.tree.types.file.FolderNode;
import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.model.tree.types.images.ImgCategoryNode;
import al.catalog.ui.util.TypeDeterminant;

public class ColumnRenderer extends DefaultTableCellRenderer {
	
	private static Color COLOR_FOCUS_BORDER = new Color(99, 130, 191);

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Color bg = null;
		Color fg = null;
		
		if (isSelected) {
			setBackground(bg == null ? table.getSelectionBackground() : bg);
			setForeground(fg == null ? table.getSelectionForeground() : fg);
		} else {
			setBackground(table.getBackground());
			setForeground(table.getForeground());
		}
		
		if (value instanceof ITreeNode) {
			ITreeNode treeNode = (ITreeNode) value;
			
			int modelIndex = table.getColumnModel().getColumn(column).getModelIndex();
			
			if (modelIndex == DBTreeModelListener.MODEL_INDEX_NAME) {			
				if (value instanceof ImgCategoryNode) {
					if (treeNode.isLogicalRoot()) {
						setIcon(UIManager.getIcon("FileView.floppyDriveIcon"));
					} else {
						setIcon(UIManager.getIcon("Tree.closedIcon"));
					}
				} else if (value instanceof ImageNode) {
					setIcon(UIManager.getIcon("FileView.hardDriveIcon"));
				} else if (value instanceof FolderNode) {
					setIcon(UIManager.getIcon("Tree.closedIcon"));
				} else if (value instanceof FileNode) {
					setIcon(UIManager.getIcon("Tree.leafIcon"));
				}

				setText(treeNode.getName());
			} else if (modelIndex == DBTreeModelListener.MODEL_INDEX_TYPE) {				
				setText(TypeDeterminant.getType(treeNode));
			} else if (modelIndex == DBTreeModelListener.MODEL_INDEX_SIZE) {
				setText("");				
			}
			
			setEnabled(table.isEnabled());
			setFont(table.getFont());
			
			Border border = null;
			
			/*
			 * Для того, чтобы border'ом была очерчена вся строка по периметру.
			 */			
			if (table.getSelectionModel().getLeadSelectionIndex() == row) {
				
				int columnCount = table.getColumnModel().getColumnCount();
				
				if(column == 0) {
					/*
					 * Если рисуемая ячейка самая первая, то отрисовываем верхнюю, нижнюю и левую границы.
					 */
					border = BorderFactory.createCompoundBorder(
							BorderFactory.createMatteBorder(1, 1, 1, 0, COLOR_FOCUS_BORDER),
							BorderFactory.createEmptyBorder(3, 2, 3, 2));					
				} else if(column == columnCount - 1) {
					/*
					 * Если рисуемая ячейка находится в последнем столбце, то отрисовываем верхнюю, нижнюю и правую границы.
					 */
					border = BorderFactory.createCompoundBorder(
							BorderFactory.createMatteBorder(1, 0, 1, 1, COLOR_FOCUS_BORDER),
							BorderFactory.createEmptyBorder(3, 3, 3, 2));																			
				} else {
					/*
					 * Если рисуемая ячейка находится в середине, то отрисовывает только верхнюю и нижнюю границы.
					 */
					border = BorderFactory.createCompoundBorder(
							BorderFactory.createMatteBorder(1, 0, 1, 0, COLOR_FOCUS_BORDER),
							BorderFactory.createEmptyBorder(3, 3, 3, 2));										
				}
			} else {
				border = BorderFactory.createEmptyBorder(4, 3, 4, 3);
			}			

			setBorder(border);
		}
		
		return this;
	}
}
