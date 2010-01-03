package al.catalog.ui.view;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import al.catalog.model.tree.DBTreeModel;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.view.list.CustomList;
import al.catalog.ui.view.list.ListPanel;
import al.catalog.ui.view.table.CustomTable;
import al.catalog.ui.view.table.TablePanel;

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

	private JScrollPane listPanel;
	private JScrollPane tablePanel;

	private CustomList list;
	private CustomTable table;

	private JComponent active;

	public ViewPanel(DBTreeModel dbModel, ActionManager aManager, JTree tree) {

		list = new CustomList(dbModel, aManager);
		listPanel = new ListPanel(list);

		table = new CustomTable(dbModel, aManager);
		tablePanel = new TablePanel(table);

		setLayout(new BorderLayout());
	}

	/**
	 * Переключает режим отображения элементов панели в "Иконки".
	 */
	public void setIconsView() {
		list.setIconsView();
		active = list;
		removeAll();
		add(listPanel, BorderLayout.CENTER);
		revalidate();
		listPanel.repaint();
		list.requestFocus();
	}

	/**
	 * Переключает режим отображения элементов панели в "Список".
	 */
	public void setListView() {
		list.setListView();
		active = list;
		removeAll();
		add(listPanel, BorderLayout.CENTER);
		revalidate();
		listPanel.repaint();
		list.requestFocus();
	}

	/**
	 * Переключает режим отображения элементов панели в "Детально".
	 */
	public void setDetailedView() {
		active = table;
		removeAll();
		add(tablePanel, BorderLayout.CENTER);
		revalidate();
		tablePanel.repaint();
		table.requestFocus();
	}

	public JComponent getActive() {
		return active;
	}
}
