package disks.catalog.ui.dialog.move;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.edit.MoveAction;
import disks.catalog.ui.resource.ResourceManager;
import disks.catalog.ui.tree.CustomTreeCellRenderer;


/**
 * Класс MoveDialog представляет собой диалог перемещения узла.
 * 
 * @author Alexander Levin
 */
public class MoveDialog extends JDialog {
	
	private static final String TITLE = "moveDialog.title";
	private static final String CANCEL = "moveDialog.cancel";
	
	private static final Dimension SIZE = new Dimension(300, 300);
	
	private static final int HGAP = 5;
	private static final int VGAP = 5;
	
	private JFrame owner;
	private DBTreeModel dbModel;
	private List<ITreeNode> treeNodes;
	private ActionManager actionManager;
	
	public MoveDialog(JFrame owner, DBTreeModel dbModel, ActionManager actionManager) {
		super(owner, ResourceManager.getString(TITLE), true);		
		this.owner = owner;
		this.dbModel = dbModel;
		this.treeNodes = dbModel.getActiveNodes();
		this.actionManager = actionManager;
		createContentPane();		
		setPreferredSize(SIZE);
		setPosition();
		pack();
	}
	
	private void createContentPane() {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setBorder(BorderFactory.createEmptyBorder(VGAP, HGAP, VGAP, HGAP));
		
		MoveTreeModel treeModel = new MoveTreeModel(dbModel);
		JTree tree = new JTree(treeModel);
		TreeCellRenderer renderer = new CustomTreeCellRenderer();
		tree.setRootVisible(false);
		tree.setCellRenderer(renderer);
		tree.setBorder(BorderFactory.createEmptyBorder(VGAP, HGAP, VGAP, HGAP));
		
		ActiveTreeNodeListener activeListener = new ActiveTreeNodeListener(dbModel, tree);
		tree.addTreeSelectionListener(activeListener);
		
		JScrollPane scrollPane = new JScrollPane(tree);
		
		JPanel treePane = new JPanel();
		treePane.setLayout(new BorderLayout());
		treePane.setBorder(BorderFactory.createEmptyBorder(VGAP, HGAP, 0, HGAP));		
		treePane.add(scrollPane, BorderLayout.CENTER);
		
		contentPane.add(treePane, BorderLayout.CENTER);
		contentPane.setPreferredSize(SIZE);
		
		JPanel buttonsPane = new JPanel();
		buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.LINE_AXIS));
		buttonsPane.setBorder(BorderFactory.createEmptyBorder(VGAP, HGAP, VGAP, HGAP));
		
		buttonsPane.add(Box.createHorizontalGlue());
		JButton buttonOk = new JButton();
		MoveAction moveAction = (MoveAction) actionManager.getAction(MoveAction.ACTION_NAME);
		moveAction.setModel(dbModel);
		moveAction.setOwner(this);
		moveAction.setTreeNodes(treeNodes);
		buttonOk.setAction(moveAction);
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
