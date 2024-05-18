package boundary;

import controller.Controller;

import javax.sound.sampled.*;
import javax.swing.*;

import javax.swing.GroupLayout.Alignment;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JPanel {
	private int width;
	private int height;
	private Controller controller;

	/**
	 * @author Elvira Grubb
	 * @param width Width of the MainFrame
	 * @param height Height of the MainFrame
	 * @param controller Active Controller object in the program
	 * This method creates the MainMenu panel
	 */
	public MainMenu(int width, int height, Controller controller)
	{
		super(null);
		this.controller = controller;
		this.width = width;
		this.height = height;
		this.controller = controller;
		this.setSize(width, height);
		this.setLayout(new BorderLayout());

		this.add(backgroundPanel());

		setVisible(true);
	}

	/**
	 * @author Elvira Grubb
	 * @return A label that functions as a panel, with a background and buttons
	 * This method creates a label that essentially functions as a panel, adding a
	 * background image to the label and buttons onto the background
	 */
	private JLabel backgroundPanel()
	{
		//Creates background label and sets the background image
		JLabel background = new JLabel();
		background.setIcon(new ImageIcon("images/main_menu_background.png"));

		//Sets the layout for the background label
		background.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//Creates a createPlant JButton
		JButton createPlant = new JButton();
		createPlant.setIcon(new ImageIcon("images/buttons/create_plant.png"));
		createPlant.setBorder(BorderFactory.createEmptyBorder());
		createPlant.setContentAreaFilled(false);
		createPlant.setRolloverEnabled(true);
		createPlant.setRolloverIcon(new ImageIcon("images/buttons/create_plant_hover.png"));
		createPlant.addActionListener(l -> clickCreatePlantButton());

		//Creates an openPlant JButton
		JButton openPlant = new JButton();
		openPlant.setIcon(new ImageIcon("images/buttons/open_last_plant.png"));
		openPlant.setBorder(BorderFactory.createEmptyBorder());
		openPlant.setContentAreaFilled(false);
		openPlant.setRolloverEnabled(true);
		openPlant.setRolloverIcon(new ImageIcon("images/buttons/open_last_plant_hover.png"));
		openPlant.addActionListener(l -> clickGetAPlantButton());

		//Adds the buttons with constraints
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;
		background.add(createPlant, c);

		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 1;
		background.add(openPlant, c);

		return background;
	}

	/**
	 * @author Elvira Grubb
	 * ActionListener used for the createPlant button that calls on a method in the
	 * Controller to allow the user to create a pant
	 */
	private void clickCreatePlantButton()
	{
		buttonPressedSoundEffect();
		controller.choosePlantFrame();
	}

	/**
	 * @author Elvira Grubb
	 * ActionListener used for the getPlant button. Calls on a method in the controller
	 * to allow the user to view their last plant
	 */
	private void clickGetAPlantButton()
	{
		buttonPressedSoundEffect();
		controller.showPlantView();
	}

	/**
	 * @author Elvira Grubb
	 * This method plays a sound effect whenever a button is pressed
	 */
	private void buttonPressedSoundEffect() {
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/button_sound.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (LineUnavailableException e) {
			throw new RuntimeException(e);
		}
	}
}
