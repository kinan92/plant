package boundary;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class MargePlantAndPotWidget extends JFrame {
	
	    private  WidgetCreator wCreator;
	    private	WidgetShapeButton wSB;

	    private int mouseX;
	    private int mouseY;
	    public MargePlantAndPotWidget(String plantImagePath, String potImagePath, JButton addWaterbutton) {
	        // Set up JFrame
	        setUndecorated(true);
	       	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       this.wCreator= new WidgetCreator(plantImagePath, potImagePath);
	       	wSB=new WidgetShapeButton(addWaterbutton);
	       		

	      
	       	JPanel panelButton = new JPanel(null);
	       	wCreator.setBounds(0, 0, wCreator.getHeight(), wCreator.getWidth());
	       	
	       	System.out.println("WidgetCreator "+wCreator);
	       	
	       	panelButton.add(addWaterbutton);
	       	
	        Area area1 = new Area(wCreator.getTheMergedImage());
	        
	        int width=   (int) (area1.getBounds().getWidth()/2);
	        int height=   (int) (area1.getBounds().getHeight()+1);
	     //   area1.transform(AffineTransform.getTranslateInstance(0, 0));
	        
	        System.out.println("area1 Bound "+area1.getBounds());
	        
	        addWaterbutton.setBounds(width, height, addWaterbutton.getPreferredSize().width, addWaterbutton.getPreferredSize().height);
	        Area area2 = new Area(wSB.getButtonShape());
	        
	        System.out.println("area2 Bound "+area2.getBounds());
	        
	     //   panelButton.setBounds(area2.getBounds());	  
	        area2.transform(AffineTransform.getTranslateInstance(width, height));
	        // Merge the areas
	        System.out.println("area2.getBounds() "+area2.getBounds());
	       
	        Area area = new Area();
	        area.add(area1);
	        area.add(area2);
	        
	        System.out.println("area  "+area.getBounds());
	        
	   
	 

	        // Add the WidgetCreator to the content pane at the center position
	        getContentPane().add(wCreator);

	        // Add the panel with the button to the content pane at the bottom position
	        getContentPane().add(panelButton);
	        
	        
	        // Set the preferred size, pack, and other frame settings
	        setPreferredSize(new Dimension(500, 500));

	        pack();
	        setLocationRelativeTo(null);
	        setVisible(true);
	        frameMover();
	        setShape(area);
	       
	    
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
	    



	    
}
