package disks.catalog;

import disks.catalog.model.DBManager;
import disks.catalog.ui.action.ActionManager;

/**
 * Содержит ссылки на главные сущности приложения. При написании приложения
 * выяснилось, что во многих местах используются DBManager и ActionManager.
 * Чтобы уменьшить количество параметров у некоторых методов и конструкторов
 * классов, был сделан данный класс. Содержит внутри себя ссылку на <b>DBManager</b>
 * и на <b>ActionManager</b>.
 * 
 * @author Alexander Levin
 */
public class MainEntity {

	private DBManager dbManager;
	private ActionManager actionManager;

	/**
	 * Создает новый пустой объект <b>MainEntity</b>.
	 */
	public MainEntity() {

	}

	/**
	 * Создает новый объект <b>MainEntity</b> с параметрами.
	 * 
	 * @param dbManager -
	 *            <b>DBManager</b> менеджер базы данных.
	 * @param aManager -
	 *            <b>ActionManager</b> менеджер.
	 */
	public MainEntity(DBManager dbManager, ActionManager aManager) {
		this.dbManager = dbManager;
		this.actionManager = aManager;
	}

	/**
	 * Устанавливает новую ссылку на <b>DBManager</b>.
	 * 
	 * @param dbManager -
	 *            новая ссылка на объект <b>DBManager</b>.
	 */
	public void setDBManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	/**
	 * Возвращает ссылку на <b>DBManager</b>.
	 * 
	 * @return ссылка на <b>DBManager</b>.
	 */
	public DBManager getDBManager() {
		return dbManager;
	}

	/**
	 * Устанавливает новую ссылку на <b>ActionManager</b>.
	 * 
	 * @param actionManager -
	 *            новая ссылак на объект <b>ActionManager</b>.
	 */
	public void setActionManager(ActionManager actionManager) {
		this.actionManager = actionManager;
	}

	/**
	 * Возвращает ссылку на <b>ActionManager</b>.
	 * 
	 * @return ссылка на <b>ActionManager</b>.
	 */
	public ActionManager getActionManager() {
		return actionManager;
	}
}
