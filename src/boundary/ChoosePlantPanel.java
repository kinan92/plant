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
        this.potBtnImages = controller.getImageListFromFile("files/potButtons.txt");
        this.width = width;
        this.height = height;

        setLayout(new BorderLayout());
        setSize(width, height);
        ImageIcon icon = new ImageIcon("images/icon.png");

       /* JScrollPane scroll = new JScrollPane(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
        );
        add(scroll);*/

        JPanel plants = plants();
        JScrollPane scroller = new JScrollPane(plants);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        add(scroller, BorderLayout.CENTER);


        //Adds plants JPanel with a scrollbar
        /*JPanel plantAndPotsPanel = plantsAndPotsPanel();
        JScrollPane scroller = new JScrollPane(plantAndPotsPanel);
        this.add(scroller, BorderLayout.CENTER);*/

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
        plants.setLayout(new GridLayout(1, plantBtnImages.size()));
        plants.setPreferredSize(new Dimension(plantBtnImages.size() * 132, 320 / 2));

        //Loops through plantImages and creates buttons with the images
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

            //ActionListener that will return the ArrayList number when the plant is pressed
            int plantNumber = i;
            plantBtn.addActionListener(l -> plantPressed(plantNumber));
            plants.add(plantBtn);
        }
        
        return plants;
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

        ImageIcon backButton = new ImageIcon("images/buttons/back.png"); // load the image to a imageIcon
        Image image = backButton.getImage(); // transform it
        Image officialBackButton = image.getScaledInstance(35, 35,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        backButton = new ImageIcon(officialBackButton);
        backBtn.setIcon(backButton);


        backBtn.setFocusPainted(false);
        backBtn.setRolloverEnabled(true);

        ImageIcon backButtonHover = new ImageIcon("images/buttons/back-hover.png"); // load the image to a imageIcon
        Image imageHover = backButton.getImage(); // transform it
        Image officialBackButtonHover = image.getScaledInstance(35, 35,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        backButtonHover = new ImageIcon(officialBackButton);
        backBtn.setIcon(backButton);
        backBtn.setRolloverIcon(backButtonHover);

        //ActionListener that will return the ArrayList number when the plant is pressed
        backBtn.addActionListener(l -> backPressed());
        backBtn.setLocation(100,100);
        navigation.add(backBtn);
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
