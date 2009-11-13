package al.catalog.ui.util;

import java.util.HashMap;
import java.util.Map;

import al.catalog.model.tree.types.ITreeNode;
import al.catalog.model.tree.types.file.FileNode;
import al.catalog.model.tree.types.file.FolderNode;
import al.catalog.model.tree.types.images.ImageNode;
import al.catalog.model.tree.types.images.ImgCategoryNode;

/**
 * Класс <b>TypeDeterminant</b> - утилитный класс для определения типа узла.
 * Имеет единственный метод, которому на вход подается ссылка на узел, а на
 * выходе получается текстовое название типа узла.
 * 
 * @author Alexander Levin
 */
public class TypeDeterminant {

	private static Map<Class, String> types = new HashMap<Class, String>();

	static {
		types.put(FileNode.class, "Файл");
		types.put(FolderNode.class, "Папка с файлами");
		types.put(ImageNode.class, "Образ");
		types.put(ImgCategoryNode.class, "Категория");
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
