package disks.catalog.ui.dialog;

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

import disks.catalog.model.DBAction;
import disks.catalog.ui.progress.ProgressPanelManager;
import disks.catalog.ui.resource.ResourceManager;


/**
 * Диалог, который открывается, если выполнение какого-либо действия занимает
 * продолжительное время. Здесь имеется кнопка "Cancel" для прерывания выпонения
 * действия. Чтобы показать диалог, необходимо создать экземпляр с помощью new,
 * а затем выполнить showDialog() вместо setVisible(). Для того, чтобы скрыть
 * диалог, надо использовать hideDialog(). Это сделано для корректной обработки
 * нажатия стандартной кнопки закрытия окна - при нажатии происходит прерывание
 * выполнения запущенного действия.
 * 
 * @author Alexander Levin
 */
public class ProgressDialog extends JDialog implements WindowListener {
    
    private static final Dimension SIZE = new Dimension(350, 120);
    private static final int HGAP = 5;
    private static final int VGAP = 5;
    
    private static final String CANCEL = "progressDialog.cancel";
    private static final String TITLE = "progressDialog.title";
    
    private JLabel label;
    private DBAction action;
    private ProgressPanelManager progressManager;
    
    private JPanel contentPane;
    
    private boolean wasAborted = false;
    private boolean inBackground = false;
    
    public ProgressDialog(JFrame owner, DBAction dbAction, ProgressPanelManager progressManager) {
        super(owner, ResourceManager.getString(TITLE), true);
        this.action = dbAction;
        this.progressManager = progressManager;
        setPreferredSize(SIZE);
        createContent();        
        pack();        
        setLocationRelativeTo(null);
        addWindowListener(this);        
    }
    
    public void createContent() {
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(VGAP, HGAP, VGAP, HGAP));
        
        label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        contentPane.add(label, BorderLayout.NORTH);
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);        
        
        JPanel progressPane = new JPanel();
        progressPane.setLayout(new BorderLayout());
        progressPane.setBorder(BorderFactory.createEmptyBorder(VGAP, HGAP, VGAP, HGAP));
        progressPane.add(progressBar, BorderLayout.CENTER);
        
        contentPane.add(progressPane, BorderLayout.CENTER);
        contentPane.setPreferredSize(SIZE);
        
        JPanel buttonsPane = new JPanel();
        buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.LINE_AXIS));
        buttonsPane.setBorder(BorderFactory.createEmptyBorder(VGAP, HGAP, VGAP, HGAP));
        
        buttonsPane.add(Box.createHorizontalGlue());
        
        String holdBack = "Свернуть";
        JButton holdBackBtn = new JButton(holdBack);
        holdBackBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	holdBack();
                progressManager.showProgressBar(action);                
            }
        });
        buttonsPane.add(holdBackBtn);
        
        buttonsPane.add(Box.createHorizontalStrut(HGAP));
        
        String pause = "Пауза";
        JButton pauseButton = new JButton(pause);
        pauseButton.setEnabled(false);
        buttonsPane.add(pauseButton);
        
        buttonsPane.add(Box.createHorizontalStrut(HGAP));
        
        String cancel = ResourceManager.getString(CANCEL);
        JButton buttonCancel = new JButton(cancel);
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	hideDialog();
                action.abort();                
            }
        });
        buttonsPane.add(buttonCancel);
        
        if(!action.isCancelable()) {
        	buttonCancel.setEnabled(false);
        }
        
        contentPane.add(buttonsPane, BorderLayout.SOUTH);
        
        getContentPane().add(contentPane);
    }
    
    /**
	 * Показывает диалог. Необходимо использовать этот метод вместо вызова
	 * setVisible(true).
	 */
    public void showDialog() {
    	inBackground = false;
        setVisible(true);        
    }
    
    /**
	 * Скрывает диалог. Необходимо использовать этот метод вместо вызова
	 * setVisible(false).
	 */
    public void hideDialog() {
        wasAborted = true;
        setVisible(false);
        dispose();
    }
    
    public void holdBack() {
    	inBackground = true;
    	setVisible(false);
    }
    
    public void windowClosed(WindowEvent e) {
        if (!wasAborted && !inBackground) {
            action.abort();
            wasAborted = true;
        }
    }
    
    public void windowClosing(WindowEvent e) {
    	if (!wasAborted && !inBackground) {
            action.abort();
            wasAborted = true;
        }
    }
    
    /**
     * Устанавливает текст, который будет показываться.
     * 
     * @param label - прогресс текст.
     */
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
