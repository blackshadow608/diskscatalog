package disks.catalog.model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import disks.catalog.model.action.DBAction;
import disks.catalog.model.action.IDBActionListener;
import disks.catalog.model.connection.IConnectionListener;
import disks.catalog.model.connection.IConnectionProvider;

/**
 * Представляет собой более или менее универсальный инструмент для работы
 * с базой данных. Всё взаимодействие с БД, состоящее из некоторого перечня
 * операций, идёт именно через этот класс. Операции можно поделить на два вида.
 * Первый вид - общие операции. Примером таких операций может служить
 * открытие/закрытие БД, создание SavePoint'ов, откат изменений БД к
 * определенному SavePoint'у. Второй вид операций - конкретная работа с БД,
 * редактирование таблиц. Практически все операции с БД инкапсулируются в {@link DBAction}.
 * 
 * @author Alexander Levin
 */
public class DBManager implements IConnectionProvider, IConstants {
	
<<<<<<< .mine
	/*
	 * Путь к серверу, который используется только в отладочных целях.
	 */
	private static final String PATH_SERVER = "jdbc:hsqldb:hsql://localhost/CatDB";
	
	/*
	 * Пользователь для доступа к БД.
	 */
	private static final String USER = "sa";
	
	/*
	 * Праоль для доступа к БД.
	 */
	private static final String PASSWORD = "";
	private static final String SAVEPOINT = "CatalogSavepoint_";
    private static final String DEFAULT_SAVEPOINT = "DefaultSavePoint";
=======
	private int mode = MODE_STANDALONE;
>>>>>>> .r117
	
<<<<<<< .mine
    /*
	 * Режими работы с БД. В отладочных целях используется MODE_SERVER, который
	 * позволяет держать с БД несколько соединений.
	 */
	public static final int MODE_STANDALONE = 0;
	public static final int MODE_SERVER = 1;
	
	/*
	 * Имя базы данных.
	 */
	private static final String DB_NAME = "CatDB";
	
	/*
	 * JDBC префикс для доступа к БД.
	 */
	private static final String DB_PREFIX = "jdbc:hsqldb:file:";
	
=======
>>>>>>> .r117
	private Connection connection = null;
<<<<<<< .mine
	
	private boolean driverIsLoaded = false;
	
	/*
	 * Режим работы с БД, по умолчанию MODE_STANDALONE.
	 */
	private int mode = MODE_STANDALONE;
=======
>>>>>>> .r117
	
	private DBAction lastAction = null;
	private List<String> savepoints = new ArrayList<String>();
	private List<DBAction> actions = new ArrayList<DBAction>();	
	private List<IConnectionListener> conListeners = new ArrayList<IConnectionListener>();
	private List<IDBActionListener> actionListeners = new ArrayList<IDBActionListener>();
	
	private String curPath;
	
	/**
	 * Создает новый объект {@link DBManager}.
	 */
	public DBManager() {		
		File currentFile = new File("");
		curPath = currentFile.getAbsolutePath();		
	}
	
	/**
	 * Открывает БД для дальнейшей работы приложения.
	 * 
	 * @throws DBException если при открытии БД произошли ошибки.
	 */
	public void open() throws DBException {		
		try {
			
            DriverLoader.loadDriver();
            
            if (connection == null || connection.isClosed()) {
            	connection = DriverManager.getConnection(getDBPath(), USER, PASSWORD);
            	connection.setAutoCommit(false);
            	addDefaultSavepoint();
            }            
        } catch (SQLException e) {
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
        	throw new DBException(e);            
        }
	}
	
