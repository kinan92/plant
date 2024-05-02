package entity;

public enum PlantStateEnum {
	
	little("Little"),
	big("Big"),
	dead("dead");

	private final String size;

	PlantStateEnum(String size) {

		this.size = size;

	}

	public String getSize() {
		return size;
	}
}