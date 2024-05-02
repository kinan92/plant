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
    private ArrayList<ImageIcon> plantImages;
    private ArrayList<ImageIcon> plantHoverImages;

    //Creates ChoosePlantFrame JFrame
    public ChoosePlantFrame(ArrayList<ImageIcon> plantImages, ArrayList<ImageIcon> plantHoverImages)
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
        setResizable(true);

        //Creates a scrollbar to enable scrolling for the plants
        JScrollPane scroll = new JScrollPane(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
        );
        add(scroll);

        JPanel plants = plants();
        JScrollPane scroller = new JScrollPane(plants);
        this.getContentPane().add(scroller, BorderLayout.CENTER);

        JPanel navigation = navigation();
        add(navigation, BorderLayout.SOUTH);

        setVisible(true);
    }

    //Loops through all PlantTypes and creates a button for each of them
    public JPanel plants()
    {
        JPanel plants = new JPanel();
        plants.setLayout(new GridLayout(2, plantImages.size()));
        plants.setPreferredSize(new Dimension(width, (height / 10) * 7));

        //Loops through plantImages and creates buttons with the images
        for (int i = 0; i < plantImages.size(); i++)
        {
            JButton plantBtn = new JButton();
            plantBtn.setBorder(BorderFactory.createEmptyBorder());
            plantBtn.setContentAreaFilled(false);
            System.out.println(plantImages.get(i));
            plantBtn.setIcon(plantImages.get(i));
            ImageIcon shadow = plantHoverImages.get(i);

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
    private void backPressed() {
    }

    //This method will be replaced by a method that calls a method in the Controller to notify
    //that a plant has been chosen and which plant has been chosen (via the index that will
    //correspond to an index in the PlantType ArrayList)
    private void plantPressed(int plant)
    {
        System.out.println(plant);
    }
}
