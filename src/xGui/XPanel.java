package xGui;

import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.JPanel;

import xThemes.XStyle;
import xThemes.XTheme;
import xThemes.XThemeListener;

public class XPanel extends JPanel implements XThemeListener {

	private static final long serialVersionUID = -6584549369425169404L;
	
	private XStyle style;
	
	public XPanel() {
		this(new FlowLayout());
	}
	
	public XPanel(LayoutManager layout) {
		this(XStyle.TRANSPARENT, layout);
	}
	
	public XPanel(XStyle style) {
		this(style, new FlowLayout());
	}
	
	public XPanel(LayoutManager layout, XStyle style) {
		this(style, layout);
	}
	
	public XPanel(XStyle style, LayoutManager layout) {
		super();
		this.style = style;
		setLayout(layout);
		
		XTheme.addXThemeListener(this);
	}
	
	public void addAll(JComponent... components) {
		for (JComponent jComponent : components) {
			add(jComponent);
		}
	}

	@Override
	public void ThemeChanged(XTheme theme) {
	}

	@Override
	public XStyle getStyle() {
		return style;
	}
}