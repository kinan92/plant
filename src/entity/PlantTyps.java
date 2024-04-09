package entity;

import java.awt.Image;

public class PlantTyps {
	Plant plant ;
	String bilderEnum = PlantSizeEnum.big.getSize();
	Image image;
	public void creatPlant () {
		
		
		plant= new Plant(bilderEnum, 0, bilderEnum, null);
	}
	
	//private String getImageName() {
		
		
	//	return String;
//	}
}
