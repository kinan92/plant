package boundary;
import controller.Controller;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PlantView extends JPanel {
    int width;
    int height;

    private PlantPanel plantPanel;
    boolean soundEffectSetting;
    private Controller controller;
    SettingsView settingsView;

   //Creates the base PlantView panel, sets rules for the panel and adds other panels
    public PlantView(int width, int height, Controller controller)
    {
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

        SideButtons sideButtons = new SideButtons(width, height, this);
        add(sideButtons, BorderLayout.EAST);

        settingsView = new SettingsView(width, height, this);
    }

    //Method that is called when the Plant Collection button is pressed
    //Method is a work in progress and currently has no functionality.
    //When functionality is added this method will open the user's Plant Storage
    public void getPlantPressed()
    {
        if (soundEffectSetting)
        {
            buttonPressedSoundEffect();
        }
        System.out.println("Plant Collection pressed.");
    }

    //Method used when the Widget button is pressed
    //Method creates a widget of the currently open plant
    public void widgetPressed()
    {
        if (soundEffectSetting)
        {
            buttonPressedSoundEffect();
        }
    	// test other plants      images/plants/snakeplant.png   images/plants/goldenbarrelcactus.png  images/plants/bunnyear.png  images/plants/moneyplant.png
    	SwingUtilities.invokeLater(() -> {
    									//plant	path						pot path
            new MargePlantAndPotWidget("images/plants/snakeplant.png", "images/pots/pot-with-bow-tie2.png",plantPanel.getWaterPlantButton());
            
        });
    	
        System.out.println("Widget pressed.");
    }

    //Method will be called when the Skip Hour button is pressed
    //Method is a work in progress, functionality will be added later
    public void skipHourPressed()
    {
        System.out.println("Skip hour pressed.");
        if (soundEffectSetting)
        {
            buttonPressedSoundEffect();
        }
        /*int hoursToSkip = 1;
        controller.skipTime(hoursToSkip);
        System.out.println("Skipped " + hoursToSkip + " hour(s).");

         */
    }

    //Method used when Vacation button is pressed
    //Method is a work in progress
    //When method is done this method will allow the user to set the program to vacation mode
    public void vacationPressed()
    {
        System.out.println("Vacation pressed.");

        if (soundEffectSetting)
        {
            buttonPressedSoundEffect();
        }
    }

    //Method used when water button is pressed
    //Method is a work in progress
    public void waterPressed()
    {
        try {
            System.out.println("Water pressed.");
            controller.waterPlant();
            plantPanel.updateWaterLevel(controller.getPlantWaterLevel());
            System.out.println("Water level: " + controller.getPlantWaterLevel());
        } catch (NullPointerException e) {
            //JOptionPane.showMessageDialog(waterPlant, "No plant exists!");
        }
    }

    public void settingsPressed()
    {
        settingsView.setVisible(true);
    }

    public void mainMenuPressed()
    {
        controller.showMainMenu();
    }

    private void buttonPressedSoundEffect()
    {
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

    public void setSoundEffectSetting(boolean setting)
    {
        soundEffectSetting = setting;
    }

    public boolean getSoundEffectSetting()
    {
        return soundEffectSetting;
    }

    //for display in the PlantPanel
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
}
