package entity;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.swing.*;

public class Plant implements Serializable {
	private String name;
	private ImageIcon image;
	private LocalDateTime dateAndTime;
	private LocalDateTime growthStartTime;
	private Timer deathTimer;
	private Timer refreshPlantImageTimer;
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
	 * @param state PlantStateEnum, little, medium, grown or any of the dead states,
	 *                 new plants start as little
	 * @param dateAndTime LocalDateTime, the exact time the plant was created
	 * @author Petri Närhi
	 * */
	public Plant(String name, int initialWaterLevel, PlantType type, PlantStateEnum state, LocalDateTime dateAndTime, Pot pot) {
		super();
		this.name = name;
		this.dateAndTime = dateAndTime;
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
			case small -> this.image = type.getLittlePlantImage();
			case medium -> this.image = type.getMediumPlantImage();
			case large -> this.image = type.getGrownPlantImage();
			case smallDead -> this.image = type.getSmallDeadPlantImage();
			case mediumDead -> this.image = type.getMediumDeadPlantImage();
			case largeDead -> this.image = type.getLargeDeadPlantImage();
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

	public void updateState(){
		if (waterLevel > 0) {
			cancelDeathTimer();
		} else {
			startDeathTimer();
		}
		updateStateImage(getState());
	}

	public void checkAndGrow(){
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

	private void startDeathTimer(){
		if (deathTimer == null){

			deathTimer = new Timer(3000, e -> {
				isDeathStarted = true;
				setState(getState().getDeadState());
				updateStateImage(getState());
				deathTimer.stop();
			});
		}
		isDeathStarted = false;
		deathTimer.start();
	}

	private void cancelDeathTimer(){
		if (deathTimer != null && deathTimer.isRunning()){
			deathTimer.stop();
		}
	}

	public boolean isStartDeathTimer(){
		return isDeathStarted;
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
		return ("Name: " + name + " | Image: "  + image + " | Created: "  + dateAndTime + " | WaterLevel: " + waterLevel + " | " + type + " | State: "  + state);
	}
}