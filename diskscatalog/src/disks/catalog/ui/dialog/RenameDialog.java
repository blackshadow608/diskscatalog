package disks.catalog.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.edit.RenameAction;
import disks.catalog.ui.resource.ResourceManager;


/**
 * Класс RenameDialog представляет собой диалог для переименования узла.
 * 
 * @author Alexander Levin
 */
public class RenameDialog extends JDialog {
	
	private static final String TITLE = "renameDialog.title";
	private static final String RENAME = "renameDialog.rename";
	private static final String CANCEL = "renameDialog.cancel";
	private static final String NEW_NAME_LABEL = "renameDialog.newNameLabel";
	
	private static final Dimension SIZE = new Dimension(310, 130);
	
	private static final int HGAP = 5;
	private static final int VGAP = 5;
	
	private JFrame owner;
	private DBTreeModel dbModel;
	private ActionManager actionManager;
	
	public RenameDialog(JFrame owner, DBTreeModel dbModel, ActionManager actionManager) {
		super(owner, ResourceManager.getString(TITLE), true);		
		this.owner = owner;
		this.dbModel = dbModel;
		this.actionManager = actionManager;
		createContentPane();		
		setPreferredSize(SIZE);
		setPosition();
		pack();
	}
	
	private void createContentPane() {
		final RenameAction renameAction = (RenameAction) actionManager.getAction(RenameAction.ACTION_NAME);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setBorder(BorderFactory.createEmptyBorder(4 * VGAP, HGAP, VGAP, HGAP));
		
		JPanel editPane = new JPanel();
		editPane.setLayout(new FlowLayout());
		
		String newNameLabel = ResourceManager.getString(NEW_NAME_LABEL);
		JLabel label = new JLabel(newNameLabel);
		editPane.add(label);
		
		final JTextField textField = new JTextField();
		textField.setColumns(18);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				renameAction.setName(textField.getText());
				renameAction.actionPerformed(e);
				setVisible(false);												
			}
		});
		editPane.add(textField);
				
		contentPane.add(editPane, BorderLayout.CENTER);
		contentPane.setPreferredSize(SIZE);
		
		JPanel buttonsPane = new JPanel();
		buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.LINE_AXIS));
		buttonsPane.setBorder(BorderFactory.createEmptyBorder(VGAP, HGAP, VGAP, HGAP));
		
		buttonsPane.add(Box.createHorizontalGlue());
		String rename = ResourceManager.getString(RENAME);
		JButton buttonOk = new JButton(rename);		
		
		buttonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				renameAction.setName(textField.getText());
				renameAction.actionPerformed(e);
				setVisible(false);
			}
		});
		
		buttonsPane.add(buttonOk);		
		buttonsPane.add(Box.createRigidArea(new Dimension(HGAP, HGAP)));
		
		String cancel = ResourceManager.getString(CANCEL);
		JButton buttonCancel = new JButton(cancel);
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);								
			}
		});
		buttonsPane.add(buttonCancel);
		
		contentPane.add(buttonsPane, BorderLayout.SOUTH);		
		getContentPane().add(contentPane);
		
		ITreeNode activeNode = dbModel.getActiveNodes().get(0);
		if (activeNode != null) {
			textField.setText(activeNode.getName());
			textField.selectAll();
		}
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
