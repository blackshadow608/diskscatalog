package al.catalog.ui.progress;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import al.catalog.model.DBAction;
import al.catalog.model.IDBActionListener;
import al.catalog.ui.CatalogFrame;
import al.catalog.ui.dialog.ProgressDialog;

public class ProgressActionListener implements IDBActionListener {
    
    private JFrame owner;
    private ProgressDialog progressDialog;
    private ProgressPanelManager progressManager;
    private volatile boolean actionAlreadyExecuted = false;
    private Thread showDialogThread;
    
    public ProgressActionListener(JFrame owner) {
        this.owner = owner;
        this.progressManager = new ProgressPanelManager((CatalogFrame)owner);
    }
    
    public void afterActionExecuting(DBAction dbAction) {       
        actionAlreadyExecuted = true;
        if (progressDialog != null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressManager.hideProgressBar();
            //progressDialog.hideDialog();            
        }
    }
    
    public void beforeActionExecuting(final DBAction action) {
        actionAlreadyExecuted = false;
        progressDialog = new ProgressDialog(owner, action);
        final String progressText = action.getProgressText();
        showDialogThread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!actionAlreadyExecuted) {
                	progressManager.showProgressBar(action);
                	
                    //progressDialog.setLabel(progressText);
                    //progressDialog.showDialog();
                }
            }
        };
        showDialogThread.start();
    }
    
    public void actionThrowException(DBAction dbAction, Exception exception) {
        JOptionPane.showMessageDialog(owner, exception.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
    }
    
    public void redoBecameDisabled() {
        
    }
    
    public void redoBecameEnabled() {
        
    }
    
    public void someActionWasAdded() {
        
    }
    
    public void undoBecameDisabled() {
        
    }
    
    public void undoBecameEnabled() {
        
    }
    
    public void actionWasAborted(DBAction dbAction) {
        
    }
}
