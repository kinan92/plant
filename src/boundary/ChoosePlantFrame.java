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
    private List<ImageIcon> plantImages = new ArrayList<>();
    private List<ImageIcon> plantHoverImages = new ArrayList<>();
    //Constructor will receive controller and an ArrayList of PlantType when created
    public ChoosePlantFrame()
    {
        getArrayList();
        getHoverArrayList();
        setLayout(new BorderLayout());
        setTitle("Virtual Plant Widgets");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
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

    //Metoden är enbart för test och kommer att ersättas
    //Klassen kommer att ha en ArrayList av PlantType från controllern och i denna metoden kommer
    //bilder att hämtas genom den ArrayListen
    private void getArrayList()
    {
        //ImageIcon elefantora = new ImageIcon("images/elefantora.png");
        ImageIcon elefantora = new ImageIcon(new ImageIcon("images/plants/elefantora.png").getImage().getScaledInstance(154, 192, Image.SCALE_DEFAULT));
        ImageIcon snakeplant = new ImageIcon(new ImageIcon("images/plants/snakeplant.png").getImage().getScaledInstance(154, 192, Image.SCALE_DEFAULT));
        ImageIcon cactus = new ImageIcon(new ImageIcon("images/plants/cactus1.png").getImage().getScaledInstance(154, 192, Image.SCALE_DEFAULT));

        plantImages.add(elefantora);
        plantImages.add(snakeplant);
        plantImages.add(cactus);
        plantImages.add(elefantora);
        plantImages.add(snakeplant);
        plantImages.add(cactus);
        plantImages.add(elefantora);
        plantImages.add(snakeplant);
        plantImages.add(cactus);
    }

    //Metoden är enbart för test och kommer att ersättas
    //Klassen kommer att ha en ArrayList av PlantType från controllern och i denna metoden kommer
    //bilder att hämtas genom den ArrayListen
    private void getHoverArrayList()
    {
        //ImageIcon elefantora = new ImageIcon("images/elefantora.png");
        ImageIcon elefantora = new ImageIcon(new ImageIcon("images/plants/elefantora_hover.png").getImage().getScaledInstance(154, 192, Image.SCALE_DEFAULT));
        ImageIcon snakeplant = new ImageIcon(new ImageIcon("images/plants/snakeplant_hover.png").getImage().getScaledInstance(154, 192, Image.SCALE_DEFAULT));
        ImageIcon cactus = new ImageIcon(new ImageIcon("images/plants/cactus1_hover.png").getImage().getScaledInstance(154, 192, Image.SCALE_DEFAULT));

        plantHoverImages.add(elefantora);
        plantHoverImages.add(snakeplant);
        plantHoverImages.add(cactus);
        plantHoverImages.add(elefantora);
        plantHoverImages.add(snakeplant);
        plantHoverImages.add(cactus);
        plantHoverImages.add(elefantora);
        plantHoverImages.add(snakeplant);
        plantHoverImages.add(cactus);
    }

    public JPanel plants()
    {
        JPanel plants = new JPanel();
        plants.setBackground(Color.PINK);
        plants.setLayout(new GridLayout(2, plantImages.size()));

        //Loops through plantImages and creates buttons with the images
        for (int i = 0; i < plantImages.size(); i++)
        {
            JButton plant = new JButton();
            plant.setBorder(BorderFactory.createEmptyBorder());
            plant.setContentAreaFilled(false);
            plant.setIcon(plantImages.get(i));
            ImageIcon shadow = plantHoverImages.get(i);
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