	/**
	 * Закрывает БД.
	 * 
	 * @throws DBException если при закрытии БД произошли ошибки.
	 */
	public void close() throws DBException { 
		try {
			if (connection != null && !connection.isClosed()) {				
				try {
					if (mode == MODE_STANDALONE) {
						String shutdownQuery = "SHUTDOWN";
						Statement statement = connection.createStatement();
						statement.executeQuery(shutdownQuery);						
					}					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				connection.close();				
			}
		} catch (SQLException e) {
			throw new DBException(e);
		}		
	}
	
	/**
	 * Сохраняет все изменения, которые были произведены с БД с момента её открытия
	 * или последнего сохранения.
	 * 
	 * @throws DBException если при сохранении изменений произошла ошибка.
	 */
	public void save() throws DBException {
		try {		
			connection.commit();
			if (mode == MODE_STANDALONE) {
				String shutdownQuery = "SHUTDOWN COMPACT";
				Statement statement = connection.createStatement();
				statement.executeQuery(shutdownQuery);			
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		savepoints.clear();		
		actions.clear();
	}
	
	public Connection getConnection() {
		return connection;		
	}
		
	/**
	 * Добавляет dbAction в стек и запускает действие на выполнение.
	 * 
	 * @param dbAction - {@link DBAction} объект, который нужно добавить и выполнить.
	 */
	public void addAction(DBAction dbAction) {
		
		if (lastAction != null) {
			int nextIndex = actions.indexOf(lastAction) + 1;			
			while (nextIndex < actions.size()) {				
				actions.remove(nextIndex);
			}
		} else {
			actions.clear();
		}
		
		actions.add(dbAction);
		lastAction = dbAction;
		
		fireUndoEnabled();
		fireRedoDisabled();
		fireActionWasAdded();		
	}
	
	/**
	 * Отмена последнего выполненного действия.
	 */
	public void undoAction() {
		if (lastAction != null) {
			int index = actions.indexOf(lastAction);
			lastAction.unexecute();
			if (index < 1) {
				lastAction = null;
				fireUndoDisabled();								
			} else {
				lastAction = actions.get(index - 1);				
			}
			fireRedoEnabled();
		}		
	}
	
	/**
	 * Повтор выполнения дейсвтия.
	 */
	public void redoAction() {
		if (actions.size() > 0) {								
			if (lastAction == null) {				
				lastAction = actions.get(0);				
			} else {				
				int index = actions.indexOf(lastAction);
				lastAction = actions.get(index + 1);
			}
			
			lastAction.execute();
			
			if (actions.indexOf(lastAction) == actions.size() - 1) {
				fireRedoDisabled();				
			}
			fireUndoEnabled();
		}
	}
	
	/**
	 * Прерывает выполнение действия.
	 * 
	 * @param dbAction - {@link DBAction} объект, выполнение которого необходимо прервать.
	 */
	public void abortAction(DBAction dbAction) {
		if (dbAction != null) {
			if (actions.contains(dbAction) && dbAction == lastAction) {
				int index = actions.indexOf(lastAction);
				if (index < 1) {
					lastAction = null;
					fireUndoDisabled();								
				} else {
					lastAction = actions.get(index - 1);				
				}				
			}			
		}
	}
	
	/**
	 * Возвращает последний выполненный {@link DBAction}.
	 * 
	 * @return Возвращает последний выполненный {@link DBAction}.
	 */
	public DBAction getLastAction() {
		return lastAction;
	}
	
	/**
	 * Добавляет {@link IConnectionListener} слушателя.
	 * 
	 * @param listener - добавляемый {@link IConnectionListener} слушатель.
	 */
	public void addConnectionListener(IConnectionListener listener) {
		conListeners.add(listener);
	}
	
	/**
	 * Удаляет {@link IConnectionListener} слушателя.
	 * 
	 * @param listener - удаляемый {@link IConnectionListener} слушатль.
	 */
	public void removeConnectionListener(IConnectionListener listener) {
		conListeners.remove(listener);
	}
	
	/**
	 * Добавляет {@link DBActionListener} слушателя.
	 * 
	 * @param listener - добавляемый {@link DBActionListener} слушаткль.
	 */
	public void addActionListener(IDBActionListener listener) {
		actionListeners.add(listener);
	}
	
	/**
	 * Удаляет {@link IDBActionListener} слушателя.
	 * 
	 * @param listener - удаляемый {@link IDBActionListener} слушатель.
	 */
	public void removeDBActionListener(IDBActionListener listener) {
		actionListeners.remove(listener);
	}
	
	/**
	 * Оповещает IConnectionListener слушателей о том, что соединение с БД
	 * было открыто.
	 */
	public void fireConnectionOpened() {
		for (IConnectionListener listener : conListeners) {
			listener.connectionWasOpened();			
		}
	}
	
	/**
	 * Оповещает IConnectionListener слушателей о том, что соединение с БД
	 * было закрыто.
	 */
	public void fireConnectionClosed() {
		for (IConnectionListener listener : conListeners) {
			listener.connectionWasClosed();			
		}
	}
	
	/**
	 * Оповещает IDBActionListener слушателей о том, что откат последнего выполненного
	 * DBAction доступен.
	 */
	public void fireUndoEnabled() {
		for (IDBActionListener listener : actionListeners) {
			listener.undoBecameEnabled();
		}
	}
	
	/**
	 * Оповещает IDBActionListener слушателей о том, что выполнение последнего
	 * откатанного DBAction доступно.
	 *
	 */
	public void fireRedoEnabled() {
		for (IDBActionListener listener : actionListeners) {
			listener.redoBecameEnabled();
		}		
	}
	
	/**
	 * Оповещает IDBActionListener слушателей о том, что откат последнего выполненного
	 * DBAction недоступен.
	 */
	public void fireUndoDisabled() {
		for (IDBActionListener listener : actionListeners) {
			listener.undoBecameDisabled();
		}		
	}
	
	/**
	 * Оповещает IDBActionListener слушателей о том, что выполнение последнего
	 * откатанного DBAction недоступен. 
	 */
	public void fireRedoDisabled() {
		for (IDBActionListener listener : actionListeners) {
			listener.redoBecameDisabled();
		}		
	}
	
	/**
	 * Оповещает всех слушателей IDBActionListener о том, что некий DBAction
	 * был добавлен в стек. 
	 */
	public void fireActionWasAdded() {
		for (IDBActionListener listener : actionListeners) {
			listener.someActionWasAdded();
		}		
	}

	/**
	 * Устанавливает новую точку сохранения SavePoint. Предполагается, что добавляет
	 * в очередь для возможности возвращения базы данных в исходное состояние при отмене
	 * каких-либо действий.
	 */
	public Savepoint setSavepoint() {
		String number = Integer.toString(savepoints.size());
		Savepoint savepoint = null;
		String savepointName = SAVEPOINT + number;
		
		try {			
			savepoint = connection.setSavepoint(savepointName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		savepoints.add(savepointName);
		
		return savepoint;
	}
	
	/**
	 * Откатывает состояние базы данных к точке сохранения {@link Savepoint},
	 * переданному в качестве параметра методу.
	 * 
	 * @param savepoint - {@link Savepoint} объект, к которому требуется произвести откат.
	 */
	public void rollback(Savepoint savepoint) {
		try {
			connection.rollback(savepoint);
			savepoints.remove(savepoints.indexOf(savepoint.getSavepointName()));
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Делает полный откат всех несохраненных изменений базы данных.
	 */
	public void rollback() {
		try {
			connection.rollback();
			savepoints.clear();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Производит коммит, то есть сохранение, а точнее фиксацию различных
	 * изменений в базе данных.
	 */
	public void commit() {		
		try {		
			connection.commit();
			addDefaultSavepoint();
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		
		savepoints.clear();				
	}
	
	/**
	 * Вызывается до начала выполнения dbAction.
	 * 
	 * @param dbAction - DBAction, который будет выполняться.
	 */
	public void fireBeforeAction(DBAction dbAction) {
		for (IDBActionListener listener : actionListeners) {
			listener.beforeActionExecuting(dbAction);
		}		
	}
	
	/**
	 * Вызывается после выполнения dbAction. Оповещает всех заинтересованных
	 * слушателей о том, что выполнение Action'а завершилось.
	 * 
	 * @param dbAction -
	 *            DBAction, выполнение которого завершилось.
	 */
	public void fireAfterAction(DBAction dbAction) {
		for (IDBActionListener listener : actionListeners) {
			listener.afterActionExecuting(dbAction);
		}		
	}
	
	/**
	 * Вызывается для оповещения слушателей IDBActionListener о том, что во время выполнения
	 * DBAction выбросилось исключение.
	 * 
	 * @param dbAction - DBAction, во время выполнения которого было выброшено исключение.
	 * @param exception - исключение, которое было выброшено.
	 * 
	 */
	public void fireActionExecutingException(DBAction dbAction, Exception exception) {
		for (IDBActionListener listener : actionListeners) {
			listener.actionThrowException(dbAction, exception);
		}				
	}
	
	/**
	 * Устанавливает режим обращения приложения к базе данных. Если это MODE_STANDALONE, то
	 * приложение обращается к файлу базы данных в режиме STANDALONE, если это MODE_SERVER, то
	 * приложение обращается к серверу базы данных, если таковой запущен и на нем имеется база
	 * данных с определенным именем.
	 * 
	 * @param mode - режим работы БД.
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}
	
<<<<<<< .mine
	/**
	 * Осуществляет загрузку драйвера.
	 * 
	 * @throws ClassNotFoundException если указанный класс драйвера не обнаружен.
	 */
	private void loadDriver() throws ClassNotFoundException{
		if (!driverIsLoaded) {
			Class.forName("org.hsqldb.jdbcDriver");			
		}		
	}
	
=======
>>>>>>> .r117
	private String getDBFolder(String curPath) {
		return curPath + "\\hsqldb\\data\\CatDB\\";
	}
	
	/**
	 * Возвращает JDBC url в зависимости от режима работы с БД.
	 * 
	 * @return Возвращает JDBC url в виде {@link String} строки.
	 */
	private String getDBPath() {
		if (mode == MODE_STANDALONE) {
			String dbPath = getDBFolder(curPath);
			File dbFolder = new File(dbPath);
			if (dbFolder.exists()) {
				//Если файлик catalog.jar лежит в корне
				return DB_PREFIX + dbPath + DB_NAME;
			}
			int index = curPath.indexOf("\\src\\CatalogDB");
			if (index >= 0) {
				//Если catalog.jar лежит в сырцах, в директории проекта
				String path = curPath.substring(0, index);
				return DB_PREFIX + getDBFolder(path) + DB_NAME;			
			}			
		}		
		return PATH_SERVER;
	}
	
	private void addDefaultSavepoint() {
		try {
			connection.setSavepoint(DEFAULT_SAVEPOINT);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
