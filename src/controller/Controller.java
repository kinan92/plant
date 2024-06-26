package controller;

import boundary.*;
import boundary.Widget.WidgetCreatorJFX;
import entity.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

/**
 * Main control for the program, controls the flow
 * @author Petri Närhi
 * @author Elvira Grubb
 * @author Aleksander Augustyniak
 * */
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
	 * @author Petri Närhi
	 * @author Elvira Grubb
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
		startRefreshTimer();
	}

	/**
	 * Autosave method
	 * starts file saving thread if turned on
	 * interrupts file saving thread if turned off
	 * @param on boolean
	 * @author Petri Närhi
	 * */
	public void autoSave(boolean on) {
		if (on) {
			file.start();
		}
		else {
			file.interrupt();
		}
	}

	/**
	 * Calls to a MainFrame method to open the confirm plant panel
	 * @param plantNumber An int corresponding to a PlantType in the ArrayList of PlantTypes
	 * @param potNumber An int corresponding to a pot in the pot ArrayList
	 * @author Elvira Grubb
	 */
	public void confirmPlant(int plantNumber, int potNumber)
	{
		mainFrame.confirmPlantPanel(plantTypes.get(plantNumber).getGrownPlantImage(), pots.get(potNumber).getPotImage(), plantNumber, potNumber, plantTypes.get(plantNumber).getPlantInfoArray());
	}

	/**
	 * Creates a new plant object based on the choice of the user
	 * called by a boundary class
	 * @param plantNumber int, the index of the chosen plant in the GUI
	 * @param potNumber int, the index of the chosen pot in the GUI
	 * @param plantName The name the user has selected for their plant
	 * @author Petri Närhi, Elvira Grubb
	 * */
	public void createPlant(int plantNumber, int potNumber, String plantName) {
		PlantType type = plantTypes.get(plantNumber);
		int initialWaterLevel = random.nextInt(21) * 5; //divisible by 5 so the watering will work as intended
		LocalDateTime dateAndTime = LocalDateTime.now();

		Plant newPlant = new Plant(plantName, initialWaterLevel, type, PlantStateEnum.small, dateAndTime, pots.get(potNumber));
		listOfPlants.add(newPlant);
		try {
			currentPlant.setLastPlant(false);
		} catch (NullPointerException e) {}
		currentPlant = newPlant;
		currentPlant.setLastPlant(true);
		System.out.println("New plant! " + currentPlant);
		showPlantView();
		checkGrowthForAllPlants();
		startRefreshTimer();
		currentPlant.checkAndGrow();
		mainFrame.getPlantView().updatePlantDetails(currentPlant);
		saveUserData();
	}

	/**
	 * Deletes a selected plant in Storage
	 * then sets the last plant in the list as current plant if not null
	 * @param selectedPlant int, receives index of selected plant
	 * @author Petri Närhi
	 * */
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
	 * synchronized since both main thread and autosave thread uses it
	 * @author Petri Närhi
	 * */
	public synchronized void saveUserData() {
		try {
			file.writePlantsToFile(listOfPlants);
			System.out.println("User data saved.");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Loads saved user data
	 * Calls filemanager's methods to read plants from file
	 * finds current plant from the list of plants by checking boolean value
	 * @author Petri Närhi
	 * */
	public void loadUserData()
	{
		ArrayList<Plant> plantList;
		try {
			plantList = file.readPlantsFromFile();
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

	/**
	 * Stops the water level timer if it is currently running
	 * @author Aleksander Augustyniak
	 */
	public void stopWaterLevelTimer(){
		if (waterLevelTimer != null) {
			waterLevelTimer.stop();
		}
	}

	/**
	 * Stops the growth check timer if it is currently running
	 * @author Aleksander Augustyniak
	 */
	public void stopCheckGrowTimer(){
		if (checkGrowTimer != null) {
			checkGrowTimer.stop();
		}
	}

	/**
	 * Returns the total duration for which the plant timer has been paused.
	 * This duration accumulates each time the timer is paused and resumed.
	 * @return Duration representing the total paused duration
	 * @author Aleksander Augustyniak
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

	/**
	 * Starts the timer that periodically check for plant growth.
	 * The timer is set to check every 60 seconds.
	 * If the timer is paused, it will not perform the growth check.
	 * @author Aleksander Augustyniak
	 */
	public void startCheckGrowTimer(){
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

	/**
	 * Starts the timer that periodically refreshes the plant image.
	 * The timer is set to refresh every second.
	 * It updates the plant details if the death timer for the current plant is running
	 * @author Aleksander Augustyniak
	 */
	public void startRefreshTimer(){
		if (refreshPlantImageTimer == null && currentPlant != null){
			refreshPlantImageTimer = new Timer(1000, e -> {
				if (currentPlant.isStartDeathTimer()){
					mainFrame.getPlantView().updatePlantDetails(currentPlant);
				}
			});
			refreshPlantImageTimer.start();
		}
	}

	/**
	 * Starts the timer that periodically deceases the water level for all plants.
	 * The timer is set to decease the water level every 30 mins
	 * @author Aleksander Augustyniak
	 */
	public void startWaterLevelTimer(){
		waterLevelTimer = new Timer(1300000 , e -> {
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

	/**
	 * Creates the ChoosePlantFrame by making ArrayLists of the button images and then creating the panel
	 * @author Elvira Grubb
	 * @author Petri Närhi (refactored from Strings to ImageIcons throughout the program)
	 */
	public void createChoosePlantPanel()
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

	/**
	 * Calls on a method to create the Main Menu
	 * @author Elvira Grubb
	 */
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
		currentPlant.updateDeathTimer();
		currentPlant.checkAndGrow();
		mainFrame.getPlantView().updateElapsedTime();
		mainFrame.getPlantView().updatePlantDetails(currentPlant);
	}

	/**
	 * Shows the PlantView if the currentPlant isn't null. If currentPlant is null the method will show a JOptionPane informing the user
	 * @author Elvira Grubb
	 */
	public void showPlantView()
	{
		if (currentPlant != null)
		{
			mainFrame.addPlantView();
			mainFrame.getPlantView().updatePlantDetails(currentPlant);
			mainFrame.getPlantView().updateElapsedTime();
		}

		else
		{
			JOptionPane.showMessageDialog(mainFrame, "No last plant found.");
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

	/**
	 * Checks the growth state for all plants in the list and updates the details.
	 * It iterates through the list of plants and invokes the checkAndGrow method on each plant.
	 * It then updates the plant details in the view for the current plant.
	 * @author Aleksander Augustyniak
	 */
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
	 * to show the current plant that is shown in PlantView
	 * and to be able to use the plant's methods in various classes
	 * @return Plant
	 * @author Petri Närhi
	 * */
	public Plant getCurrentPlant() {
		return currentPlant;
	}

	/**
	 * Sets the current plant
	 * to change the current plant that is shown in PlantView
	 * checks if list of plants is empty first to avoid null exception
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

	/**
	 * @author Petri Närhi
	 * */
	public ArrayList<Plant> getListOfPlants() {
		return listOfPlants;
	}

	/**
	 * Creates buttons and hover buttons to be used for the plants in Storage,
	 * then adds the Storage window
	 * Uses the current look of the plants
	 * @author Petri Närhi
	 * */
	public void createStorage()
	{
		ArrayList<ImageIcon> plantBtnImages = new ArrayList<>();
		ArrayList<ImageIcon> plantBtnHoverImages = new ArrayList<>();

		for (int i = 0; i < listOfPlants.size(); i++)
		{
			ImageIcon plant = listOfPlants.get(i).getPlantImage();
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
	 * @param originalImage BufferedImage
	 * @param width int, desired width of the image
	 * @param height int, desired height of the image
	 * @return resizedImage BufferedImage
	 * @author Petri Närhi
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
	 * @param image BufferedImage, the image to be brightened/darkened
	 * @param scaleFactor float, desired brightness
	 * @return image BufferedImage
	 * @author Petri Närhi
	 * */
	public BufferedImage brightenImage(BufferedImage image, float scaleFactor)
	{
		RescaleOp rescaleOp = new RescaleOp(scaleFactor, 0, null);
		return rescaleOp.filter(image, null);
	}
}