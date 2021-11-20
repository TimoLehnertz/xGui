package xGui;

import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import xThemes.XStyle;
import xThemes.XStyle.BackgroundType;
import xThemes.XTheme;

public class XSplitPanel extends XPanel implements ComponentListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	
	static final List<ComponentListener> compListeners = new ArrayList<>();
	
	private JComponent right;
	private XSplitPanel left = null;
	
	public static Map<String, JComponentSupplier> contentsMap;
	
	private double splitPercentage = 0;
	
	private boolean splitX = true;
	
	private Timer t = new Timer(10, e -> drag());
	
	private int width;
	private int height;
	
	private XSplitPanel parent;
	
	private boolean onlyMove = false;
	
	private Point mouseDown; // can be null
	
	private int recursion = 0;
	
	private int addSize = 20;
	
	public XSplitPanel() {
		this(null, null);
	}
	
	private XSplitPanel(JComponent content, SplitGetter getter) {
		super(XStyle.BACKGROUND);
		setLayout(null);
		if(content == null) {
			right = new XSplitArea(getter);
		} else {
			right = content;
		}
		
		add(right);
		
		right.setBounds(0, 0, width, height);
		
		initEvents();
		
		width = getWidth();
		height = getHeight();
		setBounds(0,0, width, height);
		updateScale(width, height);
		
		setBackground(XTheme.getTheme().getBGColor(BackgroundType.background));
		
		Timer t = new Timer(10, e -> {
			revalidate();
			repaint();
			((Timer) e.getSource()).stop();
		});
		t.start();
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	private void startDrag() {
		t.start();
	}
	
	private void endDrag() {
		t.stop();
		if(parent != null) {
			parent.endDrag();
		}
		revalidate();
		repaint();
	}
	
	public void loadPreset(PresetStep step) {
		loadPreset(step, true);
	}
	
	public void loadPreset(PresetStep step, boolean first) {
		die();
		if(first) {
			remove(right);
			right = new XSplitArea(step.name);
			add(right);
		} else {
			addSplit();
			endDrag();
			splitPercentage = step.splitPercentage;
			splitX = step.splitX;
			((XSplitArea)((XSplitPanel) right).right).load(step.name);
		}
		if(step.rightContent != null) {			
			if(first) {
				loadPreset(step.rightContent, false);
			} else {
				((XSplitPanel) right).loadPreset(step.rightContent, false);
			}
		};
		if(step.leftContent != null) {
			left.loadPreset(step.leftContent);
		}
		updateScale(width, height);
		revalidate();
		repaint();
	}
	
	private JFrame getFrame() {
		return (JFrame) SwingUtilities.getWindowAncestor(this);
	}

	private void drag() {
		Point m = getMousePos();
		if(getPointLength(m) < addSize && !onlyMove) {
			endDrag();
			removeSplit();
			return;
		}
		
		if(!onlyMove) {
			splitX = m.x > m.y;
		}
		if(splitX) {
			splitPercentage =  m.x / (double) width;
		} else {
			splitPercentage = m.y / (double) height;
		}
		splitPercentage = Math.min(splitPercentage, 0.9);
		splitPercentage = Math.max(splitPercentage, 0.1);
		if(onlyMove && (splitX ? m.x : m.y) <= 0) {
			splitPercentage = 0;
		}
		updateScale(width, height);
	}
	
	private Point getMousePos() {
		try {			
			Point mouse = MouseInfo.getPointerInfo().getLocation();
			Point offset = getLocationOnScreen();
			return new Point(mouse.x - offset.x, mouse.y - offset.y);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void initEvents() {
		removeComponentListener(this);
		removeMouseListener(this);
		removeMouseMotionListener(this);
		
		addComponentListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	private boolean isParentDragging() {
		if(parent != null) {
			return parent.isDragging();
		}
		return false;
	}
	
	private boolean isChildDragging() {
		boolean dragging = false;
		if(left != null) {
			dragging |= left.t.isRunning() || left.isChildDragging();
		}
		if(right instanceof XSplitPanel) {
			XSplitPanel rightCast = (XSplitPanel) right;
			dragging |= rightCast.t.isRunning() || rightCast.isChildDragging();
		}
		return dragging;
	}
	
	private boolean isDragging() {
		if(t.isRunning()) return true;
		boolean isDragging = false;
		if(parent != null) {
			isDragging = parent.isDragging();
		}
		isDragging |= isChildDragging();
		return isDragging;
	}

	private boolean addSplit() {
		if(isParentDragging()) return false;
		if(right instanceof XSplitPanel && ((XSplitPanel) right).left != null) {
			if(((XSplitPanel) right).left.addSplit()) {
				return true;
			}
		}
		if(left == null) {
			onlyMove = false;
			remove(right);
			SplitGetter getter = null;
			if(right instanceof XSplitArea) {
				getter = ((XSplitArea)right).getLoadedGetter();
			}
			right = new XSplitPanel(right, null);
			((XSplitPanel)right).parent = this;
			((XSplitPanel)right).recursion = recursion++;
			add(right);
			((XSplitPanel)right).initEvents();
			left = new XSplitPanel(null, getter);
			left.parent = this;
			left.initEvents();
			add(left);
			startDrag();
			return true;
		}
		return false;
	}
	
	private void removeSplit() {
		if(left != null && right instanceof XSplitPanel) {
			splitPercentage = 0;
			remove(left);
			left.die();
			left = null;
			updateScale(width, height);
		}
	}
	
	
	private int getWidthForChild(boolean right) {
		if(right) {
			if(splitX) {
				return (int) ((1 - splitPercentage) * width);
			} else {
				return width; 
			}
		} else {
			return splitX ? (int) (splitPercentage * width) : width;
		}
	}
	
	private int getHeightForChild(boolean right) {
		if(right) {
			if(splitX) {
				return height;
			} else {
				return (int) ((1 - splitPercentage) * height); 
			}
		} else {
			return splitX ? height : (int) (splitPercentage * height);
		}
	}

	private void updateScale(int width, int height) {
		int padding = XTheme.getTheme().getPadding();
		if(splitX) {
			right.setLocation((int) (width * splitPercentage) + padding, 0);
			right.setSize((int) (width * (1 - splitPercentage)) - padding, height);
		} else {
			right.setLocation(0, (int) (height * splitPercentage) + padding);
			right.setSize(width, (int) (height * (1 - splitPercentage)) - padding);
		}
		if(left != null) {
			if(splitX) {
//				left.setBounds(0, 0, (int) (width * splitPercentage), height);
				left.setSize((int) (width * splitPercentage), height);
			} else {
//				left.setBounds(0, 0, width, (int) (height * splitPercentage));
				left.setSize(width, (int) (height * splitPercentage));
			}
		}
		if(right instanceof XSplitPanel) {
			((XSplitPanel) right).updateScale(getWidthForChild(true), getHeightForChild(true));
		}
		if(left != null) {
			left.updateScale(getWidthForChild(false), getHeightForChild(false));
		}
		revalidate();
		repaint();
		for (ComponentListener componentListener : compListeners) {
			componentListener.componentResized(null);
		}
	}
	
	private static double getPointLength(Point p) {
		return Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY());
	}
	
	private void mousePressedPrivate(MouseEvent e) {
		mouseDown = getMousePos();
		if(parent != null) {
			parent.mousePressedPrivate(e);
		}
	}
	
//	private boolean atOwnAdd() {
//		Point mouse = getMousePos();
//		return mouse.getX() < addSize && mouse.getY() < addSize;
//	}
	
	private boolean atAdd() {
		Point mouse = getMousePos();
		if(left == null) return getPointLength(mouse) < addSize;
		boolean atAdd;
		double line = splitPercentage * (splitX ? width : height);
		if(splitX) {
			atAdd = Math.abs(mouse.x - line) < addSize && mouse.y < addSize;
		} else {
			atAdd = Math.abs(mouse.y - line) < addSize && mouse.x < addSize;
		}
		atAdd |= left.atAdd();
		if(right instanceof XSplitPanel) {
			atAdd |= ((XSplitPanel) right).atAdd();
		}
		return atAdd;
	}
	
	private boolean atSlide() {
		if(left == null) return false;
		Point mouse = getMousePos();
		int distance = 10;
		double line = splitPercentage * (splitX ? width : height);
		int x = splitX ? mouse.x : mouse.y;
		boolean atAdd = atAdd();
		return x + distance > line && x - distance < line && !atAdd;
	}
	
	private void die() {
		if(left != null) left.die();
		if(right instanceof XSplitPanel) ((XSplitPanel) right).die();
		removeSplit();
	}
	
	private void mouseDraggedPrivate(MouseEvent e) {
		if(isDragging()) return;
		Point mouse = getMousePos();
		if(mouse.x < 0 || mouse.y < 0) return;
		
		if(left == null && mouseDown != null) {
			if(getPointLength(mouseDown) < addSize && getPointLength(new Point(mouse.x, mouse.y)) > addSize) {
				addSplit();
				return;
			}
		}

		if(left != null && atSlide()) {
			onlyMove = true;
			startDrag();
		}
		if(parent != null) {
			parent.mouseDraggedPrivate(e);
		}
	}
	
	private boolean mouseAtSlide = false;
	private boolean lastMouseAtSlide = false;
	
	private boolean mouseAtAdd = false;
	private boolean lastMouseAtAdd = false;
	
	private void mouseMovedPrivate(MouseEvent e) {
		int cursor = Cursor.DEFAULT_CURSOR;
		mouseAtSlide = false;
		mouseAtAdd = false;
		if(atAdd()) {
			cursor = Cursor.CROSSHAIR_CURSOR;
		} else if(atSlide()){
			mouseAtSlide = true;
			cursor = splitX ? Cursor.E_RESIZE_CURSOR : Cursor.N_RESIZE_CURSOR;
			repaint();
		} else {
			cursor = Cursor.DEFAULT_CURSOR;
		}
		getFrame().setCursor(new Cursor(cursor));
		if(cursor == Cursor.DEFAULT_CURSOR && parent != null) {
			parent.mouseMovedPrivate(e);
		}
		if(lastMouseAtSlide != mouseAtSlide) {
			lastMouseAtSlide = mouseAtSlide;
			repaint();
		}
		if(mouseAtAdd != lastMouseAtAdd) {
			lastMouseAtAdd = mouseAtAdd;
			repaint();
		}
	}
	
	private void mouseReleasedPrivate(MouseEvent e) {
		endDrag();
		if(splitPercentage == 0) {
			removeSplit();
		}
		if(parent != null) {
			parent.mouseReleased(e);
		}
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		width = getWidth();
		height = getHeight();
		updateScale(width, height);
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
		width = getWidth();
		height = getHeight();
		updateScale(width, height);
		revalidate();
		repaint();
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		endDrag();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		mousePressedPrivate(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseReleasedPrivate(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		repaint();
		getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseDraggedPrivate(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseMovedPrivate(e);
	}
}