package boundary;

import controller.Controller;

import javax.swing.*;

import javax.swing.GroupLayout.Alignment;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu extends JPanel {
	JButton createPlantButton = new JButton();
	JButton getAPlantButton = new JButton("Open Last Plant");
	GroupLayout groupLayout;
	Controller controller;
	int width;
	int height;

	/**
	 * Create the application.
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
		JLabel background = new JLabel();
		background.setIcon(new ImageIcon("images/main_menu_background.png"));

		background.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JButton createPlant = new JButton();
		createPlant.setIcon(new ImageIcon("images/buttons/create_plant.png"));
		createPlant.setBorder(BorderFactory.createEmptyBorder());
		createPlant.setContentAreaFilled(false);
		createPlant.setRolloverEnabled(true);
		createPlant.setRolloverIcon(new ImageIcon("images/buttons/create_plant_hover.png"));
		createPlant.addActionListener(l -> clickCreatePlantButton());

		JButton openPlant = new JButton();
		openPlant.setIcon(new ImageIcon("images/buttons/open_last_plant.png"));
		openPlant.setBorder(BorderFactory.createEmptyBorder());
		openPlant.setContentAreaFilled(false);
		openPlant.setRolloverEnabled(true);
		openPlant.setRolloverIcon(new ImageIcon("images/buttons/open_last_plant_hover.png"));
		openPlant.addActionListener(l -> clickGetAPlantButton());

		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;
		background.add(createPlant, c);

		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 1;
		background.add(openPlant, c);

		this.add(background);

		setVisible(true);
		//initialize();
	}

	private void clickCreatePlantButton() {
		controller.choosePlantFrame();
		/*createPlantButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});*/
	}

	private void clickGetAPlantButton() {
		controller.showPlantView();
		/*getAPlantButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 controller.showPlantView();
			}
		});*/
	}


	private void initialize() {
		setSize(306, 226);
		SetgroupLayoutVertical();
		SetgroupLayoutHorizontal();
		clickGetAPlantButton();
		clickCreatePlantButton();
	}
	
	private void SetgroupLayoutVertical() {
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(41)
						.addComponent(createPlantButton, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(getAPlantButton, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(43, Short.MAX_VALUE)));
	}
	
	private void SetgroupLayoutHorizontal() {
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(89)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(getAPlantButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(createPlantButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 102,
										Short.MAX_VALUE))
						.addContainerGap(99, Short.MAX_VALUE)));
		
	}
}
