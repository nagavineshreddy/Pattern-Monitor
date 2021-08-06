package controller;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

import model.Symbol;

/**
*
* This class handles the mouse listeners for the Input panel on
*               the left containing symbols.
*/
public class SetSymbolListener {
	
	private Map<String, Class<?>> classNames;
	
	public SetSymbolListener(WorkPanel panel) {
		
		this.classNames = Collector.getInstance().getSymbolNames();

		panel.setTransferHandler(new SymbolSelection());
		
		panel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				try {
					boolean symbolPresent = false;
					Symbol symbolName = Collector.getInstance().getSymbolSelected();
					
					
					if(!symbolPresent) { //
						JButton symbol = (JButton) classNames.get(symbolName.getName()).getDeclaredConstructor(JComponent.class, 
								int.class, int.class).newInstance(panel,e.getX(),e.getY());
						panel.setComponentZOrder(symbol, 0);
						panel.repaint();
						Collector.getInstance().setLinkSelected(null);
					}
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException 
						| InvocationTargetException | NoSuchMethodException | SecurityException 
						| NullPointerException e1) {}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
	}

	/**
	 *
	 * @Description: This class is to send the symbol selection by the user to other
	 *               packages
	 */
	public static class SymbolSelection extends TransferHandler {

		public static final DataFlavor d = DataFlavor.stringFlavor;

		public SymbolSelection() {
		}

		@Override
		public boolean canImport(TransferHandler.TransferSupport support) {
			return support.isDataFlavorSupported(d);
		}

		@Override
		public boolean importData(TransferHandler.TransferSupport support) {
			boolean accept = false;
			Map<String, Class<?>> classNames = Collector.getInstance().getSymbolNames();
			if (canImport(support)) {
				try {
					Transferable t = support.getTransferable();
					Object value = t.getTransferData(d);
					if (value instanceof String) {
						Component component = support.getComponent();
						if (component instanceof WorkPanel) {

							WorkPanel panel = (WorkPanel) component;
							boolean symbolPresent = false;
							JButton jbtn = new JButton();
							boolean f = true;

							if (!symbolPresent) {
								Point mousePosition = MouseInfo.getPointerInfo().getLocation();
								Point panelPosition = panel.getLocationOnScreen();
								int x = mousePosition.x - panelPosition.x;
								int y = mousePosition.y - panelPosition.y;
								JButton symbol = (JButton) classNames.get(value.toString())
										.getDeclaredConstructor(JComponent.class, int.class, int.class, JButton.class)
										.newInstance(panel, x, y, jbtn);
								panel.repaint();
								Collector.getInstance().setLinkSelected(null);
								accept = true;
							}
						}
					}
				} catch (Exception exp) {
					exp.printStackTrace();
				}
			}
			return accept;
		}
	}
}
