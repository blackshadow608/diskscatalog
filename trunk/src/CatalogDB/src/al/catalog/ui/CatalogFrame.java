package al.catalog.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;

import al.catalog.model.DBManager;
import al.catalog.model.tree.DBTreeModel;
import al.catalog.ui.action.ActionManager;
import al.catalog.ui.popup.PopupMenuHelper;
import al.catalog.ui.resource.ResourceManager;
import al.catalog.ui.tree.CatalogTree;
import al.catalog.ui.view.ViewPanel;

public class CatalogFrame extends JFrame {
	
	public static final Font FONT = new Font("Verdana", Font.PLAIN, 11);
	
	private static final Dimension SIZE = new Dimension(900, 600);
	private static final String TITLE = "mainFrame.title";
	
	private static final int DIVIDER_SIZE = 8;
	private static final int DIVIDER_POS = 250;
	
	public static final int BORDER = 5;
	
	private DBManager dbManager;
	private JTree tree;
	private ViewPanel viewPanel;
	
	public CatalogFrame(DBManager dbManager) {
		this.dbManager = dbManager;
		customizeFont();
		createGUI();
        configWindow();
	}
	
	private void configWindow() {		
		//setFocusTraversalPolicy(new CustomFocusTraversalPolicy(this));
        setPreferredSize(SIZE);
        setTitle(ResourceManager.getString(TITLE));
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        addWindowListener(new CatalogWindowListener(dbManager));
    }
	
	private void createGUI() {
		DBTreeModel dbModel = dbManager.getTreeModel();				
				
		tree = new CatalogTree(dbManager);
		
		ActionManager actionManager = new ActionManager(dbManager);
		ProgressActionListener progressListener = new ProgressActionListener(this);
		
		dbManager.addConnectionListener(actionManager);
		dbManager.addDBActionListener(actionManager);
		dbManager.addDBActionListener(progressListener);
		dbModel.addHistoryListener(actionManager);		
		
		actionManager.setProperty(ActionManager.PROPERTY_TREE, tree);		
		actionManager.setProperty(ActionManager.PROPERTY_OWNER_FRAME, this);		
		
		CatalogMenuBar menuBar = new CatalogMenuBar(actionManager);
		menuBar.setFocusable(false);
		setJMenuBar(menuBar);
		
		dbModel.addListener(menuBar);
		
		PopupMenuHelper.createPopupMenu(tree, actionManager, PopupMenuHelper.TYPE_TREE);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		viewPanel = new ViewPanel(dbModel, actionManager, tree);
		viewPanel.setIconsView();
		actionManager.setProperty(ActionManager.PROPERTY_VIEW_PANEL, viewPanel);
		
		JPanel treePanel = new JPanel();
		treePanel.setBorder(BorderFactory.createEmptyBorder(CatalogFrame.BORDER, CatalogFrame.BORDER, CatalogFrame.BORDER, CatalogFrame.BORDER));
		treePanel.setLayout(new BorderLayout());
		treePanel.add(tree, BorderLayout.CENTER);
		treePanel.setBackground(Color.WHITE);
		
		JScrollPane treeScrollPane = new JScrollPane(treePanel);		
		treeScrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		treeScrollPane.getVerticalScrollBar().setUnitIncrement(10);
		treeScrollPane.getHorizontalScrollBar().setUnitIncrement(10);
		
		splitPane.setLeftComponent(treeScrollPane);
		splitPane.setRightComponent(viewPanel);
		splitPane.setDividerSize(DIVIDER_SIZE);
		splitPane.setDividerLocation(DIVIDER_POS);
		
		getContentPane().add(splitPane);
		
		CatalogToolBar toolBar = new CatalogToolBar(actionManager);
		
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
		
		JPanel statusBar = new JPanel();
		statusBar.setLayout(new BorderLayout());
		
		JPanel progressPanel = new JPanel();
		progressPanel.add(new JLabel("Открытие соединения..."));
		
		JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressPanel.add(progressBar);
        
        JButton pauseButton = new JButton("Пауза");
        pauseButton.setFocusable(false);
        progressPanel.add(pauseButton);
        
        JButton cancelButton = new JButton("Отмена");
        cancelButton.setFocusable(false);
        progressPanel.add(cancelButton);        
		
		statusBar.add(progressPanel, BorderLayout.EAST);
		
		statusBar.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, new Color(166, 166, 166)));
		
		getContentPane().add(statusBar, BorderLayout.PAGE_END);
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
	
	public JTree getTree() {
		return tree;		
	}
	
	public ViewPanel getViewPanel() {
		return viewPanel;
	}
}
