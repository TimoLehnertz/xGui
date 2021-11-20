package xGui;

import javax.swing.JCheckBox;

import xThemes.XStyle;
import xThemes.XTheme;
import xThemes.XThemeListener;

public class XCheckBox extends JCheckBox implements XThemeListener {

	private static final long serialVersionUID = 1L;

	private XStyle style;
	
	public XCheckBox() {
		this("");
	}
	
	public XCheckBox(String text) {
		this(text, XStyle.TRANSPARENT, false);
	}
	
	public XCheckBox(String text, boolean checked) {
		this(text, XStyle.TRANSPARENT, checked);
	}
	
	public XCheckBox(String text, XStyle style, boolean checked) {
		super(text, checked);
		this.style = style;
		XTheme.addXThemeListener(this);
	}

	@Override
	public void ThemeChanged(XTheme theme) {
	}

	@Override
	public XStyle getStyle() {
		return style;
	}
}
