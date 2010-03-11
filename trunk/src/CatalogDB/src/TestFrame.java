import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				if(source instanceof JButton) {
					JButton button = (JButton) e.getSource();
					float align = button.getAlignmentY();
					String title = "";
					if(align == JComponent.TOP_ALIGNMENT) {
						align = JComponent.CENTER_ALIGNMENT;
						title = "CENTER_ALIGNMENT";
					} else if (align == JComponent.CENTER_ALIGNMENT) {
						align = JComponent.BOTTOM_ALIGNMENT;
						title = "BOTTOM_ALIGNMENT";
					} else {
						align = JComponent.TOP_ALIGNMENT;
						title = "TOP_ALIGNMENT";
					}
					button.setAlignmentY(align);
					button.setText(title);
				}												
			}			
		};
		
		Font font = new Font("Verdana", Font.PLAIN, 11);

		JButton button1 = new VerticalButton("TOP_ALIGNMENT", false);
		panel.add(button1);
		button1.setAlignmentY(JComponent.TOP_ALIGNMENT);
		button1.setMaximumSize(new Dimension(100, 160));
		button1.addActionListener(listener);
		button1.setFocusable(false);
		button1.setFont(font);

		JButton button2 = new VerticalButton("TOP_ALIGNMENT", false);
		panel.add(button2);
		button2.setAlignmentY(JComponent.TOP_ALIGNMENT);
		button2.setMaximumSize(new Dimension(100, 200));
		button2.addActionListener(listener);
		button2.setFocusable(false);
		button2.setFont(font);

		JButton button3 = new VerticalButton("TOP_ALIGNMENT", false);
		panel.add(button3);
		button3.setAlignmentY(JComponent.TOP_ALIGNMENT);
		button3.setMaximumSize(new Dimension(100, 250));
		button3.addActionListener(listener);
		button3.setFocusable(false);
		button3.setFont(font);
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		frame.setPreferredSize(new Dimension(300, 300));
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