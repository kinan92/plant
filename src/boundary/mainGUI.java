package boundary;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;

public class mainGUI extends JFrame {

    private JLabel plantName;
    int width = 550;
    int height = 450;
    private ImageIcon elefantöra = new ImageIcon("images/elefantora.png");
    private ImageIcon skiphour = new ImageIcon("images/skiphour.png");
    private ImageIcon storage = new ImageIcon("images/storage.png");
    private ImageIcon vacationImage = new ImageIcon("images/vacation.png");
    private ImageIcon widgetImage = new ImageIcon("images/widget.png");
    public mainGUI()
    {
        setLayout(new BorderLayout());
        setTitle("Virtual Plant Widgets");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel plantView = plantView();
        add(plantView, BorderLayout.WEST);

        JPanel sideButtons = sideButtons();
        add(sideButtons, BorderLayout.EAST);

        setVisible(true);
    }

    public JPanel sideButtons()
    {
        JPanel sideButtons = new JPanel();
        sideButtons.setBackground(Color.GRAY);
        sideButtons.setPreferredSize(new Dimension(230, (height / 5) * 3));
        sideButtons.setLayout(new GridBagLayout());
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
        sideButtons.add(getPlant, c);
        getPlant.addActionListener(l -> getPlantPressed());
        getPlant.setRolloverEnabled(true);
        getPlant.setRolloverIcon(new ImageIcon("images/storage_hover.png"));

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
        widget.setRolloverIcon(new ImageIcon("images/widget_hover.png"));

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
        skipHour.setRolloverIcon(new ImageIcon("images/skiphour_hover.png"));

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
        vacation.setRolloverIcon(new ImageIcon("images/vacation_hover.png"));

        return sideButtons;
    }

    private void getPlantPressed()
    {
        System.out.println("Plant Collection pressed.");
    }

    private void widgetPressed()
    {
        System.out.println("Widget pressed.");
    }

    private void skipHourPressed()
    {
        System.out.println("Skip hour pressed.");
    }

    private void vacationPressed()
    {
        System.out.println("Vacation pressed.");
    }

    public JPanel nameView()
    {
        JPanel nameView = new JPanel();
        nameView.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        JLabel plantName = new JLabel("Bob");
        plantName.setFont(new Font("Montserrat", Font.PLAIN, 32));
        nameView.add(plantName, c);

        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 1;
        JLabel plantSpecies = new JLabel("Species: Elefantöra");
        plantName.setFont(new Font("Montserrat", Font.PLAIN, 24));
        nameView.add(plantSpecies, c);

        return nameView;
    }

    public JPanel plantView()
    {
        JPanel plantView = new JPanel();
        plantView.setPreferredSize(new Dimension(256, (height / 5) * 4));
        plantView.setBackground(Color.ORANGE);
        plantView.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        plantView.add(nameView(), c);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        JLabel plantImage = new JLabel(elefantöra);
        plantView.add(plantImage, c);

        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 3;
        JPanel plantCare = plantCare();
        plantView.add(plantCare, c);

        return plantView;
    }

    private JPanel plantCare()
    {
        JPanel plantView = new JPanel();
        plantView.setPreferredSize(new Dimension(256, 55));
        plantView.setBackground(Color.RED);
        plantView.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        /*JButton waterPlant = new JButton("Water plant");
        waterPlant.setFont(new Font("Montserrat", Font.PLAIN, 16));*/

        JButton waterPlant = new JButton();
        waterPlant.setBorder(BorderFactory.createEmptyBorder());
        waterPlant.setContentAreaFilled(false);
        waterPlant.setIcon(new ImageIcon("images/water.png"));
        waterPlant.setPreferredSize(new Dimension(45, 45));

        plantView.add(waterPlant, c);
        waterPlant.addActionListener(l -> getPlantPressed());
        waterPlant.setRolloverEnabled(true);
        waterPlant.setRolloverIcon(new ImageIcon("images/water_hover.png"));

        c.weightx = 0;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        plantView.add(waterPlant, c);

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 0;
        plantView.add(Box.createGlue(), c);

        return plantView;
    }
}
