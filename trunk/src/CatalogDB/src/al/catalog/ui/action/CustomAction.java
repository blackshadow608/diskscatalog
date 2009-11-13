package al.catalog.ui.action;

import javax.swing.AbstractAction;

public abstract class CustomAction extends AbstractAction {
	
	protected ActionManager actionManager;
	
	public CustomAction(String text) {
		super(text);		
	}
		
	public CustomAction(ActionManager actionManager, String text) {
		super(text);
		this.actionManager = actionManager;				
	}
}
