package model;

import javax.swing.JButton;
import javax.swing.JComponent;

/**
 *
 * Extends the class Symbol and draws '-|' symbol.
 */
public class NInOneOut extends Symbol {

	private static int noOfInputs = 99999;
	private static int noOfOutputs = 1;

	public NInOneOut(JComponent panel, int x, int y, JButton symbol) {
		super("|-", panel, x, y, symbol,  "|-", noOfInputs, noOfOutputs);

	}

}
