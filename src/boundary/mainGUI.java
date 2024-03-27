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
    public mainGUI()
    {
        setLayout(new BorderLayout());
        setTitle("Test");
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

        JButton getPlant = new JButton("Plant Collection");
        getPlant.setPreferredSize(new Dimension(190, 35));
        getPlant.addActionListener(l -> getPlantPressed());

        JButton widget = new JButton("Place Widget");
        widget.setPreferredSize(new Dimension(190, 35));
        widget.addActionListener(l -> widgetPressed());

        JButton skipHour = new JButton("Skip Hour");
        skipHour.setPreferredSize(new Dimension(190, 35));
        skipHour.addActionListener(l -> skipHourPressed());

        JButton vacation = new JButton("Vacation mode");
        vacation.setPreferredSize(new Dimension(190, 35));
        vacation.addActionListener(l -> vacationPressed());

        sideButtons.add(getPlant);
        sideButtons.add(widget);
        sideButtons.add(skipHour);
        sideButtons.add(vacation);


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
        nameView.setPreferredSize(new Dimension(width / 2, (height / 10)));
        nameView.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel plantName = new JLabel("Test");
        plantName.setFont(new Font("Montserrat", Font.PLAIN, 18));
        nameView.add(plantName);

        return nameView;
    }

    public JPanel plantView()
    {
        JPanel plantView = new JPanel();
        plantView.setPreferredSize(new Dimension(256, (height / 5) * 4));
        plantView.setBackground(Color.ORANGE);
        plantView.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel plantName = new JLabel("Bob");
        plantName.setFont(new Font("Montserrat", Font.PLAIN, 24));

        JLabel plantSpecies = new JLabel("Species: Elefantöra");
        plantName.setFont(new Font("Montserrat", Font.PLAIN, 16));

        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 0;
        plantView.add(plantName, c);

        c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        plantView.add(plantSpecies, c);

        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        JLabel plantImage = new JLabel(elefantöra);
        plantView.add(plantImage, c);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        JPanel plantCare = plantCare();
        plantView.add(plantCare, c);

        return plantView;
    }

    private JPanel plantCare()
    {
        JPanel plantView = new JPanel();
        plantView.setPreferredSize(new Dimension(256, (height / 10)));
        plantView.setBackground(Color.RED);
        plantView.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton waterPlant = new JButton("Water plant");
        waterPlant.setFont(new Font("Montserrat", Font.PLAIN, 16));

        JButton feedPlant = new JButton("Feed plant");
        feedPlant.setFont(new Font("Montserrat", Font.PLAIN, 16));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        plantView.add(waterPlant, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        plantView.add(feedPlant, c);

        return plantView;
    }
}
