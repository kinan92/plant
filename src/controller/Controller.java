package controller;

import boundary.*;
import boundary.Widget.WidgetCreatorJFX;
import entity.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
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
	private Timer plantTimer;
	private boolean isPaused = false;
	private LocalDateTime pauseStartTime;
	private Duration totalPausedDuration = Duration.ZERO;
	private Random random = new Random();
	private FileManager file;
	private WidgetCreatorJFX widget = new WidgetCreatorJFX(); //used here only to access its image merging methods

	public Controller() {
		mainFrame = new MainFrame(this);
		mainFrame.addMainMenu();
		this.file = new FileManager();
		plantTypes = file.loadPlantTypes();
		pots = file.loadPots();
		/*startWaterDecreaseTimer();
		startAgeTimer();*/
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

		Plant newPlant = new Plant(name, 0, initialWaterLevel, type, PlantStateEnum.little, dateAndTime, pots.get(potNumber)); //ny planta är alltid liten
		listOfPlants.add(newPlant);
		currentPlant = newPlant;
		System.out.println("New plant! " + currentPlant);
		showPlantView();
		mainFrame.getPlantView().updatePlantDetails(currentPlant);
		startPlantTimer();
	}

	/**
	 * Pauses the plant timer if it is currently running. Records the start time of the pause
	 * Sets the isPaused flag to true
	 * @author Aleksander Augustyniak
	 */
	public void pausTime(){
		if(!isPaused){
			isPaused = true;
			pauseStartTime = LocalDateTime.now();
			stopPlantTimer();
			System.out.println("Tid är pausad");
		}
	}

	/**
	 * Stops the plant timer if it is currently running.
	 * Ensures that the timer does not continue to update plant state while paused
	 * @author Aleksander Augustyniak
	 */
	public void stopPlantTimer() {
		if (plantTimer != null) {
			plantTimer.stop();
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
			isPaused = false;
			Duration pauseDuration = Duration.between(pauseStartTime, LocalDateTime.now());
			totalPausedDuration = totalPausedDuration.plus(pauseDuration);
			startPlantTimer();
			System.out.println("Tiden återupptas");
		}
	}

	/**
	 * Starts the plant timer if it is not already running. The timer updates the plant's age,
	 * decreases the water level for all plants, and updates the elapsed time in the PlantView.
	 * @author Aleksander Augustyniak
	 */
	private void startPlantTimer(){
		if (plantTimer == null){
			plantTimer = new Timer(1000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!isPaused){
						incrementAgeForALlPlants();
						decreaseWaterLevelForAllPlants();
						mainFrame.getPlantView().updateElapsedTime();
					}
				}
			});
		}
		plantTimer.start();
	}

	private void updateAge(){
		for (Plant plant : listOfPlants){
			if (plant != null){
				plant.incrementAge(1);
			}
		}
	}

	/**
	 * @author Elvira Grubb
	 * Test class to make sure the planttypes are read correctly. Will be deleted when PlantType class is done
	 */
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

	/**
	 * Waters the current plant by calling its waterPlant method.
	 * Updates the PlantView to reflect the new state of the plant
	 * @author Aleksander Augustyniak
	 */
	public void waterPlant(){
		currentPlant.waterPlant();
		mainFrame.getPlantView().updatePlantDetails(currentPlant);

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

	/**
	 * Skips a specific number of hours for the current plant. This method adjusts the plant's
	 * Creation time, increments its age, decreases its water level, and updates its state.
	 * The PlantView is the updated to reflect these changes
	 * @param hours the number of hours to skip. Must be a positive integer.
	 * @author Aleksander Augustyniak
	 */
	public void skipTime(int hours){
		if (hours <= 0){
			System.out.println("Skipped time requires a positive number of hours");
			return;
		}
		LocalDateTime newCreationTime = currentPlant.getDateAndTime().minusHours(hours);
		currentPlant.setDateAndTime(newCreationTime);
		int ageIncrement = hours / 24;
		currentPlant.incrementAge(ageIncrement);
		currentPlant.decreaseWaterLevel();
		currentPlant.updateState();
		mainFrame.getPlantView().updateElapsedTime();
		mainFrame.getPlantView().updatePlantDetails(currentPlant);
		notifyTimeSkipped(hours);
	}

	public void showPlantView()
	{
		mainFrame.addPlantView();
	}

	/**
	 * Increments the age of all plants in the list by 1 unit.
	 * This method iterates through the list of plants and calls the incrementAge method for each plant.
	 * @author Aleksander Augustyniak
	 */
	public void incrementAgeForALlPlants(){
		for (int i = 0; i < listOfPlants.size(); i++){
			Plant plant = listOfPlants.get(i);
			if(plant != null){
				plant.incrementAge(1);
			}
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
	 * Gets the current plant
	 * to show the current plant that is shown in boundary.PlantView.PlantView
	 * and to be able to use the plant's methods in various classes
	 * @return Plant
	 * @author Petri Närhi
	 * */
	public Plant getCurrentPlant() {
		return currentPlant;
	}
	public boolean isPaused(){
		return isPaused;
	}

	/**
	 * Sets the current plant
	 * to change the current plant that is shown in boundary.PlantView.PlantView
	 * @param selecedPlant int, the plant to replace the current plant selected in storage
	 * @author Petri Närhi
	 * */
	public void setCurrentPlant(int selecedPlant) {
		this.currentPlant = listOfPlants.get(selecedPlant);
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