package controller;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JTabbedPane;
import model.Symbol;

/**
 *
 * Checks if the rules are validated.
 */
public class Compile {

	public static String isCompiled = "";

	private int getIndexOfSymbol(Symbol symbol, WorkPanel tab) {
		int i = 0;
		for (Component s : tab.getComponents()) {
			if (symbol == (Symbol) s) {
				return i;
			}
			i++;
		}
		return -1;

	}

	public String getStringType(String type) {
		String symbol = "";
		switch (type) {
		case "(":
			symbol = "openParen";
			break;
		case ")":
			symbol = "closeParen";
			break;
		case "<":
			symbol = "lessThan";
			break;
		case ">":
			symbol = "greaterThan";
			break;
		case "@":
			symbol = "atTheRate";
			break;
		case "|-":
			symbol = "nInOneOut";
			break;
		case "-|":
			symbol = "oneInNOut";
			break;
		case "-":
			symbol = "minus";
			break;
		}
		return symbol;
	}

	// method for compile file generation
	public String validatePanelConnections() {

		int index = 0;
		String lines = "";
		String first = "";
		String start = "";
		String end = "";
		lines += "digraph G" + "{" + System.lineSeparator();

		Map<WorkPanel, Map<Symbol, ArrayList<Symbol>>> tabLines = Collector.getInstance().getSpaceLines();

		for (WorkPanel tab : tabLines.keySet()) {
			Map<Symbol, ArrayList<Symbol>> line = tabLines.get(tab);
			if (index == 0) {
				for (Symbol l1 : line.keySet()) {
					int pl1Index = getIndexOfSymbol(l1, tab);
					Symbol firstSymbol = (Symbol) tab.getComponent(pl1Index);
					int firstSymbolId = firstSymbol.getX() + firstSymbol.getY() + pl1Index;

					String firstSymbolType = getStringType(firstSymbol.getType());
					if (firstSymbol.getType() == "(") {

						start += "start" + "->" + firstSymbolType + "_" + index + firstSymbolId + ";"
								+ System.lineSeparator();
					}

					first += firstSymbolType + "_" + index + firstSymbolId + "->";
					int links = 0;
					for (Symbol l2 : line.get(l1)) {
						int pl2Index = getIndexOfSymbol(l2, tab);
						links++;

						Symbol secondSymbol = (Symbol) tab.getComponent(pl2Index);
						String secondSymbolType = getStringType(secondSymbol.getType());
						int secondSymbolId = secondSymbol.getX() + secondSymbol.getY() + pl2Index;

						if (secondSymbol.getType() == ")") {

							end += secondSymbolType + "_" + index + secondSymbolId + "->" + "end" + ";"
									+ System.lineSeparator();
						}

						if (links > 1) {
							first += System.lineSeparator();
							first += firstSymbolType + "_" + index + firstSymbolId + "->";
							first += secondSymbolType + "_" + index + secondSymbolId + ";";
						} else {
							first += secondSymbolType + "_" + index + secondSymbolId + ";";
						}
					}

					first += System.lineSeparator();

				}

			} else {
				lines += "subgraph cluster_" + index + "{" + System.lineSeparator();
				for (Symbol l1 : line.keySet()) {

					int pl1Index = getIndexOfSymbol(l1, tab);
					Symbol firstSymbol = (Symbol) tab.getComponent(pl1Index);
					String firstSymbolType = getStringType(firstSymbol.getType());
					int firstSymbolId = firstSymbol.getX() + firstSymbol.getY() + pl1Index;
					if (firstSymbol.getType() == "(") {
						start += "start" + "->" + firstSymbolType + "_" + index + firstSymbolId + ";"
								+ System.lineSeparator();
					}

					lines += firstSymbolType + "_" + index + firstSymbolId + "->";
					int links = 0;
					for (Symbol l2 : line.get(l1)) {
						int pl2Index = getIndexOfSymbol(l2, tab);
						links++;

						Symbol secondSymbol = (Symbol) tab.getComponent(pl2Index);
						String secondSymbolType = getStringType(secondSymbol.getType());
						int secondSymbolId = secondSymbol.getX() + secondSymbol.getY() + pl2Index;
						if (secondSymbol.getType() == ")") {
							end += secondSymbolType + "_" + index + secondSymbolId + "->" + "end" + ";"
									+ System.lineSeparator();
						}

						if (links > 1) {
							lines += System.lineSeparator();
							lines += firstSymbolType + "_" + index + firstSymbolId + "->";
							lines += secondSymbolType + "_" + index + secondSymbolId + ";";
						} else {
							lines += secondSymbolType + "_" + index + secondSymbolId + ";";
						}
					}
					lines += System.lineSeparator();
				}
				lines += "label =" + " process " + index;
				lines += System.lineSeparator();
				lines += "}";
				lines += System.lineSeparator();
			}
			index++;
		}
		lines += System.lineSeparator();
		lines += start;
		lines += System.lineSeparator();
		lines += first;
		lines += System.lineSeparator();
		lines += end;
		lines += System.lineSeparator();
		lines += "}";
		isCompiled = lines;

		JTabbedPane rightPanel = WorkSpace.getInstance().getRightPanel();
		int noOfSpaces = rightPanel.getComponents().length;

		for (int index1 = 0; index1 < noOfSpaces; index1++) {

			WorkPanel tab = (WorkPanel) rightPanel.getComponentAt(index1);

			String msg = checkAllConnections(tab);
			if (!msg.equals("Compiled Successfully")) {
				return msg;
			}

		}

		return "Compiled Successfully";

	}

	public String checkAllConnections(WorkPanel tab) {
		int noOfSymbols = tab.getComponents().length;
		if (noOfSymbols == 0) {
			return "Cant compile Empty tabs";
		}

		try {
			for (int symbolIndex = 0; symbolIndex < noOfSymbols; symbolIndex++) {
				Symbol s = (Symbol) tab.getComponent(symbolIndex);
				int noOfConnectors = s.maxOutput;
				int currentInputCount = s.inputCount;
				int currentOutputCount = s.outputCount;
				if (s.maxInput == 99999 || s.maxOutput == 9999) {
					if (currentInputCount < 1 || currentOutputCount < 1) {
						return "Some of the Symbols are not Connected";
					}
				}
				if (s.maxOutput == 99999 && currentOutputCount > 0 && currentInputCount > 0
						|| s.maxInput == 99999 && currentInputCount > 0 && currentOutputCount > 0) {
					continue;
				}
				if (noOfConnectors > s.inputCount + s.outputCount) {
					return "Some of the Symbols are not Connected";
				}

			}
		} catch (Exception e) {
			return "check all connections in all tabs";
		}
		return "Compiled Successfully";
	}
}
