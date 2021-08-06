package controller;

import java.awt.Component;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import model.Symbol;

/**
 *
 * Saves the data in a WorkSpace to a .txt file.
 */
public class SaveFileContents implements java.io.Serializable{

	public SaveFileContents(JFrame mainFrame) {
		super();
		
		JLabel fileName = new JLabel("no file selected");
		JFileChooser fileChooser = new JFileChooser(); 
	
		int isOperationApproved = fileChooser.showSaveDialog(null);
		
		if (isOperationApproved == JFileChooser.APPROVE_OPTION){ 
			try(FileWriter fWriter = new FileWriter(fileChooser.getSelectedFile()+".txt")) {
				
				JTabbedPane rightPanel = WorkSpace.getInstance().getRightPanel();
				int index = 0;
				for(Component i : rightPanel.getComponents()) {
					
					WorkPanel tab = (WorkPanel) i;
					String title = rightPanel.getTitleAt(index);
					
					fWriter.write("Tab" + ";" + index + ";" + title + System.lineSeparator());
					index++;
				}
				
				index = 0;
				for(Component i : rightPanel.getComponents()) {
					WorkPanel tab = (WorkPanel) i;
					
					for(Component j : tab.getComponents()) {
						Symbol symbol = (Symbol) j;
						
						String name = symbol.getText();
						String x = Integer.toString(symbol.getX());
						String y = Integer.toString(symbol.getY());
						String userInput = symbol.getUserInput();
						
						fWriter.write("shape" + ";" + name + ";" + x + ";" + y + ";" + userInput + ";" + index + 
									System.lineSeparator());	
					}
					index++;
				}
				String lines = getLines();
				fWriter.write(lines);
				
            	fWriter.close();
            }
            catch (IOException e1) {
            	e1.printStackTrace();
			}
        } 
        else
            fileName.setText("the user cancelled the operation"); 
	}
	
	private String getLines() {
		
		String lines = "";
		Map<WorkPanel, Map<Symbol, ArrayList<Symbol>>> tabLines = Collector.getInstance().getSpaceLines();
		
		for(WorkPanel tab : tabLines.keySet()) {
			Map<Symbol, ArrayList<Symbol>> line  = tabLines.get(tab);
			for(Symbol l1 : line.keySet()) {
				
				int tabIndex = WorkSpace.getInstance().getRightPanel().indexOfComponent(tab);
				int pl1Index = getIndexOfSymbol(l1, tab);
				int l1Index = 0;
				
				lines += "line" + ";" + tabIndex + ";" + pl1Index + ";" + l1Index + ";";
				
				for(Symbol l2 : line.get(l1)) {
					int pl2Index = getIndexOfSymbol(l2, tab);
					int l2Index = 0;
					
					lines += pl2Index + "-" + l2Index + ":";
				}
				lines += System.lineSeparator();
			}
		}
		return lines;
	}
	
	private int getIndexOfSymbol(Symbol symbol, WorkPanel tab) {
		int i = 0;
		for(Component s : tab.getComponents()) {
			if(symbol == (Symbol) s) {
				return i;
			}
			i++;
		}
		return -1;
		
	}
	
}
