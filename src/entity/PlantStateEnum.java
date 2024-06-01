package entity;

public enum PlantStateEnum {

	small("Seedling"),
	medium("Growing"),
	large("Fully grown"),
	smallDead("Dead"),
	mediumDead("Dead"),
	largeDead("Dead");

	private final String state;

	PlantStateEnum(String state)
	{
		this.state = state;
	}

	/**
	 * Gets the dead state for the current plant state.
	 * This method checks the current plant state and returns the corresponding dead state.
	 * If the state is small, it returns smallDead and so on.
	 * @return PlantStateEnum representing the dead state for the current plant state.
	 * @author Aleksander Augustyniak
	 */
	public PlantStateEnum getDeadState()
	{
		switch (this){
			case small:
				return smallDead;
			case medium:
				return mediumDead;
			case large:
				return largeDead;
			default:
				return this;
		}
	}
}