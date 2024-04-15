package entity;

public enum PlantSizeEnum {
	
	little("Littel"),
	big("Big"),
	dead("dead");

	private final String size;



	PlantSizeEnum(String size) {

		this.size = size;

	}

	public String getSize() {
		return size;
	}


}
