package boundary;

import controller.Controller;
import entity.Plant;
import javafx.application.Platform;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class PlantView extends JPanel {
	private int width;
	private int height;
	private PlantPanel plantPanel;
	boolean soundEffectSetting;
	private Controller controller;
	private SettingsView settingsView;
	private Timer updateTimer;
	private SideButtons sideButtons;
	private JLabel creationTimeLabel;
	private Plant plant;
	private WidgetJavaFXApplication javaFXApp;
	private static boolean isJavaFXInitialized = false;

	/**
	 * @author Elvira Grubb
	 * @param width Width of MainFrame
	 * @param height Height of MainFrame
	 * @param controller Active Controller object used in program
	 * This constructor creates a PlantView Panel that adds relevant panels to show the user's
	 * active plant, relevant information about the plant and buttons for taking care of the plant
	 */
	public PlantView(int width, int height, Controller controller) {
		super(null);
		this.width = width;
		this.height = height;
		this.controller = controller;
		soundEffectSetting = true;
		this.setSize(width, height);
		BorderLayout borderLayout = new BorderLayout();
		this.setLayout(borderLayout);

		creationTimeLabel = new JLabel("Elapsed Time: Calculating...");
		add(creationTimeLabel, BorderLayout.NORTH);
		startUpdateTimer();
		updateElapsedTime();

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
	public void storagePressed() {
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

	public void startUpdateTimer(){
		updateTimer = new Timer(1000, e -> updateElapsedTime());
		updateTimer.start();
	}
	public void updateElapsedTime(){
		if (plant != null){
			LocalDateTime creationTime = plant.getDateAndTime();
			LocalDateTime now = LocalDateTime.now();
			Duration duration = Duration.between(creationTime, now);

			long days = duration.toDays();
			long hours = duration.toHours() % 24;
			long minutes = duration.toMinutes() % 60;
			long seconds = duration.getSeconds() % 60;

			creationTimeLabel.setText("Elapsed time: " + days + " days, " + hours + " h, " + minutes + " min, " + seconds + " sec");
		}
	}

	// Method will be called when the Skip Hour button is pressed
	// Method is a work in progress, functionality will be added later
	public void skipHourPressed()
	{
		int hoursToSkip = 1;
		Plant currentPlant = controller.getPlant();
		if (currentPlant != null){
			controller.skipTime(hoursToSkip);
			updatePlantDetails(currentPlant);
		} else {
			System.out.println("Error: No current plant found");
		}

		if (soundEffectSetting)
		{
			buttonPressedSoundEffect();
		}
	}

	public void updatePlantDetails(Plant plant){
		this.plant = plant;
		updateElapsedTime();
		plantPanel.updatePlantImage(plant.getImage());
	}

	// Method used when Vacation button is pressed
	// Method is a work in progress
	// When method is done this method will allow the user to set the program to
	// vacation mode
	public void vacationPressed() {
		System.out.println("Vacation pressed.");
	}

	/**
	 * @author Elvira Grubb
	 * Method called when the Main Menu button is pressed. It calls on a method in controller
     * to show the main menu in the mainframe, and plays the button sound effect if sound
	 * is on in the settings
	 */
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
			plantPanel.updateWaterLevel(controller.getPlant().getWaterLevel());
			updatePlantDetails(controller.getPlant());
			System.out.println("Water level: " + controller.getPlant().getWaterLevel());
		} catch (NullPointerException e) {
			// JOptionPane.showMessageDialog(waterPlant, "No plant exists!");
		}
	}

	/**
	 * @author Elvira Grubb
	 * Method called when the settings button is pressed. If sound settings is on it
	 * calls to the button sound effect method, then sets the settings menu to visible.
	 */
	public void settingsPressed() {
		if (soundEffectSetting)
		{
			buttonPressedSoundEffect();
		}
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

	/**
	 * @author Elvira Grubb
	 * @param setting A boolean indicating whether the sound is on or off in the settings
	 * Method called when the settings in the settings menu are saved to update whether
	 * the sound is on or off
	 */
	public void setSoundEffectSetting(boolean setting)
	{
		soundEffectSetting = setting;
	}

	public boolean getSoundEffectSetting()
	{
		return soundEffectSetting;
	}

	/**
	 * Gets the current plant image from the current plant in controller
	 * if no plant exists, returns a default empty image
	 * for display in the PlantPanel
	 * @return ImageIcon current plant
	 * @author Petri Närhi
	 * */
	public ImageIcon getCurrentPlantImage()
	{
		try {
			return controller.getPlant().getImage();
		} catch (NullPointerException e) {
			return new ImageIcon("images/plants/default_plant.png");
		}
	}

	/**
	 * Gets the current pot image from the current plant in controller
	 * if no plant exists, returns a default empty image
	 * for display in the PlantPanel
	 * @return ImageIcon current pot
	 * @author Petri Närhi
	 * */
	public ImageIcon getCurrentPot()
	{
		try {
			return controller.getPlant().getPot();
		} catch (NullPointerException e) {
			return new ImageIcon("images/pots/default_pot.png");
		}
	}

	/**
	 * Gets the current plant name from the current plant in controller
	 * if no plant exists, returns a text that no plant is created
	 * for display in the PlantPanel
	 * @return String plant name
	 * @author Petri Närhi
	 * */
	public String getCurrentPlantName()
	{
		try {
			return controller.getPlant().getName();
		} catch (NullPointerException e) {
			return "No Plant Created";
		}
	}

	/**
	 * Gets the current plant type from the current plant in controller
	 * if no plant exists, returns a default text
	 * for display in the PlantPanel
	 * @return String current plant species
	 * @author Petri Närhi
	 * */
	public String getCurrentPlantSpecies()
	{
		try {
			String species = controller.getPlant().getType().getPlantTypeNameAlternative();
			return ("Species: " + species);
		} catch (NullPointerException e) {
			return "Create a plant to show it here!";
		}
	}

	/**
	 * Gets the current water level from the current plant in controller
	 * if no plant exists, returns 0
	 * for display in the PlantPanel
	 * @return int plant water level
	 * @author Petri Närhi
	 * */
	public int getCurrentPlantWaterLevel()
	{
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
