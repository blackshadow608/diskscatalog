package disks.catalog.ui.progress;

import javax.swing.JOptionPane;

import disks.catalog.MainEntity;
import disks.catalog.model.DBAction;
import disks.catalog.model.DBManager;
import disks.catalog.model.IDBActionAdapter;
import disks.catalog.ui.CatFrame;


public class ProgressListener extends IDBActionAdapter {
    
    private CatFrame mainFrame;    
    private ProgressPanelManager progressManager;
    private Thread showDialogThread;
    
    public ProgressListener(MainEntity mainEntity, CatFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.progressManager = new ProgressPanelManager(mainFrame);
        
        DBManager dbManager = mainEntity.getDBManager();
        dbManager.addActionListener(this);
    }
    
    public void afterActionExecuting(DBAction dbAction) {
        
        if (progressManager != null) {
        	/*
			 * Чтобы диалог прогресса выполнения и прогресс панель не мелькали,
			 * а показывались хотя бы секунду.
			 */
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*
			 * Как только выполнения action'а завершено, нужно скрыть диалог и
			 * прогресс панель.
			 */
            progressManager.hideProgressBar();
        }
    }
    
    public void beforeActionExecuting(final DBAction action) {
        
        showDialogThread = new Thread() {
            public void run() {
            	
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                progressManager.showProgressBar(action);
            }
        };
        
        showDialogThread.start();
    }
    
    public void actionThrowException(DBAction dbAction, Exception exception) {
        JOptionPane.showMessageDialog(mainFrame, exception.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
    }
}
