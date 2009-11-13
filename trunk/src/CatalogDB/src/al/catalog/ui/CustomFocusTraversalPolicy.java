package al.catalog.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;

public class CustomFocusTraversalPolicy extends FocusTraversalPolicy {
	
	private CatalogFrame frame;
	
	public CustomFocusTraversalPolicy(CatalogFrame frame) {
		this.frame = frame;		
	}

	public Component getComponentAfter(Container focusCycleRoot, Component component) {
		if(component == frame.getTree()) {
			return frame.getViewPanel().getActive();			
		}
		return frame.getTree();
	}

	public Component getComponentBefore(Container focusCycleRoot, Component component) {
		if(component == frame.getTree()) {
			return frame.getViewPanel().getActive();			
		}
		return frame.getTree();
	}

	public Component getDefaultComponent(Container focusCycleRoot) {
		return frame.getTree();
	}

	public Component getFirstComponent(Container focusCycleRoot) {
		return frame.getTree();
	}

	public Component getLastComponent(Container focusCycleRoot) {
		return frame.getViewPanel().getActive();
	}
}
