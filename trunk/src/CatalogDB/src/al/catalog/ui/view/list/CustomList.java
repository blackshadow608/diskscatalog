package al.catalog.ui.view.list;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import al.catalog.model.tree.DBTreeModel;
import al.catalog.ui.CatalogFrame;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.popup.ListPopupMouseListener;
import al.catalog.ui.view.list.event.CustomListDataEvent;

public class CustomList extends JList {

	private int lastSelIndex = -1;
	private Object lastSelValue = null;
	
	ListItemsChangingListener listChangeListener;
	
	public CustomList(DBTreeModel dbModel, ActionManager actionManager) {		
		super(new CustomListModel(dbModel));		
		getModel().addListDataListener(new CustomListDataListener(this));
		setBorder(BorderFactory.createEmptyBorder(CatalogFrame.BORDER, CatalogFrame.BORDER, CatalogFrame.BORDER, CatalogFrame.BORDER));
		
		ListCellRenderer listRenderer = new CustomListCellRenderer();
		ActiveListItemListener listSelectionListener = new ActiveListItemListener(dbModel);
		ListFocusListener listFocusListener = new ListFocusListener(actionManager);
		
		ListPopupMouseListener listMouseListener = new ListPopupMouseListener(this, actionManager);
		ActiveItemMouseListener activeItemListener = new ActiveItemMouseListener(dbModel);
		
		actionManager.setProperty(ActionManager.PROPERTY_VIEW_PANEL, this);
		
		setLayoutOrientation(JList.HORIZONTAL_WRAP);
		setVisibleRowCount(0);		
		setCellRenderer(listRenderer);
		getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		addListSelectionListener(listSelectionListener);		
		addFocusListener(listFocusListener);
		addMouseListener(activeItemListener);
		addMouseListener(listMouseListener);
		
		listChangeListener = new ListItemsChangingListener(this);		
		dbModel.addListener(listChangeListener);
	}
	
	private class CustomListDataListener implements ListDataListener {
		
		private CustomList list;
		
		public CustomListDataListener(CustomList list) {
			this.list = list;
		}

		public void contentsChanged(ListDataEvent e) {
						
		}

		public void intervalAdded(ListDataEvent e) {			
			CustomListDataEvent customEvent = (CustomListDataEvent) e;
			CustomListModel listModel = (CustomListModel) getModel();
			if (listModel.getOpenedNode() == customEvent.getTreeNode()
					.getParent()) {
				if (list.hasFocus()) {
					list.clearSelection();
					list.selectLast();
				}
			}
		}

		public void intervalRemoved(ListDataEvent e) {			
			CustomListDataEvent customEvent = (CustomListDataEvent) e;
			CustomListModel listModel = (CustomListModel) getModel();
			if (listModel.getOpenedNode() == customEvent.getTreeNode()
					.getParent()) {
				if (list.hasFocus()) {
					int size = getModel().getSize();
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

	public int getLastSelectedIndex() {
		return lastSelIndex;
	}

	public void selectLast() {
		int size = getModel().getSize();
		
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

	protected void processMouseEvent(MouseEvent e) {
		String eventParams = e.paramString();

		if (eventParams.indexOf("MOUSE_PRESSED") >= 0) {
			JList list = (JList) e.getSource();
			int index = -1;
			int x = e.getX();
			int y = e.getY();
			for (int i = list.getFirstVisibleIndex(); i <= list
					.getLastVisibleIndex(); i++) {
				Rectangle cellBounds = list.getCellBounds(i, i);
				if (cellBounds != null) {
					int minX = (int) cellBounds.getMinX();
					int maxX = (int) cellBounds.getMaxX();
					int minY = (int) cellBounds.getMinY();
					int maxY = (int) cellBounds.getMaxY();
					if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
						index = i;
					}
				}
			}

			if (index < 0) {
				CustomListModel listModel = (CustomListModel) list.getModel();
				listModel.activeNodeWasChanged(null);
				list.clearSelection();
				list.transferFocus();
			} else {
				if (eventParams.indexOf("button=3") >= 0) {
					list.requestFocus();
					if (!list.isSelectedIndex(index)) {
						list.setSelectedIndex(index);
					}
				}
				super.processMouseEvent(e);
			}
		} else {
			super.processMouseEvent(e);
		}
	}

	protected void processMouseMotionEvent(MouseEvent e) {
		if (e.getID() != MouseEvent.MOUSE_DRAGGED) {
			super.processMouseMotionEvent(e);
		}
	}
}