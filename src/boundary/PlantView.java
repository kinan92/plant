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
    PlantPanel plantPanel;
    boolean soundEffectSetting;
    private Controller controller;
    SettingsView settingsView;
    //Temporary ImageIcon of an image that will later be replaced by an image from the Plant class
    private ImageIcon elefantöra = new ImageIcon("images/plants/moneyplant.png");

    //ImageIcons for the various buttons
    private ImageIcon skiphour = new ImageIcon("images/buttons/skiphour.png");
    private ImageIcon storage = new ImageIcon("images/buttons/storage.png");
    private ImageIcon vacationImage = new ImageIcon("images/buttons/vacation.png");
    private ImageIcon widgetImage = new ImageIcon("images/buttons/widget.png");
   private JButton waterPlant;

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
            new MargePlantAndPotWidget("images/plants/snakeplant.png", "images/pots/pot-with-bow-tie2.png",waterPlant);
            
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
        System.out.println("Water pressed.");
        controller.waterPlant();
        plantPanel.updateWaterLevel(controller.getPlantWaterLevel());
        System.out.println("Water level: " + controller.getPlantWaterLevel());
    }

    public void settingsPressed()
    {
        settingsView.setVisible(true);
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
}
