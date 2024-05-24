package boundary.Widget;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.swing.ImageIcon;

import controller.Controller;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

/***
 * this Clsass is responsible for creating a JavaFX Pane that contain the Plant
 * image
 * 
 * @author kinan
 */
public class WidgetCreatorJFX extends Pane {

	private BufferedImage plantBufferedImage;
	private BufferedImage potBufferedImage;
	private BufferedImage combinedImage;
	private ImageView imageView;

	public WidgetCreatorJFX(Controller controller) {

		plantBufferedImage = convertImageIconToBufferedImage(controller.getCurrentPlant().getImage());

		potBufferedImage = convertImageIconToBufferedImage(controller.getCurrentPlant().getPot());

		combinedImage = mergeAndDrawTheCombinedImages(plantBufferedImage, potBufferedImage);

		// Convert the BufferedImage to a JavaFX Image
		Image fxImage = SwingFXUtils.toFXImage(combinedImage, null);

		// Create an ImageView to display the combined image
		imageView = new ImageView(fxImage);

		// Display the merged image
		this.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		this.getChildren().add(imageView);

	}

	/**
	 * Default constructor used to access the class' methods without the above code execution
	 * used for Storage to create buttons
	 * @author Petri Närhi
	 * */
	public WidgetCreatorJFX() {}

	/**
	 * This method responsible for update the plant image and add the image to the existing image
	 * @param newPlantImage
	 * @author kinan
	 */
	public void updatePlantImage(ImageIcon newPlantImage) {
		this.plantBufferedImage = convertImageIconToBufferedImage(newPlantImage);
		refreshImageView();
	}
	
	/**
	 * This method responsible for refresh the ImageView and marge the new plant image to the combined image.
	 * @author kinan
	 */

	private void refreshImageView() {
		this.combinedImage = mergeAndDrawTheCombinedImages(plantBufferedImage, potBufferedImage);
		Image fxImage = SwingFXUtils.toFXImage(combinedImage, null);
		this.imageView.setImage(fxImage);
	}

	/**
	 * This method responsible to convert ImageIcon to BufferedImage
	 * 
	 * @author kinan
	 * @param imageIcon
	 * @return BufferedImage
	 */
	public BufferedImage convertImageIconToBufferedImage(ImageIcon imageIcon) {
		BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = bufferedImage.createGraphics();
		imageIcon.paintIcon(null, g, 0, 0);
		g.dispose();
		return bufferedImage;
	}

	/**
	 * This method responsible for merge the plant and the pot and return a Combined
	 * image
	 * 
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
	
}
