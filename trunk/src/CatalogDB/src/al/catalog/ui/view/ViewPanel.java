package al.catalog.ui.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;

import al.catalog.model.tree.DBTreeModel;
import al.catalog.ui.CatalogFrame;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.popup.PopupMenuHelper;
import al.catalog.ui.view.list.CustomList;
import al.catalog.ui.view.list.CustomListCellRenderer;
import al.catalog.ui.view.table.ActiveTableItemListener;
import al.catalog.ui.view.table.CustomTable;
import al.catalog.ui.view.table.CustomTableModel;
import al.catalog.ui.view.table.DBTreeModelListener;

/**
 * Представляет собой правую панель от дерева каталога. На этой панели
 * показывается содержимое узла, который в данный момент выделен в дереве.
 * Панель имеет несколько вариантов представления данных пользователю -
 * "Список", "Иконки" и "Детально". Для переключения режима просмотра имеются
 * соответствующие методы.
 * 
 * @author Alexander Levin
 */
public class ViewPanel extends JPanel {
	
	private JScrollPane listScrollPanel;
	private JScrollPane tableScrollPanel;
	private CustomList list;
	private CustomTable table;
	
	private JComponent active;
	
	public ViewPanel(DBTreeModel dbModel, ActionManager actionManager, JTree tree) {
		
		list = new CustomList(dbModel, actionManager);
		list.setDoubleBuffered(true);
		
		PopupMenuHelper.createPopupMenu(list, actionManager, PopupMenuHelper.TYPE_FILE_LIST);
				
		listScrollPanel = new JScrollPane(list);
		listScrollPanel.setBorder(BorderFactory.createEmptyBorder());
		
		listScrollPanel.getVerticalScrollBar().setUnitIncrement(10);
		listScrollPanel.getHorizontalScrollBar().setUnitIncrement(10);
						
		table = new CustomTable(dbModel, actionManager);
		table.setRowHeight(20);
		table.setShowGrid(false);
		table.setDoubleBuffered(true);
		table.setRowMargin(0);
		table.setBorder(BorderFactory.createEmptyBorder(CatalogFrame.BORDER, CatalogFrame.BORDER, CatalogFrame.BORDER, CatalogFrame.BORDER));		
		
		ActiveTableItemListener tableItemListener = new ActiveTableItemListener(dbModel, table);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.addMouseListener(tableItemListener);
		
		DBTreeModelListener listener = new DBTreeModelListener(table);				
		dbModel.addListener(listener);
		
		tableScrollPanel = new JScrollPane(table);
		tableScrollPanel.setBorder(BorderFactory.createEmptyBorder());		
				
		table.setFillsViewportHeight(true);		
		
		setLayout(new BorderLayout());
	}
	
	public void setIconsView() {
		CustomListCellRenderer renderer = (CustomListCellRenderer) list.getCellRenderer();
		int type = renderer.getType();
		
		if (type != CustomListCellRenderer.TYPE_ICONS) {
			renderer.setType(CustomListCellRenderer.TYPE_ICONS);
			list.setLayoutOrientation(JList.HORIZONTAL_WRAP);			
			list.setCellRenderer(renderer);
		}
				
		active = list;		
		
		removeAll();
		add(listScrollPanel, BorderLayout.CENTER);		
		revalidate();		
		listScrollPanel.repaint();
		
		list.requestFocus();
	}
	
	public void setListView() {
		CustomListCellRenderer renderer = (CustomListCellRenderer) list.getCellRenderer();
		int type = renderer.getType();
				
		if (type != CustomListCellRenderer.TYPE_LIST) {
			renderer.setType(CustomListCellRenderer.TYPE_LIST);
			list.setLayoutOrientation(JList.VERTICAL_WRAP);			
			list.setCellRenderer(renderer);
		}
				
		active = list;		
		
		removeAll();
		add(listScrollPanel, BorderLayout.CENTER);		
		revalidate();
		listScrollPanel.repaint();
		
		list.requestFocus();
	}
	
	public void setDetailedView() {		
		active = table;
		
		removeAll();
		add(tableScrollPanel, BorderLayout.CENTER);
		revalidate();
		tableScrollPanel.repaint();
	}
	
	public JComponent getActive() {
		return active;
	}
}
