package controller;

import boundary.*;
import entity.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.TimerTask;

import boundary.MainMenu;
import boundary.PlantView;

import javax.swing.*;

public class Controller {
	private ArrayList<Plant> listOffPlant = new ArrayList<>();
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
		startWaterDecreaseTimer();
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
		for (Plant plant : listOffPlant){
			plant.incrementAge();
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
			System.out.println(pt.getDefaultPlantImage());
			System.out.println(pt.getPlantImageButton());
			System.out.println(pt.getPlantInformation());
			System.out.println();
		}
	}

	public void startWaterDecreaseTimer(){
		waterDecreaseTimer = new Timer(60000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				plant.decreaseWaterLevel();
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
