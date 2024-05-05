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

	public Plant(String name, int age, int initialWaterLevel, PlantType type, PlantStateEnum state, LocalDateTime dateAndTime) {
		super();
		this.name = name;
		this.age = age;
		this.dateAndTime = dateAndTime;
		this.waterLevel = initialWaterLevel;
		this.type = type;
		this.state = state;
		switch (state) {
			case little -> this.image = type.getLittlePlantImage();
			case big -> this.image = type.getGrownPlantImage();
			case dead -> this.image = type.getDeadPlantImage();
		}
		this.pot = new ImageIcon("images/pots/default_pot.png"); //läggs till som parameter sen när vi har ett "välj kruka"-fönster
	}

	public void waterPlant(){
		waterLevel += WATER_INCREMENT;
	}

	public void incrementAge(int age){
		this.age += age;
	}


	public void decreaseWaterLevel(int level){
		if(waterLevel > 0){
			waterLevel -= WATER_DECREMENT;
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

	public PlantType getType() {
		return type;
	}

	public void setType(PlantType type) {
		this.type = type;
	}

	public PlantStateEnum getState() {
		return state;
	}

	public void setState(PlantStateEnum state) {
		this.state = state;
	}

	public ImageIcon getPot() {
		return pot;
	}

	public void setPot(ImageIcon pot) {
		this.pot = pot;
	}

	//for test purposes
	public String toString() {
		return ("Name: " + name + " | Age: " + age + " | Image: "  + image + " | Created: "  + dateAndTime + " | WaterLevel: " + waterLevel + " | " + type + " | State: "  + state);
	}
}
