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
		groupLayout = new GroupLayout(this);
		this.setLayout(groupLayout);
		this.setBackground(Color.ORANGE);

		setVisible(true);
		initialize();
	}

	private void clickCreatePlantButton() {
		createPlantButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.choosePlantFrame();
			}
		});
	}

	private void clickGetAPlantButton() {
		getAPlantButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 controller.showPlantView();
			}
		});
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
