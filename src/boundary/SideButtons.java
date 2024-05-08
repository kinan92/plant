package boundary;

import javax.swing.*;
import java.awt.*;

public class SideButtons extends JPanel {
    private ImageIcon skiphour = new ImageIcon("images/buttons/skiphour.png");
    private ImageIcon storage = new ImageIcon("images/buttons/storage.png");
    private ImageIcon vacationImage = new ImageIcon("images/buttons/vacation.png");
    private ImageIcon widgetImage = new ImageIcon("images/buttons/widget.png");
    public SideButtons(int width, int height, PlantView plantView)
    {
        JPanel sideButtons = new JPanel();
        this.setBackground(Color.LIGHT_GRAY);
        this.setPreferredSize(new Dimension(220, (height / 5) * 3));
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

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
        this.add(getPlant, c);
        getPlant.addActionListener(l -> plantView.getPlantPressed());
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
        this.add(widget, c);
        widget.addActionListener(l -> plantView.widgetPressed());
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
        this.add(skipHour, c);
        skipHour.addActionListener(l -> plantView.skipHourPressed());
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
        this.add(vacation, c);
        vacation.addActionListener(l -> plantView.vacationPressed());
        vacation.setRolloverEnabled(true);
        vacation.setRolloverIcon(new ImageIcon("images/buttons/vacation_hover.png"));


        JButton settings = new JButton("Settings");
        /*vacation.setBorder(BorderFactory.createEmptyBorder());
        vacation.setContentAreaFilled(false);
        vacation.setIcon(vacationImage);*/
        settings.setPreferredSize(new Dimension(150, 30));


        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 4;

        //Adds Vacation button to panel and adds actionlistener
        this.add(settings, c);
        settings.addActionListener(l -> plantView.settingsPressed());
        /*vacation.setRolloverEnabled(true);
        vacation.setRolloverIcon(new ImageIcon("images/buttons/vacation_hover.png"));*/

        JButton mainMenu = new JButton("Main Menu");
        mainMenu.setPreferredSize(new Dimension(150, 30));
        /*vacation.setBorder(BorderFactory.createEmptyBorder());
        vacation.setContentAreaFilled(false);
        vacation.setIcon(vacationImage);*/


        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 5;

        //Adds Vacation button to panel and adds actionlistener
        this.add(mainMenu, c);
        mainMenu.addActionListener(l -> plantView.mainMenuPressed());
        /*vacation.setRolloverEnabled(true);
        vacation.setRolloverIcon(new ImageIcon("images/buttons/vacation_hover.png"));*/
    }
}
