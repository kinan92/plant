package boundary;

import controller.Controller;
import javafx.application.Application;
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
	private Stage stage;
	private Stage barMenuStage;
	private WidgetCreatorJFX widgetCreator;
	private WidgetBarMenu widgetBarMenu;

	public WidgetJavaFXApplication(Controller controller, Stage stage, PlantView plantView) {
		this.controller = controller;
		this.stage = stage;
		this.plantView = plantView;
	}

	@Override
	public void start(Stage primaryStage) {

		widgetBarMenu = new WidgetBarMenu(plantView);
		Scene BarMenuScene = new Scene(widgetBarMenu, Color.TRANSPARENT);

		barMenuStage = new Stage();
		barMenuStage.setScene(BarMenuScene);
		barMenuStage.setTitle("BAR Menu ");
		barMenuStage.initStyle(StageStyle.TRANSPARENT);

		this.widgetCreator = new WidgetCreatorJFX(controller);
		Scene wScene = new Scene(widgetCreator, Color.TRANSPARENT);

		Stage widgetCreatorStage = new Stage();
		widgetCreatorStage.setScene(wScene);
		widgetCreatorStage.initStyle(StageStyle.TRANSPARENT);

		frameMoverWidget(widgetCreatorStage, widgetCreator);
		frameMoverProgressBar();		

		barMenuStage.show();

		widgetCreatorStage.show();
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
