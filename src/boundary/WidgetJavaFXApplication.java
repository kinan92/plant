package boundary;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class WidgetJavaFXApplication extends Application {
	private  ImageIcon currentPlant;
    private  ImageIcon currentPot;
    private  JButton waterPlantButton;
    private WidgetFX widgetFX;
    
public void WidgetJavaFXApplication(ImageIcon plantImageIcon , ImageIcon potImageIcon, JButton addWaterbutton) {
	this.currentPlant= plantImageIcon;
	this.currentPot=potImageIcon;
	this.waterPlantButton=addWaterbutton;
	this.widgetFX=new WidgetFX( currentPlant,currentPot,waterPlantButton);
}

	
    @Override
    public void start(Stage primaryStage)throws Exception  {
        // Create a StackPane as the root node
        StackPane root = new StackPane();
        
        // Create a Scene with the root node
        Scene scene = new Scene(root, 400, 300);
        
        // Set the scene to the primary stage
        primaryStage.setScene(scene);
        
        // Set the title of the primary stage
        primaryStage.setTitle("JavaFX Application");
        
        // Show the primary stage
        primaryStage.show();
    }

	
}