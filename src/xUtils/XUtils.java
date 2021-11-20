package xUtils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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
}