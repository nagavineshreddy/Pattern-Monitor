package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import model.Symbol;

/**
 *
 * The DrawConnection class listens and adds the line between the
 * two symbols
 */
public class DrawConnection {

	public DrawConnection(Symbol l2) {

		l2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Collector data = Collector.getInstance();

				Symbol l1 = data.getSymbolSelected();
				if (l1 != null && l1 != l2) {
					data.addNewLinks((WorkPanel) l2.getParent(), l2, l1);
					data.setSymbolSelected(null);
					l2.getParent().getParent().repaint();
					l2.setLine(true);
					l1.setLine(true);
				} 
				else {				
						data.setSymbolSelected(l2);
					}
				}
		});
	}

}
