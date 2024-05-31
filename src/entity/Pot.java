package entity;

import javax.swing.*;
import java.io.Serializable;

public class Pot implements Serializable
{
    private ImageIcon potImage;
    private ImageIcon potButton;
    private ImageIcon potButtonHoverImage;

    /**
     * This constructor sets the various images that a pot uses
     * @param potImage The path to the default pot image
     * @param potButton The path to the image used for pot buttons
     * @param potButtonHoverImage The path to the hover effect for a pot button
     * @author Elvira Grubb
     */
    public Pot(String potImage, String potButton, String potButtonHoverImage)
    {
        this.potImage = new ImageIcon(potImage);
        this.potButton = new ImageIcon(potButton);
        this.potButtonHoverImage = new ImageIcon(potButtonHoverImage);
    }

    public ImageIcon getPotImage()
    {
        return potImage;
    }

    public ImageIcon getPotButton()
    {
        return potButton;
    }

    public ImageIcon getPotButtonHoverImage()
    {
        return potButtonHoverImage;
    }
}