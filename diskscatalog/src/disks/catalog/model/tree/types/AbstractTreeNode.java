package disks.catalog.model.tree.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Представляет собой абстрактный узел дерева.
 * 
 * @author Alexander Levin
 */
public abstract class AbstractTreeNode implements ITreeNode {
	
	protected Integer id;
	protected String name;
	protected Integer parentId;
	protected String comments = "";
	
	protected List<ITreeNode> children = null;
	protected ITreeNode parent = null;
	
	protected boolean firstRefresh = false;
	protected boolean logicalRoot = false;
	
	/**
	 * Создает пустой узел.
	 */
	public AbstractTreeNode() {
		
	}
		
	/**
	 * Создает узел с определенными параметрами.
	 * 
	 * @param id - уникальный идентификатор узла.
	 * @param name - символическое имя узла.
	 * @param parentId - уникальный идентификатор родительского узла.
	 */
	public AbstractTreeNode(Integer id, String name, Integer parentId) {
		this.id = id;
		this.name= name;
		this.parentId = parentId;
	}
	
	/**
	 * Устанавливает значение уникального идентификатора узла.
	 * 
	 * @param id - уникальный идентификатор узла.
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	/**
	 * Устанавливает уникальный идентификатор родительского узла для данного.
	 * 
	 * @param parentId - уникальный идентификатор узла.
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * Возвращает уникальный идентификатор родительского узла для даннного.
	 */
	public Integer getParentId() {
		return parentId;
	}
	
	public String toString() {		
		return name;
	}

	public void addChild(ITreeNode child) {
		if (canHaveChildren()) {
			if (children == null) {
				children = new ArrayList<ITreeNode>();
			}
			children.add(child);
			Collections.sort(children);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	public List<ITreeNode> getChildren() {
		return children;
	}

	public String getName() {		
		return name;
	}

	public ITreeNode getParent() {
		return parent;
	}

	public boolean hasChildWithName(String name) {
		if (canHaveChildren()) {
			if (children == null) {
				return false;			
			}
			
			for (ITreeNode node : children) {
				if (node.getName().equals(name)) {
					return true;
				}
			}			
		} else {
			throw new UnsupportedOperationException();
		}
		return false;
	}

	public boolean hasChildren() {
		if (canHaveChildren()) {			
			if (children == null || children.size() == 0) {
				return false;
			}			
			return true;
		}
		return false;
	}

	public boolean hasParent() {
		if (parentId != null || parent != null) {
			return true;
		}
		return false;
	}

	public void removeChild(ITreeNode child) {
		if (canHaveChildren()) {
			if (children.contains(child)) {
				children.remove(child);
			}
		} else {
			throw new UnsupportedOperationException();
		}
	}

	public void setChildren(List<ITreeNode> children) {
		this.children = children;
	}

	public void setFirstRefresh(boolean firstRefresh) {
		this.firstRefresh = firstRefresh;
	}
	
	public boolean wasRefreshed() {
		return firstRefresh;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(ITreeNode parent) {
		this.parent = parent;
		this.parentId = parent.getId();
	}
	
	abstract public boolean canHaveChildren();
	
	public int compareTo(ITreeNode obj) {
		if (obj instanceof AbstractTreeNode) {
			AbstractTreeNode objNode = (AbstractTreeNode) obj;
			int compareByType = 0;
			if (this.getClass() == objNode.getClass()) {
				compareByType = 0;
			} else {
				if (getPrioritet() < objNode.getPrioritet()) {
					compareByType = 1;
				} else {
					compareByType = -1;
				}
			}

			if (compareByType != 0) {
				return compareByType;
			}

			if (name == null) {
				return 1;
			}

			return this.name.compareTo(objNode.getName());
		}
		return 0;
	}
	
	public boolean isLogicalRoot() {
		return logicalRoot;
	}
	
	public void setLogicalRoot(boolean logicalRoot){
		this.logicalRoot = logicalRoot;		
	}
	
	public void sortChildren() {
		if (hasChildren()) {			 
			Collections.sort(children);
		}
	}
	
	/**
	 * Возвращает приоритет узла. Приоритет используется при сортировке узлов
	 * разных типов. К примеру, у узла типа "категория" приоритет выше, чем у
	 * узла типа "образ", поэтому при сортировке сначала будут идти узлы типа
	 * "категория", а только потом "образы". 
	 * 
	 * @return <b>int</b> - приоритет узла. 
	 */
	abstract public int getPrioritet();
	
	/**
	 * Возвращает комментарии для данного узла.
	 * 
	 * @return <b>String</b> - строка комменариев для данного узла.
	 */
	public String getComments() {
		return comments;
	}
	
	/**
	 * Устанавливает комментарии для данного узла.
	 * 
	 * @param comments - новая <b>String</b> строка комментариев.
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	abstract public void refresh();
	
	abstract public ITreeNode clone();
}
