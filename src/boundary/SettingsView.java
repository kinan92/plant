package boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsView extends JFrame {
    int width;
    int height;
    PlantView plantView;
    JCheckBox soundEffects;

    //Creates a JFrame for the Settings menu
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

    //JPanel that contains the various settings and a "Confirm" button
    public JPanel settings()
    {
        JPanel settings = new JPanel();
        settings.setBackground(Color.ORANGE);
        soundEffects = new JCheckBox("Sound Effects", plantView.getSoundEffectSetting());
        //soundEffects.addItemListener(l -> soundEffectsUpdated());
        settings.add(soundEffects);

        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(l -> confirmSettings());
        settings.add(confirm);
        return settings;
    }

    //Currently sets the window as invisible
    //Will later give the user a warning in case the user has made changes
    //that have not yet been saved
    private void windowClose()
    {
        this.setVisible(false);
    }

    //Method called when settings are confirmed that updates the settings in PlantView
    //and sets the settings windows as invisible
    private void confirmSettings()
    {
        plantView.setSoundEffectSetting(soundEffects.isSelected());
        this.setVisible(false);
    }
}
