package disks.catalog.model.tree;

import java.util.ArrayList;
import java.util.List;

import disks.catalog.logger.Logger;
import disks.catalog.model.DBManager;
import disks.catalog.model.action.IActionCallback;
import disks.catalog.model.dao.DAOFactory;
import disks.catalog.model.dao.hsql.HSQLDAOFactory;
import disks.catalog.model.tree.action.CreateNodeAction;
import disks.catalog.model.tree.action.MoveNodeAction;
import disks.catalog.model.tree.action.RemoveNodeAction;
import disks.catalog.model.tree.action.RenameNodeAction;
import disks.catalog.model.tree.action.UpdateNodeAction;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.model.tree.types.RootNode;


/**
 * Представляет собой модель дерева, которое сохраняется в базе данных. Модель  содержит
 * информацию о структуре дерева - для этого имеется ссылка на корневой узел. Предоставляет
 * возможность создавать, редактировать и удалять узлы, причем все эти действия сохраняются
 * в истории, поэтому их можно отменить или же наоборот повторить выполнение отмененного, то
 * есть реализация Undo/Redo. Кроме этого модель содержит информацию об активных и открытых
 * узлах дерева. Модель также хранит историю того, какие узлы были активными.
 * 
 * @author Alexander Levin
 */
public class DBTreeModel {

	protected List<IDBTreeModelListener> listeners = new ArrayList<IDBTreeModelListener>();

	protected List<IHistoryListener> historyListeners = new ArrayList<IHistoryListener>();

	protected List<ITreeNode> activeNodes;
	
	protected ITreeNode openedNode;

	protected DBManager dbManager;

	protected DAOFactory daoFactory;

	private List<ITreeNode> history = new ArrayList<ITreeNode>();

	private int point = -1;

	private int size = 0;

	private ITreeNode root = null;

	public DBTreeModel(DBManager dbManager) {
		this.dbManager = dbManager;
		this.daoFactory = new HSQLDAOFactory(dbManager);
	}

	public DBManager getDBManager() {
		return dbManager;
	}

	/**
	 * Возвращает корневой узел дерева.
	 * 
	 * @return <b>ITreeNode</b> корневой узел дерева.
	 */
	public ITreeNode getRoot() {
		if (root == null) {
			root = new RootNode(daoFactory);
			root.refresh();
			root.setFirstRefresh(true);
		}
		return root;
	}

	/**
	 * Создает узел определенного класса, ссылка на объект <b>Class</b> которого
	 * передается методу в качестве параметра.
	 * 
	 * @param className
	 *            - <b>Class</b> класс, который должен иметь создаваемый узел.
	 */
	public void createNode(Class<?> className) {
		new CreateNodeAction(this, className).execute();
	}
	
	public void createNode(Class<?> className, IActionCallback callback) {
		new CreateNodeAction(this, className, callback).execute();
	}

	/**
	 * Удаляет активный узел дерева.
	 */
	public void removeNode() {
		new RemoveNodeAction(this, activeNodes).execute();
	}

	/**
	 * Обновляет или редактирует узел, ссылка на который передается методу в
	 * качестве параметра.
	 * 
	 * @param treeNode
	 *            - <b>ITreeNode</b> узел, который требуется обновить.
	 */
	public void renameNode(ITreeNode treeNode) {
		new RenameNodeAction(this, activeNodes.get(0)).execute();
	}
	
	public void updateNode(ITreeNode treeNode) {
		new UpdateNodeAction(this, treeNode).execute();
	}

	/**
	 * Перемещает выбранные узлы к новому родителю.
	 * 
	 * @param nodes - <b>List</b> узлов, которые требуется переместить.
	 * @param parentNode - <b>ITreeNode</b> новый родительский узел.
	 */
	public void moveNodes(List<ITreeNode> nodes, ITreeNode parentNode) {
		new MoveNodeAction(this, nodes, parentNode).execute();
	}

	public void setActiveNode(ITreeNode node) {
		Logger.openStack();
		if (node == null) {
			activeNodes = null;
		} else {
			if (activeNodes == null) {
				activeNodes = new ArrayList<ITreeNode>();
			}
			activeNodes.clear();
			activeNodes.add(node);
		}
		Logger.closeStack();
	}

