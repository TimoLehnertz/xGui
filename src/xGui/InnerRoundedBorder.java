package xGui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.border.Border;

public class InnerRoundedBorder  implements Border {

	private int radius;
	private Color outerColor;
	private Color innerColor;

    public InnerRoundedBorder(int radius, Color innerColor, Color outerColor) {
        this.radius = radius;
        this.innerColor = innerColor;
        this.outerColor = outerColor;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(radius,radius,radius,radius);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    	Graphics2D g2 = (Graphics2D) g;
    	g2.setColor(innerColor);
    	g.fillRect(x, y, c.getWidth() + radius, c.getHeight() + radius);
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	g2.setColor(outerColor);
    	g2.setStroke(new BasicStroke(radius * 1));
    	g2.drawRoundRect(x - radius / 3, y - radius / 3, c.getWidth(), c.getHeight(), radius * 3, radius * 3);
    }
}