package boundary;
import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class PlantView extends JPanel {
    private JLabel plantName;
    int width;
    int height;
    private JProgressBar waterBar;
    private Controller controller;
    //Temporary ImageIcon of an image that will later be replaced by an image from the Plant class
    private ImageIcon elefantöra = new ImageIcon("images/plants/moneyplant.png");

    //ImageIcons for the various buttons
    private ImageIcon skiphour = new ImageIcon("images/buttons/skiphour.png");
    private ImageIcon storage = new ImageIcon("images/buttons/storage.png");
    private ImageIcon vacationImage = new ImageIcon("images/buttons/vacation.png");
    private ImageIcon widgetImage = new ImageIcon("images/buttons/widget.png");
   private JButton waterPlant;

   //Creates the base PlantView panel, sets rules for the panel and adds other panels
    public PlantView(int width, int height, Controller controller)
    {
        super(null);
        this.width = width;
        this.height = height;
        this.controller = controller;
        System.out.println("hej plantview");
        this.setSize(width, height);
        BorderLayout borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        this.setBackground(Color.ORANGE);


        JPanel plantView = plantView();
        add(plantView, BorderLayout.WEST);

        JPanel sideButtons = sideButtons();
        add(sideButtons, BorderLayout.EAST);
    }

    //Creates a JPanel with various buttons
    public JPanel sideButtons()
    {
        JPanel sideButtons = new JPanel();
        sideButtons.setBackground(Color.LIGHT_GRAY);
        sideButtons.setPreferredSize(new Dimension(220, (height / 5) * 3));
        sideButtons.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // creates the time indication
        /*
        JLabel timeLabel = new JLabel("Time: ");
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        sideButtons.add(timeLabel, c);
 */

        //Creates Plant Storage button
        JButton getPlant = new JButton();
        getPlant.setBorder(BorderFactory.createEmptyBorder());
        getPlant.setContentAreaFilled(false);
        getPlant.setIcon(storage);
        getPlant.setPreferredSize(new Dimension(208, 60));

        //Sets location for Plant Storage button
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;

        //Adds Plant Storage button to panel and adds actionlistener
        sideButtons.add(getPlant, c);
        getPlant.addActionListener(l -> getPlantPressed());
        getPlant.setRolloverEnabled(true);
        getPlant.setRolloverIcon(new ImageIcon("images/buttons/storage_hover.png"));

        //Creates Widget button
        JButton widget = new JButton();
        widget.setBorder(BorderFactory.createEmptyBorder());
        widget.setContentAreaFilled(false);
        widget.setIcon(widgetImage);
        widget.setPreferredSize(new Dimension(208, 60));

        //Sets location for Widget button
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 1;

        //Adds Widget button to panel and adds actionlistener
        sideButtons.add(widget, c);
        widget.addActionListener(l -> widgetPressed());
        widget.setRolloverEnabled(true);
        widget.setRolloverIcon(new ImageIcon("images/buttons/widget_hover.png"));

        //Creates Skip Hour button
        JButton skipHour = new JButton();
        skipHour.setBorder(BorderFactory.createEmptyBorder());
        skipHour.setContentAreaFilled(false);
        skipHour.setIcon(skiphour);
        skipHour.setPreferredSize(new Dimension(208, 60));

        //Sets location for Skip Hour button
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 2;

        //Adds Skip Hour button to panel and adds actionlistener
        sideButtons.add(skipHour, c);
        skipHour.addActionListener(l -> skipHourPressed());
        skipHour.setRolloverEnabled(true);
        skipHour.setRolloverIcon(new ImageIcon("images/buttons/skiphour_hover.png"));
        // skipHour.setRolloverIcon(new ImageIcon(skiphour.getImage().getScaledInstance(skiphour.getIconWidth()-5, skiphour.getIconHeight()-1, Image.SCALE_SMOOTH)));

        //Creates Vacation button
        JButton vacation = new JButton();
        vacation.setBorder(BorderFactory.createEmptyBorder());
        vacation.setContentAreaFilled(false);
        vacation.setIcon(vacationImage);
        vacation.setPreferredSize(new Dimension(208, 60));

        //Sets location for Vacation button
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 3;

        //Adds Vacation button to panel and adds actionlistener
        sideButtons.add(vacation, c);
        vacation.addActionListener(l -> vacationPressed());
        vacation.setRolloverEnabled(true);
        vacation.setRolloverIcon(new ImageIcon("images/buttons/vacation_hover.png"));

        return sideButtons;
    }

    //Method that is called when the Plant Collection button is pressed
    //Method is a work in progress and currently has no functionality.
    //When functionality is added this method will open the user's Plant Storage
    private void getPlantPressed()
    {
        System.out.println("Plant Collection pressed.");
    }

    //Method used when the Widget button is pressed
    //Method creates a widget of the currently open plant
    private void widgetPressed()
    {
    	// test other plants      images/plants/snakeplant.png   images/plants/goldenbarrelcactus.png  images/plants/bunnyear.png  images/plants/moneyplant.png
    	SwingUtilities.invokeLater(() -> {
    									//plant	path						pot path
            new MargePlantAndPotWidget("images/plants/snakeplant.png", "images/pots/pot-with-bow-tie2.png",waterPlant);
            
        });
        System.out.println("Widget pressed.");
    }

    //Method will be called when the Skip Hour button is pressed
    //Method is a work in progress, functionality will be added later
    private void skipHourPressed()
    {
        /*
        System.out.println("Skip hour pressed.");
        int hoursToSkip = 1;
        controller.skipTime(hoursToSkip);
        System.out.println("Skipped " + hoursToSkip + " hour(s).");

         */
    }

    //Method used when Vacation button is pressed
    //Method is a work in progress
    //When method is done this method will allow the user to set the program to vacation mode
    private void vacationPressed()
    {
        System.out.println("Vacation pressed.");
    }

    //Method used when water button is pressed
    //Method is a work in progress
    private void waterPressed()
    {
        System.out.println("Water pressed.");
        controller.waterPlant();
        updateWaterLevel(controller.getPlantWaterLevel());
        System.out.println("Water level: " + controller.getPlantWaterLevel());
    }

    //NameView creates a JPanel containing the active plant's name, species name and age
    public JPanel nameView()
    {
        JPanel nameView = new JPanel();
        nameView.setPreferredSize(new Dimension(256, height / 7));
        nameView.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Creates JLabel with the active plant's name
        //Name will later be replaced by a name from the active plant object
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        JLabel plantName = new JLabel("Bob");
        plantName.setFont(new Font("Calibri", Font.PLAIN, 26));
        nameView.add(plantName, c);

        //Creates JLabel with the active plant's species
        //Species will later be replaced by a species name from the active plant object
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 1;
        JLabel plantSpecies = new JLabel("Species: Elefantöra");
        plantSpecies.setFont(new Font("Calibri", Font.PLAIN, 16));
        nameView.add(plantSpecies, c);

        return nameView;
    }

    //Creates a JPanel that shows the plant and adds other JPanels that shows the plant's name and water care and levels
    public JPanel plantView()
    {
        JPanel plantView = new JPanel();
        plantView.setPreferredSize(new Dimension(256, height));
        plantView.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Calls to add a NameView JPanel
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        plantView.add(nameView(), c);

        //Adds Plant image to JPanel
        //The ImageIcons used are just for testing and will later be replaced with
        //ImageIcons made from Strings taken from the active plant object
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        JLabel plantImage = new JLabel(elefantöra);
        JLabel plantBackground = new JLabel(new ImageIcon("images/background/blue_gradient.png"));
        JLabel plantPot = new JLabel(new ImageIcon("images/pots/default_pot.png"));
        plantView.add(plantImage, c);
        plantView.add(plantPot, c);
        plantView.add(plantBackground, c);

        //Adds PlantCare JPanel
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        JPanel plantCare = plantCare();
        plantView.add(plantCare, c);

        return plantView;
    }

    //Creates a JPanel containing a button for watering a plant,
    //and a healthbar showing if the active plant needs watering
    private JPanel plantCare()
    {
        JPanel plantView = new JPanel();
        plantView.setSize(new Dimension(256, 50));
        plantView.setLayout(new GridBagLayout());
        plantView.setBackground(Color.GRAY);
        GridBagConstraints c = new GridBagConstraints();

        /*JButton waterPlant = new JButton("Water plant");
        waterPlant.setFont(new Font("Montserrat", Font.PLAIN, 16));*/

       waterPlant = new JButton();
        waterPlant.setBorder(BorderFactory.createEmptyBorder());
        waterPlant.setContentAreaFilled(false);
        waterPlant.setIcon(new ImageIcon("images/buttons/water.png"));
        waterPlant.setPreferredSize(new Dimension(45, 45));
        plantView.add(waterPlant, c);
        waterPlant.addActionListener(l -> waterPressed());
        waterPlant.setRolloverEnabled(true);
        waterPlant.setRolloverIcon(new ImageIcon("images/buttons/water_hover.png"));

        c.weightx = 0;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        plantView.add(waterPlant, c);

        waterBar = new JProgressBar(0, 100);
        waterBar.setValue(0);
        waterBar.setStringPainted(true);
        c.gridx = 0;
        c.gridy = 1;
        plantView.add(waterBar, c);

        return plantView;
    }

    public void updateWaterLevel(int waterLevel){
        waterBar.setValue(waterLevel);
    }


}
