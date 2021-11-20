package xGui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import xThemes.XStyle;
import xThemes.XTheme;
import xThemes.XThemeListener;

public class BorderLayoutPanel extends JPanel implements XThemeListener {

	private static final long serialVersionUID = 6767280797766845319L;

	private JPanel top = new JPanel();
	private JPanel right = new JPanel();
	private JPanel bottom = new JPanel();
	private JPanel left = new JPanel();
	private JPanel center = new JPanel();
	
	XStyle style = new XStyle();
	
	public BorderLayoutPanel() {
		super();
		int padding = XTheme.getTheme().getPadding();
		setLayout(new BorderLayout(padding, padding));
		add(top, BorderLayout.NORTH);
		add(right, BorderLayout.EAST);
		add(bottom, BorderLayout.SOUTH);
		add(left, BorderLayout.WEST);
		add(center, BorderLayout.CENTER);
		XTheme.addXThemeListener(this);
	}

	public JPanel getTop() {
		return top;
	}

	public JPanel getRight() {
		return right;
	}

	public JPanel getBottom() {
		return bottom;
	}

	public JPanel getLeft() {
		return left;
	}

	public JPanel getCenter() {
		return center;
	}

	@Override
	public void ThemeChanged(XTheme theme) {
	}
	
	@Override
	public XStyle getStyle() {
		return style;
	}
}