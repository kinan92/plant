package entity;

import java.time.LocalDateTime;

import javax.swing.ImageIcon;

public class Plant {
	
	private String name;
	private int age;
	private ImageIcon image;
	private LocalDateTime dateAndTime;
	private int waterLevel;
	
	public Plant(String name, int age, ImageIcon image, LocalDateTime dateAndTime) {
		super();
		this.name = name;
		this.age = age;
		this.image = image;
		this.dateAndTime = dateAndTime;
		this.waterLevel = 0;
	}
	public void waterPlant(){
		waterLevel++;
	}

	public int getWaterLevel(){
		return this.waterLevel;
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
