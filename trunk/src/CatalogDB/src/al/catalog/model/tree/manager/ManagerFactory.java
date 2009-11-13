package al.catalog.model.tree.manager;

import java.util.HashMap;
import java.util.Map;

import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.model.tree.types.images.ImgCategoryNode;

public class ManagerFactory {
	
	private static Map<Class, INodeManager> managers = new HashMap<Class, INodeManager>();

	static {		
		managers.put(ImgCategoryNode.class, new ImgCategoryManager());
		managers.put(ImageNode.class, new ImageManager());
	}
	
	public static INodeManager getNodeManager(Class className) {
		return managers.get(className);		
	}
}
