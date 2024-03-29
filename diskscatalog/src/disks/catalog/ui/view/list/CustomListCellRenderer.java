package disks.catalog.ui.view.list;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.model.tree.types.file.FileNode;
import disks.catalog.model.tree.types.file.FolderNode;
import disks.catalog.model.tree.types.images.ImageNode;
import disks.catalog.model.tree.types.images.ImgCategoryNode;


/**
 * Класс <b>CustomListCellRenderer</b> представляет собой кастомизированный
 * визуализатор для отображения элементов списка. Есть возможность переключать
 * вид на "Иконки" или "Список".
 * 
 * @author Alexander Levin
 */
public class CustomListCellRenderer extends DefaultListCellRenderer {
	
	protected static Border noFocusBorder = new EmptyBorder(1, 2, 1, 2);
	
	public static final int TYPE_LIST = 0;
	public static final int TYPE_ICONS = 1;
	
	private int type;
	
	public CustomListCellRenderer() {
		setType(TYPE_ICONS);
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		
			setComponentOrientation(list.getComponentOrientation());

			Color bg = null;
			Color fg = null;

			JList.DropLocation dropLocation = list.getDropLocation();
			if (dropLocation != null && !dropLocation.isInsert()
					&& dropLocation.getIndex() == index) {
				bg = UIManager.getColor("List.dropCellBackground");
				fg = UIManager.getColor("List.dropCellForeground");
				isSelected = true;
			}

			if (isSelected) {
				setBackground(bg == null ? list.getSelectionBackground() : bg);
				setForeground(fg == null ? list.getSelectionForeground() : fg);
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			ITreeNode treeNode = (ITreeNode) value;

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

			String fullText = (value == null) ? "" : value.toString();
			String partText = fullText;

			if (type == TYPE_ICONS) {
				if(list.isDisplayable()) {
					Font font = list.getFont();
					for (int i = 0; i <= fullText.length(); i++) {
						partText = fullText.substring(0, i);
						/*FontRenderContext context = list.getGraphics().getFontMetrics(font).getFontRenderContext();
						Rectangle2D bounds = font.getStringBounds(partText, context);
						if (bounds.getWidth() > getPreferredSize().width) {
							partText += "...";
							break;
						}*/
						int width = list.getGraphics().getFontMetrics(font).stringWidth(partText);						
						if (width > getPreferredSize().width) {
							partText += "...";
							break;
						}
					}					
				}				
			}

			setText(partText);

			setEnabled(list.isEnabled());
			setFont(list.getFont());

			Border border = null;
			if (cellHasFocus) {
				if (isSelected) {
					border = UIManager.getBorder("List.focusSelectedCellHighlightBorder");
				}
				if (border == null) {					
					border = BorderFactory.createCompoundBorder(UIManager.getBorder("List.focusCellHighlightBorder"),
								BorderFactory.createEmptyBorder(1, 1, 1, 1));
				}
			} else {
				border = noFocusBorder;
			}

			setBorder(border);

			return this;
		
	}
	
	/**
	 * Задает используемый тип, который определяет, какой режим используется для
	 * визуализации компонентов - "Иконки" или "Список". В качестве параметров
	 * должны передаваться константы TYPE_LIST или TYPE_ICONS.
	 * 
	 * @param type - тип визуализации элементов списка.
	 */
	public void setType(int type) {
		this.type = type;		
		if (type == TYPE_ICONS) {
			int maxWidth = 120;
			int maxHeight = 50;
			setPreferredSize(new Dimension(maxWidth, maxHeight));			
			setHorizontalAlignment(JLabel.CENTER);
			setVerticalTextPosition(JLabel.BOTTOM);
			setHorizontalTextPosition(JLabel.CENTER);			
		} else if (type == TYPE_LIST) {
			setPreferredSize(new Dimension(250, 20));			
			setHorizontalAlignment(JLabel.LEFT);
			setVerticalTextPosition(JLabel.CENTER);
			setHorizontalTextPosition(JLabel.RIGHT);						
		}
	}
	
	/**
	 * Возвращает тип, который определяет, какой режим используется для
	 * визуализации компонентов - "Иконки" или "Список". Возвращаемый тип
	 * представляется константой - TYPE_LIST или TYPE_ICONS.
	 * 
	 * @return одна из констант TYPE_LIST или TYPE_ICONS.
	 */
	public int getType() {
		return type;
	}
}
