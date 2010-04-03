package disks.catalog.ui.view.list.event;

import javax.swing.event.ListDataEvent;

import disks.catalog.model.tree.types.ITreeNode;


/**
 * Кастомизированное событие. Необходимо было хранить также узел, к которому относится
 * событие. Для этого был расширен класс стандартного события и добавлен новый метод
 * для получения узла.
 * 
 * @author Alexander Levin
 */
public class CustomListDataEvent extends ListDataEvent {

	private ITreeNode treeNode;
	
	public CustomListDataEvent(Object source, int type, int index0, int index1) {
		super(source, type, index0, index1);		
	}
	
	public CustomListDataEvent(Object source, int type, int index0, int index1, ITreeNode treeNode) {
		super(source, type, index0, index1);
		this.treeNode = treeNode;
	}
	
	/**
	 * Получаем узел, к которому относится событие.
	 * 
	 * @return <b>ITreeNode</b> узел, к которому относится событие.
	 */
	public ITreeNode getTreeNode() {
		return treeNode;
	}
}
