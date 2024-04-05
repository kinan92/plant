package boundary;

import javax.swing.JFrame;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
	JButton createPlantButton = new JButton("Create Plant");
	JButton getAPlantButton = new JButton("Open Last Plant");
	GroupLayout groupLayout = new GroupLayout(getContentPane());

	/**
	 * Create the application.
	 */
	public MainFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		initialize();
	}

	private void clickCreatePlantButton() {
		createPlantButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChoosePlantFrame choosePlantFrame = new ChoosePlantFrame();
				dispose();
			}
		});
	}

	private void clickGetAPlantButton() {
		getAPlantButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 mainGUI maingui = new mainGUI();
				 dispose();	 
			}
		});
	}


	private void initialize() {
		setLocationRelativeTo(null);
		setSize(306, 226);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		getContentPane().setLayout(groupLayout);
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
