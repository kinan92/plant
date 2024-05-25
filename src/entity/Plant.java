package entity;

import controller.Controller;

import java.time.Duration;
import java.time.LocalDateTime;
import javax.swing.*;

public class Plant {
	
	private String name;
	private ImageIcon image;
	private LocalDateTime dateAndTime;
	private LocalDateTime growthStartTime;
	private Timer deathTimer;
	private int waterLevel;
	private final int WATER_INCREMENT = 5;
	private final int WATER_DECREMENT = 1;
	private PlantType type;
	private PlantStateEnum state;
	private Pot pot;
	private Controller controller;

	/**
	 * Constructor for plant
	 * @param name the String user has typed in as its name
	 *               creating grown plants becomes an option
	 * @param initialWaterLevel int, a random water level for new plants
	 * @param type PlantType, its species
	 * @param state PlantStateEnum, little, medium, grown or any of the dead states,
	 *                 new plants start as little
	 * @param dateAndTime LocalDateTime, the exact time the plant was created
	 * @author Petri Närhi
	 * */
	public Plant(String name, int initialWaterLevel, PlantType type, PlantStateEnum state, LocalDateTime dateAndTime, Pot pot, Controller controller) {
		super();
		this.controller = controller;
		this.name = name;

		this.dateAndTime = dateAndTime;
		this.growthStartTime = dateAndTime;
		this.waterLevel = initialWaterLevel;
		this.type = type;
		this.state = state;
		updateStateImage(state);
		this.pot = pot; //läggs till som parameter sen när vi har ett "välj kruka"-fönster
	}

	/**
	 * Updates the image for the plant based on its state
	 * @param state PlantStateEnum
	 * @author Petri Närhi
	 * @author Aleksander Augustyniak
	 * */
	public void updateStateImage(PlantStateEnum state)
	{
		switch (state) {
			case little -> this.image = type.getLittlePlantImage();
			case big -> this.image = type.getGrownPlantImage();
			case dead -> this.image = type.getDeadPlantImage();
		}
	}

	/**
	 * Increases the water level of the plant by a predefined increment.
	 * Updates the state of the plant based on the new water level (Just for test)
	 * @author Aleksander Augustyniak
	 */
	public void waterPlant(){
			waterLevel += WATER_INCREMENT;
			cancelDeathTimer();
			updateState();
	}

	/**
	 * Decreases the water level of the plant by a predefined decrement if the current water level is above 0.
	 * Updates the state of the platn based on the new level
	 * @param
	 * @author Aleksander Augustyniak
	 */
	public void decreaseWaterLevel(){
		if(waterLevel > 0) {
			waterLevel -= WATER_DECREMENT;
			if (waterLevel <= 0) {
				startDeathTimer();
			}
		}
		updateState();
	}

	/**
	 * Updates the state of the plant based on its water level
	 * If the water level is greater than zero, the death timer is canceled.
	 * If the water level is zero, the death timer is started
	 * The method then updates the state image to reflect the current state of the plant
	 * @author Aleksander Augustyniak
	 */
	public void updateState(){
		if (waterLevel > 0) {
			cancelDeathTimer();
		} else {
			startDeathTimer();
		}
		updateStateImage(getState());
	}

	/**
	 * Checks the growth conditions for the plant and updates its state if the conditions are met.
	 * If the growth start timer is not set, it initializes it to the current time.
	 * Then, it calculates the duration since the last growth check.
	 * If the duration is at least 2 days and the water level is greater than,
	 * the plant's state is set to 'big', the growth start timer is updated, and the state image is updated,
	 * It updates the plant details in the UI
	 */
	public void checkAndGrow(){
		if (growthStartTime == null){
			growthStartTime = LocalDateTime.now();
		}
		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(dateAndTime, now);
		if (duration.toDays() >= 2 && waterLevel > 0){
			setState(PlantStateEnum.big);
			growthStartTime = now;
			updateStateImage(getState());
		}
			PlantStateEnum state = getState();
			setState(state);
			controller.getMainFrame().getPlantView().updatePlantDetails(Plant.this);
	}

	/**
	 * Starts the death timer for the plant.
	 * If the death timer is not initialized, it creates a new timer that then sets the plant's state to 'dead',
	 * updates the state image, updates the plant details in the UI, and stops the timer. The timer is started regardless of its previous state
	 */
	private void startDeathTimer(){
		if (deathTimer == null){
			deathTimer = new Timer(10000, e -> {
                setState(PlantStateEnum.dead);
                updateStateImage(getState());
                controller.getMainFrame().getPlantView().updatePlantDetails(Plant.this);
                deathTimer.stop();
            });
		}
		deathTimer.start();
	}

	/**
	 * Cancels the death timer if it is currently running.
	 * This method checks if the death timer is not null and is running,
	 * and stops the timer if both conditions are met.
	 */
	private void cancelDeathTimer(){
		if (deathTimer != null && deathTimer.isRunning()){
			deathTimer.stop();
		}
	}

	/**
	 * Gets the current water level of the plant
	 * @return the water level
	 */
	public int getWaterLevel(){
		return this.waterLevel;
	}
	public boolean needsWater(){
		return waterLevel < 10;
	}
	public boolean isOverWatered(){
		return waterLevel > 100;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ImageIcon getImage() {
		return image;
	}
	public void setImage(ImageIcon image) {
		this.image = image;
	}
	
	public LocalDateTime getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(LocalDateTime dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	/**
	 * Getter for plant type
	 * @return type PlantType
	 * @author Petri Närhi
	 * */
	public PlantType getType() {
		return type;
	}

	/**
	 * Getter for plant state
	 * @return state PlantStateEnum
	 * @author Petri Närhi
	 * */
	public PlantStateEnum getState() {
		return state;
	}

	/**
	 * Setter for plant state
	 * @param state PlantStateEnum
	 * @author Petri Närhi
	 * */
	public void setState(PlantStateEnum state) {
		this.state = state;
	}

	/**
	 * Getter for pot image
	 * @return pot ImageIcon
	 * @author Petri Närhi
	 * */
	public ImageIcon getPot()
	{
		return pot.getPotImage();
	}

	/**
	 * Setter for pot image
	 * @param pot ImageIcon
	 * @author Petri Närhi
	 * */
	public void setPot(ImageIcon pot)
	{
		//this.pot = pot;
	}

	/**
	 * To String method for test purposes
	 * @return String toString
	 * @author Petri Närhi
	 * */
	public String toString() {
		return ("Name: " + name + " | Age: " + " | Image: "  + image + " | Created: "  + dateAndTime + " | WaterLevel: " + waterLevel + " | " + type + " | State: "  + state);
	}
}