package boundary.PlantView;

import boundary.HelpMenu;
import boundary.SettingsView;
import boundary.Widget.WidgetJavaFXApplication;
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
    private SideButtons sideButtons;
    private JLabel creationTimeLabel;
    private JLabel stateLabel;
    private Plant currentPlant;
    private WidgetJavaFXApplication javaFXApp;
    private static boolean isJavaFXInitialized = false;
    private boolean isVacationMode = false;

    /**
     * @param width      Width of MainFrame
     * @param height     Height of MainFrame
     * @param controller Active Controller object used in program
     * This constructor creates a boundary.PlantView.PlantView Panel that adds relevant panels to show the user's
     * active plant, relevant information about the plant and buttons for taking care of the plant
     * @author Elvira Grubb
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
        this.setBackground(new java.awt.Color(184, 200, 177));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        creationTimeLabel = new JLabel("Elapsed Time: Calculating...");
        stateLabel = new JLabel("State: Unknown");
        topPanel.add(creationTimeLabel);
        topPanel.add(stateLabel);
        add(topPanel, BorderLayout.NORTH);
        updateElapsedTime();

        plantPanel = new PlantPanel(width, height, this);
        add(plantPanel, BorderLayout.WEST);

        sideButtons = new SideButtons(width, height, this);
        add(sideButtons, BorderLayout.EAST);

        settingsView = new SettingsView(width, height, this);

        checkJavaFXToolKit();

    }
    /* TODO: Not just in this class, but: make open all plants as widgets button in storage
     *   merge plants and pots and resize them and make buttons and hoverbuttons
     *   of them with java graphics or javafx
     *   make the name display under the buttons */

    /**
     * @author Petri Närhi
     * ActionListener used for the Storage button that calls on a method in the
     * Controller to show the Storage panel
     */
    public void storagePressed()
    {
        if (soundEffectSetting) {
            buttonPressedSoundEffect();
        }
        System.out.println("Storage pressed.");
        try {
            if (!controller.getListOfPlants().isEmpty()) {
                controller.createStorage();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used when the Widget button is pressed
     * Method creates a widget of the currently open plant
     * it will cheack if the Plant and the images is not null and it will call the sendDataToJavaFX method
     * The Widget will start from here.
     *
     * @author kinan
     */
    public void widgetPressed() {
        SwingUtilities.invokeLater(() -> {
            if ((controller.getCurrentPlant() == null)
                    || ((controller.getCurrentPlant().getImage() == null) && (controller.getCurrentPlant().getPot() == null))) {
                System.out.println("You can't create a Widget because the plant or the pot image is null");
            } else {
                updateButtonState();
                sendDataToJavaFX(controller);
            }
        });
        if (soundEffectSetting) {

            buttonPressedSoundEffect();
        }
        System.out.println("Widget pressed.");
    }

    /**
     * Updates the elapsed time for the current plant by calculating the duration from its creation time,
     * adjusted for any paused durations, to the current time. The elapsed time is displayed in a label.
     *
     * @author Aleksander Augustyniak
     */
    public void updateElapsedTime(){
        if (currentPlant != null){
            LocalDateTime creationTime = currentPlant.getDateAndTime().plus(controller.getTotalPausedDuration());
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(creationTime, now);

            long days = duration.toDays();
            long hours = duration.toHours() % 24;
            long minutes = duration.toMinutes() % 60;
            creationTimeLabel.setText("Elapsed time: " + days + " days, " + hours + " h, " + minutes + " min");
        }
    }

    /**
     * Handles the action of skipping one hour for the current plant.
     * Calls the controller to skip the specified time and updates the plant details.
     * If no plant is found, an error message is printed(just for test now)
     * Plays a button pressed sound effect
     *
     * @author Aleksander Augustyniak
     */
    public void skipHourPressed()
    {
        int hoursToSkip = 1;
        Plant currentPlant = controller.getCurrentPlant();
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

    /**
     * Updates the details of the specified plant, including the elapsed time, plant image, and water level.
     * Updates the widget images if applicable
     *
     * @param plant the plant whose details are to be updated
     * @author Aleksander Augustyniak
     */
    public void updatePlantDetails(Plant plant){
        this.currentPlant = plant;
        updateElapsedTime();
        plantPanel.updatePlantImage(plant.getImage());
        JProgressBar waterBar = plantPanel.getWaterBar();
        waterBar.setValue(plant.getWaterLevel());
        waterBar.repaint();
        stateLabel.setText("State: " + plant.getState().toString());
        UpdateWidgetImages();
    }

    /**
     * Toggles the vacation mode for the plant. When the vacation mode is enabled, the plant's time is paused.
     * When vacation mode is disabled, it resumes
     *
     * @author Aleksander Augustyniak
     */
    public void vacationPressed() {
        if (isVacationMode) {
            controller.resumeTime();
            isVacationMode = false;
            System.out.println("Vacation mode disabled, resuming time");
        } else {
            controller.pausTime();
            isVacationMode = true;
            System.out.println("Vacation mode enabled, pausing time");
        }
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

    /**
     * Waters the current plant by calling the controller's waterPlant method.
     * Updates the plant's water level, details
     *
     * @author Aleksander Augustyniak (main)
     * @author Petri Närhi (edits)
     */
    public void waterPressed() {
        try {
            System.out.println("Water pressed.");
            controller.waterPlant();
            plantPanel.updateWaterLevel(controller.getCurrentPlant().getWaterLevel());
            updatePlantDetails(controller.getCurrentPlant());
            System.out.println("Water level: " + controller.getCurrentPlant().getWaterLevel());
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
        if (soundEffectSetting) {
            buttonPressedSoundEffect();
        }
        settingsView.setVisible(true);
    }

    /**
     * @author Elvira Grubb
     * Used when the help button is clicked. This method creates the HelpMenu.
     */
    public void helpMenuPressed()
    {
        new HelpMenu(width, height);
    }

    /**
     * @author Elvira Grubb
     * This method plays a button clicking sound effect.
     */
    private void buttonPressedSoundEffect()
    {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/button_sound.wav").getAbsoluteFile());
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
     * @param setting A boolean indicating whether the sound is on or off in the settings
     * Method called when the settings in the settings menu are saved to update whether
     * the sound is on or off
     * @author Elvira Grubb
     */
    public void setSoundEffectSetting(boolean setting)
    {
        this.soundEffectSetting = setting;
    }

    /**
     * @author Elvira Grubb
     * @return boolean for whether the sound effect setting is on or off
     * This method returns a boolean indicating whether the user has turned the settings on or off
     */
    public boolean getSoundEffectSetting()
    {
        return soundEffectSetting;
    }

    /**
     * Gets the current plant image from the current plant in controller
     * if no plant exists, returns a default empty image
     * for display in the PlantPanel
     *
     * @return ImageIcon current plant
     * @author Petri Närhi
     */
    public ImageIcon getCurrentPlantImage() {
        try {
            return controller.getCurrentPlant().getImage();
        } catch (NullPointerException e) {
            return new ImageIcon("images/plants/default_plant.png");
        }
    }

    /**
     * Gets the current pot image from the current plant in controller
     * if no plant exists, returns a default empty image
     * for display in the PlantPanel
     *
     * @return ImageIcon current pot
     * @author Petri Närhi
     */
    public ImageIcon getCurrentPot() {
        try {
            return controller.getCurrentPlant().getPot();
        } catch (NullPointerException e) {
            return new ImageIcon("images/pots/default_pot.png");
        }
    }

    /**
     * Gets the current plant name from the current plant in controller
     * if no plant exists, returns a text that no plant is created
     * for display in the PlantPanel
     *
     * @return String plant name
     * @author Petri Närhi
     */
    public String getCurrentPlantName() {
        try {
            return controller.getCurrentPlant().getName();
        } catch (NullPointerException e) {
            return "No Plant Created";
        }
    }

    /**
     * Gets the current plant type from the current plant in controller
     * if no plant exists, returns a default text
     * for display in the PlantPanel
     *
     * @return String current plant species
     * @author Petri Närhi
     */
    public String getCurrentPlantSpecies() {
        try {
            String species = controller.getCurrentPlant().getType().getPlantTypeNameAlternative();
            return ("Species: " + species);
        } catch (NullPointerException e) {
            return "Create a plant to show it here!";
        }
    }

    /**
     * Gets the current water level from the current plant in controller
     * if no plant exists, returns 0
     * for display in the PlantPanel
     *
     * @return int plant water level
     * @author Petri Närhi
     */
    public int getCurrentPlantWaterLevel() {
        try {
            return controller.getCurrentPlant().getWaterLevel();
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
     *
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
     * @param controller
     * @author kinan
     */
    private void sendDataToJavaFX(Controller controller) {
        Platform.runLater(() -> {
            // Create an instance of the JavaFX application and pass the data
            Stage stage = new Stage();
            this.javaFXApp = new WidgetJavaFXApplication(controller, stage, this);
            javaFXApp.start(stage);
        });
    }

    /**
     * this  Method responsible for updating the button states based on widget existence
     *
     * @author kinan
     */
    public void updateButtonStatesIfWidgeIsON() {
        boolean isWidgetCreated = getJavaFXAppClass() != null;
        // Disable or enable buttons based on the widget existence
        getSideButtonsClass().getSkipHour().setEnabled(!isWidgetCreated);
        getSideButtonsClass().getSettings().setEnabled(!isWidgetCreated);
        getSideButtonsClass().getWidget().setEnabled(!isWidgetCreated);
        getPlantPanelClass().getWaterPlantButton().setEnabled(!isWidgetCreated);
    }

    public void updateButtonState() {
        getSideButtonsClass().getSkipHour().setEnabled(false);
        getSideButtonsClass().getSettings().setEnabled(false);
        getSideButtonsClass().getWidget().setEnabled(false);
        getPlantPanelClass().getWaterPlantButton().setEnabled(false);
    }

    /**
     * This method is responsible for updating the image if the WidgetJavaFXApplication is not null
     *
     * @author kinan
     */
    private void UpdateWidgetImages() {
        if (getJavaFXAppClass() != null) {
            javaFXApp.updateWidgetImages(getCurrentPlantImage());
        }
    }

    public boolean isVacationMode() {
        return isVacationMode;
    }
}