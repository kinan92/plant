package controller;

import boundary.*;
import entity.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import boundary.PlantView;

import javax.swing.*;

public class Controller {
	private ArrayList<Plant> listOffPlants = new ArrayList<>();
	private Plant plant;
	private PlantView maingui;
	MainFrame mainFrame;
	ArrayList<PlantType> plantTypes = new ArrayList<>();
	private Timer waterDecreaseTimer;
	private Timer ageTimer;
	private boolean isPaused = false;
	LocalDateTime creationTime = LocalDateTime.now().minusDays(4).plusHours(5);

	public Controller() {
		plant = new Plant("TestPlanta", 0, "images/plants/moneyplant.png",0, creationTime);
		mainFrame = new MainFrame(this, plant);
		mainFrame.addMainMenu();
		maingui = new PlantView(550, 435, this, plant);

		loadPlantTypes();
		test();
		listOffPlants.add(plant);
		// startWaterDecreaseTimer();
		// startAgeTimer();
	}

	public void pausTime(){
		if(!isPaused){
			isPaused = true;
			stopAgeTimer();
			System.out.println("Tid är pausad");
		}
	}

	public void resumeTime(){
		if (isPaused){
			isPaused = false;
			startAgeTimer();
			System.out.println("Tiden återupptas");
		}
	}
	public void stopAgeTimer(){
		if (ageTimer != null){
			ageTimer.stop();
			ageTimer = null;
		}
		if(waterDecreaseTimer != null){
			waterDecreaseTimer.stop();
			waterDecreaseTimer = null;
		}
	}

	public void addPlant(Plant newPlant){
		listOffPlants.add(newPlant);
	}

	private void startAgeTimer(){
		if (ageTimer == null){
			ageTimer = new Timer(1000, e -> {
                if (!isPaused){
                    incrementAgeForALlPlants();
                }
            });
			ageTimer.start();
		}

		if (waterDecreaseTimer == null){
			waterDecreaseTimer = new Timer(6000, e -> {
                if(!isPaused){
                    decreaseWaterLevelForAllPlants();
                }
            });
			waterDecreaseTimer.start();
		}
	}

	private void updateAge(){
		for (Plant plant : listOffPlants){
			plant.incrementAge(1);
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
		waterDecreaseTimer = new Timer(60000, e -> plant.decreaseWaterLevel());
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

	public Plant getCurrentPlant(){
		if(plant == null){
			System.out.println("No current plant is set");
		}
		return plant;
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

	/**
	 * Reads image file paths from a text file and returns an arraylist
	 * can be used regardless of type of pictures
	 * @return arraylist of images
	 * @author Petri Närhi
	 * */
	private ArrayList<ImageIcon> getImageListFromFile(String filename)
	{
		ArrayList<ImageIcon> imageList = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader( new FileReader(filename));
			ImageIcon image;
			String imagePath = br.readLine();

			while(imagePath != null) {
				image = new ImageIcon(imagePath);
				imageList.add(image);
				imagePath = br.readLine();
			}
			br.close();
		} catch( IOException e ) {
			System.out.println( "getImageList: " + e );
		}
		return imageList;
	}

	public void skipTime(int hours){
		if (hours <= 0){
			System.out.println("Skipped time requires a positive number of hours");
			return;
		}

		for (Plant plant : listOffPlants){
			LocalDateTime newCreationTime = plant.getCreationTime().plusHours(hours);
			plant.setCreationTime(newCreationTime);
			updatePlantCreationTime(plant, newCreationTime);

			int ageIncrement = hours / 24;
			plant.incrementAge(ageIncrement);

            plant.decreaseWaterLevel();

			adjustPlantBasedOnWaterLevel(plant);
		}
		maingui.updateElapsedTime();

		notifyTimeSkipped(hours);
	}

	public void updatePlantCreationTime(Plant plant, LocalDateTime newCreationTime){
		if (plant == null){
			System.out.println("Plant is null, cannot update creation time");
			return;
		}
		plant.setCreationTime(newCreationTime);
		maingui.updateCreationTime(plant);
		System.out.println("Updated plant creation time to: " + newCreationTime.toString());
	}

	private void createPlant()
	{
		/*
		plant = new Plant(maingui.plantView().getName(), 0,  plant.getImage(), null);

		 */
	}

	public List<PlantType> getPlantTypes()
	{
		return plantTypes;
	}

	public void showPlantView()
	{
		mainFrame.addPlantView();
	}

	public void incrementAgeForALlPlants(){
		for (int i = 0; i < listOffPlants.size(); i++){
			Plant allPlants = listOffPlants.get(i);
			if(allPlants != null){
				allPlants.incrementAge(1);
			}
		}
	}

	public void decreaseWaterLevelForAllPlants(){
		for (int i = 0; i < listOffPlants.size(); i++){
			Plant allPlants = listOffPlants.get(i);
			if(allPlants != null) {
				allPlants.decreaseWaterLevel();
			}
		}
	}
}