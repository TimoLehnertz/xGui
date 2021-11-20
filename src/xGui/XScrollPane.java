package xGui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import xThemes.XStyle;
import xThemes.XStyle.BackgroundType;
import xThemes.XTheme;
import xThemes.XThemeListener;

public class XScrollPane extends JScrollPane implements XThemeListener {

	private static final long serialVersionUID = 1L;

	private XStyle style;
	
	public XScrollPane(JComponent component) {
		this(new XStyle(BackgroundType.none), component);
	}
	
	public XScrollPane(XStyle style, JComponent component) {
		super(component);
		this.style = style;
		XTheme.addXThemeListener(this);
		getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
//		setViewportBorder(null);
//		System.out.println(getBorder().getClass().getSimpleName());
	}
	
	@Override
	public void setOpaque(boolean isOpaque) {
		super.setOpaque(isOpaque);
		getViewport().setOpaque(isOpaque);
	}
	
	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		getViewport().setBackground(bg);
	}
	
	@Override
	public void ThemeChanged(XTheme theme) {
	}

	@Override
	public XStyle getStyle() {
		return style;
	}

}