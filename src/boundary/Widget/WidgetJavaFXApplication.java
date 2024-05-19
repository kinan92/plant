package boundary.Widget;

import javax.swing.ImageIcon;

import boundary.PlantView.PlantView;
import controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * this Class responsible for starting a JAVAFX Application it contain the main
 * functionality to create a Widget
 * @author kinan
 */
public class WidgetJavaFXApplication extends Application {

	private double xOffset = 0;
	private double yOffset = 0;
	private Controller controller;
	private PlantView plantView;

	private Stage barMenuStage;
	private Stage widgetCreatorStage;
	private WidgetCreatorJFX widgetCreator;
	private WidgetBarMenu widgetBarMenu;
	private int clickCount = 0;
	public WidgetJavaFXApplication(Controller controller, Stage primaryStage, PlantView plantView) {
		this.controller = controller;
		this.widgetCreatorStage = primaryStage;
		this.plantView = plantView;
	}

	@Override
	public void start(Stage primaryStage) {
		this.widgetCreatorStage = primaryStage;
		
		if (widgetBarMenu==null) {
			
			widgetBarMenu = new WidgetBarMenu(plantView);
			barMenuStage = new Stage();
		} 
		
		Scene BarMenuScene = new Scene(widgetBarMenu, Color.TRANSPARENT);

		barMenuStage.setScene(BarMenuScene);
		barMenuStage.setTitle("BAR Menu ");
		barMenuStage.initStyle(StageStyle.TRANSPARENT);

		this.widgetCreator = new WidgetCreatorJFX(controller);
		Scene wScene = new Scene(widgetCreator, Color.TRANSPARENT);
		showMenuBar(wScene);
		widgetCreatorStage = new Stage();
		widgetCreatorStage.setScene(wScene);
		widgetCreatorStage.initStyle(StageStyle.TRANSPARENT);

		frameMoverWidget(widgetCreatorStage, widgetCreator);
		frameMoverProgressBar();		

		widgetCreatorStage.show();
		barMenuStage.show();
		
	}

	/**
	 * This method will update send image Plant to widgetCreator and update the plant image.
	 * @param newPlantImage
	 * @param newPotImage
	 * @author kinan
	 */
    public void updateWidgetImages(ImageIcon newPlantImage) {
        widgetCreator.updatePlantImage(newPlantImage);
    }
    
    /**
     *  this method add a lesener for the mouse if click on the widgetCreatorStage and show the menu bar 
     * @param Scene
     * @return Scene
     * @author kinan
     */
    private Scene showMenuBar(Scene Scene) {
    	Scene wScene=Scene;
    	
    	wScene.setOnMouseClicked(event-> {
    		clickCount++;
    		if (clickCount==2) {
    			if (barMenuStage != null) {
    				barMenuStage.show();
    				clickCount=0;
    			}
			}
    	});
    	
    	return wScene;
    }
    
	/**
	 * This method responsible for creating a mover for the window
	 * 
	 * @author kinan
	 * @param stage
	 * @param root
	 */
	private void frameMoverWidget(Stage stage, Pane root) {
		root.setOnMousePressed(event -> {
			xOffset = event.getSceneX();
			yOffset = event.getSceneY();
			event.consume();
		});
		root.setOnMouseDragged(event -> {
			stage.setX(event.getScreenX() - xOffset);
			stage.setY(event.getScreenY() - yOffset);
			event.consume();
		
		});
	}
	/**
	 * This method responsible for creating a mover for the ProgressBar 
	 * 
	 * @author kinan
	 * @param stage
	 * @param ProgressBar
	 */
	public void frameMoverProgressBar() {
		System.out.println("you are here ProgressBar");
		widgetBarMenu.getProgressBar().setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
            event.consume(); 
        });
		widgetBarMenu.getProgressBar().setOnMouseDragged(event -> {
            double x = event.getScreenX() - xOffset;
            double y = event.getScreenY() - yOffset;
            barMenuStage.setX(x);
            barMenuStage.setY(y);
            event.consume(); 
        });
	}


	public Stage getBarMenuStage() {
		return barMenuStage;
	}

}
