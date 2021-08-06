package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.CloseParenthesisSymbol;
import model.OpenParenthesisSymbol;
import model.Symbol;

/**
 *
 * WorkPanel is the area where the user works on the symbols
 */
public class WorkPanel extends JPanel {

	private boolean isPanelOpened;
	private boolean isPanelClosed;

	private int panelWidth;
	private int panelHeight;

	private Map<Symbol, ArrayList<Symbol>> Lines;

	private WorkPanel panel;

	double ax1, ay1, ax2, ay2;
	public static final double len = 10;
	public static final double angle = Math.PI / 10;

	public WorkPanel(int width, int height) {

		this.isPanelOpened = false;
		this.isPanelClosed = false;
		this.panelWidth = width;
		this.panelHeight = height - 100;

		this.setName("Center Panel");
		this.setLayout(null);
		this.setPreferredSize(new Dimension(panelWidth, panelHeight));
		this.setBackground(new Color(200, 200, 200));
		this.panel = this;

		if (!OpenFileContents.isOpened) {
			new OpenParenthesisSymbol(this, 0, 0, new JButton("("));
			new CloseParenthesisSymbol(this, 600, 400, new JButton(")"));
		}
		new SetSymbolListener(this);
	}

	public boolean isOpenP() {
		return isPanelOpened;
	}

	public void setOpenP(boolean isOpenP) {
		this.isPanelOpened = isOpenP;
	}

	public boolean isCloseP() {
		return isPanelClosed;
	}

	public void setCloseP(boolean isCloseP) {
		this.isPanelClosed = isCloseP;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;

		Lines = Collector.getInstance().getSpaceLines(this.panel);

		if (Lines != null) {
			for (Symbol c1 : Lines.keySet()) {
				for (Symbol c2 : Lines.get(c1)) {

					int x1 = c1.getX() + c1.getParent().getX() + c1.getWidth() / 2;
					int x2 = c2.getX() + c2.getParent().getX() + c2.getWidth() / 2;

					int y1 = c1.getY() + c1.getParent().getY() + c1.getHeight() / 2;
					int y2 = c2.getY() + c2.getParent().getY() + c2.getHeight() / 2;

					x2 -= 90;
					y2 -= 40;
					arrowHead(x1, y1, x2, y2);
					g2D.drawLine(x2, y2, (int) ax1, (int) ay1);
					g2D.drawLine(x2, y2, (int) ax2, (int) ay2);
					g2D.drawLine(x1, y1, x2, y2);
				}
			}
		}
	}

	private void arrowHead(double x1, double y1, double x2, double y2) {
		double distance, resultDistance, angleOne, angleTwo, resultAngle;
		distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		if (Math.abs(x2 - x1) < 1e-6)
			if (y2 < y1)
				angleTwo = Math.PI / 2;
			else
				angleTwo = -Math.PI / 2;
		else {
			if (x2 > x1)
				angleTwo = Math.atan((y1 - y2) / (x2 - x1));
			else
				angleTwo = Math.atan((y1 - y2) / (x1 - x2));
		}
		resultDistance = Math.sqrt(len * len + distance * distance - 2 * len * distance * Math.cos(angle));
		angleOne = Math.asin(len * Math.sin(angle) / resultDistance);
		resultAngle = angleTwo - angleOne;
		ay1 = y1 - resultDistance * Math.sin(resultAngle);
		if (x2 > x1)
			ax1 = x1 + resultDistance * Math.cos(resultAngle);
		else
			ax1 = x1 - resultDistance * Math.cos(resultAngle);
		resultAngle = angleTwo + angleOne;
		ay2 = y1 - resultDistance * Math.sin(resultAngle);
		if (x2 > x1)
			ax2 = x1 + resultDistance * Math.cos(resultAngle);
		else
			ax2 = x1 - resultDistance * Math.cos(resultAngle);
	}

}
