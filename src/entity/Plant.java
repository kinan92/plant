package entity;

import java.awt.*;
import java.time.LocalDateTime;

import javax.swing.ImageIcon;

public class Plant {
	
	private String name;
	private int age;
	private ImageIcon image;
	private LocalDateTime dateAndTime;
	private int waterLevel;
	private final int WATER_INCREMENT = 5;
	private final int WATER_DECREMENT = 1;
	private PlantType type;
	private PlantStateEnum state;
	private ImageIcon pot;

	/**
	 * Constructor for plant
	 * @param name the String user has typed in as its name
	 * @param age int, starts at 0 for new plants but is a variable in case
	 *               creating grown plants becomes an option
	 * @param initialWaterLevel int, a random water level for new plants
	 * @param type PlantType, its species
	 * @param state PlantStateEnum, little, medium, grown or any of the dead states,
	 *                 new plants start as little
	 * @param dateAndTime LocalDateTime, the exact time the plant was created
	 * @author Petri Närhi
	 * */
	public Plant(String name, int age, int initialWaterLevel, PlantType type, PlantStateEnum state, LocalDateTime dateAndTime) {
		super();
		this.name = name;
		this.age = age;
		this.dateAndTime = dateAndTime;
		this.waterLevel = initialWaterLevel;
		this.type = type;
		this.state = state;
		updateStateImage(state);
		this.pot = new ImageIcon("images/pots/default_pot.png"); //läggs till som parameter sen när vi har ett "välj kruka"-fönster
	}

	/**
	 * Updates the image for the plant based on its state
	 * @param state PlantStateEnum
	 * @author Petri Närhi
	 * */
	public void updateStateImage(PlantStateEnum state)
	{
		switch (state) {
			case little -> this.image = type.getLittlePlantImage();
			case big -> this.image = type.getGrownPlantImage();
			case dead -> this.image = type.getDeadPlantImage();
		}
	}

	public void waterPlant(){
		if (waterLevel < 100){
			waterLevel += WATER_INCREMENT;
			updateState();
		}

	}

	public void incrementAge(int age){
		this.age += age;
		updateState();
	}


	public void decreaseWaterLevel(int level){
		if(waterLevel > 0){
			waterLevel -= WATER_DECREMENT;
		}
		updateState();
	}

	public void updateState(){
		if (waterLevel <= 0){
			setState(PlantStateEnum.dead);
		} else if (waterLevel >= 75){
			setState(PlantStateEnum.big);
		} else {
			setState(PlantStateEnum.little);
		}
		updateImage();
	}

	private void updateImage(){
		switch (state){
			case little:
				this.image = type.getLittlePlantImage();
				break;
			case big:
				this.image = type.getGrownPlantImage();
				break;
			case dead:
				this.image = type.getDeadPlantImage();
				break;
		}
	}


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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
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
	public ImageIcon getPot() {
		return pot;
	}

	/**
	 * Setter for pot image
	 * @param pot ImageIcon
	 * @author Petri Närhi
	 * */
	public void setPot(ImageIcon pot) {
		this.pot = pot;
	}

	/**
	 * To String method for test purposes
	 * @return String toString
	 * @author Petri Närhi
	 * */
	public String toString() {
		return ("Name: " + name + " | Age: " + age + " | Image: "  + image + " | Created: "  + dateAndTime + " | WaterLevel: " + waterLevel + " | " + type + " | State: "  + state);
	}
}
