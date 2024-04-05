package boundary;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChoosePlantFrame extends JFrame{
    private JLabel plantName;
    int width = 550;
    int height = 470;
    private List<ImageIcon> plantImages = new ArrayList<>();
    public ChoosePlantFrame()
    {
        getArrayList();
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
        ImageIcon elefantora = new ImageIcon(new ImageIcon("images/elefantora.png").getImage().getScaledInstance(154, 192, Image.SCALE_DEFAULT));
        ImageIcon snakeplant = new ImageIcon(new ImageIcon("images/snakeplant.png").getImage().getScaledInstance(154, 192, Image.SCALE_DEFAULT));
        ImageIcon cactus = new ImageIcon(new ImageIcon("images/cactus1.png").getImage().getScaledInstance(154, 192, Image.SCALE_DEFAULT));

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

            //ActionListener that will return the ArrayList number when the plant is pressed
            int plantNumber = i;
            plant.addActionListener(l -> plantPressed(plantNumber));
            plants.add(plant);
        }
        return plants;
    }

    private void plantPressed(int plant)
    {
        System.out.println(plant);
    }
}
