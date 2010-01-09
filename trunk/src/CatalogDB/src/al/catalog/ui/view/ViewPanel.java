package al.catalog.ui.view;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import al.catalog.MainEntity;
import al.catalog.model.DBManager;
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

	/**
	 * Создает новую панель <b>ViewPanel</b> с параметром <b>MainEntity</b>.
	 * 
	 * @param mainEntity - ссылка на <b>MainEntity</b>.
	 */
	public ViewPanel(MainEntity mainEntity) {

		DBManager dbManager = mainEntity.getDBManager();
		ActionManager aManager = mainEntity.getActionManager();
		DBTreeModel dbModel = dbManager.getTreeModel();
		
		list = new CustomList(dbModel, aManager);
		listPanel = new ListPanel(list);

		table = new CustomTable(dbModel, aManager);
		tablePanel = new TablePanel(table);

		setLayout(new BorderLayout());
		
		setIconsView();
	}

	/**
	 * Переключает режим отображения элементов панели в "Иконки".
	 */
	public void setIconsView() {
		list.setIconsView();
		makeActive(list, listPanel);
	}

	/**
	 * Переключает режим отображения элементов панели в "Список".
	 */
	public void setListView() {
		list.setListView();
		makeActive(list, listPanel);
	}

	/**
	 * Переключает режим отображения элементов панели в "Детально".
	 */
	public void setDetailedView() {
		makeActive(table, tablePanel);
	}
	
	private void makeActive(JComponent component, JScrollPane panel) {
		active = component;
		removeAll();
		add(panel, BorderLayout.CENTER);
		revalidate();
		panel.repaint();
		component.requestFocus();				
	}

	public JComponent getActive() {
		return active;
	}
}
