package boundary;

import controller.Controller;

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
        setTitle("Virtual Plant Widgets");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        ImageIcon icon = new ImageIcon("images/icon.png");
        setIconImage(icon.getImage());

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        this.getContentPane().setPreferredSize(new Dimension(width, height));
        this.pack();
        setVisible(true);
    }

    public void addPlantView()
    {
        System.out.println("hej");
        this.getContentPane().removeAll();
        this.revalidate();
        this.plantView = new PlantView(width, height, controller);
        this.setContentPane(plantView);
        this.repaint();
    }

    public void addMainMenu()
    {
        MainMenu mainMenu = new MainMenu(width, height, controller);
        this.setContentPane(mainMenu);
    }
}
