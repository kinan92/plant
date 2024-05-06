package boundary;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.*;


public class WidgetFX extends JFrame {

    private int mouseX;
    private int mouseY;
    private WidgetCreatorJFX  wCJFX ;
    
    public WidgetFX(ImageIcon plantImageIcon , ImageIcon potImageIcon, JButton addWaterbutton) {
        // Set up JFrame
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize JavaFX content within the JFXPanel
        JFXPanel fxPanel = new JFXPanel();
        	
        add(fxPanel, BorderLayout.CENTER);
        wCJFX=new WidgetCreatorJFX(plantImageIcon, potImageIcon) ;

        // Perform JavaFX operations on the JavaFX application thread
        Platform.runLater(() -> {
            // Create JavaFX components
            StackPane root = new StackPane();
            
            Scene scene = new Scene(wCJFX, 500, 500);
           
            Button javafxButton = new Button("JavaFX Button");
            frameMover();
            
            
           // root.getChildren().add(wCJFX);
            root.getChildren().add(javafxButton);
            // Set up mouse dragging behavior
          

            // Set the Scene to the JFXPanel
            fxPanel.setScene(scene);
        });

        // Set the preferred size, pack, and other frame settings
        setPreferredSize(new Dimension(500, 500));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setShape(wCJFX.getImageShape());
    }
    
    private void frameMover() {
    	  wCJFX.setOnMousePressed(e -> {
              mouseX = (int) e.getScreenX();
              mouseY = (int) e.getScreenY();
          });

          wCJFX.setOnMouseDragged(e -> {
              int deltaX = (int) (e.getScreenX() - mouseX);
              int deltaY = (int) (e.getScreenY() - mouseY);
              setLocation(getX() + deltaX, getY() + deltaY);
              mouseX = (int) e.getScreenX();
              mouseY = (int) e.getScreenY();
          });
    }
    
    
}
