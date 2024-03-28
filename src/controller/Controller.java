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

	}

	private void createPlant() {
		plant = new Plant(maingui.plantView().getName(), 0,  plant.getImage(), null);
	}


}
