package al.catalog.test;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class MainFrame extends JFrame {
	
	public MainFrame() {
		String[] data = {"one", "two", "three"};
		JList list = new JList(data);
		/*tree.addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent e) {
				
			}			
		});*/
		getContentPane().add(new JScrollPane(list));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);		
	}
	
	public static void main(String[] args) {
		new MainFrame();		
	}
}
