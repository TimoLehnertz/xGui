package xGui;

import java.awt.Color;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;

import xThemes.XStyle;
import xThemes.XStyle.BackgroundType;
import xUtils.XUtils;
import xThemes.XTheme;
import xThemes.XThemeListener;

public class XFrame extends JFrame implements XThemeListener {

	private static final long serialVersionUID = 1L;
	public static String LOGO;
	
	public XFrame(String name, String logoImg, int width, int height) {
		super(name);
		if(logoImg != null && logoImg.length() > 0) {			
			setIconImage(XUtils.getImage(logoImg).getImage());
			LOGO = logoImg;
		}
		Locale.setDefault(Locale.Category.FORMAT, Locale.ENGLISH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, width, height);
		setLocationRelativeTo(null);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		getContentPane().setBackground(new Color(150,150,150));
		setVisible(true);
		
		long start = System.currentTimeMillis() + 200;
		
		Timer t = new Timer(15, e -> {
			long now = System.currentTimeMillis();
			double duration = 600;
			double progress = Math.max(0, Math.min(1, (now - start) / duration));
			if(progress > 1) {
				((Timer) e.getSource()).stop();
				getContentPane().setBackground(XTheme.getTheme().getBGColor(BackgroundType.background));
				return;
			}
			progress = 1 - progress;
			getContentPane().setBackground(new Color((int) (progress * 120 + 30), (int) (progress * 120 + 30), (int) (progress * 120 + 30)));
		});
		t.start();
		
		XTheme.addXThemeListener(this);
	}

	@Override
	public void ThemeChanged(XTheme theme) {
		getContentPane().setBackground(XTheme.getTheme().getBGColor(BackgroundType.background));
	}

	@Override
	public XStyle getStyle() {
		return null;
	}
}