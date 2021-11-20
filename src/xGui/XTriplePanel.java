package xGui;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import xThemes.XStyle;
import xThemes.XTheme;

public class XTriplePanel extends XPanel {

	private static final long serialVersionUID = 1L;

	private XPanel left;
	private XPanel center;
	private XPanel right;
	
	public XTriplePanel() {
		this(new XStyle());
	}
		
	public XTriplePanel(XStyle style) {
		super(style, new GridLayout(1, 3));
		FlowLayout flowLeft = new FlowLayout();
		flowLeft.setAlignment(FlowLayout.LEFT);
		
		FlowLayout flowRight = new FlowLayout();
		flowRight.setAlignment(FlowLayout.RIGHT);
		
		left = new XPanel(style, flowLeft);
		center = new XPanel(style);
		right = new XPanel(style, flowRight);
		
		addAll(left, center, right);
		
		XTheme.addXThemeListener(this);
	}

	public XPanel getLeft() {
		return left;
	}

	public XPanel getCenter() {
		return center;
	}

	public XPanel getRight() {
		return right;
	}
}