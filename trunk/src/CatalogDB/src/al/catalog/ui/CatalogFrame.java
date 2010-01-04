package al.catalog.ui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.UIManager;

import al.catalog.MainEntity;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.resource.ResourceManager;

/**
 * Основное окно приложения. Именно это окно появляется сразу после того, как
 * запускается приложение.
 * 
 * @author Alexander Levin
 */
public class CatalogFrame extends JFrame {
	
	public static final Font FONT = new Font("Verdana", Font.PLAIN, 11);
	
	private static final Dimension SIZE = new Dimension(700, 400);
	private static final String TITLE = "mainFrame.title";
	
	public static final int BORDER = 5;
	
	private MainEntity mainEntity;
	private ContentPanel contentPanel;
	
	/**
	 * Создает новое окно с параметром <b>DBManager</b>, ссылка на который
	 * передается в качестве параметра.
	 * 
	 * @param dbManager -
	 *            ссылка на <b>DBManager</b> менеджер базы данных.
	 */
	public CatalogFrame(MainEntity mainEntity) {
		this.mainEntity = mainEntity;
		customizeFont();
		createGUI();
		configWindow();
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
		mainEntity.getActionManager().setProperty(ActionManager.PROPERTY_OWNER_FRAME, this);
		
		contentPanel = new ContentPanel(mainEntity, this);
		getContentPane().add(contentPanel);
		
		CatalogMenuBar menuBar = new CatalogMenuBar(mainEntity);		
		setJMenuBar(menuBar);
	}
	
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
	
	public ContentPanel getContentPanel() {
		return contentPanel;
	}
}
