package entity;

public class PlantType {
    private String plantTypeName;
    private String plantTypeNameAlternative;
    private String grownPlantImage;
    private String plantImageButton;
    private String plantImageButtonHover;
    private String plantInformation;

    public PlantType(String plantTypeName, String plantTypeNameAlternative, String defaultPlantImage, String plantImageButton, String plantImageButtonHover, String plantInformation)
    {
        this.plantTypeName = plantTypeName;
        this.plantTypeNameAlternative = plantTypeNameAlternative;
        this.grownPlantImage = defaultPlantImage;
        this.plantImageButton = plantImageButton;
        this.plantImageButtonHover = plantImageButtonHover;
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

    public String getGrownPlantImage()
    {
        return grownPlantImage;
    }

    public String getPlantImageButton()
    {
        return plantImageButton;
    }

    public String getPlantInformation()
    {
        return plantInformation;
    }

    public String getPlantImageButtonHover()
    {
        return plantImageButtonHover;
    }
}
