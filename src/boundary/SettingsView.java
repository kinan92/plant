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
     * @author Elvira Grubb
     * @param width The width of the MainFrame
     * @param height The height of the MainFrame
     * @param plantView The active boundary.PlantView.PlantView
     * This method creates a Settings menu but will not set it to visible until prompted
     */
    public SettingsView (int width, int height, PlantView plantView)
    {
        this.width = width / 2;
        this.height = height / 2;
        this.plantView = plantView;
        setTitle("Settings");

        //Calls a method on close instead of closing the window
        //in order to give the user a warning in case they have unsaved
        //settings
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
     * @author Elvira Grubb
     * @return JPanel with settings
     * Creates a JPanel with various settings and a confirm button
     */
    public JPanel settings()
    {
        JPanel settings = new JPanel();
        settings.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        soundEffects = new JCheckBox("Sound Effects", plantView.getSoundEffectSetting());
        //soundEffects.addItemListener(l -> soundEffectsUpdated());
        settings.add(soundEffects, c);

        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 1;
        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(l -> confirmSettings());
        settings.add(confirm, c);

        return settings;
    }

    //Currently sets the window as invisible
    //Will later give the user a warning in case the user has made changes
    //that have not yet been saved
    private void windowClose()
    {
        this.setVisible(false);
    }

    //Method called when settings are confirmed that updates the settings in boundary.PlantView.PlantView
    //and sets the settings windows as invisible
    private void confirmSettings()
    {
        plantView.setSoundEffectSetting(soundEffects.isSelected());
        this.setVisible(false);
    }
}
