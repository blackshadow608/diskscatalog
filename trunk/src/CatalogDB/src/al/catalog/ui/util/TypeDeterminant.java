package al.catalog.ui.util;

import java.util.HashMap;
import java.util.Map;

import al.catalog.model.tree.types.ITreeNode;
import al.catalog.model.tree.types.file.FileNode;
import al.catalog.model.tree.types.file.FolderNode;
import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.model.tree.types.images.ImgCategoryNode;
import al.catalog.ui.resource.ResourceManager;

/**
 * Класс <b>TypeDeterminant</b> - утилитный класс для определения типа узла.
 * Имеет единственный метод, которому на вход подается ссылка на узел, а на
 * выходе получается текстовое название типа узла.
 * 
 * @author Alexander Levin
 */
public class TypeDeterminant {

	private static Map<Class<?>, String> types = new HashMap<Class<?>, String>();
	
	private static final String FILE = "nodeType.file";
	private static final String FOLDER = "nodeType.folder";
	private static final String IMAGE = "nodeType.image";
	private static final String CATEGORY = "nodeType.category";

	static {
		types.put(FileNode.class, ResourceManager.getString(FILE));
		types.put(FolderNode.class, ResourceManager.getString(FOLDER));
		types.put(ImageNode.class, ResourceManager.getString(IMAGE));
		types.put(ImgCategoryNode.class, ResourceManager.getString(CATEGORY));
	}

	/**
	 * Возвращает текстовое название типа узла, ссылка на который передается
	 * методу в качестве параметра.
	 * 
	 * @param treeNode
	 *            - ссылка на <b>ITreeNode</b>, для которого нужно определить
	 *            название типа.
	 * 
	 * @return <b>String</b> строка названия типа узла.
	 */
	public static String getType(ITreeNode treeNode) {
		String type = types.get(treeNode.getClass());
		if (type != null) {
			return type;
		}
		return "";
	}

}
