package boundary;

import controller.Controller;
import javafx.application.Platform;
import javafx.stage.Stage;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.io.File;
import java.io.IOException;

public class PlantView extends JPanel {
	int width;
	int height;
	private PlantPanel plantPanel;

	boolean soundEffectSetting;
	private Controller controller;
	private SettingsView settingsView;
	private SideButtons sideButtons;
	
	private WidgetJavaFXApplication javaFXApp;

	private static boolean isJavaFXInitialized = false;

	// Creates the base PlantView panel, sets rules for the panel and adds other
	// panels
	public PlantView(int width, int height, Controller controller) {
		super(null);
		this.width = width;
		this.height = height;
		this.controller = controller;
		soundEffectSetting = true;
		System.out.println("hej plantview");
		this.setSize(width, height);
		BorderLayout borderLayout = new BorderLayout();
		this.setLayout(borderLayout);
		this.setBackground(Color.ORANGE);

		plantPanel = new PlantPanel(width, height, this);
		add(plantPanel, BorderLayout.WEST);

		sideButtons = new SideButtons(width, height, this);
		add(sideButtons, BorderLayout.EAST);

		settingsView = new SettingsView(width, height, this);

		checkJavaFXToolKit();

	}

	// Method that is called when the Plant Collection button is pressed
	// Method is a work in progress and currently has no functionality.
	// When functionality is added this method will open the user's Plant Storage
	public void getPlantPressed() {
		if (soundEffectSetting) {
			buttonPressedSoundEffect();
		}
		System.out.println("Plant Collection pressed.");
	}

	/**
	 *  Method used when the Widget button is pressed
	 *  Method creates a widget of the currently open plant
	 *  it will cheack if the Plant and the images is not null and it will call the sendDataToJavaFX method 
	 *  The Widget will start from here.
	 *  @author kinan
	 * 
	 */
	public void widgetPressed() {
		SwingUtilities.invokeLater(() -> {
			if ((controller.getPlant() == null)
					|| ((controller.getPlant().getImage() == null) && (controller.getPlant().getPot() == null))) {
				System.out.println("you can't ctreat a Widget because the plant or the pot image is null");
			} else {
				sendDataToJavaFX(controller);
			}
		});
		if (soundEffectSetting) {

			buttonPressedSoundEffect();
		}
		System.out.println("Widget pressed.");
	}

	// Method will be called when the Skip Hour button is pressed
	// Method is a work in progress, functionality will be added later
	public void skipHourPressed() {
		System.out.println("Skip hour pressed.");
		if (soundEffectSetting) {
			buttonPressedSoundEffect();
		}
		/*
		 * int hoursToSkip = 1; controller.skipTime(hoursToSkip);
		 * System.out.println("Skipped " + hoursToSkip + " hour(s).");
		 * 
		 */
	}

	// Method used when Vacation button is pressed
	// Method is a work in progress
	// When method is done this method will allow the user to set the program to
	// vacation mode
	public void vacationPressed() {
		System.out.println("Vacation pressed.");
	}

	public void mainMenuPressed() {
		controller.showMainMenu();
		if (soundEffectSetting) {
			buttonPressedSoundEffect();
		}
	}

	// Method used when water button is pressed
	// Method is a work in progress
	public void waterPressed() {
		try {
			System.out.println("Water pressed.");
			controller.waterPlant();
			plantPanel.updateWaterLevel(controller.getPlantWaterLevel());
			System.out.println("Water level: " + controller.getPlantWaterLevel());
		} catch (NullPointerException e) {
			// JOptionPane.showMessageDialog(waterPlant, "No plant exists!");
		}
	}

	public void settingsPressed() {
		settingsView.setVisible(true);
	}

	private void buttonPressedSoundEffect() {
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/button_sound.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (LineUnavailableException e) {
			throw new RuntimeException(e);
		}
	}

	public void setSoundEffectSetting(boolean setting) {
		soundEffectSetting = setting;
	}

	public boolean getSoundEffectSetting() {
		return soundEffectSetting;
	}

	// for display in the PlantPanel
	public ImageIcon getCurrentPlant() {
		try {
			return controller.getPlant().getImage();
		} catch (NullPointerException e) {
			return new ImageIcon("images/plants/default_plant.png");
		}
	}

	public ImageIcon getCurrentPot() {
		try {
			return controller.getPlant().getPot();
		} catch (NullPointerException e) {
			return new ImageIcon("images/pots/default_pot.png");
		}
	}

	public String getCurrentPlantName() {
		try {
			return controller.getPlant().getName();
		} catch (NullPointerException e) {
			return "No Plant Created";
		}
	}

	public String getCurrentPlantSpecies() {
		try {
			String species = controller.getPlant().getType().getPlantTypeNameAlternative();
			return ("Species: " + species);
		} catch (NullPointerException e) {
			return "Create a plant to show it here!";
		}
	}

	public int getCurrentPlantWaterLevel() {
		try {
			return controller.getPlant().getWaterLevel();
		} catch (NullPointerException e) {
			return 0;
		}
	}

	
	public PlantPanel getPlantPanelClass() {
		return plantPanel;
	}

	public SideButtons getSideButtonsClass() {
		return sideButtons;
	}

	public WidgetJavaFXApplication getJavaFXAppClass() {
		return javaFXApp;
	}
	
	/**
	 * This method responsible for JavaFX ToolKit if the ToolKit is not itialized the Platform will start 
	 * This method is called in the Constractor after creating the view ;
	 * @author kinan
	 */
	
	private void checkJavaFXToolKit() {
		if (!isJavaFXInitialized) {
			// Initialize JavaFX only if it hasn't been initialized yet
			Platform.startup(() -> {
				System.out.println("JavaFX initialization complete.");
			});
			isJavaFXInitialized = true;
		} else {
			System.out.println("JavaFX is already initialized.");
		}
	}

	/**
	 * This method responsible for sending data to the JavaFX Application
	 * 
	 * @author kinan
	 * @param controller
	 */
	private void sendDataToJavaFX(Controller controller) {
		Platform.runLater(() -> {
			// Create an instance of the JavaFX application and pass the data
			Stage stage = new Stage();
			this.javaFXApp = new WidgetJavaFXApplication(controller, stage, this);
			javaFXApp.start(stage);
			updateButtonStatesIfWidgeIsON();

		});
	}

	/**
	 * this  Method responsible for updating the button states based on widget existence
	 * @author kinan
	 */
	public void updateButtonStatesIfWidgeIsON() {
		boolean isWidgetCreated = getJavaFXAppClass() != null;
		// Disable or enable buttons based on the widget existence
		getSideButtonsClass().getSkipHour().setEnabled(!isWidgetCreated);
		getSideButtonsClass().getSettings().setEnabled(!isWidgetCreated);
		getPlantPanelClass().getWaterPlantButton().setEnabled(!isWidgetCreated);

	}

}
