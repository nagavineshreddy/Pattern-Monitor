package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.AtTheRateSymbol;
import model.GreaterThanSymbol;
import model.LessThanSymbol;
import model.MinusSymbol;
import model.OneInNOut;
import model.NInOneOut;

/**
 *
 * Contains different symbols that can be drawn on the CenterSpace.
 */
public class InputPanel extends JPanel{

	int panelWidth;
	int panelHeight;
	
	public InputPanel(JFrame mainFrame) {
		super();
		
		panelWidth = mainFrame.getPreferredSize().width / 7;
		panelHeight = mainFrame.getPreferredSize().height / 12 * 12;
		
		this.setName("Input Panel");
		this.setLayout(new FlowLayout());
		this.setBackground(new Color(255, 255, 255));
		this.setPreferredSize(new Dimension(panelWidth, panelHeight));
		
		new LessThanSymbol(this, 0, 0,new JButton("<"));
		new GreaterThanSymbol(this, 0, 0,new JButton(">"));
		new AtTheRateSymbol(this, 0, 0,new JButton("@"));
		new OneInNOut(this,0,0,new JButton("-|"));
		new NInOneOut(this,0,0,new JButton("|-"));
		new MinusSymbol(this, 0, 0,new JButton("-"));
		
		JScrollPane scrollPane = new JScrollPane(this);
		
		mainFrame.add(scrollPane, BorderLayout.LINE_START);
	}
}
