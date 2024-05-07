package boundary;

import controller.Controller;

import javax.swing.*;

import javax.swing.GroupLayout.Alignment;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu extends JPanel {
	JButton createPlantButton = new JButton("Create Plant");
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
		/*groupLayout = new GroupLayout(this);
		this.setLayout(groupLayout);*/
		JLabel background = new JLabel();
		background.setIcon(new ImageIcon("images/main_menu_background.png"));

		background.setLayout(new GridBagLayout());
		JButton createPlant = new JButton("Create Plant");
		JButton openPlant = new JButton("Open Plant");
		GridBagConstraints c = new GridBagConstraints();

		createPlant.setPreferredSize(new Dimension(155, 55));
		createPlant.addActionListener(l -> clickCreatePlantButton());
		openPlant.setPreferredSize(new Dimension(155, 55));
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
