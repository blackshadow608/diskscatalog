package al.catalog.ui.view.table;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;

import al.catalog.model.tree.DBTreeModel;
import al.catalog.ui.action.ActionManager;

public class CustomTable extends JTable {
	
	private int lastSelIndex = 0;
	private Object lastSelValue = null;
	
	public CustomTable(DBTreeModel dbModel, ActionManager actionManager) {
		super(new CustomTableModel(dbModel));
		getModel().addTableModelListener(new CustomTableModelListener(this));
		
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_TAB) {
					CustomTable.this.transferFocus();					
				}
			}

			public void keyReleased(KeyEvent e) {
				
			}

			public void keyTyped(KeyEvent e) {
				
			}
		});
		
		TableFocusListener focusListener = new TableFocusListener(actionManager);
		addFocusListener(focusListener);
		
		TableItemsChangingListener tableChangeListener = new TableItemsChangingListener(this);
		dbModel.addListener(tableChangeListener);
	}
	
	private class CustomTableModelListener implements TableModelListener {
		
		private CustomTable table;
		
		public CustomTableModelListener(CustomTable table) {
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
}
