package xPresets;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import xGui.PresetStep;
import xGui.SplitGetter;
import xGui.XDualPanel;
import xGui.XLabel;
import xGui.XMenuBar;
import xGui.XPanel;
import xGui.XSplitArea;
import xGui.XSplitPanel;
import xLayouts.XBorderLayout;
import xThemes.XStyle;
import xThemes.XStyle.FontColor;
import xThemes.XStyle.FontType;
import xThemes.XTheme;


public abstract class XIDELayoutPanel extends XPanel {

	private static final long serialVersionUID = 1L;

	protected XDualPanel header = new XDualPanel(XStyle.BACKGROUND);
	protected XSplitPanel body;
	protected XDualPanel footer = new XDualPanel(XStyle.BACKGROUND);
	
	XMenuBar menu = new XMenuBar();
	
	protected XLabel versionLabel;
	
	public XIDELayoutPanel(String programVersion) {
		super(new XBorderLayout());
		//header
		initHeaderLeftMenuBar(menu);
		menu.add(getThemeSelectMenu());
		initHeaderRightContent(header.getRight());
		header.getLeft().add(menu);
		
		//body
		XSplitArea.addAllSplitGetter(getSplitGetter());
		body = new XSplitPanel();
		body.loadPreset(getPresetSteps());
		
		// Footer
		versionLabel = new XLabel(programVersion, new XStyle(FontColor.unimportant, FontType.small));
		footer.getRight().add(versionLabel);
		
		add(header, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
		add(footer, BorderLayout.SOUTH);
	}
	
	public abstract List<SplitGetter> getSplitGetter();
	public abstract void initHeaderLeftMenuBar(XMenuBar menubar);
	public abstract void initHeaderRightContent(XPanel panel);
	public abstract PresetStep getPresetSteps();
	
	public static JMenu getThemeSelectMenu() {
		JMenu menu = new JMenu("Themes");
		for (XTheme theme : XTheme.getThemes()) {
			JMenuItem item = new JMenuItem(theme.getName());
			item.setToolTipText(theme.getDescription());
			int size = 10;
			Icon icon = new Icon() {	
				@Override
				public void paintIcon(Component c, Graphics g, int x, int y) {
					g.setColor(theme.getColorLabel());
					g.fillArc(x, y, size, size, 0, 360);
				}
				@Override
				public int getIconWidth() {return size;}
				
				@Override
				public int getIconHeight() {return size;}
			};
			item.setIcon(icon);
			item.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					XTheme.previewTheme(theme);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					XTheme.unpreviewTheme();
				}
			});
			item.addActionListener(e -> {
				XTheme.updateTheme(theme);
			});
			menu.add(item);
		}
		return menu;
	}
	
	public boolean toggleHeader() {
		header.setVisible(!header.isVisible());
		return header.isVisible();
	}
	
	public boolean toggleFooter() {
		footer.setVisible(!footer.isVisible());
		return footer.isVisible();
	}

	public XDualPanel getHeader() {
		return header;
	}

	public XPanel getBody() {
		return body;
	}

	public XDualPanel getFooter() {
		return footer;
	}

	public XMenuBar getMenu() {
		return menu;
	}
}