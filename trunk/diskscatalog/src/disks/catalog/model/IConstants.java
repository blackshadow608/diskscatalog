package disks.catalog.model;

/**
 * Содержит необходимые константы.
 * 
 * @author Alexander Levin
 */
public interface IConstants {
	public static final String PATH_SERVER = "jdbc:hsqldb:hsql://localhost/CatDB";
	public static final String USER = "sa";
	public static final String PASSWORD = "";
	public static final String SAVEPOINT = "CatalogSavepoint_";
	public static final String DEFAULT_SAVEPOINT = "DefaultSavePoint";
	public static final int MODE_STANDALONE = 0;
	public static final int MODE_SERVER = 1;
	public static final String DB_NAME = "CatDB";
	public static final String DB_PREFIX = "jdbc:hsqldb:file:";
}