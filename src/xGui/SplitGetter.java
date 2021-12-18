package xGui;

import javax.swing.Icon;
import javax.swing.JComponent;

public class SplitGetter {

	protected String name;
	protected int ctrlKey;
	protected Icon icon;
	protected JComponentSupplier supplier;
	protected XSplitArea parent;
	
	private JComponent component;
	
	public SplitGetter(String name, int ctrlKey, Icon icon, JComponentSupplier supplier) {
		super();
		this.name = name;
		this.ctrlKey = ctrlKey;
		this.icon = icon;
		this.supplier = supplier;
	}

	protected JComponent getComponent() {
		if(component == null) {
			component = supplier.getComponent();
			return component;
		}
		if(component.isDisplayable()) {
			component = supplier.getComponent();
			return component;
		}
		component = supplier.getComponent();
		return component;
	}
}