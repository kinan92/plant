package controller;

import entity.Plant;

import java.util.ArrayList;

import boundary.MainFrame;
import boundary.mainGUI;

public class Controller {
	private ArrayList<Plant> listOffPlant = new ArrayList<>();
	private Plant plant;
	private MainFrame window;
	private mainGUI maingui;

	public Controller() {
		this.window = new MainFrame();
		//Test planta för att vattna
		plant = new Plant("TestPlant", 0,  null, null);
	}

	public void createPlant() {
		plant = new Plant(maingui.plantView().getName(), 0,  plant.getImage(), null);
	}

	// Gives water to the plant
	public void waterPlant(){
		plant.waterPlant();
	}
	// Gets the current plant water level
	public int getPlantWaterLevel(){
		return plant.getWaterLevel();
	}
}