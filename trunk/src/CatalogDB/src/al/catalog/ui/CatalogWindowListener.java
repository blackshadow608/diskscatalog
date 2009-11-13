package al.catalog.ui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import al.catalog.model.DBException;
import al.catalog.model.DBManager;

public class CatalogWindowListener implements WindowListener {
	
	protected DBManager dbManager;
	
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
