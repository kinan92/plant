package boundary;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static javax.swing.JLayeredPane.DEFAULT_LAYER;

public class PlantPanel extends JPanel {
    int width;
    int height;
    JPanel plantPanel;
    PlantView plantView;
    JLayeredPane plantWindow;
    private ImageIcon elefantöra = new ImageIcon("images/plants/moneyplant.png");
    private JButton waterPlant;
    private JProgressBar waterBar;
    Timer timer;
    boolean animation = false;
    JLabel sparkle;

    public PlantPanel(int width, int height, PlantView plantView)
    {
        super(null);
        this.width = width;
        this.height = height;
        this.plantView = plantView;

        this.setPreferredSize(new Dimension(256, height));
        this.setLayout(new GridBagLayout());

        this.add(nameView());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        this.add(nameView(), c);

        /*c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        this.add(plantWindow(), c);*/
        /*JLabel plantImage = new JLabel(elefantöra);
        JLabel plantBackground = new JLabel(new ImageIcon("images/background/blue_gradient.png"));
        JLabel plantPot = new JLabel(new ImageIcon("images/pots/default_pot.png"));
        this.add(plantImage, c);
        this.add(plantPot, c);
        this.add(plantBackground, c);*/

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;

        JLayeredPane plantWindow = getPlantWindow();
        this.add(plantWindow, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        JPanel plantCare = plantCare();
        this.add(plantCare, c);
    }

    private JLayeredPane getPlantWindow() {
        plantWindow = new JLayeredPane();
        plantWindow.setLayout(new BorderLayout());
        plantWindow.setPreferredSize(new Dimension(256, 320));
        plantWindow.setBounds(0, 0, 256, 320);
        plantWindow.setBackground(Color.ORANGE);

        JLabel plantImage = new JLabel(elefantöra);
        plantImage.setBounds(0, 0, 256, 320);
        JLabel plantBackground = new JLabel(new ImageIcon("images/background/blue_gradient.png"));
        plantBackground.setBounds(0, 0, 256, 320);
        JLabel plantPot = new JLabel(new ImageIcon("images/pots/default_pot.png"));
        plantPot.setBounds(0, 0, 256, 320);
        sparkle = new JLabel();
        sparkle.setBounds(0, 0, 256, 320);

        plantWindow.add(sparkle, 1);
        plantWindow.add(plantImage, 2);
        plantWindow.add(plantPot, 3);
        plantWindow.add(plantBackground, 4);
        return plantWindow;
    }
    public JPanel nameView()
    {
        JPanel nameView = new JPanel();
        nameView.setPreferredSize(new Dimension(256, height / 7));
        nameView.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        JLabel plantName = new JLabel("Bob");
        plantName.setFont(new Font("Calibri", Font.PLAIN, 26));
        nameView.add(plantName, c);

        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 1;
        JLabel plantSpecies = new JLabel("Species: Elefantöra");
        plantSpecies.setFont(new Font("Calibri", Font.PLAIN, 16));
        nameView.add(plantSpecies, c);

        return nameView;
    }

    /**
     * @author Elvira Grubb
     * @return JPanel plantCare
     * Method creates a JPanel that contains things pertaining to plant care, such as a watering button
     */
    private JPanel plantCare()
    {
        JPanel plantCare = new JPanel();
        plantCare.setSize(new Dimension(256, 50));
        plantCare.setLayout(new GridBagLayout());
        plantCare.setBackground(Color.GRAY);
        GridBagConstraints c = new GridBagConstraints();

        waterPlant = new JButton();
        waterPlant.setBorder(BorderFactory.createEmptyBorder());
        waterPlant.setContentAreaFilled(false);
        waterPlant.setIcon(new ImageIcon("images/buttons/water.png"));
        waterPlant.setPreferredSize(new Dimension(45, 45));
        plantCare.add(waterPlant, c);
        waterPlant.addActionListener(l -> plantView.waterPressed());
        waterPlant.setRolloverEnabled(true);
        waterPlant.setRolloverIcon(new ImageIcon("images/buttons/water_hover.png"));

        c.weightx = 0;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        plantCare.add(waterPlant, c);

        waterBar = new JProgressBar(0, 100);
        waterBar.setValue(0);
        waterBar.setStringPainted(true);
        c.gridx = 0;
        c.gridy = 1;
        plantCare.add(waterBar, c);

        return plantCare;
    }

    /**
     * @author Elvira Grubb
     * @param waterLevel Instance variable of the percentage of water the current plant object is at
     * Method is called when the watering button is pressed. It updates the water levels visually by
     * calling other methods to update the water bar, play sound effects, and animations
     */
    public void updateWaterLevel(int waterLevel)
    {
        if (plantView.getSoundEffectSetting())
        {
            waterSoundEffect();
            if (waterLevel == 100)
            {
                plantSparkleAnimation();
                plantHappySoundEffect();
            }
        }
        waterBar.setValue(waterLevel);
    }

    /**
     * @author Elvira Grubb
     * Method that plays a watering sound effect
     */
    private void waterSoundEffect()
    {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/water_sound.wav").getAbsoluteFile());
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
     * Method that plays a happy sparkle sound effect
     */
    private void plantHappySoundEffect()
    {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/twinkle_sound.wav").getAbsoluteFile());
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
     * This method starts a new thread to play the sparkle animation gif
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
}
