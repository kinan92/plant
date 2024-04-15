package entity;

import java.awt.Image;
import java.time.LocalDateTime;

import javax.swing.ImageIcon;

public class Plant {
	
	private String name;
	private int age;
	private String image ;
	private LocalDateTime dateAndTime;
	
	public Plant(String name, int age, String image, LocalDateTime dateAndTime) {
		super();
		this.name = name;
		this.age = age;
		this.image = image;
		this.dateAndTime = dateAndTime;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public LocalDateTime getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(LocalDateTime dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	
}
