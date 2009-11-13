package al.catalog.ui;

import java.awt.FlowLayout;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToolBar;

import al.catalog.ui.action.ActionManager;
import al.catalog.ui.action.file.CloseAction;
import al.catalog.ui.action.file.OpenAction;
import al.catalog.ui.action.file.SaveAction;
import al.catalog.ui.action.view.UpOneLevelAction;
import al.catalog.ui.action.view.history.BackAction;
import al.catalog.ui.action.view.history.ForwardAction;

public class CatalogToolBar extends JToolBar {
	
	private ActionManager actionManager;
	
	public CatalogToolBar(ActionManager actionManager) {
		super();		
		this.actionManager = actionManager;
		
		customizeToolBar();
		
		addNavigationButtons();		
		addSeparator();		
		addOpenButton();
		addCloseButton();		
		addSeparator();		
		addSaveButton();
	}
	
	private void addBackButton() {
		JButton button = new JButton();
		Action action = actionManager.getAction(BackAction.ACTION_NAME);
		button.setAction(action);
		button.setFocusable(false);		
		add(button);		
	}
	
	private void addForwardButton() {
		JButton button = new JButton();
		Action action = actionManager.getAction(ForwardAction.ACTION_NAME);
		button.setAction(action);
		button.setFocusable(false);		
		add(button);
	}
	
	private void addUpButton() {
		JButton button = new JButton();
		Action action = actionManager.getAction(UpOneLevelAction.ACTION_NAME);
		button.setAction(action);
		button.setFocusable(false);		
		add(button);
	}
	
	private void addNavigationButtons() {
		addBackButton();
		addForwardButton();
		addUpButton();		
	}
	
	private void addOpenButton() {
		JButton button = new JButton();
		Action action = actionManager.getAction(OpenAction.ACTION_NAME);
		button.setAction(action);
		button.setFocusable(false);		
		add(button);
	}
	
	private void addCloseButton() {
		JButton button = new JButton();
		Action action = actionManager.getAction(CloseAction.ACTION_NAME);
		button.setAction(action);
		button.setFocusable(false);
		add(button);
	}
	
	private void addSaveButton() {
		JButton button = new JButton();
		Action action = actionManager.getAction(SaveAction.ACTION_NAME);
		button.setAction(action);
		button.setFocusable(false);		
		add(button);
	}
	
	private void customizeToolBar() {
		setFocusable(false);		
		setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));		
	}
}
