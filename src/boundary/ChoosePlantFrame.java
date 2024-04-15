package boundary;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ChoosePlantFrame extends JFrame{
    private Controller controller;
    int width = 550;
    int height = 470;
    private ArrayList<String> plantImages = new ArrayList<>();
    private ArrayList<String> plantHoverImages = new ArrayList<>();
    //Constructor will receive controller and an ArrayList of PlantType when created
    public ChoosePlantFrame(ArrayList<String> plantImages, ArrayList<String> plantHoverImages)
    {
        System.out.println("You're in ChoosePlantFrame");
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

    public JPanel plants()
    {
        JPanel plants = new JPanel();
        plants.setLayout(new GridLayout(2, plantImages.size()));

        //Loops through plantImages and creates buttons with the images
        for (int i = 0; i < plantImages.size(); i++)
        {
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
