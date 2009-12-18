import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TestFrame extends JFrame {

	public static String TEST_TEXT = "Test text";

	private JLabel label;

	private JCheckBox italicBox = new JCheckBox("Italic");
	private JCheckBox boldBox = new JCheckBox("Bold");
	private JCheckBox underlineBox = new JCheckBox("Underline");

	public TestFrame() {
		super("Test frame");
		createGUI();
	}

	public void createGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		label = new JLabel(getText());
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("Calibri", Font.PLAIN, 30));
		panel.add(label, BorderLayout.CENTER);

		JPanel boxesPanel = new JPanel();

		boxesPanel.add(italicBox);

		italicBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateText();
			}
		});

		boxesPanel.add(boldBox);

		boldBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateText();
			}
		});

		boxesPanel.add(underlineBox);

		underlineBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateText();
			}
		});

		panel.add(boxesPanel, BorderLayout.SOUTH);
		getContentPane().add(panel);
		setPreferredSize(new Dimension(250, 200));
	}

	public void updateText() {
		label.setText(getText());
	}

	private String getText() {
		String text = checkItalic(TEST_TEXT);
		text = checkBold(text);
		text = checkUnderline(text);
		return "<html>" + text + "</html>";
	}

	private String checkItalic(String string) {
		if (italicBox.isSelected()) {
			return "<i>" + string + "</i>";
		}
		return string;
	}

	private String checkBold(String string) {
		if (boldBox.isSelected()) {
			return "<b>" + string + "</b>";
		}
		return string;
	}

	private String checkUnderline(String string) {
		if (underlineBox.isSelected()) {
			return "<u>" + string + "</u>";
		}
		return string;
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				TestFrame frame = new TestFrame();
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}