package al.catalog.ui.progress;

import javax.swing.JOptionPane;

import al.catalog.model.DBAction;
import al.catalog.model.IDBActionAdapter;
import al.catalog.ui.CatalogFrame;

public class ProgressActionListener extends IDBActionAdapter {
    
    private CatalogFrame mainFrame;    
    private ProgressPanelManager progressManager;
    private Thread showDialogThread;
    
    public ProgressActionListener(CatalogFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.progressManager = new ProgressPanelManager(mainFrame);
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
