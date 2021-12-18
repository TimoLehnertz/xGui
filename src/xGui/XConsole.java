package xGui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;

import xThemes.XStyle.BackgroundType;

public abstract class XConsole extends XPanel implements ComponentListener {

	private static final long serialVersionUID = 1L;

	public static final String DATE_FORMAT_NOW = "HH:mm:ss.SSS";
	
	private List<LogLine> lines = new ArrayList<>();
	
	private int maxLines = 100;
	
	private XPanel body = new XPanel();
	private XPanel haed = new XPanel();
	
	public XConsole() {
		super();
		setLayout(new BorderLayout());
		add(haed, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
		body.setLayout(null);
		XButton sendBtn = new XButton("Send");
		JTextField textField = new JTextField();
		textField.setColumns(20);
		haed.add(textField);
		haed.add(sendBtn);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String msg = textField.getText();
					textField.setText("");
					send(msg + "\n");
					log(msg, true);
				}
				super.keyReleased(e);
			}
		});
		sendBtn.addActionListener(e -> {
			String msg = textField.getText();
			textField.setText("");
			send(msg + "\n");
			log(msg, true);
		});
		addComponentListener(this);
	}
	
	public abstract void send(String msg);
	
	public void clear() {
		lines.clear();
		body.removeAll();
		revalidate();
		repaint();
	}
	
	/**
	 * 
	 * @param msg
	 * @param in
	 */
	public void log(String msg, boolean in) {
		LogLine log = new LogLine(now() + "  |  " + (in ? "<<  " : ">>  ") + msg, false);
		log.copyString = msg;
		lines.add(log);
		body.add(log);
		if(lines.size() >= maxLines) {
			body.remove(lines.get(0));
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
			lines.get(i).setSize(body.getWidth() + 500, height);
			lines.get(i).setLocation(0, body.getHeight() - (used + height));
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
	
	public class LogLine extends XLabel implements MouseListener {

		private static final long serialVersionUID = 1L;
		public String copyString = null;
		
		public LogLine(String msg, boolean zebra) {
			super(msg);
			setZebra(zebra);
			setOpaque(true);
			setHorizontalAlignment(LEFT);
			addMouseListener(this);
		}
		
		public void setZebra(boolean zebra) {
			if(zebra) {
				style.backgroundType = BackgroundType.layer1;
			} else {
				style.backgroundType = BackgroundType.layer2;
			}
			updateTheme();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("Coppied to clipboard");
			String myString;
			if(copyString != null) {
				myString = copyString;
			} else {
				myString = getText();
			}
			StringSelection stringSelection = new StringSelection(myString);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}