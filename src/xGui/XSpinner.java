package xGui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import xThemes.XStyle;
import xThemes.XStyle.BackgroundType;
import xThemes.XStyle.FontColor;
import xThemes.XTheme;
import xThemes.XThemeListener;

public class XSpinner extends JSpinner implements XThemeListener{

	private static final long serialVersionUID = 1L;

	private XStyle style;
	
	public XSpinner() {
		this(new XStyle(BackgroundType.foreground, FontColor.important));
	}
	
	public XSpinner(XStyle style) {
		this(new SpinnerNumberModel(), style);
	}
	
	public XSpinner(SpinnerModel model) {
		this(model, new XStyle(BackgroundType.foreground, FontColor.important));
	}
	
	
	public XSpinner(SpinnerModel model, XStyle style) {
		super(model);
		this.style = style;
		setBorder(null);
		XTheme.addXThemeListener(this);
	}
	
	public void updateTheme() {
		XTheme.updateStyleOn(this, style);
	}
	
	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
		for (Component c : getEditor().getComponents()) {
            c.setForeground(fg);
        }
		for (Component c : getComponents()) {
			c.setForeground(fg);
		}
	}
	
	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
        for (Component c : getEditor().getComponents()) {
            c.setBackground(bg);
        }
        for (Component c : getComponents()) {
        	if(c instanceof JButton) {
        		JButton btn = (JButton) c;
        		btn.setBackground(bg);
        		btn.setBorder(null);
        		btn.setBorderPainted(false);
        		btn.setOpaque(false);
        		btn.setFocusPainted(false);
        	}
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
