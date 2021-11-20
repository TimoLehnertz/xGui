package xGui;

import java.awt.Color;
import java.awt.Component;

import javax.management.RuntimeErrorException;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;

import xThemes.XStyle;
import xThemes.XTheme;
import xThemes.XThemeListener;

public class XMenuBar extends JMenuBar implements XThemeListener {

	private static final long serialVersionUID = -7368810326214531097L;
	
	private Color bg = Color.DARK_GRAY;
	private Color fg = Color.white;
	
	XStyle style;
	
	public XMenuBar() {
		this(new XStyle());
	}
	
	public XMenuBar(XStyle style) {
		super();
		this.style = style;
		XTheme.addXThemeListener(this);
	}
	
	public JComponent getComponent() {
		if(this instanceof JComponent) {			
			return (JComponent) this;
		}
		throw new RuntimeErrorException(null, "Can't have an XTheme Listener on a non JComponent class");
	};
	
	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
		this.fg = fg;
		update();
	}
	
	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		this.bg = bg;
		update();
	}
	
	private void update() {
		for (Component c : getComponents()) {
			if(c instanceof MenuElement) {				
				changeMenuElement((MenuElement) c);
			}
		}
	}

	public void addAll(JMenu... m) {
		for (JMenu jMenu : m) {
			add(jMenu);
		}
	}
	
	@Override
	public JMenu add(JMenu m) {
		changeMenuElement((JMenu) m.getComponent());
		return super.add(m);
	}
	
	private void changeMenuElement(MenuElement menuElement) {
		if(menuElement instanceof JMenuItem)
			updateComponent((JMenuItem) menuElement.getComponent());
		
		if(!(menuElement instanceof JMenu)) return;
		JMenu menu = (JMenu) menuElement;
		updateComponent(menu);
        for (MenuElement popupMenuElement : menu.getSubElements()) {
            JPopupMenu popupMenu = (JPopupMenu) popupMenuElement.getComponent();
            popupMenu.setBorder(null);
            for (MenuElement menuItemElement : popupMenuElement.getSubElements()) {
            	changeMenuElement(menuItemElement);
            }
        }
	}
	
	private void updateComponent(JComponent c) {
		c.setForeground(fg);
		c.setBackground(bg);
        c.setOpaque(true);
        c.setBorder(null);
	}

	@Override
	public void ThemeChanged(XTheme theme) {
	}

	@Override
	public XStyle getStyle() {
		return style;
	}
}