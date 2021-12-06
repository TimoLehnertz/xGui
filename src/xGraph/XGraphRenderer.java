package xGraph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

import xGui.XPanel;
import xThemes.XStyle.FontColor;
import xThemes.XTheme;
import xUtils.XUtils;

public class XGraphRenderer extends XPanel {

	private static final long serialVersionUID = 1L;

	private List<XGraph> graphs;
	
	private static Color COLOR_LINE = Color.darkGray;
//	private double minSpan = 3000.0;
	private double maxSpan = 3000.0;
	private boolean keepCenter = false;
	private int lineMargin = 25;

	private double padding = 0.3;//	multiplicator
	
	private double minTime, maxTime, minVal, maxVal;
	private int width, height;
	
	public XGraphRenderer(List<XGraph> graphs) {
		super();
		this.graphs = graphs;
//		setPreferredSize(new Dimension(800, 700));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		/**
		 * setup
		 */
		maxTime = getMaxTime();
		minTime = getMinTime(maxTime);
		minVal = getMinVal(minTime, maxTime);
		maxVal = getMaxVal(minTime, maxTime);
		
		if(keepCenter) {
			double max = Math.max(Math.abs(minVal), Math.abs(maxVal));
			minVal = -max;
			maxVal = max;
		}
		double valRange = maxVal- minVal;
		minVal -= ((valRange * padding) / 2);
		maxVal += ((valRange * padding) / 2);
		width = getWidth();
		height = getHeight();
		
		/**
		 * draw Lines
		 */
		g2.setColor(COLOR_LINE);
		//x lines
		double lines = (width / lineMargin);
		double timeMargin = (maxTime - minTime) / lines;
		double startTime = ((long) (minTime / timeMargin)) * timeMargin;
		if(!Double.isNaN(startTime)) {
			for (int line = 0; line < lines + 2; line++) {
				Point p = getPoint(startTime + line * timeMargin, 0);
				g2.drawLine(p.x, 0, p.x, height);
			}
		}
		//y lines
		lines = (height / lineMargin);
		double valMargin = (maxVal - minVal) / lines;
		double startVal = 0 - (lines / 2) * valMargin;
		startVal = minVal;
		if(!Double.isNaN(startVal)) {
			for (int line = 0; line < lines + 2; line++) {
				double val = startVal + line * valMargin;
				Point p = getPoint(0, val);
				g2.setColor(XTheme.getTheme().getFontColor(FontColor.casual));
				g2.drawString(Math.round(val * 100.0) / 100.0 + "", 0, p.y);
				g2.setColor(COLOR_LINE);
				g2.drawLine(0, p.y, width, p.y);
			}
		}
		/**
		 * draw graphs
		 */
		for (XGraph graph : graphs) {
			if(!graph.isVisible()) continue;
			Point last = new Point(0, height);
			g2.setColor(graph.getColor());
			for (int j = 0; j < graph.getTimes().size() && j < graph.getVals().size(); j++) {
				Double time = graph.getTimes().get(j);
				double val = graph.getVals().get(j);
				if(time < minTime - (maxTime - minTime)) {
					continue;
				}
				Point p = getPoint(time, val);
				g2.drawLine(last.x, last.y, p.x, p.y);
				last = p;
			}
			/**
			 * Last value
			 */
			g2.setColor(XTheme.getTheme().getFontColor(FontColor.casual));
			double lastVal = graph.getVals().get(graph.getVals().size() - 1);
			Point p = getPoint(0, lastVal);
			p.x = getWidth() - 80;
			g2.drawString(lastVal + "", p.x, p.y);
		}
		/**
		 * mouse
		 */
		Point mouse = XUtils.getMouseRelativeTo(this);
		if(XUtils.isMouseInside(this)) {
			g2.setColor(XTheme.getTheme().getFontColor(FontColor.casual));
			double[] vals = getValuesAtScreen(mouse.getX(), mouse.getY());
			g2.drawString("" + Math.round(vals[1] * 100) / 100.0, (int) mouse.getX() + 5, (int) mouse.getY() - 0);
		}
	}
	
	private double[] getValuesAtScreen(double x, double y) {
		double xProgress = x / width;
		double yProgress = 1 - y / height;
		double timeSpan = maxTime - minTime;
		double valSpan = maxVal - minVal;
		double val = minVal + valSpan * yProgress;
		double time = minTime + timeSpan * xProgress;
		double[] res = {time, val};
		return res;
	}
	
	private Point getPoint(double time, double val) {
		double x = ((time - minTime) / (maxTime - minTime)) * width;
		double y = height - ((val - minVal) / (maxVal - minVal) * height);
		return new Point((int) x, (int) y);
	}
	
	private double getMinTime(double maxTime) {
		if(graphs.size() == 0) return 0;
		double min = Double.MAX_VALUE;
		for (XGraph graph : graphs) {
			min = Math.min(min, graph.getMinTime());
		}
		/**
		 * clampening
		 */
//		double absMax = maxTime - minSpan;
//		double absMin = maxTime - maxSpan;
		return maxTime - maxSpan;
//		return Math.max(absMin, Math.min(absMax, min));
//		return min;
	}
	
	private double getMaxTime() {
		if(graphs.size() == 0) return 0;
		double max = graphs.get(0).getMaxTime();
		for (XGraph graph : graphs) {
			max = Math.max(max, graph.getMaxTime());
		}
		return max;
	}
	
	private double getMinVal(double minTime, double maxTime) {
		if(graphs.size() == 0) return 0;
		double min = Double.MAX_VALUE;
		for (XGraph graph : graphs) {
			if(!graph.isVisible()) continue;
			min = Math.min(min, graph.getMinVal(minTime, maxTime));
		}
		return min;
	}
	
	private double getMaxVal(double minTime, double maxTime) {
		if(graphs.size() == 0) return 1;
		double max = Double.MIN_VALUE;
		for (XGraph graph : graphs) {
			if(!graph.isVisible()) continue;
			max = Math.max(max, graph.getMaxVal(minTime, maxTime));
		}
		return max;
	}
}