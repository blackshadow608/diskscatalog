package disks.catalog.model.tree.manager;

import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.types.ITreeNode;

/**
 * Интерфейс, который должны реализовывать все менеджеры узлов.
 * 
 * @author Alexander Levin
 */
public interface INodeManager {

	/**
	 * Добавляет узел дерева в базу данных. Подразумевается следующее поведение
	 * метода. Если методу в качестве параметра передаётся не null, то именно он
	 * добавляется в базу данных. Если же передаваемый узел null, то метод
	 * создает новый <b>ITreeNode</b>, ссылку на который затем возвращает.
	 * Такое нужно для реализации поведения Undo/Redo, когда ссылка на узел
	 * запоминается где-то, чтобы можно было откатить или выполнить действие по
	 * созданию узла снова.
	 * 
	 * @param treeNode -
	 *            <b>ITreeNode</b> узел, который необходимо добавить, если он
	 *            не null.
	 * @param dbModel -
	 *            <b>DBTreeModel</b> модель дерева, через методы которой
	 *            осуществляется добавление узла.
	 * 
	 * @return ссылка на <b>ITreeNode</b>, который был добавлен в базу данных.
	 */
	public ITreeNode createNode(ITreeNode treeNode, DBTreeModel dbModel);

	/**
	 * Обновляет информацию в базе данных об узле <b>ITreeNode</b>, ссылка на
	 * который передается методу в качестве параметра.
	 * 
	 * @param treeNode -
	 *            ссылка на <b>ITreeNode</b>, данные о котором необходимо
	 *            обновить.
	 * @param dbModel -
	 *            <b>DBTreeModel</b> модель дерева, через методы которой
	 *            осуществляется обновление узла.
	 * 
	 * @return <b>true</b>, если обновление данных произошло успешно и <b>false</b>,
	 *         если нет.
	 */
	public boolean updateNode(ITreeNode treeNode, DBTreeModel dbModel);

	/**
	 * Удаляет <b>ITreeNode</b> узел, ссылка на который передается методу в
	 * качестве параметра.
	 * 
	 * @param treeNode -
	 *            <b>ITreeNode</b> узел, который необходимо удалить.
	 * @param dbModel -
	 *            <b>DBTreeModel</b> модель дерева, через методы которой
	 *            осуществляется удаление узла.
	 * 
	 * @return <b>true</b>, если удаление узла прошло успешно и <b>false</b>,
	 *         если нет.
	 */
	public boolean removeNode(ITreeNode treeNode, DBTreeModel dbModel);

}
