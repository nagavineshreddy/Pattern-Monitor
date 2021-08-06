package model;

import javax.swing.JButton;
import javax.swing.JComponent;

/**
 *
 * Extends the class Symbol and draws ( symbol.
 */
public class OpenParenthesisSymbol extends Symbol {

	private static int noOfInputs = 0;
	private static int noOfOutputs = 1;

	public OpenParenthesisSymbol(JComponent panel, int x, int y, JButton symbol) {
		super("(", panel, x, y, symbol, "(", noOfInputs, noOfOutputs);

	}
}
