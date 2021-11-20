package xLayouts;

import java.awt.BorderLayout;

import xThemes.XStyle;
import xThemes.XTheme;
import xThemes.XThemeListener;

public class XBorderLayout extends BorderLayout implements XThemeListener {

	private static final long serialVersionUID = 1L;

	public XBorderLayout() {
		super(XTheme.getTheme().getPadding(), XTheme.getTheme().getPadding());
	}
	
	public XBorderLayout(int hgap, int vgap) {
		super(hgap, vgap);
	}

	@Override
	public void ThemeChanged(XTheme theme) {
		setHgap(theme.getPadding());
		setVgap(theme.getPadding());
	}

	@Override
	public XStyle getStyle() {
		return null;
	}
}
