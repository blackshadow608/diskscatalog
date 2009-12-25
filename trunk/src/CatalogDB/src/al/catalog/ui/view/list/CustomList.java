package al.catalog.ui.view.list;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import al.catalog.logger.Logger;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.model.tree.types.ITreeNode;
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
		ActiveListItemListener listSelectionListener = new ActiveListItemListener(dbModel, this);
		ListFocusListener listFocusListener = new ListFocusListener(actionManager);
		
		ListPopupMouseListener listMouseListener = new ListPopupMouseListener(this, actionManager);
		
		actionManager.setProperty(ActionManager.PROPERTY_VIEW_PANEL, this);
		
		setLayoutOrientation(JList.HORIZONTAL_WRAP);
		setVisibleRowCount(0);		
		setCellRenderer(listRenderer);
		setDoubleBuffered(true);
		getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		addMouseListener(listSelectionListener);		
		addFocusListener(listFocusListener);
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
	
	/**
	 * Запоминает текущее выделенное value. В данном случае под value понимается
	 * ITreeNode.
	 */
	public void updateLastSelectedValue() {
		lastSelValue = getSelectedValue();
	}
	
	/**
	 * Запоминает индекс текущего выделенного элемента.
	 */
	public void updateLastSelectedIndex() {
		int index = getSelectedIndex();		
		if (index >= 0) {
			setLastSelectedIndex(index);
		}		
	}

	public Object getLastSelectedValue() {
		return lastSelValue;
	}

	public int getLastSelectedIndex() {
		return lastSelIndex;
	}

	/*public void selectLast() {
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
	}*/
	
	public void selectLast() {
		Logger.openStack("CustomList: selectLast()");
		
		int size = getModel().getSize();
		
		setSelectedIndex(lastSelIndex);
		
		Logger.logIntoStack("lastSelIndex: " + lastSelIndex);
		
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
		
		Logger.closeStack();
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
	
	/**
	 * Переключает режим отображения элементов в "Иконки".
	 */
	public void setIconsView() {
		CustomListCellRenderer renderer = (CustomListCellRenderer) getCellRenderer();
		int type = renderer.getType();
		
		if (type != CustomListCellRenderer.TYPE_ICONS) {
			renderer.setType(CustomListCellRenderer.TYPE_ICONS);
			setLayoutOrientation(JList.HORIZONTAL_WRAP);			
			setCellRenderer(renderer);
		}
	}
	
	/**
	 * Переключает режим отображения элементов в "Список".
	 */
	public void setListView() {
		CustomListCellRenderer renderer = (CustomListCellRenderer) getCellRenderer();
		int type = renderer.getType();
				
		if (type != CustomListCellRenderer.TYPE_LIST) {
			renderer.setType(CustomListCellRenderer.TYPE_LIST);
			setLayoutOrientation(JList.VERTICAL_WRAP);			
			setCellRenderer(renderer);
		}
	}
	
	public List<ITreeNode> getSelectedNodes() {
		Object[] selectedValues = getSelectedValues();
		List<ITreeNode> selectedNodes = null;
		
		if (selectedValues.length > 0) {
			selectedNodes = new ArrayList<ITreeNode>(selectedValues.length);
			for (int i = 0; i < selectedValues.length; i++) {
				selectedNodes.add((ITreeNode)selectedValues[i]);				
			}			
		}	
		
		return selectedNodes;
	}
}
