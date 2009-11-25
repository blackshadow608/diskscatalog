package al.catalog.ui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import al.catalog.model.DBException;
import al.catalog.model.DBManager;

/**
 * Слушает оконные события основного окан приложения. Основная задача -
 * отслеживать событие закрытия окна и при возникновении такого события
 * закрывать соединение с базой данных.
 * 
 * @author Alexander Levin
 */
public class CatalogWindowListener implements WindowListener {
	
	protected DBManager dbManager;
	
	/**
	 * Создает новый <b>CatalogWindowListener</b> с параметром <b>DBManager</b>,
	 * через который и происходит закоытие соединения с базой данных.
	 * 
	 * @param dbManager - ссылка на <b>DBManager</b>, который управляет базой данных.
	 */
	public CatalogWindowListener(DBManager dbManager) {
		this.dbManager = dbManager;				
	}

	public void windowClosed(WindowEvent event) {
		try {
			dbManager.close();
		} catch (DBException e) {
			e.printStackTrace();
		}
	}

	public void windowClosing(WindowEvent event) {
		try {
			dbManager.close();
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	
	public void windowActivated(WindowEvent e) {

	}

	public void windowDeactivated(WindowEvent e) {

	}

	public void windowDeiconified(WindowEvent e) {

	}

	public void windowIconified(WindowEvent e) {

	}

	public void windowOpened(WindowEvent e) {

	}
}
