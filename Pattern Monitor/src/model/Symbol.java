package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

import controller.DragAndDrop;
import controller.DrawConnection;
import controller.SymbolMouseListener;

/**
 *
 * Draws the symbol for all the child symbol classes extending
 *               this class.
 */
public class Symbol extends JButton {

	private int compWidth = 180;
	private int compHeight = 80;
	private int x;
	private int y;
	private boolean link;
	private String type;
	public int inputCount;
	public int outputCount;
	public int maxInput;
	public int maxOutput;

	protected String userInput;

	public Symbol(String text, JComponent panel, int x, int y, JButton symbol, String type,
			int noOfInputs, int noOfOutputs) {
		super(text);

		inputCount = 0;
		outputCount = 0;
		maxInput = noOfInputs;
		maxOutput = noOfOutputs;

		this.link = false;
		this.type = type;

		this.x = x;
		this.y = y;

		this.setLayout(null);
		this.setPreferredSize(new Dimension(compWidth, compHeight));
		this.setBackground(null);
		this.setMinimumSize(new Dimension(compWidth, compHeight));
		this.setBounds(x, y, compWidth, compHeight);
		this.setTransferHandler(new TransferHandler(text));

		String panelName = panel.getName();

		if (panelName == "Center Panel") {

			new SymbolMouseListener(panel, this);
			this.setOpaque(false);
			this.setBorder(BorderFactory.createLineBorder(Color.black));
		} else if (panelName == "Input Panel") {
			new DragAndDrop(this);
		}
		new DrawConnection(this);
		panel.add(this);
		setContentAreaFilled(false);
	}

	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
			g.setColor(Color.gray);
		} else {
			g.setColor(getBackground());
		}
		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

		super.paintComponent(g);
	}

	protected void paintBorder(Graphics g) {
		g.setColor(Color.darkGray);
		g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
	}

	/**
	 * Sets the line as drawn.
	 */
	public void setLine(boolean link) {
		this.link = link;
	}


	/**
	 * Checks whether the line is drawn or not.
	 */
	public boolean isLinkDrawn() {
		return link;
	}

	public String getType() {
		return type;
	}

	public String getUserInput() {
		return userInput;
	}

	public void setUserInput(String userInput) {
		this.userInput = userInput;
	}

}
