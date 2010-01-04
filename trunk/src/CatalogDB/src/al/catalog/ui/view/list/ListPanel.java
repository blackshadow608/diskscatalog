package al.catalog.ui.view.list;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

public class ListPanel extends JScrollPane {
	
	public ListPanel(CustomList list) {
		super(list);
		
		setBorder(BorderFactory.createEmptyBorder());
		getVerticalScrollBar().setUnitIncrement(10);
		getHorizontalScrollBar().setUnitIncrement(10);
	}

}
