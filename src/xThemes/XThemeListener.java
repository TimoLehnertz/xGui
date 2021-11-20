package xThemes;

import javax.swing.JComponent;

public interface XThemeListener {
	
	public void ThemeChanged(XTheme theme);
	
	public XStyle getStyle();
	
	public default JComponent getComponent() {
		if(this instanceof JComponent) {			
			return (JComponent) this;
		}
		return null;
	};
}