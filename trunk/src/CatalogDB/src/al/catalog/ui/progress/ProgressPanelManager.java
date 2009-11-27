package al.catalog.ui.progress;

import al.catalog.model.DBAction;
import al.catalog.ui.CatalogFrame;

public class ProgressPanelManager {
	
	private CatalogFrame owner;
	private ProgressPanel progressPanel;
	
	public ProgressPanelManager(CatalogFrame owner) {
		this.owner = owner;
	}
	
	public void showProgressBar(DBAction action) {
		if(progressPanel == null) {
			progressPanel = new ProgressPanel(this);
		}
		progressPanel.setAction(action);
		owner.showProgressBar(progressPanel);
	}
	
	public void hideProgressBar() {		
		owner.hideProgressBar(progressPanel);		
	}

}
