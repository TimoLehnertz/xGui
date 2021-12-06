package xUtils;

import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class XUtils {

	public static ImageIcon getImage(String name) {
		return getImage(name, -1, -1);
	}
	
	/**
	 * Put images in a src folder called img
	 * @param name File name
	 * @param width width for resizing
	 * @param height height for resizing
	 * @return ImageIcon
	 */
	public static ImageIcon getImage(String name, int width, int height) {
		if(!name.contains("img")) {
			name = "img/" + name;
		}
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(name));
		} catch (IOException e) {
		    e.printStackTrace();
		    return new ImageIcon();
		}
		if(width < 0) {
			width = img.getWidth();
			height = img.getHeight();
		}
		Image dimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(dimg);;
	    return icon;
	}
	
	public static Point getMouseRelativeTo(JComponent c) {
		try {			
			Point mouse = MouseInfo.getPointerInfo().getLocation();
			Point offset = c.getLocationOnScreen();
			return new Point(mouse.x - offset.x, mouse.y - offset.y);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean isMouseInside(JComponent c) {
		Point m = getMouseRelativeTo(c);
		return m.getX() >= 0 && m.getY() >= 0 && m.getX() <= c.getWidth() && m.getY() <= c.getHeight();
	}
}