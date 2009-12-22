import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TestFrame extends JFrame {
	
	public TestFrame() {
		super("Test frame");
		createGUI();
	}

	public void createGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JTextField textField = new JTextField(20);
		panel.add(textField, BorderLayout.NORTH);
		
		textField.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				
			}

			public void keyReleased(KeyEvent e) {
				
			}

			public void keyTyped(KeyEvent e) {
				
			}
			
		});
		
		textField.addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent e) {
				
			}
			
		});
		
		JTextArea textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		JButton clearButton = new JButton("Clear");
		panel.add(clearButton, BorderLayout.SOUTH);
		
		setPreferredSize(new Dimension(300, 300));
		getContentPane().add(panel);
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