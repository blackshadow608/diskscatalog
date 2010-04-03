package disks.catalog.ui.dialog.attach;

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
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import disks.catalog.model.tree.DBTreeModel;
import disks.catalog.model.tree.types.ITreeNode;
import disks.catalog.model.tree.types.file.FolderNode;
import disks.catalog.model.tree.types.images.ImageNode;
import disks.catalog.ui.action.ActionManager;
import disks.catalog.ui.action.edit.image.RunAttachImageAction;
import disks.catalog.ui.resource.ResourceManager;


/**
 * Диалог, который открывается при привязке образа к конкретной директории или диску. Здесь имеется JTree
 * для выбора директории или диска из файловой системы.
 * 
 * @author Alexander Levin
 */
public class AttachDialog extends JDialog {
	
	private static final String TITLE = "attachDialog.title";
	private static final String CANCEL = "attachDialog.cancel";
	
	private static final Dimension SIZE = new Dimension(300, 300);
	
	private static final int HGAP = 5;
	private static final int VGAP = 5;
	
	private JFrame owner;
	private DBTreeModel dbModel;	
	private ActionManager actionManager;
	
	private JTree tree;
	
	public AttachDialog(JFrame owner, DBTreeModel dbModel, ActionManager actionManager) {
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
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setBorder(BorderFactory.createEmptyBorder(VGAP, HGAP, VGAP, HGAP));
		
		FileSystemTreeModel treeModel = new FileSystemTreeModel();
		FileSystemCellRenderer renderer = new FileSystemCellRenderer();		
		tree = new JTree(treeModel);
		tree.setCellRenderer(renderer);
		tree.setRootVisible(true);		
		tree.setBorder(BorderFactory.createEmptyBorder(VGAP, HGAP, VGAP, HGAP));		
		
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
		JButton buttonOk = new JButton("Attach");
		final RunAttachImageAction attachAction = (RunAttachImageAction) actionManager.getAction(RunAttachImageAction.ACTION_NAME);
		attachAction.setOwner(this);
		
		buttonOk.setAction(attachAction);
		buttonsPane.add(buttonOk);
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				TreePath path = e.getPath();
				if (path != null) {
					List<ITreeNode> activeNodes = dbModel.getActiveNodes();
					if (activeNodes != null && activeNodes.size() == 1) {
						ITreeNode attachedNode = activeNodes.get(0);
						if (attachedNode instanceof ImageNode) {
							ImageNode image = (ImageNode) attachedNode; 
							FolderNode folderNode = (FolderNode) path.getLastPathComponent();					
							attachAction.setImage(image);
							attachAction.setFolder(folderNode);					
							attachAction.setEnabled(true);							
						}						
					}					
				} else {
					attachAction.setEnabled(false);					
				}				
			}
		});
		
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
	
	public void setVisible(boolean visibility) {
		super.setVisible(visibility);
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
