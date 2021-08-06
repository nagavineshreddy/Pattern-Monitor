package model;

import javax.swing.JButton;
import javax.swing.JComponent;

/**
 *
 * Extends the class Symbol and draws ) symbol.
 */
public class CloseParenthesisSymbol extends Symbol {

	private static int noOfInputs = 1;
	private static int noOfOutputs = 0;

	public CloseParenthesisSymbol(JComponent panel, int x, int y, JButton symbol) {
		super(")", panel, x, y, symbol, ")", noOfInputs, noOfOutputs);

	}

}
