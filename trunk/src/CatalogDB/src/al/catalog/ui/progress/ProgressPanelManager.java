package al.catalog.ui.progress;

import al.catalog.model.DBAction;
import al.catalog.ui.CatFrame;

/**
 * Класс <b>ProgressPanelManager</b> управляет панелью прогресса выполнения
 * задач.
 * 
 * @author Alexander Levin
 */
public class ProgressPanelManager {
	
	private CatFrame mainFrame;
	private ProgressPanel progressPanel;
	
	/**
	 * Создает новый менеджер панели прогресса выполнения.
	 * 
	 * @param owner -
	 *            ссылка <b>CatalogFrame</b>, в нижней части которого будет
	 *            показывать панель прогресса выполнения.
	 */
	public ProgressPanelManager(CatFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	/**
	 * Показывает панель прогресса выполнения задачи <b>DBAction</b>, ссылка на
	 * которую передается методу в качестве параметра.
	 * 
	 * @param action -
	 *            ссылка <b>DBAction</b>, для которого будет показана панель
	 *            прогресса выполнения.
	 */
	public void showProgressBar(DBAction action) {
		if(progressPanel == null) {
			progressPanel = new ProgressPanel(this);
		}
		progressPanel.setAction(action);
		mainFrame.getContentPanel().showProgressBar(progressPanel);
	}
	
	/**
	 * Скрывает панель прогресса, которая отображается в нижней части основного
	 * окна приложения.
	 */
	public void hideProgressBar() {
		if(progressPanel != null) {
			mainFrame.getContentPanel().hideProgressBar(progressPanel);			
		}				
	}

}
