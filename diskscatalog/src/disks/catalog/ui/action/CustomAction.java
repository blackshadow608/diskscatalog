package disks.catalog.ui.action;

import javax.swing.AbstractAction;

public abstract class CustomAction extends AbstractAction {
	
	protected ActionManager aManager;
	
	public CustomAction(String text) {
		super(text);		
	}
		
	public CustomAction(ActionManager actionManager, String text) {
		super(text);
		this.aManager = actionManager;				
	}
}
