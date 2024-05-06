package boundary;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;
import java.io.File;

public class WidgetCreatorJFX extends Pane {
	private Image plantImage;
	private Image potImage;
	private Rectangle theMergedImage;

	private BufferedImage combinedImage;
	private BufferedImage plantBufferedImage;
	private BufferedImage potBufferedImage;

	public WidgetCreatorJFX(ImageIcon plantImagePath, String potImagePath) {
		// Load plant and pot images
		System.out.println("plant "+plantImagePath) ;
		
		potImage = new Image(new File(potImagePath).toURI().toString());
		int width = (int) plantImagePath.getIconWidth();
		int height = (int) plantImagePath.getIconHeight();

		combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		plantBufferedImage = new BufferedImage(plantImagePath.getIconWidth(), plantImagePath.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		potBufferedImage = SwingFXUtils.fromFXImage(potImage, null);

		// Draw pot image
		int potX = 0; // Adjust position as needed
		int potY = 0; // Adjust position as needed
		combinedImage.getGraphics().drawImage(potBufferedImage, potX, potY, null);

		// Draw plant image
		int plantX = potX + (potBufferedImage.getWidth() - plantBufferedImage.getWidth()) / 2;
		int plantY = potY + (potBufferedImage.getHeight() - plantBufferedImage.getHeight()) / 2;
		combinedImage.getGraphics().drawImage(plantBufferedImage, plantX, plantY, null);

		theMergedImage = new Rectangle(plantX, plantY, plantBufferedImage.getWidth(), plantBufferedImage.getHeight());

		setPrefSize(width, height);
		setMaxSize(width, height);

		// Display the merged image
		this.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		this.getChildren().add(new javafx.scene.image.ImageView(SwingFXUtils.toFXImage(combinedImage, null)));
	}

	public Rectangle getTheMergedImage() {
		return theMergedImage;
	}
}
