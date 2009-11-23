package al.catalog.model.tree.types;

import java.util.List;

/**
 * Интерфейс, который должны реализовать все классы, которые будут использоваться
 * как узлы дерева.
 * 
 * @author Alexander Levin
 */
public interface ITreeNode extends Comparable<ITreeNode> {
	
	/**
	 * Возвращает дочерние ITreeNode узлы данного узла.
	 * 
	 * @return List&lt;ITreeNode&gt; - список дочерних узлов.
	 */
	public List<ITreeNode> getChildren();
	
	/**
	 * Устанавливает дочерние узлы для данного.
	 * 
	 * @param children - List&lt;ITreeNode&gt; список новых дочерних узлов.
	 */
	public void setChildren(List<ITreeNode> children);
	
	/**
	 * Добавляет узел в список дочерних.
	 * 
	 * @param child - ITreeNode добавляемый узел.
	 */
	public void addChild(ITreeNode child);
	
	/**
	 * Удаляет узел из списка дочерних узлов.
	 * 
	 * @param child - ITreeNode удаляемый узел.
	 */
	public void removeChild(ITreeNode child);
	
	/**
	 * Проверяет имеет ли данный узел дочерние узлы.
	 * 
	 * @return true если имеет, false если нет.
	 */
	public boolean hasChildren();
	
	/**
	 * Возвращает ссылку на родительский узел для данного.
	 * 
	 * @return ITreeNode родительский узел, null если родительского узла нет.
	 */
	public ITreeNode getParent();
	
	/**
	 * Устанавливает родительский узел для данного.
	 * 
	 * @param parent - ITreeNode новый родитель.
	 */
	public void setParent(ITreeNode parent);
	
	/**
	 * Проверяет имеет ли узел родителя.
	 * 
	 * @return true если имееет, false если нет.
	 */
	public boolean hasParent();
	
	/**
	 * Проверяет, имеет ли данный узел дочерний с определенным символическим именем.
	 * 
	 * @param name - символическое имя дочернего узла. 
	 * 
	 * @return <code>true</code> если такой дочерний узел есть, <code>false</code> если нет.
	 */
	public boolean hasChildWithName(String name);
	
	/**
	 * Возвращает символическое имя узла.
	 * 
	 * @return String символическое имя узла.
	 */
	public String getName();
	
	/**
	 * Устанавливает новое символическое имя узла.
	 * 
	 * @param name - новое имя.
	 */
	public void setName(String name);
	
	/**
	 * Устанавливает флаг, который показывает, обновлялся ли узел или нет. В нашем случае
	 * все данные об узле хранятся в БД. Для того, чтобы не загружать всю БД в память приложения
	 * у каждого узла имеется некий флаг firstRefresh. Если он true, то это показывает, что все
	 * данные из БД об этом узле загружены.
	 * 
	 * @param firstRefresh - новое <b>boolean</b> значение флага.
	 */
	public void setFirstRefresh(boolean firstRefresh);
	
	/**
	 * Проверяет обновлялся ли узел.
	 * 
	 * @return <b>true</b> если обновлялся, <b>false</b> если нет.
	 */
	public boolean wasRefreshed();
	
	/**
	 * Проверяет, может ли данный узел иметь дочерние узлы.
	 * 
	 * @return <b>true</b> если может,<b>false</b> если нет.
	 */
	public boolean canHaveChildren();
	
	/**
	 * Проверяет, является ли узел логическим корневым. Логический корневой узел - корневой
	 * узел в БД, а в приложении он не обязательно будет корневым.
	 * 
	 * @return <b>true</b> если узел - логический корневой,<b>false</b> если нет.
	 */
	public boolean isLogicalRoot();
	
	/**
	 * Устанавливает флаг, который обозначает, является ли данный узел логическим корневым.
	 * 
	 * @param logicalRoot - новое <b>boolean</b> значение флага.
	 */
	public void setLogicalRoot(boolean logicalRoot);
	
	/**
	 * Сортирует дочерние узлы данного узла.
	 */
	public void sortChildren();
	
	/**
	 * Возвращает уникальный идентификатор узла.
	 */
	public Integer getId();
	
	/**
	 * Выполняет обновление узла, например, загружает все данные для узла с БД.
	 *
	 */
	public void refresh();
	
	public ITreeNode clone();
}
