package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import controller.Collector;
import controller.WorkSpace;

/**
 * 
 * 
 * The main application window which is basically divided into two panels:
 * left and right.
 */

public class Frame extends JFrame {
	
	private int widthOfFrame;
	private int heightOfFrame;
	public Frame(String title) {
		super(title);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		widthOfFrame = screenSize.width;
		heightOfFrame = screenSize.height;
		
		this.setLayout(new BorderLayout());
		this.setMinimumSize(new Dimension(widthOfFrame / 2, heightOfFrame / 2));
		this.setPreferredSize(new Dimension(widthOfFrame, heightOfFrame));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Collector.getInstance().initSymbol();
		
		new Menu(this);
		new InputPanel(this);
		
		WorkSpace.getInstance().createTabbedPane(this);
		
		this.setVisible(true);

	}
	
	public static void main(String args[]) {
		new Frame("Circuit Designer");
	}
}
