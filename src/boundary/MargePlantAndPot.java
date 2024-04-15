package boundary;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class MargePlantAndPot extends JFrame {
	  private BufferedImage plantImage;
	    private BufferedImage potImage;

	    public MargePlantAndPot(String plantImagePath, String potImagePath) {
	        try {
	            // Load plant and pot images
	            plantImage = ImageIO.read(new File(plantImagePath));
	            potImage = ImageIO.read(new File(potImagePath));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        // Set up JFrame
	        setTitle("Plant Pot Visualizer");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setPreferredSize(new Dimension(400, 400));
	        pack();
	        setLocationRelativeTo(null);
	        setVisible(true);
	    }

	    @Override
	    public void paint(Graphics g) {
	        super.paint(g);

	        // Draw pot image
	        int potX = 0; // Adjust position as needed
	        int potY = 0; // Adjust position as needed
	        g.drawImage(potImage, potX, potY, this);

	        // Draw plant image
	        int plantX = potX + (potImage.getWidth() - plantImage.getWidth()) / 2;
	        int plantY = potY + (potImage.getHeight() - plantImage.getHeight()) / 2;
	        g.drawImage(plantImage, plantX, plantY, this);
	    }
}
