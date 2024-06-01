package boundary;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * GUI for the Storage Panel
 * Shows all the plants the user has created and lets you choose between them
 * @author Petri Närhi
 * @author Elvira Grubb
 * */
public class StoragePanel extends JPanel {
    private Controller controller;
    private int width;
    private int height;
    private ArrayList<ImageIcon> plantBtnImages;
    private ArrayList<ImageIcon> plantBtnHoverImages;
    private ArrayList<JButton> plantButtons = new ArrayList<>();
    private int currentSelectedPlant = -1;

    /**
     * This method creates the Storage JPanel
     * @param controller The active controller class used in the program
     * @param plantBtnImages An ArrayList of plantbutton images
     * @param plantBtnHoverImages An ArrayList of plantbutton hover images
     * @param width The width of the MainFrame
     * @param height The height of the MainFrame
     * @author Elvira Grubb
     * @author Petri Närhi
     */
    public StoragePanel(Controller controller, ArrayList<ImageIcon> plantBtnImages, ArrayList<ImageIcon> plantBtnHoverImages, int width, int height)
    {
        super(null);
        this.controller = controller;
        System.out.println("You're in StoragePanel");
        this.plantBtnImages = plantBtnImages;
        this.plantBtnHoverImages = plantBtnHoverImages;
        this.width = width;
        this.height = height;

        setLayout(new BorderLayout());
        setSize(width, height);
        ImageIcon icon = new ImageIcon("images/icon.png");

        JScrollPane plantPanel= plantPanel();
        this.add(plantPanel, BorderLayout.CENTER);

        //Adds navigation JPanel
        JPanel navigation = navigation();
        add(navigation, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Creates a scrollable pane for the storedPlantsPanel
     * which includes all the plant buttons
     * @return JScrollPane plantPanel
     * @author Elvira Grubb (main)
     * @author Petri Närhi (edits)
     */
    public JScrollPane plantPanel()
    {
        JPanel plantPanel = new JPanel();
        plantPanel.setBackground(new java.awt.Color(184, 200, 177));
        plantPanel.setLayout(new GridLayout(2, plantBtnImages.size()));
        plantPanel.setPreferredSize(new Dimension(plantBtnImages.size() * 132/2, 320));

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

            plantButtons.add(plantBtn);
            //ActionListener that will return the ArrayList number when the plant is pressed
            int plantNumber = i;
            plantBtn.addActionListener(l -> plantPressed(plantNumber));
            plantPanel.add(plantBtn);
        }

        JScrollPane scrollPlantPanel = new JScrollPane(plantPanel);
        return scrollPlantPanel;
    }

    /**
     * Adds a navigation panel with a back button
     * @return JPanel the navigation panel
     * @author Elvira Grubb (main)
     * @author Petri Närhi (edits)
     */
    public JPanel navigation()
    {
        //creates panel for buttons
        JPanel navigation = new JPanel();
        navigation.setLayout(new FlowLayout());
        navigation.setPreferredSize(new Dimension(width, (height / 10)));

        //creates back button
        JButton backBtn = new JButton();
        backBtn.setBorder(BorderFactory.createEmptyBorder());
        backBtn.setContentAreaFilled(false);
        backBtn.setIcon(new ImageIcon("images/buttons/back.png"));

        backBtn.setFocusPainted(false);
        backBtn.setRolloverEnabled(true);
        backBtn.setRolloverIcon(new ImageIcon("images/buttons/back-hover.png"));

        backBtn.addActionListener(l -> backPressed());
        backBtn.setLocation(100,100);

        //creates confirm button
        JButton confirm = new JButton();
        confirm.setIcon(new ImageIcon("images/buttons/confirm.png"));
        confirm.setBorder(BorderFactory.createEmptyBorder());
        confirm.setContentAreaFilled(false);

        confirm.setRolloverEnabled(true);
        confirm.setRolloverIcon(new ImageIcon("images/buttons/confirm_hover.png"));
        confirm.addActionListener(l -> confirmButtonpressed());

        //creates delete button
        JButton delete = new JButton();
        delete.setIcon(new ImageIcon("images/buttons/delete.png"));
        delete.setBorder(BorderFactory.createEmptyBorder());
        delete.setContentAreaFilled(false);

        delete.setRolloverEnabled(true);
        delete.setRolloverIcon(new ImageIcon("images/buttons/delete-hover.png"));
        delete.addActionListener(l -> deleteButtonpressed());

        //adds all buttons
        navigation.add(backBtn);
        navigation.add(confirm);
        navigation.add(delete);
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
     * Called when a plant is pressed
     * selected plant becomes lighter and the current selected plant
     * @param selectedPlant An int corresponding to the plant chosen
     * @author Elvira Grubb
     * @author Petri Närhi (javadoc only)
     */
    private void plantPressed(int selectedPlant)
    {
        if (currentSelectedPlant != -1)
        {
            plantButtons.get(currentSelectedPlant).setIcon(plantBtnImages.get(currentSelectedPlant));
        }

        //buttonPressedSoundEffect();
        currentSelectedPlant = selectedPlant;
        plantButtons.get(selectedPlant).setIcon(plantBtnHoverImages.get(selectedPlant));
    }

    /**
     * Called when the confirm button is pressed
     * if selected plant is not -1, it becomes the current plant in Controller
     * to be shown in PlantView
     * @author Elvira Grubb
     * @author Petri Närhi
     */
    private void confirmButtonpressed()
    {
        //buttonPressedSoundEffect();
        if (currentSelectedPlant == -1)
        {
            JOptionPane.showMessageDialog(this, "Please select a plant.");
        }
        else
        {
            controller.setCurrentPlant(currentSelectedPlant);
            controller.showPlantView();
        }
    }

    /**
     * Called when the delete button is pressed
     * if selected plant is not -1, and the user is sure, it will be deleted
     * and the storage will be updated
     * @author Petri Närhi
     */
    private void deleteButtonpressed()
    {
        //buttonPressedSoundEffect();
        if (currentSelectedPlant == -1)
        {
            JOptionPane.showMessageDialog(this, "Please select a plant.");
        }
        else
        {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this plant?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                controller.deletePlant(currentSelectedPlant);
                controller.createStorage();
            }
        }
    }
}