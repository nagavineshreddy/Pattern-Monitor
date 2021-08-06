package model;

import javax.swing.JButton;
import javax.swing.JComponent;

/**
 * Extends the class Symbol and draws - symbol.
 */
public class MinusSymbol extends Symbol {

	private static int noOfInputs = 1;
	private static int noOfOutputs = 1;

	public MinusSymbol(JComponent panel, int x, int y, JButton symbol) {
		super("-", panel, x, y, symbol, "-", noOfInputs, noOfOutputs);

	}

}
