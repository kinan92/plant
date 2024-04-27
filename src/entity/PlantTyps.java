package entity;

import java.awt.Image;
import java.time.LocalDateTime;

public class PlantTyps {
	Plant plant ;
	String bilderEnum = PlantSizeEnum.big.getSize();
	Image image;
	public void creatPlant () {
		
		
		plant= new Plant(bilderEnum, 0, bilderEnum, 0, LocalDateTime.now());
	}
}