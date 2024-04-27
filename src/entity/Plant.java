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

	public Plant(String name, int age, String imagePath, int initialWaterLevel) {
		super();
		this.name = name;
		this.age = age;
		this.image = new ImageIcon(imagePath);
		this.dateAndTime = dateAndTime;
		this.waterLevel = initialWaterLevel;
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

}
