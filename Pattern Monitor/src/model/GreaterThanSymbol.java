package model;

import javax.swing.JButton;
import javax.swing.JComponent;

/**
 *
 * Extends the class Symbol and draws > symbol.
 */
public class GreaterThanSymbol extends Symbol {

	private static int noOfInputs = 2;
	private static int noOfOutputs = 1;

	public GreaterThanSymbol(JComponent panel, int x, int y, JButton symbol) {
		super(">", panel, x, y, symbol, ">", noOfInputs, noOfOutputs);
	}

}
