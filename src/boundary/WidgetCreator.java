package boundary;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class WidgetCreator extends JPanel {
	private BufferedImage plantImage;
	private BufferedImage potImage;
	private BufferedImage combinedImage;
	private Shape theMergedImage;
	

	
	public WidgetCreator(String plantImagePath, String potImagePath) {
		super();
		// Load plant and pot images
		try {
			plantImage = ImageIO.read(new File( plantImagePath ));
			potImage = ImageIO.read(new File( potImagePath ));
			
				int width=plantImage.getWidth();
				int height = plantImage.getHeight();
			
				
			combinedImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = combinedImage.createGraphics();
			paint(g2d);
			theMergedImage=getImageShape(combinedImage);
			
			g2d.dispose();
	
			setBounds(0, 0, combinedImage.getWidth(), combinedImage.getHeight());
			
			//setOpaque(false);
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}






	// Method to create a shape based on the image's
	public Shape getImageShape(BufferedImage image) {
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

	// this method is to marge the plant and the pot
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		// Draw pot image
		int potX = 0; // Adjust position as needed
		int potY = 0; // Adjust position as needed
		g.drawImage(potImage, potX, potY, this);

		// Draw plant image
		int plantX = potX + (potImage.getWidth() - plantImage.getWidth()) / 2;
		int plantY = potY + (potImage.getHeight() - plantImage.getHeight()) / 2;
		g.drawImage(plantImage, plantX, plantY, this);

	}
	
	public BufferedImage getCombinedImage() {
		return combinedImage;
	}

	public Shape getTheMergedImage() {
		return theMergedImage;
	}

}
