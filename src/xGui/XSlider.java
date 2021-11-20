package xGui;

import javax.swing.JSlider;

import xThemes.XStyle;
import xThemes.XTheme;
import xThemes.XThemeListener;

public class XSlider extends JSlider implements XThemeListener {

	private static final long serialVersionUID = 1L;

	private XStyle style;
	
	public XSlider(int orientation, int min, int max, int value) {
		this(XStyle.TRANSPARENT, orientation, min, max, value);
	}
	
	public XSlider(XStyle style, int orientation, int min, int max, int value) {
		super(orientation, min, max, value);
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
