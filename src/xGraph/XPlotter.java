package xGraph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import xGui.XButton;
import xGui.XLabel;
import xGui.XPanel;

public class XPlotter extends XPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private List<XGraph> graphs = new ArrayList<>();
	private XGraphRenderer renderer = new XGraphRenderer(graphs);
	private boolean changed = true;
	private Timer t = new Timer(1000 / 30, this);
	private XPanel legend = new XPanel();
	private XPanel header = new XPanel();
	private XLabel name = new XLabel("Plotter");
	private String requiredString = null;
	
	public XPlotter() {
		super();
		setLayout(new BorderLayout());
		
		legend.setLayout(new GridLayout(4, 1));
		setBackground(new Color(15,15,15));
		legend.setOpaque(false);
		renderer.setOpaque(false);
		legend.setPreferredSize(new Dimension(50, 0));
		revalidate();
		repaint();
		header.add(name);
		add(header, BorderLayout.NORTH);
		add(legend, BorderLayout.WEST);
		add(renderer, BorderLayout.CENTER);
		t.start();
	}
	
	public void addRemoveButton(XButton b) {
		header.add(b);
	}
	
	public void plott(String label, double value) {
		plott(label, value, System.currentTimeMillis());
	}
	
	private void addGraph(XGraph graph) {
		graphs.add(graph);
		XPanel p = new XPanel();
		XButton b = new XButton(graph.getLabel(), XButton.STYLE_CUSTOM);
		b.setBackground(graph.getColor());
		b.updateTheme();
		b.addActionListener(e -> {
			boolean t = graph.toggle();
			b.setBackground(t ? graph.getColor() : Color.LIGHT_GRAY);
			b.updateTheme();
		});
		
		p.add(b);
		p.setOpaque(false);
		legend.add(p);
		legend.setLayout(new GridLayout(legend.getComponentCount(), 1));
		revalidate();
		repaint();
	}
	
	public void plott(String label, double value, double time) {
		if(requiredString != null) {
			if(!label.contains(requiredString)) {
				return;
			}
		}
		XGraph graph = getGraph(label);
		if(graph == null) {
			graph = new XGraph(label);
			addGraph(graph);
		}
		graph.put(time, value);
		changed = true;
	}
	
	private XGraph getGraph(String label) {
		for (XGraph graph : graphs) {
			if(graph.getLabel().contentEquals(label)) {
				return graph;
			}
		}
		return null;
	}
	
	public String getName() {
		return name.getText();
	}
	
	public void setName(String name) {
		this.name.setText(name);
	}

	public String getRequiredString() {
		return requiredString;
	}

	public void setRequiredString(String requiredString) {
		this.requiredString = requiredString;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(changed) {
			renderer.repaint();
			changed = false;
		}
	}
}