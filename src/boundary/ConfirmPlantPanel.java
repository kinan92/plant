package boundary;

import controller.Controller;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ConfirmPlantPanel extends JPanel{
    private int width;
    private int height;
    private ImageIcon selectedPlant;
    private ImageIcon selectedPot;
    private ArrayList<String> plantInfoArray;
    private int plantNumber;
    private int potNumber;
    private JTextField plantName;
    Controller controller;

    /**
     * This constructor creates a ConfirmPlant panel used as a frame and adds other panels to it
     * @param width The width of the MainFrame
     * @param height THe height of the MainFrame
     * @param selectedPlant ImageIcon of the user's selected plant
     * @param selectedPot ImageIcon of the user's selected pot
     * @param plantNumber The ArrayList position of the user's selected plant
     * @param potNumber The ArrayList position of the user's selected plant
     * @param plantInfoArray An ArrayList that contains information from the PlantType class
     * @param controller The current Controller Object
     * @author Elvira Grubb
     */
    public ConfirmPlantPanel(int width, int height, ImageIcon selectedPlant, ImageIcon selectedPot, int plantNumber, int potNumber, ArrayList<String> plantInfoArray, Controller controller)
    {
        System.out.println("You're in ConfirmPlantPanel");
        this.selectedPlant = selectedPlant;
        this.selectedPot = selectedPot;
        this.width = width;
        this.height = height;
        this.plantInfoArray = plantInfoArray;
        this.controller = controller;
        this.plantNumber = plantNumber;
        this.potNumber = potNumber;

        setLayout(new BorderLayout());
        setSize(width, height);

        add(plantInformation(), BorderLayout.CENTER);
        add(titlePanel(), BorderLayout.NORTH);
        add(navigation(), BorderLayout.SOUTH);
    }

    /**
     * This method creates a JPanel that's used as a frame for two other JPanels
     * @return A PlantInformation JPanel
     * @author Elvira Grubb
     */
    private JPanel plantInformation()
    {
        JPanel plantInformation = new JPanel();
        plantInformation.setLayout(new GridLayout(1, 2));
        plantInformation.add(plantView());
        plantInformation.add(plantInfo());
        return plantInformation;
    }

    /**
     * This method creates a JPanel that shows the selected plant and pot
     * @return JPanel with the selected plant and pot shown
     * @author Elvira Grubb
     */
    private JPanel plantView()
    {
        JPanel plantView = new JPanel();
        plantView.setPreferredSize(new Dimension(width / 2, 256));
        plantView.setBackground(new java.awt.Color(199, 211, 196));
        plantView.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;

        JLabel plantImage = new JLabel(selectedPlant);

        JButton sparkleEffect = new JButton();
        sparkleEffect.setIcon(selectedPlant);
        sparkleEffect.setPreferredSize(new Dimension(320, 256));
        sparkleEffect.setBorder(BorderFactory.createEmptyBorder());
        sparkleEffect.setContentAreaFilled(false);
        sparkleEffect.setFocusPainted(false);
        sparkleEffect.setRolloverEnabled(true);
        sparkleEffect.setRolloverIcon(new ImageIcon("images/animation/sparkle_animation.gif"));

        JLabel potImage = new JLabel(selectedPot);

        plantView.add(plantImage, c);
        plantView.add(potImage, c);
        plantView.add(sparkleEffect, c);

        return plantView;
    }

    /**
     * This method creates a JPanel containing information about the selected plant and a textfield to input a name for it
     * @return JPanel with plant information
     * @author Elvira Grubb
     */
    private JPanel plantInfo()
    {
        JPanel plantInfo = new JPanel();
        plantInfo.setPreferredSize(new Dimension(width / 2, 256));
        plantInfo.setBackground(new java.awt.Color(150, 163, 145));
        plantInfo.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        c.ipady = 25;
        JLabel plantNameTitle = new JLabel("Name: ");
        plantNameTitle.setPreferredSize(new Dimension((width / 6), 20));
        plantNameTitle.setFont(new Font("Calibri", Font.BOLD, 16));
        plantInfo.add(plantNameTitle, c);

        c.gridx = 1;
        plantName = new JTextField();
        plantName.setPreferredSize(new Dimension((width / 4) + 10, 8));
        plantName.setFont(new Font("Calibri", Font.ITALIC, 16));
        plantInfo.add(plantName, c);

        c.gridy = 1;
        c.gridx = 0;
        JLabel plantNameLatinTitle = new JLabel("Species: ");
        plantNameLatinTitle.setPreferredSize(new Dimension((width / 6), 20));
        plantNameLatinTitle.setFont(new Font("Calibri", Font.BOLD, 16));
        plantInfo.add(plantNameLatinTitle, c);

        c.gridx = 1;
        JLabel plantNameLatin = new JLabel(plantInfoArray.get(0));
        plantNameLatin.setPreferredSize(new Dimension((width / 4) + 10, 20));
        plantNameLatin.setFont(new Font("Calibri", Font.ITALIC, 16));
        plantInfo.add(plantNameLatin, c);

        c.gridx = 0;
        c.gridy = 2;
        JLabel plantNameEnglishTitle = new JLabel("Also called: ");
        plantNameEnglishTitle.setPreferredSize(new Dimension((width / 6), 20));
        plantNameEnglishTitle.setFont(new Font("Calibri", Font.BOLD, 16));
        plantInfo.add(plantNameEnglishTitle, c);

        c.gridx = 1;
        c.gridy = 2;
        JLabel plantNameEnglish = new JLabel(plantInfoArray.get(1));
        plantNameEnglish.setPreferredSize(new Dimension((width / 4) + 10, 20));
        plantNameEnglish.setFont(new Font("Calibri", Font.ITALIC, 16));
        plantInfo.add(plantNameEnglish, c);

        return plantInfo;
    }

    /**
     * This method creates a JPanel that's used as a header for the ConfirmPlantPanel
     * @return A JPanel that acts as a header
     * @author Elvira Grubb
     */
    private JPanel titlePanel()
    {
        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(width, height / 7));
        titlePanel.setBackground(new java.awt.Color(184, 200, 177));
        titlePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        JLabel lblTitle = new JLabel("Is this the plant you want?");
        lblTitle.setFont(new Font("Calibri", Font.PLAIN, 32));
        titlePanel.add(lblTitle, c);

        return titlePanel;
    }

    /**
     * Adds a navigation panel with a back button
     * @return JPanel the navigation panel
     * @author Petri Närhi
     */
    public JPanel navigation()
    {
        JPanel navigation = new JPanel();
        navigation.setLayout(new FlowLayout());
        navigation.setPreferredSize(new Dimension(width, (height / 10)));

        //Loops through plantImages and creates buttons with the images
        JButton backBtn = new JButton();
        backBtn.setBorder(BorderFactory.createEmptyBorder());
        backBtn.setContentAreaFilled(false);
        backBtn.setIcon(new ImageIcon("images/buttons/back.png"));


        backBtn.setFocusPainted(false);
        backBtn.setRolloverEnabled(true);

        backBtn.setRolloverIcon(new ImageIcon("images/buttons/back-hover.png"));

        //ActionListener that will return the ArrayList number when the plant is pressed
        backBtn.addActionListener(l -> backPressed());
        backBtn.setLocation(100,100);

        JButton confirm = new JButton();
        confirm.setIcon(new ImageIcon("images/buttons/confirm.png"));
        confirm.setBorder(BorderFactory.createEmptyBorder());
        confirm.setContentAreaFilled(false);

        confirm.setRolloverEnabled(true);
        confirm.setRolloverIcon(new ImageIcon("images/buttons/confirm_hover.png"));
        confirm.addActionListener(l -> createPlant());

        navigation.add(backBtn);
        navigation.add(confirm);
        navigation.setVisible(true);

        return navigation;
    }

    /**
     * This method calls on a method to return to the ChoosePlantFrame view
     * @author Elvira Grubb
     */
    private void backPressed()
    {
        buttonPressedSoundEffect();
        controller.createChoosePlantPanel();
    }

    /**
     * This method is called when the user confirms their plant. If a name has not been input the program will prompt the user to
     * input a name, if a name is present the method will call on a method to create the plant
     * @author Elvira Grubb
     */
    private void createPlant()
    {
        buttonPressedSoundEffect();
        if (plantName.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Please enter a name for your plant.");
        }

        else
        {
            controller.createPlant(plantNumber, potNumber, plantName.getText());
        }
    }

    /**
     * This method plays a sound effect whenever a button is pressed
     * @author Elvira Grubb
     */
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
}