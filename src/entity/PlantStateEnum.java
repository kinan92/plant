package entity;

public enum PlantStateEnum {
	
	small("Small"),
	medium("Medium"),
	large("Big"),
	smallDead("Small dead"),
	mediumDead("Medium dead"),
	largeDead("Large dead");

	private final String state;

	PlantStateEnum(String state) {
		this.state = state;
	}
}