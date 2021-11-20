package xThemes;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.UIManager;

import xGui.RoundedBorder;
import xGui.XButton;
import xThemes.XStyle.BackgroundType;
import xThemes.XStyle.ColorFilter;
import xThemes.XStyle.FontColor;
import xThemes.XStyle.FontType;

public abstract class XTheme {
	
	private String name;
	private String description;
	private Color colorLabel;
	
	public static final List<XTheme> themes = new ArrayList<>();
	static {
		themes.add(new DarkestDarkTheme());
		themes.add(new BlackWidowTheme());
		themes.add(new LightTheme());
	}
	
	private static final List<XThemeListener> listeners = new ArrayList<>();
	
	public XTheme(String name, String description, Color colorLabel) {
		super();
		this.name = name;
		this.description = description;
		this.colorLabel = colorLabel;
		updateTooltip();
	}
	
	public abstract Color getBGColor(BackgroundType type);
	public abstract Color getFontColor(FontColor type);
	public abstract Font getFont(FontType type);
	public abstract Color getTooltipBGColor();
	public abstract Color getTooltipFontColor();
	public abstract int getPadding();
	public abstract int getDefaultButtonRoundness();
	
	private static XTheme theme = new DarkestDarkTheme();
//	private static XTheme theme = new LightTheme();
	
	public static XTheme getTheme() {
		return theme;
	}
	
	public static void updateTheme(XTheme theme) {
		XTheme.theme = theme;
		for (XThemeListener xThemeListener : listeners) {
			updateStyleOn(xThemeListener.getComponent(), xThemeListener.getStyle());
			xThemeListener.ThemeChanged(theme);
		}
		theme.updateTooltip();
	}
	
	private void updateTooltip() {
		UIManager.put("ToolTip.background", getTooltipBGColor());
		UIManager.put("ToolTip.foreground", getTooltipFontColor());
	}
	
	private static double colorFilterToBrightness(ColorFilter filter) {
		switch(filter) {
		case brighter: return 1.1;
		case bright: return 1.2;
		case darker: return 0.9;
		case dark: return 0.8;
		default: return 1;
		}
	}
	
	public static String getFirstExistingFont(String[] fonts) {
		for (int i = 0; i < fonts.length; i++) {
			if(doesFontExist(fonts[i])) return fonts[i];
		}
		return "Arial";
	}
	
	public static boolean doesFontExist(String font) {
		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	    for (int i = 0; i < fonts.length; i++) {
	      if(fonts[i].contentEquals(font)) return true;
	    }
	    return false;
	}
	
	public static void printAvailableFonts() {
		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	    for (int i = 0; i < fonts.length; i++) {
	      System.out.println(fonts[i]);
	    }
	}
	
	private static Color filterColor(Color c, ColorFilter filter) {
		if(filter == ColorFilter.none) return c;
		double brightness = colorFilterToBrightness(filter);
		Color filtered = changeBrightness(c, brightness);
		return filtered;
	}
	
	private static int clampRGB(double rgb) {
		return (int) Math.min(Math.max(Math.round(rgb), 0), 255);
	}
	
	private static Color changeBrightness(Color c, double b) {
		return new Color(clampRGB(c.getRed() * b), clampRGB(c.getGreen() * b), clampRGB(c.getBlue() * b));
	}
	
	public static void updateStyleOn(JComponent component, XStyle style) {
		updateStyleOn(component, style, theme);
	}
	
	public static void updateStyleOn(JComponent component, XStyle style, XTheme theme) {
		if(component == null) return;
		Color bgColor = theme.getBGColor(style.backgroundType);
		bgColor = filterColor(bgColor, style.backgroundFilter);
		if(style.backgroundType != BackgroundType.custom) { // if custom ignore
			if(style.backgroundType == BackgroundType.none) {
				component.setOpaque(false);
			} else {
				component.setOpaque(true);
				component.setBackground(bgColor);
			}
		}
		Color fontColor = theme.getFontColor(style.fontColor);
		fontColor = filterColor(fontColor, style.fontColorFilter);
		component.setForeground(fontColor);
		//XButton styling
		if(component instanceof XButton) {
			XButton button = (XButton) component;
			if(style.backgroundType == BackgroundType.custom) {
				bgColor = filterColor(button.background, style.backgroundFilter);
			}
			int roundness = style.buttonRoundness < 0 ? theme.getDefaultButtonRoundness() : style.buttonRoundness;
			button.setBorder(new RoundedBorder(roundness, bgColor));
			button.setOpaque(false);
		}
		component.setFont(theme.getFont(style.fontType));
	}
	
	public static boolean addXThemeListener(XThemeListener listener) {
		updateStyleOn(listener.getComponent(), listener.getStyle());
		return listeners.add(listener);
	}
	
	public static boolean removeXThemeListener(XThemeListener listener) {
		return listeners.remove(listener);
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Color getColorLabel() {
		return colorLabel;
	}

	public static List<XTheme> getThemes() {
		return themes;
	}

	public static void previewTheme(XTheme theme) {
		for (XThemeListener xThemeListener : listeners) {
			updateStyleOn(xThemeListener.getComponent(), xThemeListener.getStyle(), theme);
			xThemeListener.ThemeChanged(theme);
		}
		theme.updateTooltip();
	}

	public static void unpreviewTheme() {
		updateTheme(theme);
	}
}