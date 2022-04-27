package xGraph;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import xGui.XButton;
import xGui.XLabel;
import xGui.XPanel;

public class XGraphViewer extends XPanel {

	private static final long serialVersionUID = 1L;

	private JComboBox<String> combo = new JComboBox<>();
	private XPanel header = new XPanel();
	private XLabel headerLabel = new XLabel("Plotters");
	private List<XPlotter> plotters = new ArrayList<>();
	private XPanel plotterPanel = new XPanel();
	private XButton addButton = new XButton("Add");
	private XButton open3dBtn = new XButton("3D View");
	private XButton record = new XButton("Record");
	
	public XGraphViewer() {
		super();
		setLayout(new BorderLayout());
		add(header, BorderLayout.NORTH);
		add(plotterPanel, BorderLayout.CENTER);
		plotterPanel.setLayout(new GridLayout(3, 1, 0, 2));
		
		initHeader();
	}
	
	private void initHeader() {
		addButton.addActionListener(e -> {
			if(combo.getSelectedItem() != null) {				
				addPlotter(getPlotterOfType((String) combo.getSelectedItem()));
			}
		});
		header.add(headerLabel);
		header.add(combo);
		header.add(addButton);
		header.add(open3dBtn);
		header.add(record);
		open3dBtn.addActionListener(e -> {
			
		});
	}
	
	private void addPlotter(XPlotter p) {
		p.setPreferredSize(new Dimension(500, 0));
		XButton remove = new XButton(XButton.STYLE_FOREGROUND, "delete.png", 15, 15);
		remove.addActionListener(e -> removePlotter(p));
		p.addRemoveButton(remove);
		plotters.add(p);
		plotterPanel.add(p);
		
		plotterPanel.setLayout(new GridLayout(plotters.size(), 1, 0, 2));
		revalidate();
		repaint();
	}
	
	private void removePlotter(XPlotter p) {
		plotters.remove(p);
		plotterPanel.remove(p);
		
		plotterPanel.setLayout(new GridLayout(plotters.size(), 1, 0, 2));
		revalidate();
		repaint();
	}
	
	private boolean sensorListed(String label) {
		for (int i = 0; i < combo.getItemCount(); i++) {
			if(combo.getItemAt(i).contentEquals(label)) {
				return true;
			}
		}
		return false;
	}
	
	private static XPlotter getPlotterOfType(String type) {
		XPlotter p = new XPlotter();
		p.setName(type);
		return p;
	}
	
	public void plot(String sensorName, String sensorSubType, double val) {
		if(!sensorListed(sensorName)) {
			combo.addItem(sensorName);
		}

		for (XPlotter plotter : plotters) {
			if(plotter.getName().contentEquals(sensorName)) {
				plotter.plott(sensorSubType, val);
			}
		}
	}
}