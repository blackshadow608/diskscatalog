package disks.catalog.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.types.AbstractTreeNode;
import disks.catalog.model.tree.types.images.ImageNode;
import disks.catalog.model.tree.types.images.ImageType;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.resource.ResourceManager;


/**
 * 
 * Диалог свойств узла. Диалог PropertiesDialog предназначен для отображения
 * каких-либо свойств узла. Список свойств для каждого типа узла свой, но есть
 * одинаковые свойства. Например, у каждого узла есть имя, независимо от его типа.
 * При отображении диалога проверяется тип узла, свойства которого мы хотим показать,
 * и в соответствии с этим типом отображаются те или иные свойства. 
 * 
 * @author Alexander Levin
 */
public class PropertiesDialog extends JDialog {
	
	private static final String TITLE = "propertiesDialog.title";
	private static final String OK = "propertiesDialog.ok";
	private static final String CANCEL = "propertiesDialog.cancel";
	private static final String GENERAL = "propertiesDialog.general";
	private static final String ADVANCED = "propertiesDialog.advanced";
	
	private static final String NAME = "propertiesDialog.name";
	private static final String TYPE = "propertiesDialog.type";
	private static final String COMMENTS = "propertiesDialog.comments";
	
	private static final int LABEL_WIDTH = 100;
	private static final int VALUE_WIDTH = 220;
	
	private static final int HGAP = 5;
	private static final int VGAP = 5;
	private static final int BORDER = 5;
	
	private JFrame owner;
	
	private JTextField nameField;
	
	private JTextArea commentsArea;
	
	private JComboBox typeCombo;
	
	private AbstractTreeNode treeNode;
	
	private DBTreeModel dbModel;
	
	public PropertiesDialog(JFrame owner, DBTreeModel dbModel, ActionManager actionManager) {
		super(owner, ResourceManager.getString(TITLE), true);		
		this.owner = owner;
		this.dbModel = dbModel;
		
		if(dbModel.getActiveNodes() != null && dbModel.getActiveNodes().size() == 1) {
			this.treeNode = (AbstractTreeNode) dbModel.getActiveNodes().get(0);			
		} else if(dbModel.getOpenedNode() != null) {
			this.treeNode = (AbstractTreeNode) dbModel.getOpenedNode();									
		}
		
		createContentPane();		
		setResizable(false);
		setPosition();		
		pack();
	}
	
