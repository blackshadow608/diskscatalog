package al.catalog.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import al.catalog.model.DBAction;
import al.catalog.ui.resource.ResourceManager;

/**
 * Диалог, который открывается, если выполнение какого-либо действия занимает продолжительное время.
 * Здесь имеется кнопка "Cancel" для прерывания выпонения действия. Чтобы показать диалог, необходимо
 * создать экземпляр с помощью new, а затем выполнить showDialog() вместо setVisible(). Для того, чтобы
 * скрыть диалог, надо использовать hideDialog(). Это сделано для корректной обработки нажатия стандартной
 * кнопки закрытия окна - при нажатии происходит прерывание выполнения запущенного действия.
 *
 * @author Alexander Levin
 */
public class ProgressDialog extends JDialog implements WindowListener {
    
    private static final Dimension SIZE = new Dimension(300, 125);
    private static final int HGAP = 5;
    private static final int VGAP = 5;
    
    private static final String CANCEL = "progressDialog.cancel";
    private static final String TITLE = "progressDialog.title";
    
    private JFrame owner;
    private JLabel label;
    private DBAction dbAction;
    
    private boolean wasAborted = false;
    
    public ProgressDialog(JFrame owner, DBAction dbAction) {
        super(owner, ResourceManager.getString(TITLE), true);
        this.owner = owner;
        this.dbAction = dbAction;
        setPreferredSize(SIZE);
        createContent();
        setPosition();
        pack();
        addWindowListener(this);        
    }
    
    public void createContent() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBorder(BorderFactory.createEmptyBorder(VGAP, HGAP, VGAP, HGAP));
        
        label = new JLabel();
        contentPane.add(label, BorderLayout.NORTH);
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);        
        
        JPanel progressPane = new JPanel();
        progressPane.setLayout(new BorderLayout());
        progressPane.setBorder(BorderFactory.createEmptyBorder(VGAP, HGAP, VGAP, HGAP));
        progressPane.add(progressBar, BorderLayout.CENTER);
        
        contentPane.add(progressPane);
        contentPane.setPreferredSize(SIZE);
        
        JPanel buttonsPane = new JPanel();
        buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.LINE_AXIS));
        buttonsPane.setBorder(BorderFactory.createEmptyBorder(VGAP, HGAP, VGAP, HGAP));
        
        buttonsPane.add(Box.createHorizontalGlue());
        
        String cancel = ResourceManager.getString(CANCEL);
        JButton buttonCancel = new JButton(cancel);
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dbAction.abort();
                hideDialog();
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
    
    /**
     * Показывает диалог. Необходимо использовать этот метод вместо вызова setVisible(true).
     */
    public void showDialog() {
        setVisible(true);
    }
    
    /**
     * Скрывает диалог. Необходимо использовать этот метод вместо вызова setVisible(false).
     */
    public void hideDialog() {
        wasAborted = true;
        setVisible(false);
        dispose();
    }
    
    public void windowClosed(WindowEvent e) {
        if (!wasAborted) {
            dbAction.abort();
            wasAborted = true;
        }
    }
    
    public void windowClosing(WindowEvent e) {
        if (!wasAborted) {
            dbAction.abort();
            wasAborted = true;
        }
    }
    
    public void setLabel(String label) {
    	if (this.label != null) {
    		this.label.setText(label);
    	}
    }
    
    public void windowActivated(WindowEvent e) {
        
    }
    
    public void windowDeactivated(WindowEvent e) {
        
    }
    
    public void windowDeiconified(WindowEvent e) {
        
    }
    
    public void windowIconified(WindowEvent e) {
        
    }
    
    public void windowOpened(WindowEvent e) {
        
    }    
}
