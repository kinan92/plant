package boundary;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.JButton;

public class WidgetShapeButton extends JButton {

	//private JButton button;
	private Shape buttonShape;
	
	

	public WidgetShapeButton(JButton addWaterbutton) {
		super();

	
		 Icon icon = addWaterbutton.getIcon();
        // Convert the icon to a BufferedImage
        BufferedImage image = new BufferedImage(addWaterbutton.getIcon().getIconWidth(), addWaterbutton.getIcon().getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
         icon.paintIcon(addWaterbutton, g2d, 0, 0);
         g2d.dispose();

         buttonShape = ButtonShape(image);
	
		setOpaque(false);
		setBorderPainted(false);

		// Create a shape from the bounds

	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// g2.setColor(getBackground());
	
		g2.fill(buttonShape); // Fill the button shape under the image shape
		// 2.setColor(getForeground());
		//g2.drawString(getText(), 10, 20); // Adjust text position as needed
		g2.dispose();
	}
	
	public Shape ButtonShape(BufferedImage image) {
		
		Area area = new Area();

		// Create a shape from the image's alpha channel
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int alpha = (image.getRGB(x, y) >> 24) & 0xFF;
				if (alpha != 0) {
					area.add(new Area(new Rectangle(x, y, 1, 1)));
				}
			}
		}

		return area;
	}
	
	

	public Shape getButtonShape() {
		return buttonShape;
	}
}