	private void createContentPane() {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setBorder(BorderFactory.createEmptyBorder(VGAP, HGAP, VGAP, HGAP));
		
		UIManager.put("TabbedPane.selected", contentPane.getBackground());
		UIManager.put("TabbedPane.contentAreaColor", contentPane.getBackground());		
				
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(BorderFactory.createEmptyBorder(VGAP, HGAP, VGAP, HGAP));
		tabbedPane.setFocusable(false);
				
		JPanel generalPane = new JPanel();
		generalPane.setLayout(new BoxLayout(generalPane, BoxLayout.Y_AXIS));
		generalPane.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, 0, BORDER));		
		
		JPanel topPane = new JPanel();		
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.X_AXIS));
		topPane.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));		
		
		JLabel nameLabel = new JLabel(ResourceManager.getString(NAME));
		Dimension preffSize = nameLabel.getPreferredSize();
		nameLabel.setPreferredSize(new Dimension(LABEL_WIDTH, preffSize.height));
		topPane.add(nameLabel);
		
		nameField = new JTextField();
		preffSize = nameField.getPreferredSize();
		nameField.setPreferredSize(new Dimension(VALUE_WIDTH, preffSize.height));
		nameField.setText(treeNode.getName());
		topPane.add(nameField);
		
		generalPane.add(topPane);
		
		JPanel typePane = new JPanel();		
		typePane.setLayout(new BoxLayout(typePane, BoxLayout.X_AXIS));
		typePane.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));		
		
		if(treeNode instanceof ImageNode) {
			ImageNode image = (ImageNode) treeNode;
			JLabel typeLabel = new JLabel(ResourceManager.getString(TYPE));
			preffSize = typeLabel.getPreferredSize();
			typeLabel.setPreferredSize(new Dimension(LABEL_WIDTH, preffSize.height));
			typePane.add(typeLabel);
			
			Object selectedValue = null;
			ImageType[] types = ImageNode.getTypes();
			for(ImageType type : types) {
				if(type.getKey().intValue() == image.getType()) {
					selectedValue = type;
				}
				type.setText(ResourceManager.getString(type.getValue()));				
			}
			
			typeCombo = new JComboBox(types);
			typeCombo.setSelectedItem(selectedValue);
			preffSize = typeCombo.getPreferredSize();
			typeCombo.setPreferredSize(new Dimension(VALUE_WIDTH, preffSize.height));
			typePane.add(typeCombo);
			
			generalPane.add(typePane);			
		}
		
		JPanel commentsPane = new JPanel();
		commentsPane.setLayout(new BoxLayout(commentsPane, BoxLayout.X_AXIS));
		commentsPane.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));				
		
		JLabel commentsLabel = new JLabel(ResourceManager.getString(COMMENTS));		
		preffSize = commentsLabel.getPreferredSize();
		commentsLabel.setPreferredSize(new Dimension(LABEL_WIDTH, preffSize.height));
		commentsLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		commentsPane.add(commentsLabel);
		
		commentsArea = new JTextArea();		
		commentsArea.setWrapStyleWord(true);
		commentsArea.setLineWrap(true);
		commentsArea.setText(treeNode.getComments());
		JScrollPane commentsScroll = new JScrollPane(commentsArea);
		preffSize = commentsArea.getPreferredSize();
		commentsScroll.setPreferredSize(new Dimension(VALUE_WIDTH, 150));
		commentsScroll.setAlignmentY(Component.TOP_ALIGNMENT);
		commentsPane.add(commentsScroll);
		
		
		generalPane.add(commentsPane);
		
		JPanel advancedPanel = new JPanel();
		advancedPanel.setOpaque(false);
				
		tabbedPane.addTab(ResourceManager.getString(GENERAL), generalPane);
		tabbedPane.addTab(ResourceManager.getString(ADVANCED), advancedPanel);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel buttonsPane = new JPanel();
		buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.LINE_AXIS));
		buttonsPane.setBorder(BorderFactory.createEmptyBorder(VGAP, HGAP, VGAP, HGAP));
		
		buttonsPane.add(Box.createHorizontalGlue());
		JButton buttonOk = new JButton(ResourceManager.getString(OK));
		buttonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				treeNode.setName(nameField.getText());
				treeNode.setComments(commentsArea.getText());
				if(treeNode instanceof ImageNode) {
					ImageType type = (ImageType) typeCombo.getSelectedItem();
					ImageNode imageNode = (ImageNode) treeNode;
					imageNode.setType(type.getKey());
				}
				dbModel.updateNode(treeNode);				
				setVisible(false);
			}
		});
		
		buttonsPane.add(buttonOk);
		
		buttonsPane.add(Box.createRigidArea(new Dimension(HGAP, HGAP)));
		
		JButton buttonCancel = new JButton(ResourceManager.getString(CANCEL));
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);								
			}
		});
		buttonsPane.add(buttonCancel);
		
		contentPane.add(buttonsPane, BorderLayout.SOUTH);
		
		getContentPane().add(contentPane);
	}
	
	private void setPosition() {
		int ownerX = owner.getX();
		int ownerY = owner.getY();
		
		Dimension ownerSize = owner.getSize();
        Dimension frameSize = getPreferredSize();

        if (frameSize.height > ownerSize.height) {
            frameSize.height = ownerSize.height;
        }

        if (frameSize.width > ownerSize.width) {
            frameSize.width = ownerSize.width;
        }

        setLocation(ownerX + (ownerSize.width - frameSize.width) / 2,
        		ownerY + (ownerSize.height - frameSize.height) / 2);
	}
}
