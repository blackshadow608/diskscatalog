package al.catalog.model.tree.manager;

import java.util.HashMap;
import java.util.Map;

import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.model.tree.types.images.ImgCategoryNode;

/**
 * Фабрика <b>ManagerFactory</b>, которая управляет созданием менеджеров узлов
 * <b>INodeManager</b>.
 * 
 * @author Alexander Levin
 */
public class ManagerFactory {

	private static Map<Class<?>, INodeManager> managers = new HashMap<Class<?>, INodeManager>();

	static {
		managers.put(ImgCategoryNode.class, new ImgCategoryManager());
		managers.put(ImageNode.class, new ImageManager());
	}

	/**
	 * Возвращает ссылку на <b>INodeManager</b> для класса узла, который
	 * передан методу в качестве параметра.
	 * 
	 * @param className - класс <b>Class</b> узла, для которого необходимо получить менеджер узлов.
	 * 
	 * @return - требуемый <b>INodeManager</b> менеджер узлов.
	 */
	public static INodeManager getNodeManager(Class<?> className) {
		return managers.get(className);
	}
}
