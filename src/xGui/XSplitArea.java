package xGui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import xThemes.XStyle;
import xThemes.XStyle.BackgroundType;
import xThemes.XStyle.FontColor;
import xThemes.XStyle.FontType;
import xThemes.XTheme;

public class XSplitArea extends XPanel implements ComponentListener, KeyListener, MouseListener {
	
	private static final long serialVersionUID = 3826364068900712464L;

	private XTriplePanel head = new XTriplePanel(new XStyle(BackgroundType.layer2));
	private XPanel body = new XPanel();
	
	private static final List<SplitGetter> splitGetters = new ArrayList<>();
	private static final List<JMenu> menus = new ArrayList<>();
	
	private SplitGetter loadedGetter;
	
	private JComponent component = null;
	
	public XSplitArea(String getterName) {
		this(getterFromString(getterName));
	}
	
	public XSplitArea(SplitGetter getter) {
		super(new BorderLayout(XTheme.getTheme().getPadding(), XTheme.getTheme().getPadding()), XStyle.LAYER2);
		
		XMenuBar menuBar = new XMenuBar();
		
		JMenu viewMenu = new JMenu("View");
		initMenu(viewMenu);
		menus.add(viewMenu);
		
		JMenu windowMenu = new JMenu("Window");
		JMenuItem toWindow = new JMenuItem("Open in new Window");
		toWindow.addActionListener(e -> toWindow());
		windowMenu.add(toWindow);
		
		menuBar.add(viewMenu);
		menuBar.add(windowMenu);
		((FlowLayout) head.getLeft().getLayout()).setHgap(15);
		head.getLeft().add(menuBar);
		
		add(head, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
		
		body.setLayout(new BorderLayout());
		
//		body.setBackground(XTheme.getTheme().getBGColor(BackgroundType.layer1));
		setBorder(new InnerRoundedBorder(4, XTheme.getTheme().getBGColor(BackgroundType.layer2), XTheme.getTheme().getBGColor(BackgroundType.background)));
		
		if(getter != null) {
			load(getter);
		}
		
		addComponentListener(this);
		body.addMouseListener(this);
	}
	
	private void toWindow() {
		if(loadedGetter == null) return;
		XFrame window = new XFrame(loadedGetter.name, getWidth(), getHeight() + 15);// top bar
		window.setDefaultCloseOperation(XFrame.DISPOSE_ON_CLOSE);
		window.getContentPane().setBackground(XTheme.getTheme().getBGColor(BackgroundType.layer2));;
		window.setLocation(getLocationOnScreen());
		window.add(loadedGetter.getComponent());
	}
	
	private static SplitGetter getterFromString(String name) {
		for (SplitGetter getter : splitGetters) {
			if(getter.name.contentEquals(name)) {
				return getter;
			}
		}
		return null;
	}
	
	public void load(String getterName) {
		load(getterFromString(getterName));
	}
	
	private void load(SplitGetter getter) {
		head.getCenter().removeAll();
		head.getCenter().add(head.getCenter().add(new XLabel(getter.name, new XStyle(FontType.h2, FontColor.important, BackgroundType.none))));
		body.removeAll();
		component = getter.getComponent();
		if(component instanceof ComponentListener) {			
			XSplitPanel.compListeners.add((ComponentListener) component);
		}
		body.add(component, BorderLayout.CENTER);
		body.revalidate();
		body.repaint();
		loadedGetter = getter;
	}
	
	private void initMenu(JMenu menu) {
		menus.add(menu);
		menu.removeAll();
		for (SplitGetter getter : splitGetters) {
			JMenuItem item = new JMenuItem(getter.name);// + " (CTRL + " + KeyEvent.getKeyText(getter.ctrlKey) + ")", getter.icon);
			item.addActionListener(e -> load(getter));
			menu.add(item);
		}
	}
	
	public static void addAllSplitGetter(List<SplitGetter> splitGetters) {
		XSplitArea.splitGetters.addAll(splitGetters);
	}
	
	public static void addAllSplitGetter(SplitGetter... splitGetter) {
		for (SplitGetter getter : splitGetter) {
			splitGetters.add(getter);
		}
	}
	
	public static void addSplitGetter(SplitGetter splitGetter) {
		splitGetters.add(splitGetter);
	}
	
	public static void removeSplitGetter(SplitGetter splitGetter) {
		splitGetters.remove(splitGetter);
	}
	
	protected void initEvents() {
		addKeyListener(this);
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
		revalidate();
		repaint();
		addKeyListener(this);
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	public SplitGetter getLoadedGetter() {
		return loadedGetter;
	}
}