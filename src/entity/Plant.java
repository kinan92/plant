package entity;

import java.time.LocalDateTime;
import javax.swing.ImageIcon;

public class Plant {
	
	private String name;
	private int age;
	private ImageIcon image ;
	private LocalDateTime dateAndTime;
	private int waterLevel;
	private final int WATER_INCREMENT = 5;
	private final int WATER_DECREMENT = 1;
	private PlantSizeEnum size;

	public Plant(String name, int age, String imagePath, int initialWaterLevel, PlantSizeEnum size) {
		super();
		this.name = name;
		this.age = age;
		this.image = new ImageIcon(imagePath);
		this.dateAndTime = LocalDateTime.now();
		this.waterLevel = initialWaterLevel;
		this.size = size;
	}

	public PlantSizeEnum getSize(){
		return size;
	}

	public void setSize(PlantSizeEnum size){
		this.size = size;
	}

	public void adjustSizeBasedOnWaterLevel(){
		if (waterLevel < 10){
			setSize(PlantSizeEnum.dead);
		}
		// Lägg till fler efterhand eller byt concept
	}

	public void waterPlant(){
		waterLevel += WATER_INCREMENT;
	}

	public void incrementAge(){
		this.age++;
	}


	public void decreaseWaterLevel(){
		waterLevel = Math.max(waterLevel - WATER_DECREMENT, 0);
	}


	public int getWaterLevel(){
		return this.waterLevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null && !name.trim().isEmpty()){
			this.name = name;
		}
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
	public void setImage(String imagePath) {
		if (imagePath != null){
			this.image = new ImageIcon(imagePath);
		}
	}
	
	public LocalDateTime getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(LocalDateTime dateAndTime) {
		if (dateAndTime != null){
			this.dateAndTime = dateAndTime;
		}
	}
}