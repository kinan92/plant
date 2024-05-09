package boundary;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChoosePlantFrame extends JFrame{
    private Controller controller;
    private int width;
    private int height;
    private ArrayList<ImageIcon> plantBtnImages;
    private ArrayList<ImageIcon> plantBtnHoverImages;
    private ArrayList<ImageIcon> potBtnImages;
    private ArrayList<ImageIcon> potBtnHoverImages;

    //Creates ChoosePlantFrame JFrame
    public ChoosePlantFrame(Controller controller, ArrayList<ImageIcon> plantBtnImages, ArrayList<ImageIcon> plantBtnHoverImages, int width, int height)
    {
        super(null);
        this.controller = controller;
        System.out.println("You're in ChoosePlantFrame");
        this.plantBtnImages = plantBtnImages;
        this.plantBtnHoverImages = plantBtnHoverImages;
        //this.potBtnImages = controller.getImageListFromFile("files/potButtons.txt");
        this.width = width;
        this.height = height;
    int width = 550;
    int height = 470;
    private ArrayList<String> plantImages = new ArrayList<>();
    private ArrayList<String> plantHoverImages = new ArrayList<>();

    //Creates ChoosePlantFrame JFrame
    public ChoosePlantFrame(ArrayList<String> plantImages, ArrayList<String> plantHoverImages)
    {
        this.plantImages = plantImages;
        this.plantHoverImages = plantHoverImages;

        setLayout(new BorderLayout());
        setTitle("Virtual Plant Widgets");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        ImageIcon icon = new ImageIcon("images/icon.png");
        setIconImage(icon.getImage());
        setLocationRelativeTo(null);
        setResizable(false);

        //Creates a scrollbar to enable scrolling for the plants
        JScrollPane scroll = new JScrollPane(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
        );
        add(scroll);

        JPanel plants = plants();
        JScrollPane scroller = new JScrollPane(plants);
        this.getContentPane().add(scroller, BorderLayout.CENTER);

        setVisible(true);
    }

    //Loops through all PlantTypes and creates a button for each of them
    public JPanel plants()
    {
        JPanel plants = new JPanel();
        plants.setLayout(new GridLayout(2, plantBtnImages.size()));
        plants.setPreferredSize(new Dimension((width*9/10), (height / 10) * 7));
        plants.setLayout(new GridLayout(2, plantImages.size()));

        //Loops through plantImages and creates buttons with the images
        for (int i = 0; i < plantBtnImages.size(); i++)
        {
            JButton plantBtn = new JButton();
            plantBtn.setBorder(BorderFactory.createEmptyBorder());
            plantBtn.setContentAreaFilled(false);
            System.out.println(plantBtnImages.get(i));
            plantBtn.setIcon(plantBtnImages.get(i));
            ImageIcon shadow = plantBtnHoverImages.get(i);
            JButton plant = new JButton();
            plant.setBorder(BorderFactory.createEmptyBorder());
            plant.setContentAreaFilled(false);
            System.out.println(plantImages.get(i));
            plant.setIcon(new ImageIcon(plantImages.get(i)));
            ImageIcon shadow = new ImageIcon(plantHoverImages.get(i));

            plant.setFocusPainted(false);
            plant.setRolloverEnabled(true);
            plant.setRolloverIcon(shadow);

            //ActionListener that will return the ArrayList number when the plant is pressed
            int plantNumber = i;
            plant.addActionListener(l -> plantPressed(plantNumber));
            plants.add(plant);
        }
        return plants;
    }

    //This method will be replaced by a method that calls a method in the Controller to notify
    //that a plant has been chosen and which plant has been chosen (via the index that will
    //correspond to an index in the PlantType ArrayList)
    private void plantPressed(int plant)
    {
        System.out.println(plant);
    }
}
