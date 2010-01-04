package al.catalog.ui.view.table;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

public class TablePanel extends JScrollPane {
	
	public TablePanel(CustomTable table) {
		super(table);
		setBorder(BorderFactory.createEmptyBorder());		
	}

}
