package entity;

import javax.swing.*;

/**
 * Stores the plant data regarding generic information
 * (generic = same for all plants of the type rather than specific plant)
 * @author Elvira Grubb
 * @author Petri Närhi
 * */

public class PlantType {
    private String plantTypeName;
    private String plantTypeNameAlternative;
    private ImageIcon grownPlantImage;
    private ImageIcon littlePlantImage;
    private ImageIcon deadPlantImage;
    private ImageIcon plantImageButton;
    private ImageIcon plantImageButtonHover;
    private String plantInformation;

    /**
     * Constructor for PlantType
     * @param plantTypeName String, species scientific name
     * @param plantTypeNameAlternative String, casual name
     * @param grownPlantImage String, image path for the grown plant
     * @param littlePlantImage String, image path for the little plant
     * @param deadPlantImage String, image path for the dead plant
     * @param plantImageButton String, image path for corresponding button
     * @param plantImageButtonHover String, image path for hover button when mouse hovering
     * @param plantInformation String, facts about the species
     * @author Elvira Grubb
     * @author Petri Närhi
     * */
    public PlantType(String plantTypeName, String plantTypeNameAlternative, String grownPlantImage, String plantImageButton, String plantImageButtonHover, String plantInformation, String littlePlantImage, String deadPlantImage)
    {
        this.plantTypeName = plantTypeName;
        this.plantTypeNameAlternative = plantTypeNameAlternative;
        this.grownPlantImage = new ImageIcon(grownPlantImage);
        this.littlePlantImage = new ImageIcon(littlePlantImage);
        this.deadPlantImage = new ImageIcon(deadPlantImage);
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

    /**
     * Getter for the grown plant image
     * @return grownPlantImage ImageIcon
     * @author Petri Närhi
     * */
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

    /**
     * Getter for the little plant image
     * @return littlePlantImage ImageIcon
     * @author Petri Närhi
     * */
    public ImageIcon getLittlePlantImage() {
        return littlePlantImage;
    }

    /**
     * Getter for the dead plant image
     * @return deadPlantImage ImageIcon
     * @author Petri Närhi
     * */
    public ImageIcon getDeadPlantImage() {
        return deadPlantImage;
    }

    /**
     * To String method for test purposes
     * @return String toString
     * @author Petri Närhi
     * */
    public String toString() {
        return ("Type: " + plantTypeName + ", aka " + plantTypeNameAlternative + ". " + plantInformation);
    }
}
