package entity;

public enum PlantStateEnum {
	
	small("Seedling"),
	medium("Growing"),
	large("Fully grown"),
	smallDead("Dead"),
	mediumDead("Dead"),
	largeDead("Dead");

	private final String state;

	PlantStateEnum(String state) {
		this.state = state;
	}

	public String getState()
	{
		return state;
	}
}