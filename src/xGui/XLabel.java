package xGui;

import javax.swing.JLabel;

import xThemes.XStyle;
import xThemes.XStyle.BackgroundType;
import xThemes.XTheme;
import xThemes.XThemeListener;

public class XLabel extends JLabel implements XThemeListener {

	private static final long serialVersionUID = -4452947682572899576L;

	protected XStyle style;
	
	public XLabel() {
		this("");
	}
	
	public XLabel(String text) {
		this(text, new XStyle(BackgroundType.none), LEADING);
	}
	
	public XLabel(String text, XStyle style) {
		this(text, style, LEADING);
	}
	
	public XLabel(String text, int options) {
		this(text, new XStyle(BackgroundType.none), options);
	}
	
	public XLabel(String text, XStyle style, int options) {
		super(text, options);
		this.style = style;
		XTheme.addXThemeListener(this);
	}
	
	public void updateTheme() {
		XTheme.updateStyleOn(this, style);
	}
	
	@Override
	public XStyle getStyle() {
		return style;
	}

	@Override
	public void ThemeChanged(XTheme theme) {
	}
}