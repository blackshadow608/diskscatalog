package disks.catalog;

import disks.catalog.model.DBManager;
import disks.catalog.ui.action.ActionManager;

/**
 * Содержит ссылки на главные сущности приложения. При написании приложения
 * выяснилось, что во многих местах используются {@link DBManager} и
 * {@link ActionManager}. Чтобы уменьшить количество параметров у некоторых
 * методов и конструкторов классов, был сделан данный класс. Содержит внутри
 * себя ссылку на {@link DBManager} и на {@link ActionManager}.
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
	 * Создает новый объект {@link MainEntity} с параметрами.
	 * 
	 * @param dbManager
	 *            - {@link DBManager} менеджер базы данных.
	 * @param aManager
	 *            - {@link ActionManager} менеджер.
	 */
	public MainEntity(DBManager dbManager, ActionManager aManager) {
		this.dbManager = dbManager;
		this.actionManager = aManager;
	}

	/**
	 * Устанавливает новую ссылку на {@lnik DBManager}.
	 * 
	 * @param dbManager
	 *            - новая ссылка на объект {@link DBManager}.
	 */
	public void setDBManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	/**
	 * Возвращает ссылку на {@link DBManager}.
	 * 
	 * @return ссылка на {@link DBManager}.
	 */
	public DBManager getDBManager() {
		return dbManager;
	}

	/**
	 * Устанавливает новую ссылку на {@linlk ActionManager}.
	 * 
	 * @param actionManager
	 *            - новая ссылак на объект {@link ActionManager}.
	 */
	public void setActionManager(ActionManager actionManager) {
		this.actionManager = actionManager;
	}

	/**
	 * Возвращает ссылку на {@link ActionManager}.
	 * 
	 * @return ссылка на {@link ActionManager}.
	 */
	public ActionManager getActionManager() {
		return actionManager;
	}
}
