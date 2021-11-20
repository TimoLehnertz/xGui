package xGui;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import xThemes.XStyle.BackgroundType;

public class XConsole extends XPanel implements ComponentListener {

	private static final long serialVersionUID = 1L;

	public static final String DATE_FORMAT_NOW = "HH:mm:ss.SSS";
	
	private List<LogLine> lines = new ArrayList<>();
	
	private int maxLines = 100;
	
	public XConsole() {
		super();
		setLayout(new BorderLayout());
		setLayout(null);
		addComponentListener(this);
	}
	
	public void clear() {
		lines.clear();
		removeAll();
		revalidate();
		repaint();
	}
	
	public void log(String msg, boolean in) {
		LogLine log = new LogLine(now() + "  |  " + (in ? "<<  " : ">>  ") + msg, false);
		lines.add(log);
		add(log);
		if(lines.size() >= maxLines) {
			remove(lines.get(0));
			lines.remove(0);
		}
		update();
	}
	
	private void update() {
		int used = 0;
		int padding = 2;
		int height = 16;
		boolean zebra = false;
		for (int i = Math.min(maxLines - 1, lines.size() - 1); i >= 0; i--) {
			lines.get(i).setSize(getWidth() + 500, height);
			lines.get(i).setLocation(0, getHeight() - (used + height));
			lines.get(i).setZebra(zebra);
			used += height + padding;
			zebra = !zebra;
		}
	}
	
	public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

	@Override
	public void componentResized(ComponentEvent e) {
		update();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}
	
	public class LogLine extends XLabel {

		private static final long serialVersionUID = 1L;

		public LogLine(String msg, boolean zebra) {
			super(msg);
			setZebra(zebra);
			setOpaque(true);
			setHorizontalAlignment(LEFT);
		}
		
		public void setZebra(boolean zebra) {
			if(zebra) {
				style.backgroundType = BackgroundType.layer1;
			} else {
				style.backgroundType = BackgroundType.layer2;
			}
			updateTheme();
		}
	}
}