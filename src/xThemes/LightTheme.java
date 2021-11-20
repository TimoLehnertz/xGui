package xThemes;

import java.awt.Color;
import java.awt.Font;

import xThemes.XStyle.BackgroundType;
import xThemes.XStyle.FontColor;
import xThemes.XStyle.FontType;

public class LightTheme extends XTheme {

	public LightTheme() {
		super("Light theme", "Light theme", Color.LIGHT_GRAY);
	}

	@Override
	public Color getBGColor(BackgroundType type) {
		switch (type) {
		case background: 	return new Color(166, 166, 166);
		case layer1: 		return new Color(142, 142, 142);
		case layer2: 		return new Color(179, 179, 179);
		case foreground: 	return new Color(213, 213, 213);
		case highlight1a: 	return new Color(229, 115, 126);
		case highlight1b: 	return new Color(130, 64,  71);
		case highlight2a: 	return new Color(131, 183, 254);
		case highlight2b: 	return new Color(104, 146, 203);
		case highlight3a: 	return new Color(237, 157, 93);
		case highlight3b: 	return new Color(186, 123, 72);
		case none: return 	null;
		default: return Color.WHITE;
		}
	}

	@Override
	public Color getFontColor(FontColor type) {
		switch (type) {
		case important: return new Color(0, 0, 0);
		case casual: return new Color(14, 14, 14);
		case unimportant: return new Color(40, 40, 40);
		default: return Color.WHITE;
		}
	}

	@Override
	public Font getFont(FontType type) {
		String[] fonts = {"Bahnschrift", "Lucida Sans", "Myriad Pro", "Trebuchet MS"};
		final String font = XTheme.getFirstExistingFont(fonts); // First valid gets chosen
		switch (type) {
		case h1: return new Font(font, Font.PLAIN, 15);
		case h2: return new Font(font, Font.PLAIN, 14);
		case h3: return new Font(font, Font.PLAIN, 13);
		case casual: return new Font(font, Font.PLAIN, 12);
		case casualItalic: return new Font(font, Font.BOLD, 12);
		case casualBold: return new Font(font, Font.ITALIC, 12);
		case small: return new Font(font, Font.PLAIN, 10);
		default: return null;
		}
	}

	@Override
	public int getPadding() {
		return 2;
	}

	@Override
	public int getDefaultButtonRoundness() {
		return 5;
	}

	@Override
	public Color getTooltipBGColor() {
		return getBGColor(BackgroundType.background);
	}

	@Override
	public Color getTooltipFontColor() {
		return getFontColor(FontColor.important);
	}
}