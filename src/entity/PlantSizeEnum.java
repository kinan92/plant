package entity;

public enum PlantSizeEnum {
	
	little("Littel", 0),
	big("Big", 5),
	dead("dead", 10);

	private final String size;

	private final int age;

	PlantSizeEnum(String size, int age) {

		this.size = size;
		this.age = age;
	}

	public String getSize() {
		return size;
	}

	public int getPlantEnumAge() {
		return age;
	}

}
