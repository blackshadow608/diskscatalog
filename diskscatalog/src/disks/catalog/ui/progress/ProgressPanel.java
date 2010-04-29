package disks.catalog.ui.progress;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import disks.catalog.model.action.DBAction;


/**
 * Класс <b>ProgressPanel</b> представляет собой панель, на которой
 * отображается прогресс выполнения задачи, а также управляющие компоненты для
 * отмены выполняемой задачи, прерывания и дальнейшего возобновления выполнения.
 * 
 * @author Alexander Levin
 */
public class ProgressPanel extends JPanel {

	private DBAction action;

	private JLabel progressLabel;
	private JButton pauseButton;
	private JButton cancelButton;

	/**
	 * Создает новую панель отображения прогресса выполнения задачи.
	 * 
	 * @param progressManager -
	 *            ссылка <b>ProgressManager</b>, который управляет показом и
	 *            скрытием панели прогресса.
	 */
	public ProgressPanel(final ProgressPanelManager progressManager) {
		setLayout(new BorderLayout());

		JPanel progressPanel = new JPanel();

		progressLabel = new JLabel();
		progressPanel.add(progressLabel);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressPanel.add(progressBar);

		pauseButton = new JButton("Пауза");
		pauseButton.setFocusable(false);
		progressPanel.add(pauseButton);

		cancelButton = new JButton("Отмена");
		cancelButton.setFocusable(false);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action.abort();
				progressManager.hideProgressBar();
			}
		});
		progressPanel.add(cancelButton);

		add(progressPanel, BorderLayout.EAST);

		setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0,
				new Color(166, 166, 166)));
	}

	/**
	 * Устанвливает <b>DBAction</b>, прогресс выполнения которого будет
	 * показывать панель.
	 * 
	 * @param action -
	 *            <b>DBAction</b>, для которого нужно показать прогресс
	 *            выполнения на панели.
	 */
	public void setAction(DBAction action) {
		this.action = action;
		progressLabel.setText(action.getProgressText());
		pauseButton.setEnabled(action.isPausable());
		cancelButton.setEnabled(action.isCancelable());
	}

	/**
	 * Возвращает ссылку на <b>DBAction</b>, для которого панель показывает
	 * прогресс выполнения.
	 * 
	 * @return ссылка на <b>DBAction</b>, для которого показывается прогресс
	 *         выполнения.
	 */
	public DBAction getAction() {
		return action;
	}
}
