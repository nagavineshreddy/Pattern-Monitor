package controller;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

import model.Symbol;

/**
 *
 * The class used to pass symbol which is dragged to Transfer
 *               Handler
 */
public class DragAndDrop {

	public DragAndDrop(Symbol symbol) {

		symbol.setTransferHandler(new ExportSymbol(symbol.getText()));

		symbol.addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				JButton b = (JButton) e.getSource();
				TransferHandler handle = b.getTransferHandler();
				handle.exportAsDrag(b, e, TransferHandler.COPY);
			}
		});

	}

	/**
	 *
	 *This class is used to place the dragged symbol by the user in
	 *               to the CenterPanel
	 */
	public static class ExportSymbol extends TransferHandler {

		public static final DataFlavor d = DataFlavor.stringFlavor;
		private String value;

		public ExportSymbol(String val) {
			this.value = val;
		}

		public String getValue() {
			return value;
		}

		@Override
		protected void exportDone(JComponent s, Transferable data, int a) {
			super.exportDone(s, data, a);
		}

		@Override
		protected Transferable createTransferable(JComponent c) {
			Transferable t = new StringSelection(getValue());
			return t;
		}

		@Override
		public int getSourceActions(JComponent c) {
			return DnDConstants.ACTION_COPY_OR_MOVE;
		}

	}

}
