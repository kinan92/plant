package entity;

public enum PlantStateEnum {
	
	little("Little"),
	big("Big"),
	dead("dead");

	private final String state;

	PlantStateEnum(String state) {
		this.state = state;

	}
}