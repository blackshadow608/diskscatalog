package al.catalog.test;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Test {
	
	public static void main(String[] args) {		
		System.out.println("UIDefaults");
		
		JFrame frame = new JFrame("Icons");
		frame.setPreferredSize(new Dimension(500, 500));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, 1));
		
		List icons = new ArrayList();
		
		icons.add("FileView.floppyDriveIcon");
		icons.add("Tree.openIcon");
		icons.add("FileView.computerIcon");
		icons.add("Menu.checkIcon");
		icons.add("FileChooser.upFolderIcon");		
		icons.add("RadioButtonMenuItem.arrowIcon");
		icons.add("OptionPane.errorIcon");		
		icons.add("CheckBoxMenuItem.arrowIcon");
		icons.add("Slider.verticalThumbIcon");
		icons.add("MenuItem.arrowIcon");		
		icons.add("FileView.fileIcon");
		icons.add("Tree.leafIcon");
		icons.add("Tree.collapsedIcon");
		icons.add("FileView.hardDriveIcon");
		icons.add("OptionPane.informationIcon");
		icons.add("MenuItem.checkIcon");
		icons.add("FileChooser.detailsViewIcon");
		icons.add("OptionPane.warningIcon");
		icons.add("OptionPane.questionIcon");
		icons.add("FileChooser.newFolderIcon");
		icons.add("InternalFrame.icon");
		icons.add("FileChooser.listViewIcon");
		icons.add("FileChooser.homeFolderIcon");
		icons.add("Slider.horizontalThumbIcon");
		icons.add("Tree.expandedIcon");
		icons.add("FileView.directoryIcon");
		icons.add("Tree.closedIcon");
		
		System.out.println("--------------Icons-----------------");
		
		for (Object icon : icons) {
			JLabel label = new JLabel(UIManager.getIcon(icon));
			panel.add(label);
			System.out.println(icon);									
		}
		
		Map defaults = UIManager.getLookAndFeelDefaults();
		
		System.out.println("--------------UIDefaults-------------");
		
		for (Object key : defaults.keySet()) {
			System.out.println(key);			
		}
		
		frame.getContentPane().add(panel);
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
