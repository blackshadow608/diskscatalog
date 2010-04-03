package disks.catalog.model.tree;

import java.util.List;

import disks.catalog.model.tree.types.ITreeNode;


/**
 * Интерфейс, который должен реализовать слушатель, желающий прослушивать события,
 * связанные с изменением узлов дерева.
 *  
 * @author Alexander Levin
 */
public interface IDBTreeModelListener {
	
	/**
	 * Вызывается у слушателя после вставки узла.
	 * 
	 * @param node - ITreeNode узел, который был вставлен.
	 * @param parent - ITreeNode родитель узла.
	 */
	public void nodeWasInserted(ITreeNode node, ITreeNode parent);
	
	/**
	 * Вызывается у слушателя после удаления узла.
	 * 
	 * @param node - ITreeNode узел, который был удален.
	 * @param parent - ITreeNode родитель узла.
	 * @param index - индекс узла в списке дочерних узлов родителя.
	 */
	public void nodeWasRemoved(ITreeNode node, ITreeNode parent, int index);
	
	/**
	 * Вызывается у слушателя после изменения узла.
	 * 
	 * @param node - ITreeNode узел, который был изменен.
	 */
	public void nodeWasChanged(ITreeNode node);
	
	/**
	 * Вызывается у слушателя после изменения активного узла дерева.
	 * 
	 * @param node - ITreeNode узел, ставший активным.
	 */
	public void activeNodeWasChanged(ITreeNode node);
	
	/**
	 * Вызывается у слушателя после изменения активного узла дерева.
	 * 
	 * @param node - ITreeNode узел, ставший активным.
	 * @param source - Object объект-источник, который явился
	 * 			инициатором изменения активного узла.
	 */
	public void activeNodeWasChanged(ITreeNode node, Object source);
	
	/**
	 * Вызыватеся у слушателя, если активным стал не один узел, а сразу несколько.
	 * 
	 * @param nodes - List&lt;ITreeNode&gt; список узлов, ставших активными.
	 */
	public void activeNodesWasChanged(List<ITreeNode> nodes);
	
	/**
	 * Вызыватеся у слушателя, если активным стал не один узел, а сразу несколько.
	 * 
	 * @param nodes - List&lt;ITreeNode&gt; список узлов, ставших активными.
	 * @param source - Object объект-источник, который явился
	 * 			инициатором изменения активного узла.
	 */
	public void activeNodesWasChanged(List<ITreeNode> nodes, Object source);
	
	/**
	 * Вызывается у слушателя после изменения открытого узла дерева.
	 * 
	 * @param node - ITreeNode узел, который был открыт.
	 */
	public void openedNodeWasChanged(ITreeNode node);
	
	/**
	 * Вызывается у слушателя после изменения открытого узла дерева.
	 * 
	 * @param node - ITreeNode узел, который был открыт.
	 */
	public void openedNodeWasChanged(ITreeNode node, Object source);
	
	/**
	 * Вызывается у слушателя перед изменением узла, то есть перед вызовом
	 * <code>fireChangeNode(ITreeNode node)</code>.
	 * 
	 * @param node - ITreeNode узел, который будет изменен.
	 */
	public void onBeforeChangeNode(ITreeNode node);
	
	/**
	 * Вызывается у слушателя после изменения узла. На первый взгляд может
	 * показаться, что данный метод очень схож с методом
	 * <code>fireChangeNode(ITreeNode node)</code>, однако это не так. Как
	 * правило, если мы хотим отслеживать изменения узлов (в данном случае
	 * переименование), мы используем метод
	 * <code>fireChangeNode(ITreeNode node)</code>. Но бывают ситуации, когда
	 * нам нужно быть уверенными, что все слушатели изменения узлов оповещены.
	 * Например, у нас в дереве имеется сортировка и при переименовании узла мы
	 * хотим сразу же перемещать его в дереве на место, которое соответсвовало
	 * бы новому имени и сортировке. Однако, нашу DBModel слушает TreeModel и
	 * при использовании TreeModelListener из TableModel и его метода
	 * treeStructureChanged(ModelEvent event) происходит захлопывание
	 * распахнутых узлов. Как решить эту проблему. Для решения такой проблемы
	 * нам необходимо перед изменением узла (переименовании) запомнить все
	 * распахнутые пути, а после изменения вновь распахнуть старые. При
	 * использовании данного метода мы точно будем уверены, что TreeModel
	 * сделала свою "пакостную" работу и можно спокойно распахивать нужные узлы.
	 * 
	 * @param node - ITreeNode узел, который был изменен.
	 */
	public void onAfterChangeNode(ITreeNode node);
	
	/**
	 * Вызывается у слушателя, когда меняется структура дерева, то есть меняется порядок
	 * расположения узлов внутри родителя. Такое бывает, например, при переименовании узлов.
	 * В этом случае один из узлов меняет своё положение в списке дочерних относительно родителя.
	 * 
	 * @param node - ITreeNode узел, у которого был изменен порядок дочерних узлов. 
	 */
	public void nodeStructureWasChanged(ITreeNode node);
}
