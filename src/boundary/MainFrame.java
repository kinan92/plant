package boundary;

import boundary.PlantView.PlantView;
import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private int width = 520;
    private int height = 460;
    private PlantView plantView;
    private Controller controller;
    private ChoosePlantPanel choosePlantPanel;
    private ConfirmPlantPanel confirmPlantPanel;
    private StoragePanel storagePanel;

    /**
     * @author Elvira Grubb (main)
     * @author Petri Närhi (edits)
     * @param controller Current controller class used in the program
     * This constructor creates a MainFrame for the whole program
     */
    public MainFrame(Controller controller)
    {
        this.controller = controller;
        setTitle("Virtual Plant Widgets");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                windowClose();
            }
        });
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        ImageIcon icon = new ImageIcon("images/icon.png");
        setIconImage(icon.getImage());
        getContentPane().setPreferredSize(new Dimension(width, height));
        pack();
        setVisible(true);
    }

    /**
     * Close operation for mainframe
     * interrupts autosave thread, saves user data,
     * disposes gui then terminates program
     * @author Petri Närhi
     * */
    private void windowClose() {
        /*controller.autoSave(false);
        controller.saveUserData();*/
        dispose();
        System.exit(0);
    }

    /**
     * @author Elvira Grubb
     * This method clears the MainFrame and adds the boundary.PlantView.PlantView panel
     */
    public void addPlantView()
    {
        this.getContentPane().removeAll();
        this.revalidate();
        plantView = new PlantView(width, height, controller);
        this.setContentPane(plantView);
        this.repaint();
    }

    public void confirmPlantPanel(ImageIcon selectedPlant, ImageIcon selectedPot, int plantNumber, int potNumber, ArrayList<String> plantInformation)
    {
        this.getContentPane().removeAll();
        this.revalidate();
        confirmPlantPanel = new ConfirmPlantPanel(width, height, selectedPlant, selectedPot, plantNumber, potNumber, plantInformation, controller);
        this.setContentPane(confirmPlantPanel);
        this.repaint();
    }


    /**
     * Gets the current PlantView instance associated with the MainFrame
     * @return the current PlantView instance
     * @author Aleksander Augustyniak
     */
    public PlantView getPlantView(){
        return plantView;
    }

    /**
     * @author Elvira Grubb
     * @param plantImage An ArrayList of all PlantType images
     * @param plantImageHover An ArrayList of all PlantType hover images
     * This method clears the MainFrame and adds ChoosePlantPanel
     */
    public void addChoosePlantPanel(ArrayList<ImageIcon> plantImage, ArrayList<ImageIcon> plantImageHover, ArrayList<ImageIcon> potImage, ArrayList<ImageIcon> potImageHover)
    {
        this.getContentPane().removeAll();
        this.revalidate();
        choosePlantPanel = new ChoosePlantPanel(controller, plantImage, plantImageHover, potImage, potImageHover, width, height);
        this.setContentPane(choosePlantPanel);
        this.repaint();
    }

    /**
     * @author Elvira Grubb
     * This method adds the MainMenu to the MainFrame
     */
    public void addMainMenu()
    {
        MainMenu mainMenu = new MainMenu(width, height, controller);
        this.setContentPane(mainMenu);
    }

    /**
     * Clears the MainFrame and adds StoragePanel
     * @author Petri Närhi
     * @param plantBtnImages An ArrayList of all plant button images
     * @param plantBtnHoverImages An ArrayList of all plant button hover images
     */
    public void addStoragePanel(ArrayList<ImageIcon> plantBtnImages, ArrayList<ImageIcon> plantBtnHoverImages)
    {
        this.getContentPane().removeAll();
        this.revalidate();
        storagePanel = new StoragePanel(controller, plantBtnImages, plantBtnHoverImages, width, height);
        this.setContentPane(storagePanel);
        this.repaint();
    }
}