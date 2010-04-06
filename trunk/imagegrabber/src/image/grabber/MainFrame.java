package image.grabber;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * Главное окно приложения.
 * 
 * @author Alexander Levin
 */
public class MainFrame extends JFrame {

	/*
	 * Шрифт, который используется в приложении.
	 */
	public static final Font FONT = new Font("Verdana", Font.PLAIN, 11);

	/*
	 * Размеры окна.
	 */
	private static final Dimension SIZE = new Dimension(440, 167);

	/*
	 * Заголовок окна.
	 */
	private static final String TITLE = "kinopoisk.ru";

	private static final int LABEL_WIDTH = 140;
	private static final int VALUE_WIDTH = 220;

	private static final int BOR = 5;

	private Grabber grabber;

	private JLabel procLabel;
	private JTextField fField;
	private JTextField lField;
	private JButton grabButton;
	private JButton stopButton;
	private JProgressBar progressBar;

	/*
	 * Диалог консоли.
	 */
	private JDialog consoleDialog;

	/*
	 * Многострочное текстовое поле, которое располагается в диалоге консоли.
	 * Здесь отображается информация о закачивание рисунков виде набора записей.
	 */
	private JTextArea textArea;

	/**
	 * Создает главное окно приложения.
	 */
	public MainFrame() {
		customizeFont();
		createGUI();
		configWindow();
		createConsoleDialog();
		grabber = new Grabber();
		grabber.addListener(new GrabberListener());
	}

	/**
	 * Конфигурирует главное окно приложения - устанавливает размеры, заголовок,
	 * положение и так далее.
	 */
	private void configWindow() {
		setPreferredSize(SIZE);
		setTitle(TITLE);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	/**
	 * Создает интерфейс приложения.
	 */
	private void createGUI() {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(createFolderPanel());
		mainPanel.add(createLinkPanel());
		Border emptyBorder = BorderFactory.createEmptyBorder(BOR, BOR, BOR, BOR); 
		contentPanel.setBorder(emptyBorder);
		contentPanel.add(mainPanel, BorderLayout.CENTER);
		contentPanel.add(createProgressPanel(), BorderLayout.SOUTH);
		getContentPane().add(contentPanel);

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Console");
		JMenuItem menuItem = new JMenuItem("Show console");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(grabber.getConcole());
				consoleDialog.setVisible(true);
			}
		});
		menu.add(menuItem);
		menuBar.add(menu);
		setJMenuBar(menuBar);
	}

	private JPanel createFolderPanel() {
		JPanel fPanel = new JPanel();
		fPanel.setLayout(new BoxLayout(fPanel, BoxLayout.X_AXIS));
		fPanel.setBorder(BorderFactory.createEmptyBorder(BOR, 0, BOR, 0));

		JLabel fLabel = new JLabel("Folder name:");
		Dimension preffSize = fLabel.getPreferredSize();
		fLabel.setPreferredSize(new Dimension(LABEL_WIDTH, preffSize.height));
		fPanel.add(fLabel);

		fField = new JTextField();
		preffSize = fField.getPreferredSize();
		fField.setPreferredSize(new Dimension(VALUE_WIDTH, preffSize.height));
		fPanel.add(fField);

		return fPanel;
	}

	private JPanel createLinkPanel() {
		JPanel lPanel = new JPanel();
		lPanel.setLayout(new BoxLayout(lPanel, BoxLayout.X_AXIS));
		lPanel.setBorder(BorderFactory.createEmptyBorder(BOR, 0, BOR, 0));

		JLabel lLabel = new JLabel("Hyper link:");
		Dimension linkPreffSize = lLabel.getPreferredSize();
		Dimension labelSize = new Dimension(LABEL_WIDTH, linkPreffSize.height); 
		lLabel.setPreferredSize(labelSize);
		lPanel.add(lLabel);

		lField = new JTextField();
		Dimension preffSize = lField.getPreferredSize();
		lField.setPreferredSize(new Dimension(VALUE_WIDTH, preffSize.height));
		lPanel.add(lField);

		return lPanel;
	}

	/**
	 * Создает панель, на которой отображается прогресс выполнения закачки
	 * рисунков.
	 * 
	 * @return {@link JPanel} с компонентами для отображения прогресса
	 *         выполнения процесса закачки.
	 */
	private JPanel createProgressPanel() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(createGrabButton());
		bottomPanel.add(createStopButton());
		JLabel totalLabel = new JLabel("Images (total/proccessed): ");
		bottomPanel.add(totalLabel);
		procLabel = new JLabel(" 0/0 ");
		bottomPanel.add(procLabel);
		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(80, 20));
		progressBar.setIndeterminate(false);
		bottomPanel.add(progressBar);
		return bottomPanel;
	}

	/**
	 * Создает кнопку, которая запускает процесс закачивания рисунков.
	 * 
	 * @return {@link JButton}
	 */
	private JButton createGrabButton() {
		grabButton = new JButton("Start");
		grabButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grabber.start(lField.getText(), fField.getText());
			}
		});
		return grabButton;
	}

	/**
	 * Создает кнопку, которая останавливает процесс закачивания рисунков.
	 * 
	 * @return {@link JButton} для остановки процесса скачивания рисунков.
	 */
	private JButton createStopButton() {
		stopButton = new JButton("Stop");
		stopButton.setEnabled(false);
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grabber.stop();
			}
		});
		return stopButton;
	}

	/**
	 * Создает диалоговое окно консоли. В консоль при выполнении приложения
	 * добавляетмя информацию о некоторых событиях, к примеру начало скачивание
	 * рисунка.
	 */
	private void createConsoleDialog() {
		consoleDialog = new JDialog(this, "Console");
		textArea = new JTextArea();
		textArea.setEditable(false);
		consoleDialog.getContentPane().setLayout(new BorderLayout());
		consoleDialog.getContentPane().add(new JScrollPane(textArea),
				BorderLayout.CENTER);
		consoleDialog.setPreferredSize(new Dimension(500, 250));
		consoleDialog.pack();
		consoleDialog.setLocationRelativeTo(null);
	}

	/**
	 * Устанавливает шрифты для компонентов.
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
	 * Точка входа приложения.
	 * 
	 * @param args
	 *            - параметры командной строки, которые передаются приложению
	 *            при запуске в виже массива строк.
	 */
	public static void main(String[] args) {

		/*
		 * Пусть диалоги и фреймы рисуются самим Swing'ом.
		 */
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);

		new MainFrame();
	}

	private class GrabberListener implements IGrabberListener {

		public void error(Grabber grabber, String message) {
			JOptionPane.showMessageDialog(MainFrame.this, message, "Error",
					JOptionPane.ERROR_MESSAGE);
			grabButton.setEnabled(true);
			stopButton.setEnabled(false);
			progressBar.setIndeterminate(false);
			fField.setEnabled(true);
			lField.setEnabled(true);
		}

		public void started(Grabber grabber) {
			grabButton.setEnabled(false);
			stopButton.setEnabled(true);
			progressBar.setIndeterminate(true);
			fField.setEnabled(false);
			lField.setEnabled(false);
		}

		public void stateChanged(Grabber grabber) {
			procLabel.setText(grabber.getProcText());
		}

		public void stopped(Grabber grabber) {
			grabButton.setEnabled(true);
			stopButton.setEnabled(false);
			progressBar.setIndeterminate(false);
			fField.setEnabled(true);
			lField.setEnabled(true);
		}

	}
}
