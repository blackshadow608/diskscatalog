package disks.catalog.ui.view.table;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;

import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.ui.CatFrame;
import disks.catalog.ui.action.ActionManager;


/**
 * Кастомизированная для нужд приложения таблица.
 * 
 * @author Alexander Levin
 */
public class CatalogTable extends JTable {
	
	private int lastSelIndex = 0;
	private Object lastSelValue = null;
	
	private TabKeyListener tabKeyListener = new TabKeyListener();
	
	public CatalogTable(DBTreeModel dbModel, ActionManager actionManager) {
		super(new CustomTableModel(dbModel));
		
		getModel().addTableModelListener(new CustomTableModelListener(this));
		
		setRowHeight(20);
		setShowGrid(false);
		setDoubleBuffered(true);
		setRowMargin(0);
		setBorder(BorderFactory.createEmptyBorder(CatFrame.BORDER,
				CatFrame.BORDER, CatFrame.BORDER, CatFrame.BORDER));

		ActiveTableItemListener tableItemListener = new ActiveTableItemListener(dbModel, this);
		getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		addMouseListener(tableItemListener);
		
		DBTreeModelListener listener = new DBTreeModelListener(this);
		dbModel.addListener(listener);
		
		addKeyListener(tabKeyListener);
		
		TableFocusListener focusListener = new TableFocusListener(actionManager);
		addFocusListener(focusListener);
		
		TableItemsChangingListener tableChangeListener = new TableItemsChangingListener(this);
		dbModel.addListener(tableChangeListener);
		
		setFillsViewportHeight(true);
	}
	
	private class CustomTableModelListener implements TableModelListener {
		
		private CatalogTable table;
		
		public CustomTableModelListener(CatalogTable table) {
			this.table = table;
		}

		public void tableChanged(TableModelEvent e) {
			switch(e.getType()) {
				case TableModelEvent.INSERT: intervalAdded(e); break;
				case TableModelEvent.DELETE: intervalRemoved(e); break;				
			}			
		}
		
		private void intervalAdded(TableModelEvent e) {
			CustomTableModel tableModel = (CustomTableModel) e.getSource();
			CustomTableModelEvent customEvent = (CustomTableModelEvent) e;
			if (tableModel.getOpenedNode() == customEvent.getTreeNode()
					.getParent()) {
				if (table.hasFocus()) {
					table.clearSelection();
					table.selectLast();
				}
			}
		}

		private void intervalRemoved(TableModelEvent e) {			
			CustomTableModel tableModel = (CustomTableModel) e.getSource();
			CustomTableModelEvent customEvent = (CustomTableModelEvent) e;
			if (tableModel.getOpenedNode() == customEvent.getTreeNode()
					.getParent()) {
				if (table.hasFocus()) {
					int size = tableModel.getSize();
					if (lastSelIndex >= size) {
						lastSelIndex = size - 1;
					}
					setSelectedIndex(lastSelIndex);
				}				
			}
		}		
	}
	
	public void setLastSelectedIndex(int index) {
		lastSelIndex = index;
	}

	public void setLastSelectedValue(Object value) {
		lastSelValue = value;
	}

	public Object getLastSelectedValue() {
		return lastSelValue;
	}
	
	public void updateLastSelectedValue() {
		
	}

	public int getLastSelectedIndex() {
		return lastSelIndex;
	}
	
	public void updateLastSelectedIndex() {
		int index = getSelectedRow();
		if (index >= 0) {
			setLastSelectedIndex(index);
		}		
	}

	public void selectLast() {
		int size = getModel().getRowCount();
		
		setSelectedIndex(lastSelIndex);		
		
		if (lastSelIndex < 0) {
			if (size == 0) {
				transferFocus();
			} else {
				setSelectedIndex(0);
			}
		}
		
		if (lastSelIndex >= size) {
			lastSelIndex = size - 1;
			setSelectedIndex(lastSelIndex);
		}		
		
		if(lastSelIndex >= 0 && size == 0) {
			transferFocus();			
		}
		
		if(lastSelIndex < 0) {
			setSelectedIndex(0);
			transferFocus();
		}
	}
	
	public void setSelectedIndex(int index) {
		if (index >= getModel().getRowCount()) {
			return;
		}
		getSelectionModel().setSelectionInterval(index, index);
	}
	
	public TableCellRenderer getCellRenderer(int row, int columnIndex) {		
		return new ColumnRenderer();
    }
	
	/**
	 * Слушатель событий клавиатуры. Как только пользователь нажимает TAB,
	 * таблица передает фокус.
	 * 
	 * @author Alexander Levin
	 */
	private class TabKeyListener implements KeyListener {

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_TAB) {
				CatalogTable.this.transferFocus();
			}
		}

		public void keyReleased(KeyEvent e) {

		}

		public void keyTyped(KeyEvent e) {

		}
	}
}
