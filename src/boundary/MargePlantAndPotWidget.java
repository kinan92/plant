package boundary;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;



public class MargePlantAndPotWidget extends JFrame {
	  	private BufferedImage plantImage;
	    private BufferedImage potImage;
	    private BufferedImage combinedImage;
	    private int mouseX;
	    private int mouseY;
	    public MargePlantAndPotWidget(String plantImagePath, String potImagePath) {
	        try {
	            // Load plant and pot images
	            plantImage = ImageIO.read(new File(plantImagePath));
	            potImage = ImageIO.read(new File(potImagePath));
	            combinedImage = new BufferedImage(500,500, BufferedImage.TYPE_INT_ARGB);
	            Graphics2D g2d = combinedImage.createGraphics();
	            paint(g2d);
	            g2d.dispose();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        // Set up JFrame
	        setUndecorated(true);
	       	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setPreferredSize(new Dimension(500, 500));
	        pack();
	        setLocationRelativeTo(null);
	        setVisible(true);
	        setShape(getImageShape(combinedImage));
	        frameMover();
	       
	    
	    }
	    
	    
	    private void frameMover() {
	    	 addMouseListener(new MouseAdapter() {
		            @Override
		            public void mousePressed(MouseEvent e) {
		            	mouseX = e.getX();
		            	mouseY = e.getY();
		            }
		        });

		        // Add mouse motion listener for mouse dragged event
		        addMouseMotionListener(new MouseMotionAdapter() {
		            @Override
		            public void mouseDragged(MouseEvent e) {
		                // Calculate the new location of the frame based on the mouse movement
		                int deltaX = e.getXOnScreen() - mouseX;
		                int deltaY = e.getYOnScreen() - mouseY;
		                setLocation(deltaX, deltaY);
		            }
		        });

		        setVisible(true);
	    }
	    
	    // Method to create a shape based on the image's 
	    private Shape getImageShape(BufferedImage image) {
	        Area area = new Area();

	        // Create a shape from the image's alpha channel
	        for (int y = 0; y < image.getHeight(); y++) {
	            for (int x = 0; x < image.getWidth(); x++) {
	                int alpha = (image.getRGB(x, y) >> 24) & 0xFF;
	                if (alpha != 0) {
	                    area.add(new Area(new Rectangle(x, y, 1, 1)));
	                }
	            }
	        }

	        return area;
	    }
	    //this method is to marge the plant and the pot
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
