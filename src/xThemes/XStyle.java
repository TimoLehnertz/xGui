package xThemes;

public class XStyle {

	public static final XStyle TRANSPARENT = new XStyle(BackgroundType.none);
	public static final XStyle BACKGROUND = new XStyle(BackgroundType.background);
	public static final XStyle LAYER1 = new XStyle(BackgroundType.layer1);
	public static final XStyle LAYER2 = new XStyle(BackgroundType.layer2);
	public static final XStyle FORGROUND = new XStyle(BackgroundType.foreground);
	
	public static final int ROUNDNESS_DEFAULT = -1;
	
	public enum BackgroundType {
		background, layer1, layer2, foreground, highlight1a, highlight1b, highlight2a, highlight2b, highlight3a, highlight3b, none, custom
	}
	
	public enum ColorFilter {
		none, brighter, bright, darker, dark 
	}
	
	public enum FontColor {
		important, casual, unimportant
	}

	public enum FontType {
		h1, h2, h3, casual, casualItalic, casualBold, small
	}
	
	public BackgroundType backgroundType = BackgroundType.background;
	public ColorFilter backgroundFilter = ColorFilter.none;
	public FontColor fontColor = FontColor.casual;
	public ColorFilter fontColorFilter = ColorFilter.none;
	public FontType fontType = FontType.casual;
	public int buttonRoundness = ROUNDNESS_DEFAULT;//Theme default used if < 0
	
	public XStyle() {
		super();
	}
	
	public XStyle(BackgroundType backgroundType) {
		super();
		this.backgroundType = backgroundType;
	}
	
	public XStyle(BackgroundType backgroundType, FontColor fontColor) {
		super();
		this.backgroundType = backgroundType;
		this.fontColor = fontColor;
	}
	
	public XStyle(BackgroundType backgroundType, ColorFilter backgroundFilter, FontColor fontColor) {
		super();
		this.backgroundType = backgroundType;
		this.backgroundFilter = backgroundFilter;
		this.fontColor = fontColor;
	}
	
	public XStyle(BackgroundType backgroundType, ColorFilter backgroundFilter) {
		super();
		this.backgroundType = backgroundType;
		this.backgroundFilter = backgroundFilter;
	}
	
	public XStyle(BackgroundType backgroundType, ColorFilter backgroundFilter, FontColor fontColor, ColorFilter fontColorFilter) {
		super();
		this.backgroundType = backgroundType;
		this.backgroundFilter = backgroundFilter;
		this.fontColor = fontColor;
		this.fontColorFilter = fontColorFilter;
	}
	
	public XStyle(FontColor fontColor) {
		super();
		this.fontColor = fontColor;
	}
	
	public XStyle(FontType fontType) {
		super();
		this.fontType = fontType;
	}
	
	public XStyle(FontType fontType, FontColor fontColor) {
		super();
		this.fontType = fontType;
		this.fontColor = fontColor;
	}
	
	public XStyle(FontColor fontColor, FontType fontType) {
		super();
		this.fontColor = fontColor;
		this.fontType = fontType;
	}
	
	public XStyle(FontColor fontColor, FontType fontType, BackgroundType backgroundType) {
		super();
		this.fontColor = fontColor;
		this.fontType = fontType;
		this.backgroundType = backgroundType;
	}
	
	public XStyle(FontType fontType, FontColor fontColor, BackgroundType backgroundType) {
		super();
		this.fontType = fontType;
		this.fontColor = fontColor;
		this.backgroundType = backgroundType;
	}
	
	public XStyle(ColorFilter backgroundFilter) {
		super();
		this.backgroundFilter = backgroundFilter;
	}
	
	public XStyle(FontColor fontColor, ColorFilter fontColorFilter) {
		super();
		this.fontColor = fontColor;
		this.fontColorFilter = fontColorFilter;
	}
	
	public XStyle(BackgroundType backgroundType, ColorFilter backgroundFilter, FontColor fontColor, ColorFilter fontColorFilter, FontType fontType) {
		super();
		this.backgroundType = backgroundType;
		this.backgroundFilter = backgroundFilter;
		this.fontColor = fontColor;
		this.fontColorFilter = fontColorFilter; 
		this.fontType = fontType;
	}
}