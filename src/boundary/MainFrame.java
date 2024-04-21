package boundary;

import controller.Controller;
import entity.Plant;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    int width = 550;
    int height = 435;
    PlantView plantView;
    Controller controller;

    public MainFrame(Controller controller)
    {
        this.controller = controller;
        setLayout(new BorderLayout());
        setTitle("Virtual Plant Widgets");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        ImageIcon icon = new ImageIcon("images/icon.png");
        setIconImage(icon.getImage());
        getContentPane().setPreferredSize(new Dimension(width, height));
        pack();
        setVisible(true);

    }

    public void addPlantView()
    {
        System.out.println("hej");
        this.getContentPane().removeAll();
        this.revalidate();

        plantView = new PlantView(width, height, controller, controller.getCurrentPlant());
        this.setContentPane(plantView);
        this.repaint();
    }

    public void addMainMenu()
    {
        MainMenu mainMenu = new MainMenu(width, height, controller);
        this.setContentPane(mainMenu);
    }
}
