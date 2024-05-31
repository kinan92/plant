package boundary;

import boundary.PlantView.PlantView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsView extends JFrame {
    private int width;
    private int height;
    PlantView plantView;
    private JCheckBox soundEffects;

    /**
     * This method creates a Settings menu but will not set it to visible until prompted
     * @param width The width of the MainFrame
     * @param height The height of the MainFrame
     * @param plantView The active PlantView
     * @author Elvira Grubb
     */
    public SettingsView (int width, int height, PlantView plantView)
    {
        this.width = width / 3;
        this.height = height / 2;
        this.plantView = plantView;
        setTitle("Settings");

        //Calls a method on close instead of closing the window
        //so the program doesn't have to create a new settings menu
        //every time the user opens it
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                windowClose();
            }
        });
        setSize(this.width, this.height);
        setLocationRelativeTo(null);
        setResizable(false);
        ImageIcon icon = new ImageIcon("images/icon.png");
        setIconImage(icon.getImage());

        JPanel settings = settings();
        this.add(settings);
        setVisible(false);
    }

    /**
     * Creates a JPanel with various settings and a confirm button
     * @return JPanel with settings
     * @author Elvira Grubb
     */
    public JPanel settings()
    {
        JPanel settings = new JPanel();
        settings.setBackground(new java.awt.Color(184, 200, 177));
        settings.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        soundEffects = new JCheckBox("Sound Effects", plantView.getSoundEffectSetting());
        soundEffects.setFont(new Font("Calibri", Font.PLAIN, 16));
        soundEffects.setBackground(new java.awt.Color(184, 200, 177));
        //soundEffects.addItemListener(l -> soundEffectsUpdated());
        settings.add(soundEffects, c);

        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 2;
        JButton confirm = new JButton("Confirm");
        confirm.setFont(new Font("Calibri", Font.PLAIN, 16));
        confirm.addActionListener(l -> confirmSettings());
        settings.add(confirm, c);

        return settings;
    }

    /**
     * Sets the window to not visible when user closes it
     * @author Elvira Grubb
     */
    private void windowClose()
    {
        this.setVisible(false);
    }

    /**
     * Sets the user's selected settings in the PlantView when confirmed is pressed and sets the settings menu to not visible
     * @author Elvira Grubb
     */
    private void confirmSettings()
    {
        plantView.setSoundEffectSetting(soundEffects.isSelected());
        this.setVisible(false);
    }
}