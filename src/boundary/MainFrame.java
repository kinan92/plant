package boundary;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    int width = 550;
    int height = 435;
    PlantView plantView;
    Controller controller;
    ChoosePlantPanel choosePlantPanel;

    //Creates a MainFrame for the whole program
    //and sets settings for the MainFrame
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
        getContentPane().setPreferredSize(new Dimension(width, height));
        pack();
        setVisible(true);
    }

    //Called from the Controller to clear the MainFrame and add
    //a PlantView JPanel
    public void addPlantView()
    {
        this.getContentPane().removeAll();
        this.revalidate();
        plantView = new PlantView(width, height, controller);
        this.setContentPane(plantView);
        this.repaint();
    }

    public void addChoosePlantView(ArrayList<ImageIcon> plantImage, ArrayList<ImageIcon> plantImageHover)
    {
        this.getContentPane().removeAll();
        this.revalidate();
        choosePlantPanel = new ChoosePlantPanel(controller, plantImage, plantImageHover, width, height);
        this.setContentPane(choosePlantPanel);
        this.repaint();
    }

    //Adds MainMenu JPanel to the MainFrame
    public void addMainMenu()
    {
        MainMenu mainMenu = new MainMenu(width, height, controller);
        this.setContentPane(mainMenu);
    }
}
