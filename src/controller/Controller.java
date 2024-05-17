package controller;

import boundary.*;
import entity.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Controller {
	private ArrayList<Plant> listOfPlants = new ArrayList<>();
	private Plant plant;
	MainFrame mainFrame;
	ArrayList<PlantType> plantTypes;
	ArrayList<Pot> pots;
	private Timer waterDecreaseTimer;
	private Timer ageTimer;
	private boolean isPaused = false;
	private Random random = new Random();
	private FileManager file;

	public Controller() {
		mainFrame = new MainFrame(this);
		mainFrame.addMainMenu();
		this.file = new FileManager();
		plantTypes = file.loadPlantTypes();
		pots = file.loadPots();
		startWaterDecreaseTimer();
		startAgeTimer();
	}

	/**
	 * Creates a new plant object based on the choice of the user
	 * called by a boundary class
	 * @param plantNumber int, the index of the chosen plant in the GUI
	 * @author Petri Närhi
	 * */
	public void createPlant(int plantNumber, int potNumber)
	{
		PlantType type = plantTypes.get(plantNumber);
		String name;
		do {
			name = JOptionPane.showInputDialog("Give your plant a name!");
		} while (name == null || name.isEmpty());
		int initialWaterLevel = random.nextInt(21) * 5; //divisible by 5 so the watering will work as intended
		if (initialWaterLevel == 0) {
			initialWaterLevel = 5;
		}
		LocalDateTime dateAndTime = LocalDateTime.now();

		Plant newPlant = new Plant(name, 0, initialWaterLevel, type, PlantStateEnum.little, dateAndTime, pots.get(potNumber)); //ny planta är alltid liten
		listOfPlants.add(newPlant);
		plant = newPlant;
		System.out.println("New plant! " + plant);
		showPlantView();
		mainFrame.getPlantView().updatePlantDetails(plant);
	}


	private void startAgeTimer(){
		if (ageTimer == null){
			ageTimer = new Timer(1000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!isPaused){
						incrementAgeForALlPlants();
					}
				}
			});
			ageTimer.start();
		}

		if (waterDecreaseTimer == null){
			waterDecreaseTimer = new Timer(6000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!isPaused){
						decreaseWaterLevelForAllPlants();
					}
				}
			});
			waterDecreaseTimer.start();
		}
	}

	public void startWaterDecreaseTimer(){
		waterDecreaseTimer = new Timer(60000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				plant.decreaseWaterLevel(1);
			}
		});
		waterDecreaseTimer.start();
	}

	public void stopWaterDecreaseTimer(){
		if (waterDecreaseTimer != null){
			waterDecreaseTimer.stop();
			waterDecreaseTimer = null;
		}
	}

	public void waterPlant(){
		plant.waterPlant();
		mainFrame.getPlantView().updatePlantDetails(plant);

	}

	private void notifyTimeSkipped(int hours){
		System.out.println("Time skipped by " + hours + " hours.");
	}
	public void choosePlantFrame()
	{
		ArrayList<ImageIcon> plantImage = new ArrayList<>();
		ArrayList<ImageIcon> plantImageHover = new ArrayList<>();
		ArrayList<ImageIcon> potImage = new ArrayList<>();
		ArrayList<ImageIcon> potImageHover = new ArrayList<>();


		for (PlantType pt : plantTypes)
		{
			plantImage.add(pt.getPlantImageButton());
			plantImageHover.add(pt.getPlantImageButtonHover());
		}

		for (Pot p : pots)
		{
			potImage.add(p.getPotButton());
			potImageHover.add(p.getPotButtonHoverImage());
		}

		mainFrame.addChoosePlantPanel(plantImage, plantImageHover, potImage, potImageHover);
	}

	public void showMainMenu()
	{
		mainFrame.addMainMenu();
	}

	public void skipTime(int hours){
		if (hours <= 0){
			System.out.println("Skipped time requires a positive number of hours");
			return;
		}
		LocalDateTime newCreationTime = plant.getDateAndTime().minusHours(hours);
		plant.setDateAndTime(newCreationTime);
		int ageIncrement = hours / 24;
		plant.incrementAge(ageIncrement);
		plant.decreaseWaterLevel(1);
		plant.updateState();
		mainFrame.getPlantView().updateElapsedTime();
		mainFrame.getPlantView().updatePlantDetails(plant);
		notifyTimeSkipped(hours);
	}

	public void showPlantView()
	{
		mainFrame.addPlantView();
	}

	public void incrementAgeForALlPlants(){
		for (int i = 0; i < listOfPlants.size(); i++){
			Plant plant = listOfPlants.get(i);
			if(plant != null){
				plant.incrementAge(1);
			}
		}
	}

	public void decreaseWaterLevelForAllPlants(){
		for (int i = 0; i < listOfPlants.size(); i++){
			Plant plant = listOfPlants.get(i);
			if(plant != null) {
				plant.decreaseWaterLevel(1);
			}
		}
	}

	/**
	 * Gets the current plant
	 * to show the current plant that is shown in PlantView
	 * and to be able to use the plant's methods in various classes
	 * @return Plant
	 * @author Petri Närhi
	 * */
	public Plant getPlant() {
		return plant;
	}

	/**
	 * Sets the current plant
	 * to change the current plant that is shown in PlantView
	 * @param plant the plant to replace the current plant
	 * @author Petri Närhi
	 * */
	public void setPlant(Plant plant) {
		this.plant = plant;
	}
}