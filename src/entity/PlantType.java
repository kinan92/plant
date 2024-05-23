package entity;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Stores the plant data regarding generic information
 * (generic = same for all plants of the type rather than specific plant)
 * @author Elvira Grubb
 * @author Petri Närhi
 * */

public class PlantType {
    private String plantTypeName;
    private String plantTypeNameAlternative;
    private ImageIcon smallPlantImage;
    private ImageIcon mediumPlantImage;
    private ImageIcon largePlantImage;
    private ImageIcon smallDeadPlantImage;
    private ImageIcon mediumDeadPlantImage;
    private ImageIcon largeDeadPlantImage;
    private ImageIcon plantImageButton;
    private ImageIcon plantImageButtonHover;
    private String plantInformation;

    /**
     * Constructor for PlantType
     * @param plantTypeName String, species scientific name
     * @param plantTypeNameAlternative String, casual name
     * @param plantImageButton String, image path for corresponding button
     * @param plantImageButtonHover String, image path for hover button when mouse hovering
     * @param plantInformation String, facts about the species
     * @param plantStage1Image Image path for plant stage 1
     * @param plantStage2Image Image path for plant stage 2
     * @param plantStage3Image Image path for plant stage 3
     * @param plantStage1DeadImage Image path for dead plant stage 1
     * @param plantStage2DeadImage Image path for dead plant stage 2
     * @param plantStage3DeadImage Image path for dead plant stage 3
     * @author Elvira Grubb
     * @author Petri Närhi
     */
    public PlantType(String plantTypeName, String plantTypeNameAlternative, String plantImageButton, String plantImageButtonHover, String plantInformation, String plantStage1Image, String plantStage2Image, String plantStage3Image, String plantStage1DeadImage, String plantStage2DeadImage, String plantStage3DeadImage)
    {
        this.plantTypeName = plantTypeName;
        this.plantTypeNameAlternative = plantTypeNameAlternative;
        this.plantImageButton = new ImageIcon(plantImageButton);
        this.plantImageButtonHover = new ImageIcon(plantImageButtonHover);
        this.plantInformation = plantInformation;
        this.smallPlantImage = new ImageIcon(plantStage1Image);
        this.mediumPlantImage = new ImageIcon(plantStage2Image);
        this.largePlantImage = new ImageIcon(plantStage3Image);
        this.smallDeadPlantImage = new ImageIcon(plantStage1DeadImage);
        this.mediumDeadPlantImage = new ImageIcon(plantStage2DeadImage);
        this.largeDeadPlantImage = new ImageIcon(plantStage3DeadImage);
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
        return largePlantImage;
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
        return smallPlantImage;
    }

    /**
     * Getter for the dead plant image
     * @return deadPlantImage ImageIcon
     * @author Petri Närhi
     * */
    public ImageIcon getDeadPlantImage() {
        return largeDeadPlantImage;
    }

    /**
     * To String method for test purposes
     * @return String toString
     * @author Petri Närhi
     * */
    public String toString() {
        return ("Type: " + plantTypeName + ", aka " + plantTypeNameAlternative + ". " + plantInformation);
    }

    /**
     * Method that creates an array out of certain plant information
     * @return An array with plant information
     * @author Elvira Grubb
     */
    public ArrayList<String> getPlantInfoArray()
    {
        ArrayList<String> plantInfoArray = new ArrayList<>();
        plantInfoArray.add(plantTypeName);
        plantInfoArray.add(plantTypeNameAlternative);
        plantInfoArray.add(plantInformation);
        return plantInfoArray;
    }
}