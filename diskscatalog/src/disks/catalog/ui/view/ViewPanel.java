package disks.catalog.ui.view;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import disks.catalog.MainEntity;
import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.view.list.CatalogList;
import disks.catalog.ui.view.list.ListPanel;
import disks.catalog.ui.view.table.CatalogTable;
import disks.catalog.ui.view.table.TablePanel;


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

	private CatalogList list;
	private CatalogTable table;

	private JComponent active;

	/**
	 * Создает новую панель <b>ViewPanel</b> с параметром <b>MainEntity</b>.
	 * 
	 * @param mainEntity - ссылка на <b>MainEntity</b>.
	 */
	public ViewPanel(MainEntity mainEntity) {

		ActionManager aManager = mainEntity.getActionManager();
		DBTreeModel dbModel = aManager.getModel();
		
		list = new CatalogList(dbModel, aManager);
		listPanel = new ListPanel(list);

		table = new CatalogTable(dbModel, aManager);
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
	
	public CatalogList getList() {
		return list;
	}
	
	public CatalogTable getTable() {
		return table;
	}
}
