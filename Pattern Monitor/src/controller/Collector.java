package controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import model.Symbol;

/**
 *Collector class contains the CenterSpace data.
 */
public class Collector {

	private static Collector dataObj;

	private Symbol selectSymbol;

	private Symbol linkSelected;

	private Map<String, Class<?>> symbolNames;
	public Map<WorkPanel, Map<Symbol, ArrayList<Symbol>>> newLinks;

	public static Collector getInstance() {

		if (dataObj == null) {
			dataObj = new Collector();
		}
		return dataObj;
	}

	public void initSymbol() {

		this.symbolNames = new HashMap<String, Class<?>>();
		this.newLinks = new HashMap<WorkPanel, Map<Symbol, ArrayList<Symbol>>>();
		this.linkSelected = null;

		try {
			this.symbolNames.put("(", Class.forName("model.OpenParenthesisSymbol"));
			this.symbolNames.put(")", Class.forName("model.CloseParenthesisSymbol"));
			this.symbolNames.put("<", Class.forName("model.LessThanSymbol"));
			this.symbolNames.put(">", Class.forName("model.GreaterThanSymbol"));
			this.symbolNames.put("@", Class.forName("model.AtTheRateSymbol"));
			this.symbolNames.put("-", Class.forName("model.MinusSymbol"));
			this.symbolNames.put("-|", Class.forName("model.OneInNOut"));
			this.symbolNames.put("|-", Class.forName("model.NInOneOut"));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Symbol getSymbolSelected() {
		return selectSymbol;
	}

	public void setSymbolSelected(Symbol selectSymbol) {
		this.selectSymbol = selectSymbol;
	}

	public Map<String, Class<?>> getSymbolNames() {
		return symbolNames;
	}

	public Map<Symbol, ArrayList<Symbol>> getSpaceLines(WorkPanel w) {
		return newLinks.get(w);
	}

	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}

	public void addNewLinks(WorkPanel w, Symbol c2, Symbol c1) {

		ArrayList<Symbol> a = new ArrayList<Symbol>();
		
		if (this.newLinks.containsKey(w)) {
			if (c1.getType() != ")" && c2.getType() != "(") {
				if (newLinks.get(w).containsKey(c1)) {
					if (c1.outputCount < c1.maxOutput) {
						if (c2.inputCount < c2.maxInput) {
							this.newLinks.get(w).get(c1).add(c2);
							c2.inputCount++;
							c1.outputCount++;
						}

					}
				} else if (!newLinks.get(w).containsKey(c1)) {
					if (c2.inputCount < c2.maxInput) {
						a.add(c2);
						this.newLinks.get(w).put(c1, a);
						c1.outputCount++;
						c2.inputCount++;
					}

				}
			}
		}

		else if (c1.getType() != ")" && c2.getType() != "(") {
			Map<Symbol, ArrayList<Symbol>> map = new HashMap<Symbol, ArrayList<Symbol>>();
			a.add(c2);
			map.put(c1, a);
			c1.outputCount++;
			c2.inputCount++;
			this.newLinks.put(w, map);
		}

		w.repaint();

	}

	public Symbol getLinkSelected() {
		return linkSelected;
	}

	public void setLinkSelected(Symbol linkSelected) {
		if (this.linkSelected != null) {
			this.linkSelected.setBorder(UIManager.getBorder("Button.border"));
		}
		this.linkSelected = linkSelected;
		if (this.linkSelected != null) {
			this.linkSelected.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		}
	}


	public Map<WorkPanel, Map<Symbol, ArrayList<Symbol>>> getSpaceLines() {
		return newLinks;
	}

	public void setTabLines(Map<WorkPanel, Map<Symbol, ArrayList<Symbol>>> tabLines) {
		this.newLinks = tabLines;
	}

	public void initialize() {
		this.newLinks.clear();
	}
}
