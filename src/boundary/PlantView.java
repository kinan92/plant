package boundary;
import controller.Controller;
import entity.Plant;

import javax.swing.*;
import java.awt.*;

public class PlantView extends JPanel {

    int width;
    int height;
    private Plant plant;
    private JLabel plantName;
    private JLabel plantSpecies;
    private JLabel plantImage;
    private JProgressBar waterBar;
    private Controller controller;
    private ImageIcon elefantöra = new ImageIcon("images/plants/moneyplant.png");
    private ImageIcon skiphour = new ImageIcon("images/buttons/skiphour.png");
    private ImageIcon storage = new ImageIcon("images/buttons/storage.png");
    private ImageIcon vacationImage = new ImageIcon("images/buttons/vacation.png");
    private ImageIcon widgetImage = new ImageIcon("images/buttons/widget.png");
    public PlantView(int width, int height, Controller controller, Plant plant)
    {
        super(new BorderLayout());
        this.width = width;
        this.height = height;
        this.controller = controller;
        System.out.println("hej plantview");
        this.setSize(width, height);
        this.setBackground(Color.ORANGE);
        this.plant = plant;

        add(createPlantPanel(), BorderLayout.WEST);
        add(createSideButtons(), BorderLayout.EAST);

    }

    private JPanel createPlantPanel(){
        JPanel plantPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        plantName = new JLabel(plant.getName());
        plantName.setFont(new Font("Calibri", Font.PLAIN, 26));
        plantPanel.add(plantName, c);

        c.gridx = 1;
        plantSpecies = new JLabel("Species: " + plant.getName());
        plantSpecies.setFont(new Font("Calibri", Font.PLAIN, 16));
        plantPanel.add(plantSpecies, c);

        c.gridy = 2;
        plantImage = new JLabel(plant.getImage());
        plantPanel.add(plantImage, c);

        c.gridy = 3;
        plantPanel.add(createPlantPanel(), c);
        return plantPanel;
    }



    public JPanel healthBar()
    {
        JPanel healthBarPanel = new JPanel();
        healthBarPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setPreferredSize(new Dimension(62, height));

        c.weightx = 0;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 3, 10, 3);
        JLabel healthBar = new JLabel();
        healthBar.setIcon(new ImageIcon("images/healthbar.png"));
        healthBarPanel.add(healthBar, c);

        return healthBarPanel;
    }

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

    private void getPlantPressed()
    {
        System.out.println("Plant Collection pressed.");
    }



    private void widgetPressed()
    {
    	// test other plants      images/plants/snakeplant.png   images/plants/goldenbarrelcactus.png  images/plants/bunnyear.png  images/plants/moneyplant.png
    	SwingUtilities.invokeLater(() -> {
    									//plant	path						pot path
            new MargePlantAndPotWidget("images/plants/snakeplant.png", "images/h.png");
        });
        System.out.println("Widget pressed.");
    }


    private void skipHourPressed()
    {
        /*
        System.out.println("Skip hour pressed.");
        int hoursToSkip = 1;
        controller.skipTime(hoursToSkip);
        System.out.println("Skipped " + hoursToSkip + " hour(s).");

         */
    }



    private void vacationPressed()
    {
        System.out.println("Vacation pressed.");
    }

    private void waterPressed()
    {
        System.out.println("Water pressed.");
        controller.waterPlant();
        updateWaterLevel(controller.getPlantWaterLevel());
        System.out.println("Water level: " + controller.getPlantWaterLevel());
    }

    public JPanel nameView()
    {
        JPanel nameView = new JPanel();
        nameView.setPreferredSize(new Dimension(256, height / 7));
        nameView.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        JLabel plantName = new JLabel("Bob");
        plantName.setFont(new Font("Calibri", Font.PLAIN, 26));
        nameView.add(plantName, c);

        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 1;
        JLabel plantSpecies = new JLabel("Species: Elefantöra");
        plantSpecies.setFont(new Font("Calibri", Font.PLAIN, 16));
        nameView.add(plantSpecies, c);

        return nameView;
    }

    public JPanel plantView()
    {
        JPanel plantView = new JPanel();
        plantView.setPreferredSize(new Dimension(256, height));
        plantView.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        plantView.add(nameView(), c);

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

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        JPanel plantCare = plantCare();
        plantView.add(plantCare, c);

        return plantView;
    }

    private JPanel plantCare()
    {
        JPanel plantView = new JPanel();
        plantView.setSize(new Dimension(256, 50));
        plantView.setLayout(new GridBagLayout());
        plantView.setBackground(Color.GRAY);
        GridBagConstraints c = new GridBagConstraints();

        /*JButton waterPlant = new JButton("Water plant");
        waterPlant.setFont(new Font("Montserrat", Font.PLAIN, 16));*/

        JButton waterPlant = new JButton();
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