	public void setActiveNodes(List<ITreeNode> activeNodes) {
		Logger.openStack();
		this.activeNodes = activeNodes;
		Logger.closeStack();
	}

	public void addActiveNode(ITreeNode node) {
		Logger.openStack();
		if (activeNodes == null) {
			activeNodes = new ArrayList<ITreeNode>();
		}
		activeNodes.add(node);
		Logger.closeStack();
	}

	public List<ITreeNode> getActiveNodes() {
		return activeNodes;
	}

	public void setOpenedNode(ITreeNode node) {
		Logger.openStack();
		openedNode = node;
		addToHistory(node);
		Logger.closeStack();
	}
	
	public ITreeNode getOpenedNode() {
		return openedNode;
	}

	public DAOFactory getDAOFactory() {
		return daoFactory;
	}

	public void addListener(IDBTreeModelListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IDBTreeModelListener listener) {
		listeners.remove(listener);
	}

	public void fireInsertNode(ITreeNode node, ITreeNode parent) {
		Logger.openStack();
		for (IDBTreeModelListener listener : listeners) {
			listener.nodeWasInserted(node, parent);
		}
		Logger.closeStack();
	}

	public void fireRemoveNode(ITreeNode node, ITreeNode parent, int index) {
		Logger.openStack();
		for (IDBTreeModelListener listener : listeners) {
			listener.nodeWasRemoved(node, parent, index);
		}
		Logger.closeStack();
	}

	public void fireChangeActiveNode(ITreeNode node) {
		Logger.openStack();
		for (IDBTreeModelListener listener : listeners) {
			listener.activeNodeWasChanged(node);
		}
		Logger.closeStack();
	}

	public void fireChangeActiveNode(ITreeNode node, Object source) {
		Logger.openStack();
		for (IDBTreeModelListener listener : listeners) {
			listener.activeNodeWasChanged(node, source);
		}
		Logger.closeStack();
	}

	public void fireChangeActiveNodes(List<ITreeNode> nodes) {
		Logger.openStack();
		for (IDBTreeModelListener listener : listeners) {
			listener.activeNodesWasChanged(nodes);			
		}
		Logger.closeStack();
	}

	public void fireChangeActiveNodes(List<ITreeNode> nodes, Object source) {
		Logger.openStack();
		for (IDBTreeModelListener listener : listeners) {
			listener.activeNodesWasChanged(nodes, source);
		}
		Logger.closeStack();
	}

	public void fireChangeOpenedNode(ITreeNode node) {
		Logger.openStack();
		for (IDBTreeModelListener listener : listeners) {
			listener.openedNodeWasChanged(node);
		}
		Logger.closeStack();
	}

	public void fireChangeOpenedNode(ITreeNode node, Object source) {
		Logger.openStack();
		for (IDBTreeModelListener listener : listeners) {
			listener.openedNodeWasChanged(node, source);
		}
		Logger.closeStack();
	}

	public void fireChangeNode(ITreeNode node) {
		Logger.openStack();
		for (IDBTreeModelListener listener : listeners) {
			listener.nodeWasChanged(node);
		}
		Logger.closeStack();
	}

	public void fireChangeStructureNode(ITreeNode node) {
		Logger.openStack();
		for (IDBTreeModelListener listener : listeners) {
			listener.nodeStructureWasChanged(node);
		}
		Logger.closeStack();
	}

	public void fireBeforeChangeNode(ITreeNode node) {
		Logger.openStack();
		for (IDBTreeModelListener listener : listeners) {
			listener.onBeforeChangeNode(node);
		}
		Logger.closeStack();
	}

	public void fireAfterChangeNode(ITreeNode node) {
		Logger.openStack();
		for (IDBTreeModelListener listener : listeners) {
			listener.onAfterChangeNode(node);
		}
		Logger.closeStack();
	}

