package xThemes;

import java.awt.Color;
import java.awt.Font;

import xThemes.XStyle.BackgroundType;
import xThemes.XStyle.FontColor;
import xThemes.XStyle.FontType;

public class BlackWidowTheme extends XTheme {
	
	public BlackWidowTheme() {
		super("Black Widow", "Black Widow theme - deeply black with red accents", new Color(131, 26, 25));
	}

	@Override
	public Color getBGColor(BackgroundType type) {
		switch (type) {
		case background: 	return new Color(10, 10, 10);
		case layer1: 		return new Color(30, 30, 30);
		case layer2: 		return new Color(45, 45, 45);
		case foreground: 	return new Color(60, 60, 60);
		case highlight1a: 	return new Color(131, 26, 25);
		case highlight1b: 	return new Color(130, 64,  71);
		case highlight2a: 	return new Color(131, 26, 25);
		case highlight2b: 	return new Color(130, 64,  71);
		case highlight3a: 	return new Color(62, 108, 118);
		case highlight3b: 	return new Color(35, 61, 67);
		case none: return 	null;
		default: return Color.BLACK;
		}
	}

	@Override
	public Color getFontColor(FontColor type) {
		switch (type) {
		case important: return new Color(255, 255, 255);
		case casual: return new Color(205, 205, 205);
		case unimportant: return new Color(170, 170, 170);
		default: return Color.WHITE;
		}
	}

	@Override
	public Font getFont(FontType type) {
		String[] fonts = {"Gilroy", "DejaVu Sans Mono", "Myriad Pro", "Trebuchet MS"};
		final String font = XTheme.getFirstExistingFont(fonts); // First valid gets chosen
		switch (type) {
		case h1: return new Font(font, Font.PLAIN, 15);
		case h2: return new Font(font, Font.PLAIN, 14);
		case h3: return new Font(font, Font.PLAIN, 13);
		case casual: return new Font(font, Font.PLAIN, 12);
		case casualItalic: return new Font(font, Font.BOLD, 12);
		case casualBold: return new Font(font, Font.ITALIC, 12);
		case small: return new Font(font, Font.PLAIN, 11);
		default: return null;
		}
	}

	@Override
	public int getPadding() {
		return 2;
	}

	@Override
	public int getDefaultButtonRoundness() {
		return 15;
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