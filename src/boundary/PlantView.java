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
    private ImageIcon elefantöra = new ImageIcon("images/plants/moneyplant.png");
    private ImageIcon skiphour = new ImageIcon("images/buttons/skiphour.png");
    private ImageIcon storage = new ImageIcon("images/buttons/storage.png");
    private ImageIcon vacationImage = new ImageIcon("images/buttons/vacation.png");
    private ImageIcon widgetImage = new ImageIcon("images/buttons/widget.png");
    private JButton waterPlant;

   public PlantView(int width, int height, Controller controller)
    {
        super(null);
        this.width = width;
        this.height = height;
        this.controller = controller;
        setSize(width, height);
        this.setLayout(new BorderLayout());
        setBackground(Color.ORANGE);

        JPanel plantPanel = createPlantPanel();
        this.add(plantPanel, BorderLayout.WEST);

        SideButtons sideButtons = new SideButtons(this.width, this.height, this);
        this.add(sideButtons);

        /*JPanel plantView = plantView();
        add(plantView, BorderLayout.WEST);

        JPanel sideButtons = sideButtons();
        add(sideButtons, BorderLayout.EAST);*/
    }

    private JPanel createPlantPanel()
    {
        PlantPanel plantPanel = new PlantPanel(this.width, this.height, this);
        return plantPanel;
    }

    public void getPlantPressed()
    {
        System.out.println("Plant Collection pressed.");
    }



    public void widgetPressed()
    {
    	// test other plants      images/plants/snakeplant.png   images/plants/goldenbarrelcactus.png  images/plants/bunnyear.png  images/plants/moneyplant.png
    	SwingUtilities.invokeLater(() -> {
    									//plant	path						pot path
            new MargePlantAndPotWidget("images/plants/snakeplant.png", "images/pots/pot-with-bow-tie2.png",waterPlant);
            
        });
        System.out.println("Widget pressed.");
    }


    public void skipHourPressed()
    {
        /*
        System.out.println("Skip hour pressed.");
        int hoursToSkip = 1;
        controller.skipTime(hoursToSkip);
        System.out.println("Skipped " + hoursToSkip + " hour(s).");

         */
    }



    public void vacationPressed()
    {
        System.out.println("Vacation pressed.");
    }

    public void waterPressed()
    {
        System.out.println("Water pressed.");
        controller.waterPlant();
        updateWaterLevel(controller.getPlantWaterLevel());
        System.out.println("Water level: " + controller.getPlantWaterLevel());
    }

    public void updateWaterLevel(int waterLevel){
        waterBar.setValue(waterLevel);
    }


}