	public void back() {
		point = point - 1;
		if (point >= 0) {
			ITreeNode node = history.get(point);
			openedNode = node;
			activeNodes.clear();
			activeNodes.add(node);
			fireChangeOpenedNode(node);
			fireChangeActiveNode(node);
		}
		checkHistory();
	}

	public void forward() {
		int size = history.size();
		if (point < size - 1) {
			point = point + 1;
			ITreeNode node = history.get(point);
			setOpenedNode(node);
			fireChangeOpenedNode(node);
			setActiveNode(node);
			fireChangeActiveNode(node);
		}
		checkHistory();
	}

	/**
	 * Очищает историю.
	 */
	public void clearHistory() {
		history.clear();
		point = -1;
		size = 0;
		fireBackBecameDisabled();
		fireForwardBecameDisabled();
		fireClearBecameDisabled();
	}

	private void addToHistory(ITreeNode node) {
		/**
		 * Если был выделен (открыт - opened) null узел (то есть снято выделение) и до этого
		 * никакой другой узел не выделялся (то есть не был opened)
		 */
		if (node == null && point < 0) {
			return;
		}

		/**
		 * Если до этого был выделен какой-либо узел и уже добавлен в history.
		 */
		if (point >= 0) {
			ITreeNode pointed = history.get(point);
			int nextIndex = point + 1;
			/**
			 * Если узел, который добавляем к history уже добавлен.
			 */
			if (node == pointed) {
				return;
			}
			/**
			 * Если мы сделали так: выделили узел1, потом узел2, затем сделали back,
			 * а потом выделили узел2.
			 */
			if (nextIndex < size) {
				ITreeNode nextPointed = history.get(nextIndex);
				if (node == nextPointed) {
					point++;
					return;
				}
			}
		}

		/**
		 * Если указатель активного history-узла указывает не на последниц узел.
		 */
		if (point < size - 1) {			
			size = point + 1;
		}

		/*
		 * А можно просто напросто переориентирвать point и size, не удаляя
		 * ничего из history-списка, а просто затирая ненужное.
		 */
		point++;
		size++;
		/*
		 * Если затирать нечего, то добавляем.
		 */
		if (point >= history.size()) {
			history.add(node);
		} else {
			/*
			 * Если есть, что затереть, то затираем старое значение.
			 */
			history.set(point, node);
		}

		checkHistory();
		fireClearBecameEnabled();
	}

	private void checkHistory() {
		int maxIndex = size - 1;
		if (point <= 0) {
			fireBackBecameDisabled();
			if (size > 1) {
				fireForwardBecameEnabled();
			}
		} else if (point == maxIndex) {
			fireForwardBecameDisabled();
			fireBackBecameEnabled();
		} else if (point > 0 && point < maxIndex) {
			fireBackBecameEnabled();
			fireForwardBecameEnabled();
		}
	}

	/**
	 * Добавляет нового <b>IHistoryListener</b> слушателя истории в список
	 * имеющихся.
	 * 
	 * @param historyListener
	 *            - новый <b>IHistoryListener</b> слушатель.
	 */
	public void addHistoryListener(IHistoryListener historyListener) {
		historyListeners.add(historyListener);
	}

	public void removeHistoryListener(IHistoryListener historyListener) {
		historyListeners.remove(historyListener);
	}

	public void fireBackBecameDisabled() {
		for (IHistoryListener listener : historyListeners) {
			listener.backBecameDisabled();
		}
	}

	public void fireForwardBecameDisabled() {
		for (IHistoryListener listener : historyListeners) {
			listener.forwardBecameDisabled();
		}
	}

	public void fireBackBecameEnabled() {
		for (IHistoryListener listener : historyListeners) {
			listener.backBecameEnabled();
		}
	}

	public void fireForwardBecameEnabled() {
		for (IHistoryListener listener : historyListeners) {
			listener.forwardBecameEnabled();
		}
	}

	public void fireClearBecameDisabled() {
		for (IHistoryListener listener : historyListeners) {
			listener.clearBecameDisabled();
		}
	}

	public void fireClearBecameEnabled() {
		for (IHistoryListener listener : historyListeners) {
			listener.clearBecameEnabled();
		}
	}
}
