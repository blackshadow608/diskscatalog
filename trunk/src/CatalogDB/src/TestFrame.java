import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TestFrame extends JFrame {

	public TestFrame() {
		super("Test frame");
		createGUI();
	}

	public void createGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		String[] data = { "Windows XP", "Windows Vista", "Mac OS",
				"Chrome OS", "Ubuntu", "Linux", "Unix", "Solaris",
				"Windows 98", "Windows 95", "Windows 2000",
				"Windows 2003" };

		final JList list = new JList(data);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		panel.add(new JScrollPane(list), BorderLayout.CENTER);

		final JLabel label = new JLabel(" ");

		panel.add(label, BorderLayout.SOUTH);

		list.addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						Object element = list.getSelectedValue();
						label.setText(element.toString());
					}
				});

		getContentPane().add(panel);

		setPreferredSize(new Dimension(250, 200));
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