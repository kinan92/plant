package boundary;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StoragePanel extends JPanel {
    private Controller controller;
    private int width;
    private int height;
    private ArrayList<ImageIcon> plantBtnImages;
    private ArrayList<ImageIcon> plantBtnHoverImages;
    private ArrayList<JButton> plantButtons = new ArrayList<>();
    private int currentSelectedPlant;

    /**
     * @author Elvira Grubb
     * @param controller The active controller class used in the program
     * @param plantBtnImages An ArrayList of plantbutton images
     * @param plantBtnHoverImages An ArrayList of plantbutton hover images
     * @param width The width of the MainFrame
     * @param height The height of the MainFrame
     * This method creates the base JPanel for the ChoosePlantPanel and adds relevant panels onto it
     */
    public StoragePanel(Controller controller, ArrayList<ImageIcon> plantBtnImages, ArrayList<ImageIcon> plantBtnHoverImages, int width, int height)
    {
        super(null);
        this.controller = controller;
        System.out.println("You're in StoragePanel");
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
        ImageIcon icon = new ImageIcon("images/icon.png");

        JPanel plantAndPotPanel = plantAndPotPanel();
        this.add(plantAndPotPanel, BorderLayout.CENTER);

        //Adds navigation JPanel
        JPanel navigation = navigation();
        add(navigation, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * @author Elvira Grubb
     * @return JPanel plants
     * Creates a JPanel that adds all PlantTypes by looping through the PlantButton ArrayList to
     * create buttons of each PlantType
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

        JButton confirm = new JButton("Confirm");
        confirm.setPreferredSize(new Dimension(135, 35));
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
        controller.showMainMenu();
    }

    private void confirmButtonpressed()
    {
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
            //controller.createPlant(currentSelectedPlant, currentSelectedPot);
        }
    }

    /**
     * @author Elvira Grubb
     * @param plant An int corresponding to the PlantTypes
     * This method will be replaced by a method that calls a method in the Controller to notify
     * that a plant has been chosen and which plant has been chosen (via the index that will
     *  correspond to an index in the PlantType ArrayList)
     */
    private void plantPressed(int plant)
    {
        if (currentSelectedPlant != -1)
        {
            plantButtons.get(currentSelectedPlant).setIcon(plantBtnImages.get(currentSelectedPlant));
        }
        currentSelectedPlant = plant;
        plantButtons.get(plant).setIcon(plantBtnHoverImages.get(plant));
        System.out.println(plant);
    }

    private void potPressed(int pot)
    {
        if (currentSelectedPot != -1)
        {
            potButtons.get(currentSelectedPot).setIcon(potBtnImages.get(currentSelectedPot));
        }

        currentSelectedPot = pot;
        potButtons.get(pot).setIcon(potBtnHoverImages.get(pot));
    }
}
