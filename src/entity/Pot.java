package entity;

import javax.swing.*;

public class Pot {
    private ImageIcon potImage;
    private ImageIcon potButton;
    private ImageIcon potButtonHoverImage;

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