package controller;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
*
* Space where panels can be added for the symbols to be dragged.
*/
public class WorkSpace implements java.io.Serializable{

	private static WorkSpace dataObj;
	private int workSpaceWidth;
	private int workSpaceHeight;
	private JTabbedPane rightPanel;
	
	public static WorkSpace getInstance() {
			
		if (dataObj == null) {
			dataObj = new WorkSpace();
		}
		return dataObj;
	}
	
	public void createTabbedPane(JFrame frame) {
		
		rightPanel = new JTabbedPane();
		workSpaceWidth = frame.getPreferredSize().width / 6 * 5;
		workSpaceHeight = frame.getPreferredSize().height / 8 * 7;
		
		rightPanel.setName("Tabbed Panel");
		rightPanel.setPreferredSize(new Dimension(workSpaceWidth,workSpaceHeight));
		rightPanel.setBackground(new Color(255, 255, 255));
		
		addTab("Main Tab");
		
		JScrollPane scrollPane = new JScrollPane(rightPanel);
		frame.add(scrollPane);
	}
	
	public WorkPanel addTab(String name) {
		
		WorkPanel panel = new WorkPanel(workSpaceWidth, workSpaceHeight);
		
		rightPanel.addTab(name,panel);
		
		return panel;
	}
	
	public void changeName(String name, WorkPanel panel) {
		rightPanel.setTitleAt(rightPanel.indexOfComponent(panel), name);
	}
	

	public JTabbedPane getRightPanel() {
		return rightPanel;
	}

	public void setRightPanel(JTabbedPane rightPanel) {
		this.rightPanel = rightPanel;
	}
	

}
