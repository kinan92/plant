package boundary;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PlantPanel extends JPanel {
    int width;
    int height;
    JPanel plantPanel;
    PlantView plantView;
    private ImageIcon elefantöra = new ImageIcon("images/plants/moneyplant.png");
    private JButton waterPlant;
    private JProgressBar waterBar;

    public PlantPanel(int width, int height, PlantView plantView)
    {
        super(null);
        this.width = width;
        this.height = height;
        this.plantView = plantView;

        plantPanel = new JPanel();
        this.setPreferredSize(new Dimension(256, height));
        this.setLayout(new GridBagLayout());

        this.add(nameView());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        this.add(nameView(), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        JLabel plantImage = new JLabel(elefantöra);
        JLabel plantBackground = new JLabel(new ImageIcon("images/background/blue_gradient.png"));
        JLabel plantPot = new JLabel(new ImageIcon("images/pots/default_pot.png"));
        this.add(plantImage, c);
        this.add(plantPot, c);
        this.add(plantBackground, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        JPanel plantCare = plantCare();
        this.add(plantCare, c);
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

    private JPanel plantCare()
    {
        JPanel plantCare = new JPanel();
        plantCare.setSize(new Dimension(256, 50));
        plantCare.setLayout(new GridBagLayout());
        plantCare.setBackground(Color.GRAY);
        GridBagConstraints c = new GridBagConstraints();

        /*JButton waterPlant = new JButton("Water plant");
        waterPlant.setFont(new Font("Montserrat", Font.PLAIN, 16));*/

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
        waterBar.setValue(plantView.getController().getPlantWaterLevel());
        waterBar.setStringPainted(true);
        c.gridx = 0;
        c.gridy = 1;
        plantCare.add(waterBar, c);

        return plantCare;
    }

    public JProgressBar getWaterBar(){
        return this.waterBar;
    }


    //Updates the water health bar and calls other methods to show the user that the plant
    //has been watered. Currently this only calls methods that play sound effects but may later
    //include things such as new sprites or animations
    public void updateWaterLevel(int waterLevel){
        if (plantView.getSoundEffectSetting())
        {
            waterSoundEffect();
            if (waterLevel == 100)
            {
                plantHappySoundEffect();
            }
        }
        waterBar.setValue(waterLevel);
    }

    //Method that plays a watering sound effect when plant is watered
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

    //Method that plays a happy sound effect when plant is 100% watered
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
}
