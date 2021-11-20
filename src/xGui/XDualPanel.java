package xGui;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import xThemes.XStyle;
import xThemes.XTheme;

public class XDualPanel extends XPanel {

	private static final long serialVersionUID = 1L;

	private XPanel left;
	private XPanel right;
	
	public XDualPanel() {
		this(new XStyle());
	}
		
	public XDualPanel(XStyle style) {
		super(style, new GridLayout(1, 2));
		FlowLayout flowLeft = new FlowLayout();
		flowLeft.setAlignment(FlowLayout.LEFT);
		
		FlowLayout flowRight = new FlowLayout();
		flowRight.setAlignment(FlowLayout.RIGHT);
		
		left = new XPanel(style, flowLeft);
		right = new XPanel(style, flowRight);
		
		addAll(left, right);
		
		XTheme.addXThemeListener(this);
	}

	public XPanel getLeft() {
		return left;
	}

	public XPanel getRight() {
		return right;
	}
}