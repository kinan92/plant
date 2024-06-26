package boundary.PlantView;

import javax.swing.*;
import java.awt.*;

public class SideButtons extends JPanel {
	private int height;
	private PlantView plantView;
	private ImageIcon skiphour = new ImageIcon("images/buttons/skiphour.png");
	private ImageIcon storage = new ImageIcon("images/buttons/storage.png");
	private ImageIcon vacationImage = new ImageIcon("images/buttons/vacation.png");
	private ImageIcon widgetImage = new ImageIcon("images/buttons/widget.png");
	private JButton skipHour;
	private JButton settings;
	private JButton widget;

	/**
	 * This constructor creates a SideButton panel.
	 * @param height The height of the MainFrame
	 * @param plantView The active PlantView object
	 * @author Elvira Grubb
	 */
	public SideButtons(int height, PlantView plantView) {
		this.height = height;
		this.plantView = plantView;
		this.setBackground(Color.LIGHT_GRAY);
		this.setPreferredSize(new Dimension(240, height));
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;
		this.add(backgroundPanel(), c);
	}

	/**
	 * This method creates a JLabel that functions as a background panel
	 * @return JLabel background
	 * @author Elvira Grubb
	 */
	private JLabel backgroundPanel() {
		JLabel backgroundPanel = new JLabel();
		backgroundPanel.setIcon(new ImageIcon("images/background/buttons_background.png"));
		backgroundPanel.setPreferredSize(new Dimension(240, height));
		backgroundPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//Creates Plant Storage button
		JButton getPlant = new JButton();
		getPlant.setBorder(BorderFactory.createEmptyBorder());
		getPlant.setContentAreaFilled(false);
		getPlant.setIcon(storage);

		//Sets location for Plant Storage button
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;

		//Adds Plant Storage button to panel and adds actionlistener
		backgroundPanel.add(getPlant, c);
		getPlant.addActionListener(l -> plantView.storagePressed());
		getPlant.setRolloverEnabled(true);
		getPlant.setRolloverIcon(new ImageIcon("images/buttons/storage_hover.png"));

		//Creates Widget button
		widget = new JButton();
		widget.setBorder(BorderFactory.createEmptyBorder());
		widget.setContentAreaFilled(false);
		widget.setIcon(widgetImage);

		//Sets location for Widget button
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 1;

		//Adds Widget button to panel and adds actionlistener
		backgroundPanel.add(widget, c);
		widget.addActionListener(l -> plantView.widgetPressed());
		widget.setRolloverEnabled(true);
		widget.setRolloverIcon(new ImageIcon("images/buttons/widget_hover.png"));

		//Creates Skip Hour button
		skipHour = new JButton();
		skipHour.setBorder(BorderFactory.createEmptyBorder());
		skipHour.setContentAreaFilled(false);
		skipHour.setIcon(skiphour);

		//Sets location for Skip Hour button
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 2;

		//Adds Skip Hour button to panel and adds actionlistener
		backgroundPanel.add(skipHour, c);
		skipHour.addActionListener(l -> plantView.skipHourPressed());
		skipHour.setRolloverEnabled(true);
		skipHour.setRolloverIcon(new ImageIcon("images/buttons/skiphour_hover.png"));

		//Creates Vacation button
		JButton vacation = new JButton();
		vacation.setBorder(BorderFactory.createEmptyBorder());
		vacation.setContentAreaFilled(false);
		vacation.setIcon(vacationImage);

		//Sets location for Vacation button
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 3;

		//Adds Vacation button to panel and adds actionlistener
		backgroundPanel.add(vacation, c);
		vacation.addActionListener(l -> plantView.vacationPressed());
		vacation.setRolloverEnabled(true);
		vacation.setRolloverIcon(new ImageIcon("images/buttons/vacation_hover.png"));

		//Creates a JLabel to put the Settings and Main Menu buttons on
		JLabel settingsMenuButtons = new JLabel();
		settingsMenuButtons.setLayout(new GridLayout(1, 2));
		settingsMenuButtons.setPreferredSize(new Dimension(200, 60));

		//Creates Settings button
		settings = new JButton();
		settings.setBorder(BorderFactory.createEmptyBorder());
		settings.setContentAreaFilled(false);
		settings.setFocusPainted(false);
		settings.setIcon(new ImageIcon("images/buttons/settings.png"));

		settings.setRolloverEnabled(true);
		settings.setRolloverIcon(new ImageIcon("images/buttons/settings_hover.png"));
		settings.addActionListener(l -> plantView.settingsPressed());

		//Creates Main Menu button
		JButton mainMenu = new JButton();
		mainMenu.setBorder(BorderFactory.createEmptyBorder());
		mainMenu.setContentAreaFilled(false);
		mainMenu.setFocusPainted(false);
		mainMenu.setIcon(new ImageIcon("images/buttons/home.png"));

		mainMenu.setRolloverEnabled(true);
		mainMenu.setRolloverIcon(new ImageIcon("images/buttons/home_hover.png"));
		mainMenu.addActionListener(l -> plantView.mainMenuPressed());

		JButton helpButton = new JButton();
		helpButton.setBorder(BorderFactory.createEmptyBorder());
		helpButton.setContentAreaFilled(false);
		helpButton.setFocusPainted(false);
		helpButton.setIcon(new ImageIcon("images/buttons/help.png"));

		helpButton.setRolloverEnabled(true);
		helpButton.setRolloverIcon(new ImageIcon("images/buttons/help_hover.png"));
		helpButton.addActionListener(l -> plantView.helpMenuPressed());

		//Adds mainMenu and settings buttons onto their shared JLabel
		settingsMenuButtons.add(mainMenu);
		settingsMenuButtons.add(settings);
		settingsMenuButtons.add(helpButton);

		//Adds settingsMenuButton JLabel to the main panel with constraints
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 4;
		backgroundPanel.add(settingsMenuButtons, c);

		return backgroundPanel;
	}

	public JButton getSettings()
	{
		return settings;
	}

	public JButton getSkipHour() {
		return skipHour;
	}

	public JButton getWidget() {
		return widget;
	}
}