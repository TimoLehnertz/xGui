package xGui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import xThemes.XStyle;
import xThemes.XStyle.BackgroundType;
import xThemes.XStyle.ColorFilter;
import xThemes.XStyle.FontColor;
import xUtils.XUtils;
import xThemes.XTheme;
import xThemes.XThemeListener;

public class XButton extends JPanel implements XThemeListener, MouseListener{

	private static final long serialVersionUID = -1343181121326671295L;

	private XStyle styleDefault;
	private XStyle styleHovered;
	private XStyle styleClicked;
	
	public Color background = Color.black;
	
	public static final int STYLE_FOREGROUND = 0;
	public static final int STYLE_HIGHLIGHT1 = 1;
	public static final int STYLE_HIGHLIGHT2 = 2;
	public static final int STYLE_HIGHLIGHT3 = 3;
	public static final int STYLE_CUSTOM = 4;
	
	private boolean mouseDown = false;
	private boolean mouseInside = false;
	
	private List<ActionListener> actionListeners = new ArrayList<>();
	
	private JButton button;
	
	//String Constructor
	public XButton() {
		this("");
	}
	
	//String Constructor
	public XButton(String text) {
		this(text, STYLE_FOREGROUND);
	}
	
	//String Constructor
	public XButton(String text, int style) {
		this(text, style, null, -1, -1);
	}
	
	//Image Constructor
	public XButton(String img, int width, int height) {
		this(STYLE_FOREGROUND, img, width, height);
	}
	
	//Image Constructor
	public XButton(int style, String img, int width, int height) {
		this(null, style, img, width, height);
	}
	
	//Constructor
	public XButton(String text, int style, String img, int width, int height) {
		super();
		if(img != null) {
			Icon icon = XUtils.getImage(img, width, height);
			button = new JButton(icon);
		} else {
			button = new JButton(text);
		}

		add(button);
		initStyle(style);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setOpaque(false);
		XTheme.addXThemeListener(this);
		button.addMouseListener(this);
	}
	
	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		background = bg;
	}
	
	private void initStyle(int style) {
		switch(style) {
		case STYLE_FOREGROUND:
			styleDefault = new XStyle(BackgroundType.foreground, ColorFilter.none,		FontColor.casual, ColorFilter.none);
			styleHovered = new XStyle(BackgroundType.highlight2b, ColorFilter.darker,		FontColor.casual, ColorFilter.brighter);
			styleClicked = new XStyle(BackgroundType.highlight2b, ColorFilter.bright,	FontColor.casual, ColorFilter.bright);
			break;
		case STYLE_HIGHLIGHT1:
			styleDefault = new XStyle(BackgroundType.highlight1b, ColorFilter.none,		FontColor.casual, ColorFilter.none);
			styleHovered = new XStyle(BackgroundType.highlight1b, ColorFilter.bright,	FontColor.casual, ColorFilter.bright);
			styleClicked = new XStyle(BackgroundType.highlight1b, ColorFilter.dark,		FontColor.casual, ColorFilter.bright);
			break;
		case STYLE_HIGHLIGHT2:
			styleDefault = new XStyle(BackgroundType.highlight2b, ColorFilter.none,		FontColor.casual, ColorFilter.none);
			styleHovered = new XStyle(BackgroundType.highlight2b, ColorFilter.bright,	FontColor.casual, ColorFilter.bright);
			styleClicked = new XStyle(BackgroundType.highlight2b, ColorFilter.dark,		FontColor.casual, ColorFilter.bright);
			break;
		case STYLE_HIGHLIGHT3:
			styleDefault = new XStyle(BackgroundType.highlight3b, ColorFilter.none,		FontColor.casual, ColorFilter.none);
			styleHovered = new XStyle(BackgroundType.highlight3b, ColorFilter.bright,	FontColor.casual, ColorFilter.bright);
			styleClicked = new XStyle(BackgroundType.highlight3b, ColorFilter.dark,		FontColor.casual, ColorFilter.bright);
			break;
		case STYLE_CUSTOM:
			styleDefault = new XStyle(BackgroundType.custom,	  ColorFilter.none,		FontColor.casual, ColorFilter.none);
			styleHovered = new XStyle(BackgroundType.custom, 	  ColorFilter.bright,	FontColor.casual, ColorFilter.brighter);
			styleClicked = new XStyle(BackgroundType.custom, 	  ColorFilter.dark,		FontColor.casual, ColorFilter.bright);
			break;
		}
	}

	public void updateTheme() {
		XTheme.updateStyleOn(this, styleDefault);
	}
	
	public void addActionListener(ActionListener e) {
		actionListeners.add(e);
	}
	
	public void removeActionListener(ActionListener e) {
		actionListeners.remove(e);
	}
	
	public void setEnabled(boolean enabled) {
		button.setEnabled(enabled);
	}
	
	public String getText() {
		return button.getText();
	}
	
	public void setText(String text) {
		button.setText(text);
	}
	
	@Override
	public void setToolTipText(String text) {
		button.setToolTipText(text);
		super.setToolTipText(text);
	}
	
	@Override
	public void setForeground(Color fg) {
		if(button != null) {			
			button.setForeground(fg);
		}
		super.setForeground(fg);
	}
	
	private JFrame getFrame() {
		return (JFrame) SwingUtilities.getWindowAncestor(this);
	}

	@Override
	public void ThemeChanged(XTheme theme) {
	}

	@Override
	public XStyle getStyle() {
		return styleDefault;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (ActionListener actionListener : actionListeners) {
			actionListener.actionPerformed(new ActionEvent(button, 0, "Sus"));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
		XTheme.updateStyleOn(this, styleClicked);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
		if(mouseInside) {			
			XTheme.updateStyleOn(this, styleHovered);
		} else {
			XTheme.updateStyleOn(this, styleDefault);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseInside = true;
		if(!mouseDown) {			
			XTheme.updateStyleOn(this, styleHovered);
		}
		getFrame().setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseInside = false;
		if(!mouseDown) {
			XTheme.updateStyleOn(this, styleDefault);
		}
	}
}