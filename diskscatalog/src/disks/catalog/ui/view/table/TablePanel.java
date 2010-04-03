package disks.catalog.ui.view.table;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

public class TablePanel extends JScrollPane {
	
	public TablePanel(CatalogTable table) {
		super(table);
		setBorder(BorderFactory.createEmptyBorder());		
	}

}
