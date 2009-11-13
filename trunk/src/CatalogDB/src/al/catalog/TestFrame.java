package al.catalog;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestFrame extends JFrame {

	public TestFrame() {
		super("Test frame");
		createGUI();
	}

	public void createGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JButton northButton = new JButton("PAGE_START (NORTH)");
		panel.add(northButton, BorderLayout.PAGE_START);
		
		JButton westButton = new JButton("LINE_START (WEST)");
		panel.add(westButton, BorderLayout.LINE_START);
		
		JButton eastButton = new JButton("LINE_END (EAST)");
		panel.add(eastButton, BorderLayout.LINE_END);
		
		JButton centerButton = new JButton("CENTER");
		panel.add(centerButton, BorderLayout.CENTER);
		
		JButton southButton = new JButton("PAGE_END (SOUTH)");
		panel.add(southButton, BorderLayout.PAGE_END);

		getContentPane().add(panel);

		setPreferredSize(new Dimension(550, 300));
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