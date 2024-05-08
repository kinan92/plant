package boundary;

import java.awt.geom.Area;

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
  private WidgetCreatorJFX widgetCreator;
public void WidgetJavaFXApplication(Area theShapeOfThecombinedImage,ImageIcon plantImageIcon , ImageIcon potImageIcon, JButton addWaterbutton) {
	this.currentPlant= plantImageIcon;
	this.currentPot=potImageIcon;
	this.waterPlantButton=addWaterbutton;
	this.widgetFX=new WidgetFX(theShapeOfThecombinedImage, currentPlant,currentPot,waterPlantButton);
	this.widgetCreator= new WidgetCreatorJFX(plantImageIcon, potImageIcon);
}

public static void main(String[] args) {
	launch(args);
}
	
    @Override
    public void start(Stage primaryStage)throws Exception  {
    	  WidgetBarMenu wbm = new WidgetBarMenu();

          // Create a Scene with the root node
          Scene scene = new Scene(wbm);
         
          
          // Set the scene to the primary stage
          primaryStage.setScene(scene);
          // Set the title of the primary stage
          primaryStage.setTitle("JavaFX Application");

          // Show the primary stage
          primaryStage.show();
      //    Stage secondStage =new Stage();
          
          
      //    Scene scene2 = new Scene(widgetCreator);
       //   secondStage.setScene(scene2);
          // Set the title of the primary stage
       //   secondStage.setTitle("JavaFX Application");

          // Show the primary stage
     //     secondStage.show();
      }
      

	
}