package boundary;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChoosePlantPanel extends JPanel {
    private Controller controller;
    private int width;
    private int height;
    private ArrayList<ImageIcon> plantBtnImages;
    private ArrayList<ImageIcon> plantBtnHoverImages;
    private ArrayList<ImageIcon> potBtnImages;
    private ArrayList<ImageIcon> potBtnHoverImages;

    /**
     * @author Elvira Grubb
     * @param controller The active controller class used in the program
     * @param plantBtnImages An ArrayList of plantbutton images
     * @param plantBtnHoverImages An ArrayList of plantbutton hover images
     * @param width The width of the MainFrame
     * @param height The height of the MainFrame
     * This method creates the base JPanel for the ChoosePlantPanel and adds relevant panels onto it
     */
    public ChoosePlantPanel(Controller controller, ArrayList<ImageIcon> plantBtnImages, ArrayList<ImageIcon> plantBtnHoverImages, int width, int height)
    {
        super(null);
        this.controller = controller;
        System.out.println("You're in ChoosePlantFrame");
        this.plantBtnImages = plantBtnImages;
        this.plantBtnHoverImages = plantBtnHoverImages;
        //this.potBtnImages = controller.getImageListFromFile("files/potButtons.txt");
        this.width = width;
        this.height = height;

        setLayout(new BorderLayout());
        setSize(width, height);
        ImageIcon icon = new ImageIcon("images/icon.png");

        //Adds plants JPanel with a scrollbar
        JPanel plants = plants();
        JScrollPane scroller = new JScrollPane(plants);
        this.add(scroller, BorderLayout.CENTER);

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
    public JPanel plants()
    {
        JPanel plants = new JPanel();
        plants.setLayout(new GridLayout(2, plantBtnImages.size()));
        plants.setPreferredSize(new Dimension((width*9/10), (height / 10) * 7));

        //Loops through plantImages and creates buttons with the images
        for (int i = 0; i < plantBtnImages.size(); i++)
        {
            JButton plantBtn = new JButton();
            plantBtn.setBorder(BorderFactory.createEmptyBorder());
            plantBtn.setContentAreaFilled(false);
            System.out.println(plantBtnImages.get(i));
            plantBtn.setIcon(plantBtnImages.get(i));
            ImageIcon shadow = plantBtnHoverImages.get(i);

            //Disables any default JButton effects
            plantBtn.setFocusPainted(false);
            plantBtn.setRolloverEnabled(true);
            plantBtn.setRolloverIcon(shadow);

            //ActionListener that will return the ArrayList number when the plant is pressed
            int plantNumber = i;
            plantBtn.addActionListener(l -> plantPressed(plantNumber));
            plants.add(plantBtn);
        }
        return plants;
    }

    /**
     * @author Petri Närhi */
    public JPanel navigation()
    {
        JPanel navigation = new JPanel();
        navigation.setLayout(new FlowLayout());
        navigation.setBackground(Color.ORANGE);
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
        navigation.add(backBtn);
        navigation.setVisible(true);

        return navigation;
    }

    /**
     * @author Petri Närhi */
    private void backPressed()
    {
        controller.showMainMenu();
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
        controller.createPlant(plant);
        System.out.println(plant);
    }
}
