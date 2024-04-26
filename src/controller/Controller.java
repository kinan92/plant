package controller;

import boundary.*;
import entity.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import boundary.MainMenu;
import boundary.PlantView;

import javax.swing.*;

public class Controller {
	private ArrayList<Plant> listOffPlants = new ArrayList<>();
	private Plant plant;
	private MainMenu window;
	private PlantView maingui;
	MainFrame mainFrame;
	ArrayList<PlantType> plantTypes = new ArrayList<>();
	private Timer waterDecreaseTimer;
	private Timer ageTimer;

	public Controller() {
		//this.window = new MainMenu(this);
		mainFrame = new MainFrame(this);
		mainFrame.addMainMenu();

		loadPlantTypes();
		test();
		plant = new Plant("TestPlanta", 0, "images/plants/moneyplant.png",50);
		listOffPlants.add(plant);
		// startWaterDecreaseTimer();
		// startAgeTimer();
	}

	public void addPlant(Plant newPlant){
		listOffPlants.add(newPlant);
	}

	private void startAgeTimer(){
		ageTimer = new Timer(86400000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateAge();
			}
		});
		ageTimer.start();
	}

	private void updateAge(){
		for (Plant plant : listOffPlants){
			plant.incrementAge(1);
		}
	}

	public void stopAgeTimer(){
		if (ageTimer != null){
			ageTimer.stop();
		}
	}

	private void test()
	{
		for (PlantType pt : plantTypes)
		{
			System.out.println(pt.getPlantTypeName());
			System.out.println(pt.getPlantTypeNameAlternative());
			System.out.println(pt.getGrownPlantImage());
			System.out.println(pt.getPlantImageButton());
			System.out.println(pt.getPlantInformation());
			System.out.println();
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
	}
	// Gets the current plant water level
	public int getPlantWaterLevel(){
		return plant.getWaterLevel();
	}

	private void adjustPlantBasedOnWaterLevel(Plant plant){
		if (plant.getWaterLevel() < 10){
			System.out.println(plant.getName() + " needs water");
			// Add additional logic for low water levels
		} else if (plant.getWaterLevel() > 100){
			System.out.println(plant.getName() + " is overwatered");
			// Add additional logic for overwatered plants
		}
	}

	private void notifyTimeSkipped(int hours){
		System.out.println("Time skipped by " + hours + " hours.");
	}


	public void choosePlantFrame()
	{
		ArrayList<String> plantImage = new ArrayList<>();
		ArrayList<String> plantImageHover = new ArrayList<>();


		for (PlantType pt : plantTypes)
		{
			plantImage.add(pt.getPlantImageButton());
			plantImageHover.add(pt.getPlantImageButtonHover());
		}

		ChoosePlantFrame choosePlantFrame = new ChoosePlantFrame(plantImage, plantImageHover);
	}

	//Reads PlantTypes from the plantTypes textfile, creates an object of them and adds them to the plantTypes ArrayList
	private void loadPlantTypes()
	{
		try {
			BufferedReader br = new BufferedReader( new FileReader("files/plantTypes.txt"));
			PlantType plantType;
			String string = br.readLine();

			while(string != null) {
				String[] plantInformation;
				plantInformation = string.split( "," );

				plantType = new PlantType(plantInformation[0], plantInformation[1], plantInformation[2], plantInformation[3], plantInformation[4], plantInformation[5]);
				plantTypes.add(plantType);
				string = br.readLine();
			}
			br.close();
		} catch( IOException e ) {
			System.out.println( "readPlantType: " + e );
		}
	}

	public void skipTime(int hours){
		if (hours <= 0){
			System.out.println("Skipped time requires a positive number of hours");
			return;
		}

		for (Plant plant : listOffPlants){
			int ageIncrement = hours / 24;
			plant.incrementAge(ageIncrement);

            plant.decreaseWaterLevel(hours);

			adjustPlantBasedOnWaterLevel(plant);
		}

		notifyTimeSkipped(hours);
	}

	private void createPlant()
	{
		/*
		plant = new Plant(maingui.plantView().getName(), 0,  plant.getImage(), null);

		 */
	}

	public ArrayList<PlantType> getPlantTypes()
	{
		return plantTypes;
	}

	public void showPlantView()
	{
		mainFrame.addPlantView();
	}
}
