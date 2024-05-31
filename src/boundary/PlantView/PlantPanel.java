package boundary.PlantView;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PlantPanel extends JPanel {
    private int height;
    private PlantView plantView;
    private ImageIcon currentPlantImage;
    private ImageIcon currentPot;
    private String currentPlantName;
    private String currentPlantSpecies;
    private int currentPlantWaterLevel;
    private JButton waterPlantButton;
	private JProgressBar waterBar;
    private JLayeredPane plantWindow;
    private JLabel sparkle;
    private JLabel creationTimeLabel;
    private JLabel plantImageLabel;

    /**
     * This constructor creates a PlantPanel, filling it with relevant JPanels to function
     * as a window where the user can see their plant, relevant plant information, and plant care
     * @param height Height of the MainFrame
     * @param plantView The active boundary.PlantView.PlantView class
     * @author Elvira Grubb (main)
     * @author Petri Närhi (edits)
     */
    public PlantPanel(int height, PlantView plantView)
    {
        this.height = height;
        this.plantView = plantView;
        this.currentPlantImage = plantView.getCurrentPlantImage();
        this.currentPot = plantView.getCurrentPot();
        this.setPreferredSize(new Dimension(256, height));
        this.setLayout(new GridBagLayout());

        //Creates nameView panel and adds with constraints
        this.add(nameView());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        this.add(nameView(), c);

        //Creates plantWindow JLayeredPane and adds to this with constraints
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 1;
        JLayeredPane plantWindow = getPlantWindow();
        creationTimeLabel = new JLabel("Creation Time: 0 days, 0h, 0 min");
        this.add(plantWindow, c);
        this.add(creationTimeLabel, c);

        //Creates PlantCare JPanel and adds to this with constraints
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 2;
        JPanel plantCare = plantCare();
        this.add(plantCare, c);
    }

    /**
     * This method creates a JLayeredPane where the plant is shown by layering relevant
     * images on top of each other
     * @return JLayeredPane plantWindow, a window where the user can see its active plant
     * @author Elvira Grubb
     */
    private JLayeredPane getPlantWindow() {
        //Creates a JLayeredPane
        plantWindow = new JLayeredPane();
        plantWindow.setLayout(new BorderLayout());
        plantWindow.setPreferredSize(new Dimension(256, 320));
        plantWindow.setBounds(0, 0, 256, 320);
        plantWindow.setBackground(Color.ORANGE);

        //Creates various labels to use in the JLayeredPane
        plantImageLabel = new JLabel(currentPlantImage);
        plantImageLabel.setBounds(0, 0, 256, 320);
        JLabel plantBackground = new JLabel(new ImageIcon("images/background/green_gradient.png"));
        plantBackground.setBounds(0, 0, 256, 320);
        JLabel plantPot = new JLabel(currentPot);
        plantPot.setBounds(0, 0, 256, 320);
        sparkle = new JLabel();
        sparkle.setBounds(0, 0, 256, 320);

        //Adds all the labels to the JLayeredPane in the correct order
        plantWindow.add(sparkle, 1);
        plantWindow.add(plantImageLabel, 2);
        plantWindow.add(plantPot, 3);
        plantWindow.add(plantBackground, 4);

        return plantWindow;
    }

    /**
     * This method creates a nameView JPanel where the user can see its active plant's name
     * and species
     * @return A nameView JPanel
     * @author Elvira Grubb (main)
     * @author Petri Närhi (edits)
     */
    public JPanel nameView()
    {
        //Creates a JPanel for the NameView
        JPanel nameView = new JPanel();
        nameView.setPreferredSize(new Dimension(256, height / 7));
        nameView.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Creates JLabels with relevant information and adding it with constraints
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        currentPlantName = plantView.getCurrentPlantName();
        JLabel plantName = new JLabel(currentPlantName);
        plantName.setFont(new Font("Calibri", Font.PLAIN, 26));
        nameView.add(plantName, c);

        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 1;
        currentPlantSpecies = plantView.getCurrentPlantSpecies();
        JLabel plantSpecies = new JLabel(currentPlantSpecies);
        plantSpecies.setFont(new Font("Calibri", Font.PLAIN, 16));
        nameView.add(plantSpecies, c);

        return nameView;
    }

    /**
     * This method creates a plantCare JPanel that shows the healthbar and buttons to
     * take care of the user's active plant
     * @return A PlantCare JPanel
     * @author Elvira Grubb
     * @author Petri Närhi (edits)
     */
    private JPanel plantCare()
    {
        //Creates a JPanel for PlantCare
        JPanel plantCare = new JPanel();
        plantCare.setSize(new Dimension(256, 50));
        plantCare.setLayout(new GridBagLayout());
        plantCare.setBackground(new java.awt.Color(199, 214, 196));
        GridBagConstraints c = new GridBagConstraints();

        //Creates the watering button and adds it to the PlantCare panel
        waterPlantButton = new JButton();
        waterPlantButton.setBorder(BorderFactory.createEmptyBorder());
        waterPlantButton.setContentAreaFilled(false);
        waterPlantButton.setIcon(new ImageIcon("images/buttons/water.png"));
        waterPlantButton.setPreferredSize(new Dimension(45, 45));
        waterPlantButton.addActionListener(l -> plantView.waterPressed());

        waterPlantButton.setRolloverEnabled(true);
        waterPlantButton.setRolloverIcon(new ImageIcon("images/buttons/water_hover.png"));

        c.weightx = 0;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        plantCare.add(waterPlantButton, c);

        //Creates a healthbar and adds it to the panel
        waterBar = new JProgressBar(0, 100);
        currentPlantWaterLevel = plantView.getCurrentPlantWaterLevel();
        waterBar.setValue(currentPlantWaterLevel);
        waterBar.setStringPainted(true);
        waterBar.setForeground(new java.awt.Color(116, 173, 182));
        c.gridx = 0;
        c.gridy = 1;
        plantCare.add(waterBar, c);

        return plantCare;
    }

    /**
     * Updates the plant image with a new image.
     * Sets the plantImageLabel's icon to the new image and repaints the component.
     * @param newImage the new ImageICon to be displayed as the plant image.
     * @author Aleksander Augustyniak
     */
    public void updatePlantImage(ImageIcon newImage)
    {
        this.currentPlantImage = newImage;
        plantImageLabel.setIcon(this.currentPlantImage);
        repaint();
    }

    /**
     * Method is called when the watering levels should be updated. It updates the water levels visually by
     * updating the color of the waterbar and setting the percentage of the waterbar.
     * @param waterLevel Instance variable of the percentage of water the current plant object is at
     * @author Elvira Grubb
     */
    public void updateWaterLevel(int waterLevel)
    {
        if (waterLevel <= 80 && waterLevel > 50)
        {
            waterBar.setForeground(new java.awt.Color(119, 156, 162));
        }

        else if (waterLevel <= 50 && waterLevel > 20)
        {
            waterBar.setForeground(new java.awt.Color(114, 141, 145));
        }

        else if (waterLevel <= 20)
        {
            waterBar.setForeground(new java.awt.Color(116, 130, 132));
        }

        else if (waterLevel >= 110 && waterLevel < 120)
        {
            waterBar.setForeground(new java.awt.Color(208, 167, 57));
        }

        else if (waterLevel >= 120)
        {
            waterBar.setForeground(new java.awt.Color(193, 57, 57));
        }

        else if (waterLevel >= 80 && waterLevel < 110)
        {
            waterBar.setForeground(new java.awt.Color(116, 173, 182));
        }
        waterBar.setValue(waterLevel);
        waterBar.setString(waterLevel + "%");
    }

    /**
     * This method is called when the water button is pressed. It plays sound effects if sound effects are on in the settings, and an
     * animation if the water levels have reached 100
     * @param sparkleEffect A boolean that indicates whether the sparkle effect should play
     * @author Elvira Grubb
     */
    public void waterLevelEffects(boolean sparkleEffect)
    {
        if (plantView.getSoundEffectSetting())
        {
            waterSoundEffect();
        }

        if (sparkleEffect)
        {
            plantSparkleAnimation();

            if (plantView.getSoundEffectSetting())
            {
                plantHappySoundEffect();
            }
        }
    }

    /**
     * Method that plays a watering sound effect
     * @author Elvira Grubb
     */
    private void waterSoundEffect()
    {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/water_sound.wav").getAbsoluteFile());
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
     * Method that plays a happy sparkle sound effect
     * @author Elvira Grubb
     */
    private void plantHappySoundEffect()
    {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/twinkle_sound.wav").getAbsoluteFile());
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
     * This method starts a new thread to play the sparkle animation gif
     * @author Elvira Grubb
     */
    public void plantSparkleAnimation()
    {
        new Thread(()->
        {
            sparkle.setIcon(new ImageIcon("images/animation/sparkle_animation.gif"));
            try {
                Thread.sleep(2500);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sparkle.setIcon(null);
        }).start();
    }

    public JButton getWaterPlantButton()
    {
 		return waterPlantButton;
 	}
}