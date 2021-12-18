package xGui;

import javax.swing.JComponent;

@FunctionalInterface
public interface JComponentSupplier {

	public JComponent getComponent();
}