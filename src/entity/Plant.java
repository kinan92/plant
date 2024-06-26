package entity;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.swing.*;

/**
 * Plant class holds data for the created plants
 * Serializable due to being saved in an array to file
 * @author Petri Närhi
 * @author Aleksander Augustyniak
 * @author Elvira Grubb
 * */
public class Plant implements Serializable
{
	private String name;
	private ImageIcon plantImage;
	private LocalDateTime dateAndTime;
	private LocalDateTime growthStartTime;
	private Timer deathTimer;
	private int waterLevel;
	private final int WATER_INCREMENT = 5;
	private final int WATER_DECREMENT = 1;
	private PlantType type;
	private PlantStateEnum state;
	private Pot pot;
	private boolean isLastPlant = false;
	private boolean isDeathStarted = false;

	/**
	 * Constructor for plant
	 * @param name the String user has typed in as its name
	 * @param initialWaterLevel int, a random water level for new plants
	 * @param type PlantType, its species
	 * @param state PlantStateEnum, small, medium, big or any of the dead states, new plants start as little
	 * @param dateAndTime LocalDateTime, the exact time the plant was created
	 * @param pot Pot, chosen pot for the plant
	 * @author Petri Närhi
	 * */
	public Plant(String name, int initialWaterLevel, PlantType type, PlantStateEnum state, LocalDateTime dateAndTime, Pot pot)
	{
		super();
		this.name = name;
		this.dateAndTime = dateAndTime;
		this.waterLevel = initialWaterLevel;
		this.type = type;
		this.state = state;
		updateStateImage(state);
		this.pot = pot;
	}

	/**
	 * Updates the image for the plant based on its state
	 * @param state PlantStateEnum
	 * @author Petri Närhi
	 * @author Aleksander Augustyniak
	 * @author Elvira Grubb
	 * */
	public void updateStateImage(PlantStateEnum state)
	{
		switch (state) {
			case small -> this.plantImage = type.getLittlePlantImage();
			case medium -> this.plantImage = type.getMediumPlantImage();
			case large -> this.plantImage = type.getGrownPlantImage();
			case smallDead -> this.plantImage = type.getSmallDeadPlantImage();
			case mediumDead -> this.plantImage = type.getMediumDeadPlantImage();
			case largeDead -> this.plantImage = type.getLargeDeadPlantImage();
		}
	}

	/**
	 * Increases the water level of the plant by a predefined increment.
	 * Updates the state of the plant based on the new water level (Just for test)
	 * @author Aleksander Augustyniak
	 */
	public void waterPlant()
	{
		waterLevel += WATER_INCREMENT;
		if (waterLevel >= 120){
			startDeathTimer();
	} else {
			cancelDeathTimer();
			updateDeathTimer();
		}
	}

	/**
	 * Decreases the water level of the plant by a predefined decrement if the current water level is above 0.
	 * Updates the state of the platn based on the new level
	 * @param
	 * @author Aleksander Augustyniak
	 */
	public void decreaseWaterLevel()
	{
		if(waterLevel > 0) {
			waterLevel -= WATER_DECREMENT;
			if (waterLevel <= 0) {
				startDeathTimer();
			}
		}
		updateDeathTimer();
	}

	/**
	 * Updates the death timer based on the current water level.
	 * If the water level is greater than zero, the death timer is canceled. Otherwise, the death timer is started.
	 * The plant's image is then updated to reflect its current state.
	 * @author Aleksander Augustyniak
	 */
	public void updateDeathTimer()
	{
		if (waterLevel > 0) {
			cancelDeathTimer();
		} else {
			startDeathTimer();
		}
		updateStateImage(getState());
	}

	/**
	 * Checks the growth of the plant and updates its state based on the elapsed time and water level.
	 * It first checks if the growthStartTime is null, and if so, initializes it to the current time.
	 * It then calculates the duration between the plant's creation time and the current time.
	 * Based on this duration and plant's water level, it updates the plant's state to either medium or large.
	 * Finally, it updates the plant's image to reflect the new state.
	 * @author Aleksander Augustyniak
	 */
	public void checkAndGrow()
	{
		if (growthStartTime == null){
			growthStartTime = LocalDateTime.now();
		}
		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(dateAndTime, now);
		if (duration.toDays() >= 4 && waterLevel > 0){
			setState(PlantStateEnum.large);
			growthStartTime = now;
			updateStateImage(getState());
		} else if (duration.toDays() >= 3 && waterLevel > 0){
			setState(PlantStateEnum.medium);
			growthStartTime = now;
			updateStateImage(getState());
		}
		PlantStateEnum state = getState();
		setState(state);
	}

	/**
	 * Starts the death timer for the plant.
	 * This method initializes a timer that triggers the death
	 * process for the plant after a set delay. When the timer completes,
	 * it sets the plant's state to the corresponding dead state and updates
	 * the plant's image.
	 * @author Aleksander Augustyniak
	 */
	private void startDeathTimer()
	{
		if (deathTimer == null){
			deathTimer = new Timer(3600000, e -> {
				isDeathStarted = true;
				setState(getState().getDeadState());
				updateStateImage(getState());
				deathTimer.stop();
			});
		}
		isDeathStarted = false;
		deathTimer.start();
	}

	/**
	 * Cancels the death timer if it is currently running.
	 * This method checks if the death timer is not null and is running,
	 * and stops the timer if both conditions are met.
	 * @author Aleksander Augustyniak
	 */
	private void cancelDeathTimer()
	{
		if (deathTimer != null && deathTimer.isRunning()){
			deathTimer.stop();
		}
	}

	/**
	 * Checks if the death timer has started for the plant.
	 * This method returns the status of whatever the death timer for the plant has started or not.
	 * @return boolean indicating if the death timer has started.
	 * @author Aleksander Augustyniak
	 */
	public boolean isStartDeathTimer(){
		return isDeathStarted;
	}

	public int getWaterLevel(){
		return this.waterLevel;
	}
	public String getName() {
		return name;
	}
	public ImageIcon getPlantImage() {
		return plantImage;
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
	 * Getter and setter for isLastPlant
	 * a boolean used to determine which plant is the current plant
	 * when loading from file
	 * @author Petri Närhi
	 * */
	public boolean isLastPlant() {
		return isLastPlant;
	}

	public void setLastPlant(boolean lastPlant) {
		isLastPlant = lastPlant;
	}

	/**
	 * To String method for test purposes
	 * @return String toString
	 * @author Petri Närhi
	 * */
	public String toString() {
		return ("Name: " + name + " | Image: "  + plantImage + " | Created: "  + dateAndTime + " | WaterLevel: " + waterLevel + " | " + type + " | State: "  + state);
	}
}