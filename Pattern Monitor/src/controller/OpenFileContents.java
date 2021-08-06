package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import model.AtTheRateSymbol;
import model.CloseParenthesisSymbol;
import model.GreaterThanSymbol;
import model.LessThanSymbol;
import model.MinusSymbol;
import model.OneInNOut;
import model.OpenParenthesisSymbol;
//import model.OrSymbol;
import model.Symbol;

/**
 * Repaint data from the .txt file to the WorkSpace from the chosen file.
 */
public class OpenFileContents implements java.io.Serializable {
	public static boolean isOpened = false;
	public static int tabValue =0;

	public OpenFileContents(JFrame frame) {
		super();

		JLabel fileName = new JLabel("no file selected");
		JFileChooser fileChooser = new JFileChooser();
		int isOperationApproved = fileChooser.showOpenDialog(null);

		if (isOperationApproved == JFileChooser.APPROVE_OPTION) {
			fileName.setText(fileChooser.getSelectedFile().getAbsolutePath());

			File fileContent = fileChooser.getSelectedFile();
			BufferedReader fReader;

			JTabbedPane rightPanel = WorkSpace.getInstance().getRightPanel();
			rightPanel.removeAll();

			Collector.getInstance().initialize();
			int count =-1;
			try {
				fReader = new BufferedReader(new FileReader(fileContent));
				String line = fReader.readLine();

				while (line != null) {

					String[] info = line.split(";");

					if (info[0].equals("Tab")) {
						isOpened = true;
						WorkPanel workPanel = new WorkPanel(rightPanel.getWidth(), rightPanel.getHeight());
						rightPanel.addTab(info[2], workPanel);
					} else if (info[0].equals("shape")) {

						String name = info[1];
						int x = Integer.parseInt(info[2]);
						int y = Integer.parseInt(info[3]);
						String userInput = info[4];
						int hashTabIndex = -1;
						int tabIndex = Integer.parseInt(info[5]);

						if (info.length == 7) {
							hashTabIndex = Integer.parseInt(info[6]);
						}

						createSymbol(name, x, y, userInput, hashTabIndex, tabIndex);

						rightPanel.repaint();
					} else {

						int tabIndex = Integer.parseInt(info[1]);
						count++;
						int pl1Index = Integer.parseInt(info[2]);
						int l1Index = Integer.parseInt(info[3]);

						for (String l2 : info[4].split(":")) {
							String[] l2L = l2.split("-");
							int pl2Index = Integer.parseInt(l2L[0]);
							int l2Index = Integer.parseInt(l2L[1]);

							connectLine(tabIndex, pl1Index, l1Index, pl2Index, l2Index);
						}

					}
					line = fReader.readLine();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			tabValue = count;

		} else
			fileName.setText("the user cancelled the operation");
	}

	private void createSymbol(String name, int x, int y, String userInput, int hashTabIndex, int tabIndex) {

		Symbol s = null;
		JTabbedPane rightPanel = WorkSpace.getInstance().getRightPanel();
		WorkPanel wp = (WorkPanel) rightPanel.getComponent(tabIndex);

		switch (name) {
		case "(":
			s = new OpenParenthesisSymbol(wp, x, y, new JButton("("));
			wp.setOpenP(true);
			break;
		case ")":
			s = new CloseParenthesisSymbol(wp, x, y, new JButton(")"));
			wp.setCloseP(true);
			break;
		case "<":
			s = new LessThanSymbol(wp, x, y, new JButton("<"));
			break;
		case ">":
			s = new GreaterThanSymbol(wp, x, y, new JButton(">"));
			break;
		case "@":
			s = new AtTheRateSymbol(wp, x, y, new JButton("@"));
			break;
		case "-|":
			s = new OneInNOut(wp, x, y, new JButton("-|"));
			break;
		case "|-":
			s = new OneInNOut(wp, x, y, new JButton("|-"));
			break;
		case "-":
			s = new MinusSymbol(wp, x, y, new JButton("-"));
			break;
		}
		s.setUserInput(userInput);
	}

	private void connectLine(int tabIndex, int pl1Index, int l1Index, int pl2Index, int l2Index) {

		WorkPanel tab = (WorkPanel) WorkSpace.getInstance().getRightPanel().getComponent(tabIndex);

		Symbol s1 = (Symbol) tab.getComponent(pl1Index);
		Symbol s2 = (Symbol) tab.getComponent(pl2Index);

		Collector.getInstance().addNewLinks(tab, s2, s1);

	}
}
