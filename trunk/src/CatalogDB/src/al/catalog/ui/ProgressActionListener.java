package al.catalog.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import al.catalog.model.DBAction;
import al.catalog.model.IDBActionListener;
import al.catalog.ui.dialog.ProgressDialog;

public class ProgressActionListener implements IDBActionListener {
    
    private JFrame owner;
    private ProgressDialog progressDialog;
    private volatile boolean actionAlreadyExecuted = false;
    private Thread showDialogThread;
    
    public ProgressActionListener(JFrame owner) {
        this.owner = owner;
    }
    
    public void afterActionExecuting(DBAction dbAction) {
        //TODO решить проблему прерывания выполняемого действия
        /*
         * Проблема прерывания выполняемого действия состоит в следующем - при запуске действия
         * открывается диалог, если действие выполняется короткое время, то получается мелькание
         * диалога, для этого 500мс после того, как действие закончилось диалог всё еще остается
         * открытым. А если пользоаптель нажимает кнопку Cancel (прерывание выполнения действия)
         * за эти 500мс, то прерывания нет, так как действие уже выполнено. Возможное решение -
         * это вызов метода unexecute() действия или же каким-то образом блокировка кнопкм Cancel,
         * можно также попробовать сократить 500мс до 200мс или подобным образом. Можно еще
         * реализовать возможность для некоторых действий не показывать кнопочку Cancel :)
         */
        actionAlreadyExecuted = true;
        if (progressDialog != null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }            
            progressDialog.hideDialog();
        }
    }
    
    public void beforeActionExecuting(DBAction dbAction) {
        actionAlreadyExecuted = false;
        progressDialog = new ProgressDialog(owner, dbAction);
        final String progressText = dbAction.getProgressText();
        showDialogThread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!actionAlreadyExecuted) {                    
                    progressDialog.setLabel(progressText);
                    progressDialog.showDialog();                    
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
