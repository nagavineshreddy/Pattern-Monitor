package view;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.OpenFileContents;
import controller.WorkSpace;
import controller.Compile;
import controller.SaveFileContents;
import javax.swing.*;

/**
 *
 * Adds open, save, newSpace and compile options to the Menu
 */
public class Menu {
	private int tabCount = 0;

	public Menu(JFrame mainFrame) {

		JMenuBar menuBar = new JMenuBar();
		JMenuItem newSpace = new JMenuItem("New Space");
		JMenuItem load = new JMenuItem("Load");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem compile = new JMenuItem("Compile");

		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new SaveFileContents(mainFrame);
			}
		});

		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new OpenFileContents(mainFrame);
			}
		});

		newSpace.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				OpenFileContents.isOpened = false;
				int openedTabValue = OpenFileContents.tabValue;
				int newTabCount = incrementTabCount();
				int c = openedTabValue + newTabCount;
				WorkSpace.getInstance().addTab("New Tab" + c);

			}
		});

		compile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String msg = new Compile().validatePanelConnections();
				String p = Compile.isCompiled;
				showMessageDialog(null, msg);
				if(msg == "Compiled Successfully") {
					
					JFrame frame = new JFrame("Compiled doc");
					frame.setSize(300, 700);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					JTextArea t1 = new JTextArea(p);
					JPanel panel = new JPanel();
					panel.setSize(300,700);
					panel.add(t1);
					frame.add(panel);
					
				}
				
			}
		});

		menuBar.add(load);
		menuBar.add(save);
		menuBar.add(newSpace);
		menuBar.add(compile);

		mainFrame.setJMenuBar(menuBar);
	}

	public int incrementTabCount() {
		tabCount++;
		return tabCount;
	}

}
