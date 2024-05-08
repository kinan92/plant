package boundary;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;


public class WidgetCreatorJFX extends Pane {
	private BufferedImage plantBufferedImage;
	private BufferedImage potBufferedImage;
	private BufferedImage combinedImage;
	

	public WidgetCreatorJFX(ImageIcon plantImageIcon, ImageIcon potImageIcon) {
		// Load plant and pot images
		System.out.println("plant "+plantImageIcon) ;

		plantBufferedImage =convertImageIconToBufferedImage(plantImageIcon);
		
		potBufferedImage = convertImageIconToBufferedImage(potImageIcon);
		
		combinedImage= mergeAndDrawTheCombinedImages(plantBufferedImage,potBufferedImage);
	
		 // Convert the BufferedImage to a JavaFX Image
		Image fxImage = SwingFXUtils.toFXImage(combinedImage, null);
		
		 // Create an ImageView to display the combined image
        ImageView imageView = new ImageView(fxImage);
        

		// Display the merged image
		//this.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		this.getChildren().add(imageView);
	}


	public BufferedImage mergeAndDrawTheCombinedImages(BufferedImage plantImage, BufferedImage potImage) {
	    // Determine the size of the combined image
	    int combinedWidth = Math.max(plantImage.getWidth(), potImage.getWidth());
	    int combinedHeight = Math.max(plantImage.getHeight(), potImage.getHeight());
	    // Create a new BufferedImage for the combined image
	    BufferedImage combinedImage = new BufferedImage(combinedWidth, combinedHeight, BufferedImage.TYPE_INT_ARGB);
	    // Create a Graphics2D object to draw on the combined image
	    Graphics2D g2d = combinedImage.createGraphics();
	    // Draw the pot image on bottom
	    int potX = 0;
	    int potY = 0;
	    g2d.drawImage(potImage, potX, potY, null);
	    
	    // Draw the plant image first
	    int plantX = (combinedWidth - plantImage.getWidth()) / 2;
	    int plantY = (combinedHeight - potImage.getHeight()) / 2;
	    g2d.drawImage(plantImage, plantX, plantY, null);


	    // Dispose of the Graphics2D object
	    g2d.dispose();

	    // Return the combined image
	    return combinedImage;
	}
	


	// Method to convert ImageIcon to BufferedImage
    private BufferedImage convertImageIconToBufferedImage(ImageIcon imageIcon) {
        BufferedImage bufferedImage = new BufferedImage(
                imageIcon.getIconWidth(),
                imageIcon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics g = bufferedImage.createGraphics();
        imageIcon.paintIcon(null, g, 0, 0);
        g.dispose();
        return bufferedImage;
    }
    
    
    // Method to create a shape based on the image's alpha,
    //channel and it add the Shape to the JFrame in the WidgetFX class
    public Area getImageShape() {
    	BufferedImage image=combinedImage;
        Area area = new Area();

        // Create a shape from the image's alpha channel
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int alpha = (image.getRGB(x, y) >> 24) & 0xFF;
                if (alpha != 0) {
                    area.add(new Area( new Rectangle(x, y, 1, 1)));
                }
            }
        }

        return area;
    }

}
