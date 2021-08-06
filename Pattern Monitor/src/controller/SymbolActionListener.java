package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Symbol;

/**
 *
 * This class is used to set the selected symbol from the Input panel.
 */
public class SymbolActionListener {

	public SymbolActionListener(Symbol symbol) {
		
		symbol.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Collector.getInstance().setSymbolSelected(symbol);
			}
		});
	}

}
