package disks.catalog.ui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.UIManager;

import disks.catalog.MainEntity;
import disks.catalog.ui.progress.ProgressListener;
import disks.catalog.ui.resource.ResourceManager;
import disks.catalog.ui.view.ViewPanel;


/**
 * Основное окно приложения. Именно это окно появляется сразу после того, как
 * запускается приложение.
 * 
 * @author Alexander Levin
 */
public class CatFrame extends JFrame {
	
	/*
	 * Шрифт, который будет использоваться для всех компонентов.
	 */
	public static final Font FONT = new Font("Verdana", Font.PLAIN, 11);
	
	/*
	 * Начальные размеры окна.
	 */
	private static final Dimension SIZE = new Dimension(660, 400);
	
	/*
	 * Заголовок окна.
	 */
	private static final String TITLE = "mainFrame.title";
	
	public static final int BORDER = 5;
	
	private MainEntity mainEntity;
	
	private ContentPanel contentPanel;
	
	/**
	 * Создает новое окно с параметром <b>DBManager</b>, ссылка на который
	 * передается в качестве параметра.
	 * 
	 * @param dbManager -
	 *            ссылка на {@link DBManager} менеджер базы данных.
	 */
	public CatFrame(MainEntity mainEntity) {
		this.mainEntity = mainEntity;
		
		customizeFont();
		createGUI();
		configWindow();
		
		mainEntity.getActionManager().setMainFrame(this);
	}
	
	private void configWindow() {		
        setPreferredSize(SIZE);
        setTitle(ResourceManager.getString(TITLE));
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        addWindowListener(new CatalogWindowListener(mainEntity));
    }
	
	private void createGUI() {
		contentPanel = new ContentPanel(mainEntity);
		getContentPane().add(contentPanel);
		setJMenuBar(new CatalogMenuBar(mainEntity));
		new ProgressListener(mainEntity, this);
	}
	
	/*
	 * Устанавливает шрифт для всех компонентов приложения.
	 */
	private void customizeFont() {		
		UIManager.put("Button.font", FONT);
		UIManager.put("Tree.font", FONT);
		UIManager.put("MenuItem.font", FONT);
		UIManager.put("Menu.font", FONT);
		UIManager.put("TabbedPane.font", FONT);
		UIManager.put("Label.font", FONT);
		UIManager.put("List.font", FONT);
		UIManager.put("TextField.font", FONT);
		UIManager.put("TextArea.font", FONT);
		UIManager.put("CheckBoxMenuItem.font", FONT);
		UIManager.put("RadioButtonMenuItem.font", FONT);
		UIManager.put("ComboBox.font", FONT);
		UIManager.put("Table.font", FONT);
		UIManager.put("Tree.rowHeight", 20);
	}
	
	/**
	 * Возвращает панель содержимого {@link ContentPanel}, на которой
	 * располагаются остальные компоненты графического интерфейса пользователя.
	 * 
	 * @return Возвращает {@link ContentPanel} панель содержиого.
	 */
	public ContentPanel getContentPanel() {
		return contentPanel;
	}
	
	public ViewPanel getViewPanel() {
		return contentPanel.getViewPanel();
	}
}
