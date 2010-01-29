import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestFrame extends JFrame {
	
	public static void createGUI() {
		JFrame frame = new JFrame("Test frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				if(source instanceof JButton) {
					JButton button = (JButton) e.getSource();
					float align = button.getAlignmentX();
					String title = "";
					if(align == JComponent.LEFT_ALIGNMENT) {
						align = JComponent.CENTER_ALIGNMENT;
						title = "CENTER_ALIGNMENT";
					} else if (align == JComponent.CENTER_ALIGNMENT) {
						align = JComponent.RIGHT_ALIGNMENT;
						title = "RIGHT_ALIGNMENT";
					} else {
						align = JComponent.LEFT_ALIGNMENT;
						title = "LEFT_ALIGNMENT";
					}
					button.setAlignmentX(align);
					button.setText(title);
				}												
			}			
		};

		JButton button1 = new JButton("LEFT_ALIGNMENT");
		panel.add(button1);
		button1.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		button1.setMaximumSize(new Dimension(150, 100));
		button1.addActionListener(listener);
		button1.setFocusable(false);

		JButton button2 = new JButton("LEFT_ALIGNMENT");
		panel.add(button2);
		button2.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		button2.setMaximumSize(new Dimension(200, 100));
		button2.addActionListener(listener);
		button2.setFocusable(false);

		JButton button3 = new JButton("LEFT_ALIGNMENT");
		panel.add(button3);
		button3.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		button3.setMaximumSize(new Dimension(250, 100));
		button3.addActionListener(listener);
		button3.setFocusable(false);
		
		frame.getContentPane().add(panel);
		
		frame.setPreferredSize(new Dimension(300, 220));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				createGUI();
			}
		});
	}
}