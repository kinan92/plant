package boundary;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.swing.ImageIcon;

import controller.Controller;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import java.awt.image.BufferedImage;
/***
 * this Clsass is responsible for creating a JavaFX Pane that contain the Plant image 
 * @author kinan
 */
public class WidgetCreatorJFX extends Pane {

	private BufferedImage plantBufferedImage;
	private BufferedImage potBufferedImage;
	private BufferedImage combinedImage;

	public WidgetCreatorJFX(Controller controller) {

		plantBufferedImage = convertImageIconToBufferedImage(controller.getPlant().getImage());

		potBufferedImage = convertImageIconToBufferedImage(controller.getPlant().getPot());

		combinedImage = mergeAndDrawTheCombinedImages(plantBufferedImage, potBufferedImage);

		// Convert the BufferedImage to a JavaFX Image
		Image fxImage = SwingFXUtils.toFXImage(combinedImage, null);

		// Create an ImageView to display the combined image
		ImageView imageView = new ImageView(fxImage);

		// Display the merged image
		this.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		this.getChildren().add(imageView);

	}


	/**
	 * This method responsible to convert ImageIcon to BufferedImage
	 * 
	 * @author kinan
	 * @param imageIcon
	 * @return BufferedImage
	 */
	private BufferedImage convertImageIconToBufferedImage(ImageIcon imageIcon) {
		BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = bufferedImage.createGraphics();
		imageIcon.paintIcon(null, g, 0, 0);
		g.dispose();
		return bufferedImage;
	}

	/**
	 *  This method responsible for merge the plant and the pot and return a Combined image 
	 * @author kinan
	 * @param plantImage
	 * @param potImage
	 * @return BufferedImage
	 */
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

	/***
	 * This method responsible for create Shape Image
	 * 
	 * @author kinan
	 * @return Path2D
	 */
	public Path2D getImageShape() {
		Path2D pixelShape = new Path2D.Double();
		BufferedImage image = combinedImage;
		// Iterate through the image and create a shape from its alpha channel
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int alpha = (image.getRGB(x, y) >> 24) & 0xFF;
				if (alpha != 0) {
					pixelShape = createPixelShape(x, y);

				}
			}
		}
		return pixelShape;
	}

	/****
	 * This method responsible for create a Pixel shape path representing a single
	 * pixel at the specified coordinates
	 * 
	 * @author kinan
	 * @param double x
	 * @param double y
	 * @return Path2D
	 */
	private Path2D createPixelShape(double x, double y) {
		Path2D path = new Path2D.Double();
		path.moveTo(x, y);
		path.lineTo(x + 1, y);
		path.lineTo(x + 1, y + 1);
		path.lineTo(x, y + 1);
		path.closePath();
		return path;
	}

}
