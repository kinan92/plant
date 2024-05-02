package entity;

import javax.swing.*;

/**
 * Stores the plant data regarding generic information
 * (generic = same for all plants of the type rather than specific plant)
 * @author Elvira Grubb (main)
 * @author Petri Närhi (edits)
 * */

public class PlantType {
    private String plantTypeName;
    private String plantTypeNameAlternative;
    private ImageIcon grownPlantImage;
    private ImageIcon plantImageButton;
    private ImageIcon plantImageButtonHover;
    private String plantInformation;

    public PlantType(String plantTypeName, String plantTypeNameAlternative, String grownPlantImage, String plantImageButton, String plantImageButtonHover, String plantInformation)
    {
        this.plantTypeName = plantTypeName;
        this.plantTypeNameAlternative = plantTypeNameAlternative;
        this.grownPlantImage = new ImageIcon(grownPlantImage);
        this.plantImageButton = new ImageIcon(plantImageButton);
        this.plantImageButtonHover = new ImageIcon(plantImageButtonHover);
        this.plantInformation = plantInformation;
    }
    public String getPlantTypeName()
    {
        return plantTypeName;
    }
    public String getPlantTypeNameAlternative()
    {
        return  plantTypeNameAlternative;
    }

    public ImageIcon getGrownPlantImage()
    {
        return grownPlantImage;
    }

    public ImageIcon getPlantImageButton()
    {
        return plantImageButton;
    }

    public String getPlantInformation()
    {
        return plantInformation;
    }

    public ImageIcon getPlantImageButtonHover()
    {
        return plantImageButtonHover;
    }
}
