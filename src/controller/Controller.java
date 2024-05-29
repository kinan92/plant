package controller;

import boundary.*;
import boundary.Widget.WidgetCreatorJFX;
import entity.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Controller {
	private ArrayList<Plant> listOfPlants = new ArrayList<>();
	private Plant currentPlant;
	private MainFrame mainFrame;
	ArrayList<PlantType> plantTypes;
	ArrayList<Pot> pots;
	private Timer waterLevelTimer;
	private Timer checkGrowTimer;
	private Timer refreshPlantImageTimer;

	private boolean isPaused = false;
	private LocalDateTime pauseStartTime;
	private Duration totalPausedDuration = Duration.ZERO;
	private Random random = new Random();
	private FileManager file;
	private WidgetCreatorJFX widget = new WidgetCreatorJFX(); //used here only to access its image merging methods

	/**
	 * Constructor for Controller
	 * creates filemanager and mainframe, loads planttypes,
	 * pots and user data and turns on autosave
	 * @author Petri Närhi and others
	 * */
	public Controller() {

		this.file = new FileManager(this);
		mainFrame = new MainFrame(this);
		mainFrame.addMainMenu();
		plantTypes = file.loadPlantTypes();
		this.pots = file.loadPots();
		try {
			loadUserData();

		} catch (RuntimeException e) {}
		autoSave(true);

	}

	/**
	 * Autosave method
	 * starts file saving thread if turned on
	 * interrupts file saving thread if turned off
	 * @author Petri Närhi
	 * @param on boolean
	 * */
	public void autoSave(boolean on) {
		if (on) {
			file.start();
		}
		else {
			file.interrupt();
		}
	}

	public void confirmPlant(int plantNumber, int potNumber) {
		mainFrame.confirmPlantPanel(plantTypes.get(plantNumber).getGrownPlantImage(), pots.get(potNumber).getPotImage(), plantNumber, potNumber, plantTypes.get(plantNumber).getPlantInfoArray());
	}

	/**
	 * Creates a new plant object based on the choice of the user
	 * called by a boundary class
	 * @param plantNumber int, the index of the chosen plant in the GUI
	 * @param potNumber int, the index of the chosen pot in the GUI
	 * @author Petri Närhi, Elvira Grubb
	 * */
	public void createPlant(int plantNumber, int potNumber, String plantName) {
		PlantType type = plantTypes.get(plantNumber);
		String name = plantName;
		int initialWaterLevel = random.nextInt(21) * 5; //divisible by 5 so the watering will work as intended
		LocalDateTime dateAndTime = LocalDateTime.now();

		Plant newPlant = new Plant(name, initialWaterLevel, type, PlantStateEnum.small, dateAndTime, pots.get(potNumber)); //ny planta är alltid liten
		listOfPlants.add(newPlant);
		try {
			currentPlant.setLastPlant(false);
		} catch (NullPointerException e) {}
		currentPlant = newPlant;
		currentPlant.setLastPlant(true);
		System.out.println("New plant! " + currentPlant);
		showPlantView();
		startWaterLevelTimer();
		startCheckGrowTimer();
		startRefreshTimer();
		currentPlant.checkAndGrow();
		mainFrame.getPlantView().updatePlantDetails(currentPlant);
		saveUserData();
	}

	public void deletePlant(int selectedPlant)
	{
		if (selectedPlant < listOfPlants.size()) {
			listOfPlants.remove(selectedPlant);
		}
		try {
			currentPlant = listOfPlants.getLast();
			currentPlant.setLastPlant(true);
		} catch (Exception e) {
			currentPlant = null;
		}
		saveUserData();
	}

	/**
	 * Saves user data
	 * calls filemanager's methods to write plant list to file
	 * @author Petri Närhi
	 * */
	public synchronized void saveUserData() {
		try {
			file.writePlantsToFile(listOfPlants);
			//file.writeSettingsToFile();
			System.out.println("User data saved.");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Loads saved user data
	 * Calls filemanager's methods to read plants from file
	 * Future possible implementation: read settings
	 * @author Petri Närhi
	 * */
	public void loadUserData() {
		ArrayList<Plant> plantList;
		boolean soundEffectsOn;
		try {
			plantList = file.readPlantsFromFile();
			//soundEffectsOn = file.readSettingsFromFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (plantList != null && !plantList.isEmpty()) {
			listOfPlants = plantList;
			Plant plant;
			for (int i = 0; i < listOfPlants.size(); i++) {
				plant = listOfPlants.get(i);
				if(plant.isLastPlant()) {
					currentPlant = plant;
				}
			}
		}
		//soundEffectSetting = soundEffectsOn;
	}

	/**
	 * Pauses the plant timer if it is currently running. Records the start time of the pause
	 * Sets the isPaused flag to true
	 * @author Aleksander Augustyniak
	 */
	public void pausTime(){

			isPaused = true;
			pauseStartTime = LocalDateTime.now();
			stopWaterLevelTimer();
			stopCheckGrowTimer();
			System.out.println("Tid är pausad");
	}

	public void stopWaterLevelTimer(){
		if (waterLevelTimer != null) {
			waterLevelTimer.stop();
		}
	}
	public void stopCheckGrowTimer(){
		if (checkGrowTimer != null) {
			checkGrowTimer.stop();
		}
	}

	/**
	 * Returns the total duration for which the plant timer has been paused.
	 * This duration accumulates each time the timer is paused and resumed.
	 * @return Duration representing the total paused duration
	 */
	public Duration getTotalPausedDuration(){
		return totalPausedDuration;
	}

	/**
	 * Resumes the plant timer if it is currently paused. Calculates and accumulates the duration
	 * for which the timer was paused. Sets the isPaused flag to false and restarts the plant timer.
	 * @author Aleksander Augustyniak
	 */
	public void resumeTime(){
		if (isPaused){
			Duration pauseDuration = Duration.between(pauseStartTime, LocalDateTime.now());
			totalPausedDuration = totalPausedDuration.plus(pauseDuration);
			startWaterLevelTimer();
			startCheckGrowTimer();
			startRefreshTimer();
			System.out.println("Tiden återupptas");
		}
	}

	private void startCheckGrowTimer(){
		if (checkGrowTimer == null){
			checkGrowTimer = new Timer(60000, e -> {
				if (!isPaused){
					checkGrowthForAllPlants();
					mainFrame.getPlantView().updateElapsedTime();
					mainFrame.getPlantView().updatePlantDetails(currentPlant);
				}
			});
		}
		checkGrowTimer.start();
	}

	public void startRefreshTimer(){
		if (refreshPlantImageTimer == null){
			refreshPlantImageTimer = new Timer(1000, e -> {
				if (currentPlant.isStartDeathTimer()){
					mainFrame.getPlantView().updatePlantDetails(currentPlant);
				}
			});
			refreshPlantImageTimer.start();
		}
	}

	public void startWaterLevelTimer(){
			waterLevelTimer = new Timer(1000, e -> {
					decreaseWaterLevelForAllPlants();
					mainFrame.getPlantView().updatePlantDetails(currentPlant);
			});
			waterLevelTimer.start();

	}

	/**
	 * Waters the current plant by calling its waterPlant method.
	 * Updates the PlantView to reflect the new state of the plant
	 * @author Aleksander Augustyniak
	 */
	public void waterPlant(){
		currentPlant.waterPlant();
		mainFrame.getPlantView().updatePlantDetails(currentPlant);

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

	/**
	 * Skips a specific number of hours for the current plant. This method adjusts the plant's
	 * Creation time, increments its age, decreases its water level, and updates its state.
	 * The PlantView is the updated to reflect these changes
	 * @param hours the number of hours to skip. Must be a positive integer.
	 * @author Aleksander Augustyniak
	 */
	public void skipTime(int hours){
		LocalDateTime newCreationTime = currentPlant.getDateAndTime().minusHours(hours);
		currentPlant.setDateAndTime(newCreationTime);
		currentPlant.decreaseWaterLevel();
		currentPlant.updateState();
		currentPlant.checkAndGrow();
		mainFrame.getPlantView().updateElapsedTime();
		mainFrame.getPlantView().updatePlantDetails(currentPlant);
	}

	public void showPlantView()
	{
		mainFrame.addPlantView();
		if(currentPlant != null){
			mainFrame.getPlantView().updatePlantDetails(currentPlant);
			mainFrame.getPlantView().updateElapsedTime();
		}
	}

	/**
	 * Decreases the water level of all plants in the list by 1 unit.
	 * This method iterates through the list of plants and calls the decreaseWaterLevel method for each plant.
	 * @author Aleksander Augustyniak
	 */
	public void decreaseWaterLevelForAllPlants(){
		for (int i = 0; i < listOfPlants.size(); i++){
			Plant plant = listOfPlants.get(i);
			if(plant != null) {
				plant.decreaseWaterLevel();
			}
		}
	}

	public void checkGrowthForAllPlants(){
		for (Plant plant : listOfPlants){
			if (plant != null){
				plant.checkAndGrow();
				mainFrame.getPlantView().updatePlantDetails(currentPlant);
			}
		}
	}

	/**
	 * Gets the current plant
	 * to show the current plant that is shown in boundary.PlantView.PlantView
	 * and to be able to use the plant's methods in various classes
	 * @return Plant
	 * @author Petri Närhi
	 * */
	public Plant getCurrentPlant() {
		return currentPlant;
	}

	/**
	 * Sets the current plant
	 * to change the current plant that is shown in boundary.PlantView.PlantView
	 * @param selectedPlant int, the plant to replace the current plant selected in storage
	 * @author Petri Närhi
	 * */
	public void setCurrentPlant(int selectedPlant) {
		if (!listOfPlants.isEmpty()) {
			this.currentPlant.setLastPlant(false); //old plant is not the last plant anymore
			if (selectedPlant < listOfPlants.size()) {
				this.currentPlant = listOfPlants.get(selectedPlant);
			} else {
				this.currentPlant = listOfPlants.getLast();
			}
			this.currentPlant.setLastPlant(true); //sets the current plant as the last plant
		}
		else {
			this.currentPlant = null;
		}
	}

	public ArrayList<Plant> getListOfPlants() {
		return listOfPlants;
	}

	/**
	 * Creates images to be used for the plant buttons in Storage,
	 * then adds the Storage window
	 * @author Petri Närhi
	 * */
	public void createStorage()
	{
		ArrayList<ImageIcon> plantBtnImages = new ArrayList<>();
		ArrayList<ImageIcon> plantBtnHoverImages = new ArrayList<>();

		for (int i = 0; i < listOfPlants.size(); i++)
		{
			ImageIcon plant = listOfPlants.get(i).getImage();
			ImageIcon pot = listOfPlants.get(i).getPot();

			BufferedImage plantBuffered = widget.convertImageIconToBufferedImage(plant);
			BufferedImage potBuffered = widget.convertImageIconToBufferedImage(pot);
			BufferedImage combinedImage = widget.mergeAndDrawTheCombinedImages(plantBuffered, potBuffered);
			BufferedImage shrunkImage = resizeImage(combinedImage, 128, 160);
			BufferedImage hoverButton = brightenImage(shrunkImage, 1.5f);

			plantBtnImages.add(new ImageIcon(shrunkImage));
			plantBtnHoverImages.add(new ImageIcon(hoverButton));
		}
		mainFrame.addStoragePanel(plantBtnImages, plantBtnHoverImages);
	}

	/**
	 * Resizes an image (type BufferedImage)
	 * used to create buttons of plants dynamically for Storage
	 * based on the actual state of the plant in question
	 * @author Petri Närhi
	 * @param originalImage BufferedImage
	 * @param width int, desired width of the image
	 * @param height int, desired height of the image
	 * @return resizedImage BufferedImage
	 * */
	public BufferedImage resizeImage(BufferedImage originalImage, int width, int height)
	{
		BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
		Graphics2D graphics2D = resizedImage.createGraphics();
		graphics2D.drawImage(originalImage, 0, 0, width, height, null);
		graphics2D.dispose();
		return resizedImage;
	}

	/**
	 * Changes brightness of image (type BufferedImage)
	 * used to create hover buttons dynamically for the Storage panel
	 * @author Petri Närhi
	 * @param image BufferedImage, the image to be brightened/darkened
	 * @param scaleFactor float, desired brightness
	 * @return image BufferedImage
	 * */
	public BufferedImage brightenImage(BufferedImage image, float scaleFactor)
	{
		RescaleOp rescaleOp = new RescaleOp(scaleFactor, 0, null);
		return rescaleOp.filter(image, null);
	}
}