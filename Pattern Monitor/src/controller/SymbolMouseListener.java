package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import model.Symbol;

/**
 *
 * This class is used to handle the mouse listeners for symbols.
 */
public class SymbolMouseListener {

	private int mouseXPosition;
	private int mouseYPosition;
	private int xLeft;
	private int yTop;
	private int xRight;
	private int yBottom;
	private int screenXPosition;
	private int screenYPosition;

	public SymbolMouseListener(JComponent panel, Symbol symbol) {

		symbol.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
				screenXPosition = e.getXOnScreen();
				screenYPosition = e.getYOnScreen();
				xLeft = panel.getX() + panel.getParent().getX();
				yTop = panel.getY() + panel.getParent().getY();
				xRight = panel.getX() + panel.getPreferredSize().width - 200;
				yBottom = panel.getY() + panel.getPreferredSize().height - 30;
				mouseXPosition = symbol.getX();
				mouseYPosition = symbol.getY();
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			// This method is to handle the input data dialog box
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					setResponse(symbol);
			}
		});
		;

		symbol.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen() - screenXPosition + mouseXPosition;
				int y = e.getYOnScreen() - screenYPosition + mouseYPosition;

				putLocation(x, y, symbol, panel);
			}
		});
	}
	

	/**
	 *
	 * This method is used to add data by the user when double clicked the symbol
	 */
	private void setResponse(Symbol symbol) {
		String prevResponse = symbol.getUserInput();
		String response = (String) JOptionPane.showInputDialog(null, "Data:", "Enter User Input",
				JOptionPane.QUESTION_MESSAGE, null, null, prevResponse);

		if (response == null)
			response = prevResponse;

		symbol.setUserInput(response);
	}

	/**
	 *
	 * This method gives the click location
	 */
	private void putLocation(int x, int y, Symbol symbol, JComponent panel) {
		if (x > xLeft && y > yTop && x < xRight && y < yBottom)
			symbol.setLocation(x, y);
		else if (x < xLeft && y > yTop && y < yBottom)
			symbol.setLocation(panel.getX(), y);
		else if (x > xLeft && y < yTop && x < xRight)
			symbol.setLocation(x, panel.getY() - 30);
		else if (x > xRight && y < yBottom && y > yTop)
			symbol.setLocation(panel.getPreferredSize().width - 200, y);
		else if (x < xRight && y > yBottom && x > xLeft)
			symbol.setLocation(x, panel.getPreferredSize().height - 10);

		symbol.getParent().setComponentZOrder(symbol, 0);

		panel.repaint();
	}
}
