package boundary;

import controller.Controller;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ChoosePlantPanel extends JPanel {
    private Controller controller;
    private int width;
    private int height;
    private ArrayList<ImageIcon> plantBtnImages;
    private ArrayList<ImageIcon> plantBtnHoverImages;
    private ArrayList<ImageIcon> potBtnImages;
    private ArrayList<ImageIcon> potBtnHoverImages;
    private ArrayList<JButton> plantButtons = new ArrayList<>();
    private ArrayList<JButton> potButtons = new ArrayList<>();
    private int currentSelectedPlant;
    private int currentSelectedPot;

    /**
     * This method creates the base JPanel for the ChoosePlantPanel and adds relevant panels onto it
     * @param controller The active controller class used in the program
     * @param plantBtnImages An ArrayList of plantbutton images
     * @param plantBtnHoverImages An ArrayList of plantbutton hover images
     * @param potBtnImages An ArrayList of potbutton images
     * @param potBtnHoverImages An ArrayList of potbutton hover images
     * @param width The width of the MainFrame
     * @param height The height of the MainFrame
     * @author Elvira Grubb
     */
    public ChoosePlantPanel(Controller controller, ArrayList<ImageIcon> plantBtnImages, ArrayList<ImageIcon> plantBtnHoverImages, ArrayList<ImageIcon> potBtnImages, ArrayList<ImageIcon> potBtnHoverImages, int width, int height)
    {
        super(null);
        this.controller = controller;
        System.out.println("You're in ChoosePlantFrame");
        this.plantBtnImages = plantBtnImages;
        this.plantBtnHoverImages = plantBtnHoverImages;
        this.potBtnImages = potBtnImages;
        this.potBtnHoverImages = potBtnHoverImages;
        this.width = width;
        this.height = height;
        currentSelectedPlant = -1;
        currentSelectedPot = -1;

        setLayout(new BorderLayout());
        setSize(width, height);

        JPanel plantAndPotPanel = plantAndPotPanel();
        this.add(plantAndPotPanel, BorderLayout.CENTER);

        //Adds navigation JPanel
        JPanel navigation = navigation();
        add(navigation, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Creates a JPanel that adds all PlantTypes by looping through the PlantButton ArrayList to
     * create buttons of each PlantType
     * @return JPanel plants
     * @author Elvira Grubb
     */
    public JPanel plantAndPotPanel()
    {
        JPanel plants = new JPanel();
        plants.setLayout(new GridLayout(2, 1));
        plants.setPreferredSize(new Dimension(plantBtnImages.size() * 132, 320));
        plants.add(plantPanel());
        plants.add(potPanel());

        return plants;
    }

    /**
     * Creates a JScrollPane and adds plant buttons to it
     * @return A JScrollPane containing plant buttons
     * @author Elvira Grubb
     */
    public JScrollPane plantPanel()
    {
        JPanel plantPanel = new JPanel();
        plantPanel.setBackground(new java.awt.Color(184, 200, 177));
        plantPanel.setLayout(new GridLayout(1, plantBtnImages.size()));
        plantPanel.setPreferredSize(new Dimension(plantBtnImages.size() * 132, 320 / 2));

        for (int i = 0; i < plantBtnImages.size(); i++)
        {
            JButton plantBtn = new JButton();
            plantBtn.setSize(new Dimension(132, 160));
            plantBtn.setBorder(BorderFactory.createEmptyBorder());
            plantBtn.setContentAreaFilled(false);
            plantBtn.setIcon(plantBtnImages.get(i));
            ImageIcon shadow = plantBtnHoverImages.get(i);

            plantBtn.setFocusPainted(false);
            plantBtn.setRolloverEnabled(true);
            plantBtn.setRolloverIcon(shadow);

            plantButtons.add(plantBtn);
            //ActionListener that will return the ArrayList number when the plant is pressed
            int plantNumber = i;
            plantBtn.addActionListener(l -> plantPressed(plantNumber));
            plantPanel.add(plantBtn);
        }

        JScrollPane scrollPlantPanel = new JScrollPane(plantPanel);
        return scrollPlantPanel;
    }

    /**
     * Creates a JScrollPane and adds pot buttons to it
     * @return A JScrollPane containing pot buttons
     * @author Elvira Grubb
     */
    public JScrollPane potPanel()
    {
        JPanel potPanel = new JPanel();
        potPanel.setBackground(new java.awt.Color(184, 200, 177));
        potPanel.setLayout(new GridLayout(1, potBtnImages.size()));
        potPanel.setPreferredSize(new Dimension(potBtnImages.size() * 132, 320 / 2));

        for (int i = 0; i < potBtnImages.size(); i++)
        {
            JButton plantBtn = new JButton();
            plantBtn.setSize(new Dimension(132, 160));
            plantBtn.setBorder(BorderFactory.createEmptyBorder());
            plantBtn.setContentAreaFilled(false);
            plantBtn.setIcon(potBtnImages.get(i));
            ImageIcon shadow = potBtnHoverImages.get(i);

            plantBtn.setFocusPainted(false);
            plantBtn.setRolloverEnabled(true);
            plantBtn.setRolloverIcon(shadow);

            potButtons.add(plantBtn);
            //ActionListener that will return the ArrayList number when the plant is pressed
            int potNumber = i;
            plantBtn.addActionListener(l -> potPressed(potNumber));
            potPanel.add(plantBtn);
        }

        JScrollPane scrollPotPanel = new JScrollPane(potPanel);
        return scrollPotPanel;
    }

    /**
     * Adds a navigation panel with a back button
     * @return JPanel the navigation panel
     * @author Elvira Grubb (main)
     * @author Petri Närhi (edits)
     */
    public JPanel navigation()
    {
        JPanel navigation = new JPanel();
        navigation.setLayout(new FlowLayout());
        navigation.setPreferredSize(new Dimension(width, (height / 10)));

        JButton backBtn = new JButton();
        backBtn.setBorder(BorderFactory.createEmptyBorder());
        backBtn.setContentAreaFilled(false);
        backBtn.setIcon(new ImageIcon("images/buttons/back.png"));

        backBtn.setFocusPainted(false);
        backBtn.setRolloverEnabled(true);

        backBtn.setRolloverIcon(new ImageIcon("images/buttons/back-hover.png"));

        backBtn.addActionListener(l -> backPressed());
        backBtn.setLocation(100,100);

        JButton confirm = new JButton();
        confirm.setIcon(new ImageIcon("images/buttons/confirm.png"));
        confirm.setBorder(BorderFactory.createEmptyBorder());
        confirm.setContentAreaFilled(false);

        confirm.setRolloverEnabled(true);
        confirm.setRolloverIcon(new ImageIcon("images/buttons/confirm_hover.png"));
        confirm.addActionListener(l -> confirmButtonpressed());

        navigation.add(backBtn);
        navigation.add(confirm);
        navigation.setVisible(true);

        return navigation;
    }

    /**
     * Called when actionlistener notices the back button has been pressed
     * @author Petri Närhi
     */
    private void backPressed()
    {
        buttonPressedSoundEffect();
        controller.showMainMenu();
    }

    private void confirmButtonpressed()
    {
        buttonPressedSoundEffect();
        if (currentSelectedPlant == -1)
        {
            JOptionPane.showMessageDialog(this, "Please select a plant.");
        }

        else if (currentSelectedPot == -1)
        {
            JOptionPane.showMessageDialog(this, "Please select a pot.");
        }

        else
        {
            controller.confirmPlant(currentSelectedPlant, currentSelectedPot);
        }
    }

    /**
     * This method sets the selected plant icon to indicate it's been selected, and saves which plant is selected as an int
     * @param plant An int corresponding to the PlantTypes
     * @author Elvira Grubb
     */
    private void plantPressed(int plant)
    {
        if (currentSelectedPlant != -1)
        {
            plantButtons.get(currentSelectedPlant).setIcon(plantBtnImages.get(currentSelectedPlant));
        }

        buttonPressedSoundEffect();
        currentSelectedPlant = plant;
        plantButtons.get(plant).setIcon(plantBtnHoverImages.get(plant));
        System.out.println(plant);
    }

    /**
     * This method sets the selected pot icon to indicate it's been selected, and saves which pot is selected as an int
     * @param pot An int corresponding to the pots
     * @author Elvira Grubb
     */
    private void potPressed(int pot)
    {
        if (currentSelectedPot != -1)
        {
            potButtons.get(currentSelectedPot).setIcon(potBtnImages.get(currentSelectedPot));
        }

        buttonPressedSoundEffect();
        currentSelectedPot = pot;
        potButtons.get(pot).setIcon(potBtnHoverImages.get(pot));
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