package boundary;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * this class responsible for creating a JavaFX Pane that contain the menu Bar
 * components
 * 
 * @author kinan
 * 
 */
public class WidgetBarMenu extends Pane {

	private PlantView plantView;
	private Button skipHourButton;
	private ImageView skipHourImage;
	private Button waterButton;
	private ImageView waterImage;
	private ProgressBar progressBar;
	private CheckBox checkBox;
	private MenuButton menuButtons;
	private MenuItem soundMenuItem;
	private MenuItem minimizeMenuItem;
	private Button minimizeButton;
	private MenuItem plantViewMenuItem;
	private Button planViewButton;
	private HBox hbox;
	private double progress = -1;

	/**
	 * this is the constractor
	 * 
	 * @author kinan
	 * @param plantView
	 */
	public WidgetBarMenu(PlantView plantView) {

		this.plantView = plantView;

		initializeAllComponent();
		styleForAllComponent();
		addActionToTheComponent();
		menuButtons.getItems().addAll(soundMenuItem, minimizeMenuItem, plantViewMenuItem);
		hbox.getChildren().addAll(waterButton, skipHourButton, menuButtons);
		updateProgressBar();
		this.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		getChildren().addAll(progressBar, hbox);
	}

	/**
	 * this method responsible for initialize UI components used in the BarMenu
	 * class, such as buttons, menu items, checkbox, and progres bar
	 * 
	 * @author kinan
	 */
	private void initializeAllComponent() {
		this.waterImage = convertJButtonSwingToJavaFxImageView(plantView.getPlantPanelClass().getWaterPlantButton());
		this.waterButton = new Button();
		this.skipHourImage = convertJButtonSwingToJavaFxImageView(plantView.getSideButtonsClass().getSkipHour());
		this.skipHourButton = new Button();
		this.menuButtons = new MenuButton("Menu");
		this.soundMenuItem = new MenuItem();
		this.checkBox = new CheckBox("sound");
		this.minimizeMenuItem = new MenuItem();
		this.minimizeButton = new Button("minimize");
		this.plantViewMenuItem = new MenuItem();
		this.planViewButton = new Button("plant view");
		this.hbox = new HBox();
		this.progressBar = new ProgressBar();

	}

	/**
	 * this method responsible for adding style for the UI components
	 * 
	 * @author kinan
	 */
	private void styleForAllComponent() {
		waterButton = addStyleToTheButtonWithImage(waterButton, waterImage);
		skipHourButton = addStyleToTheButtonWithImage(skipHourButton, skipHourImage);
		soundMenuItem.setGraphic(checkBox);
		soundMenuItem.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		addStyleToMenuItemAndButton(minimizeButton, minimizeMenuItem);
		addStyleToMenuItemAndButton(planViewButton, plantViewMenuItem);
		hbox.setLayoutX(10);
		hbox.setLayoutY(25);
		hbox.setPadding(new Insets(1));
		// hbox.setSpacing(0);// padding around the HBox
		progressBar.setLayoutX(0);
		progressBar.setLayoutY(10);
		progressBar.setPrefSize(300, 20);
		progressBar.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
	}

	/**
	 * this method responsible for adding add Action for the Buttons components
	 * 
	 * @author kinan
	 */
	private void addActionToTheComponent() {
		waterButton.setOnAction(event -> updateProgressBar());
		skipHourButton.setOnAction(event -> plantView.skipHourPressed());
		// minimize Action tor the Bar Menu
		minimizeButton.setOnAction(event -> plantView.getJavaFXAppClass().getBarMenuStage().setIconified(true));
		
		checkBoxAction();
	}

	/**
	 * this method responsible for adding add Action for the sound checkBox
	 * component it will take the boolean from the plantView and check if the
	 * soundEffect is true or not
	 * 
	 * @author kinan
	 */
	private void checkBoxAction() {
		boolean soundEffect = plantView.getSoundEffectSetting();
		checkBox.setSelected(soundEffect);
		checkBox.setOnAction(event -> {

			if (checkBox.isSelected()) {
				System.out.println("Feature is enabled");
				// Add your action when the checkbox is selected
				plantView.setSoundEffectSetting(true);
			} else {
				System.out.println("Feature is disabled");

				plantView.setSoundEffectSetting(false);
			}
		});
	}

	/**
	 * this method responsible for adding style for the Menu Item And Buttons
	 * 
	 * @author kinan
	 * 
	 * @param Button
	 * @param menuItem
	 */
	private void addStyleToMenuItemAndButton(Button Button, MenuItem menuItem) {
		Button tempButton = Button;
		MenuItem tempMenuItem = menuItem;
		tempButton.setPadding(new Insets(5));
		tempMenuItem.setGraphic(tempButton);
		tempButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		tempMenuItem.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

	}

	/**
	 * this method responsible for adding style for the Buttons With Image
	 * 
	 * @author kinan
	 * @param Button
	 * @param ImageView
	 * @return Button
	 */
	private Button addStyleToTheButtonWithImage(Button Button, ImageView Image) {
		Button tempButton = Button;
		tempButton.setPrefSize(Image.getImage().getWidth(), Image.getImage().getHeight());
		tempButton.setPadding(new Insets(5));
		tempButton.setGraphic(Image);
		tempButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		return tempButton;
	}

	/**
	 * 
	 * this method is responsible for converting the icon in the JButton Swing to a
	 * ImageView JavaFX . first it will get the aicon and if the ImageIcon is not
	 * null it will convert the image AS {@link java.awt.Image} to BufferedImage and
	 * it will draw the image to an Image AS {@link javafx.scene.image.Image} and
	 * last it will convert to ImageView
	 * 
	 * @author kinan
	 * @param jButton
	 * 
	 * @return ImageView
	 */
	private ImageView convertJButtonSwingToJavaFxImageView(JButton jButton) {
		ImageIcon icon = (ImageIcon) jButton.getIcon();
		if (icon == null) {
			System.out.println("the image icon is null");
			return null; // Return null if there's no icon
		}
		// Convert the ImageIcon to BufferedImage
		java.awt.Image Image = icon.getImage();
		BufferedImage bufferedImage = new BufferedImage(Image.getWidth(null), Image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(Image, 0, 0, null);
		g2d.dispose();

		// Convert the BufferedImage to JavaFX Image
		Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);

		// Create and return the ImageView
		return new ImageView(fxImage);
	}

	/**
	 * this method update the Progress bar by geting the waterLevel from the
	 * PlantView and dived the value on 100 because the JavaFX Bar take double 1
	 * 
	 * @author kinan
	 */
	private void updateProgressBar() {
		double newValue;
		newValue = plantView.getCurrentPlantWaterLevel() / 100.0;
		System.out.println("Water level Progress" + newValue);
		// get the waterLevel progress
		if (progress == -1) {
			progressBar.setProgress(newValue);
			System.out.println("xxxxxxxxxxxxxxxxxxxxx");
			progress = newValue;
		} else {
			plantView.waterPressed();
			newValue = plantView.getCurrentPlantWaterLevel() / 100.0;
			System.out.println("yyyyyyyyyyy");
			progressBar.setProgress(newValue);
		}

	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}
	

}
