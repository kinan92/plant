package boundary;

import controller.Controller;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JPanel {
	private Controller controller;

	/**
	 * This method creates the MainMenu panel
	 * @param width Width of the MainFrame
	 * @param height Height of the MainFrame
	 * @param controller Active Controller object in the program
	 * @author Elvira Grubb
	 */
	public MainMenu(int width, int height, Controller controller)
	{
		this.controller = controller;
		this.setSize(width, height);
		this.setLayout(new BorderLayout());

		this.add(backgroundPanel());

		setVisible(true);
	}

	/**
	 * This method creates a label that essentially functions as a panel, adding a
	 * background image to the label and buttons onto the background
	 * @return A label that functions as a panel, with a background and buttons
	 * @author Elvira Grubb
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

		JButton plantStorage = new JButton();
		plantStorage.setIcon(new ImageIcon("images/buttons/plant_storage.png"));
		plantStorage.setBorder(BorderFactory.createEmptyBorder());
		plantStorage.setContentAreaFilled(false);
		plantStorage.setRolloverEnabled(true);
		plantStorage.setRolloverIcon(new ImageIcon("images/buttons/plant_storage_hover.png"));
		plantStorage.addActionListener(l -> clickPlantStorageButton());

		//Adds the buttons with constraints
		c.insets = new Insets(26, 0, 0, 0);
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;
		background.add(createPlant, c);

		c.insets = new Insets(0, 0, 0, 0);
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 1;
		background.add(openPlant, c);

		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 2;
		background.add(plantStorage, c);

		return background;
	}

	/**
	 * ActionListener used for the createPlant button that calls on a method in the
	 * Controller to allow the user to create a plant
	 * @author Elvira Grubb
	 */
	private void clickCreatePlantButton()
	{
		buttonPressedSoundEffect();
		controller.createChoosePlantPanel();
	}

	/**
	 * ActionListener used for the getPlant button. Calls on a method in the controller
	 * to allow the user to view their last plant
	 * @author Elvira Grubb
	 */
	private void clickGetAPlantButton()
	{
		buttonPressedSoundEffect();
		controller.showPlantView();
	}

	/**
	 * ActionListener used to open Plant Storage from the main menu
	 * @author Elvira Grubb
	 */
	private void clickPlantStorageButton()
	{
		buttonPressedSoundEffect();
		controller.createStorage();
	}

	/**
	 * This method plays a sound effect whenever a button is pressed
	 * @author Elvira Grubb
	 */
	private void buttonPressedSoundEffect() {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/button_sound.wav").getAbsoluteFile());
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