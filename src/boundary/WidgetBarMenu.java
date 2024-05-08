package boundary;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class WidgetBarMenu extends AnchorPane {
    
    public WidgetBarMenu() {
        // Create buttons
        Button waterButton = createButton("Water");
        Button soundButton = createButton("Sound");
        Button closeButton = createButton("Close");
        Button minimizeButton = createButton("Minimize");

        // Create HBox and add buttons
        HBox buttonBox = new HBox(waterButton, soundButton, closeButton, minimizeButton);
        buttonBox.setPrefSize(210, 26);
        AnchorPane.setTopAnchor(buttonBox, 50.0);

        // Create ProgressIndicator
        ProgressBar progressIndicator = new ProgressBar();
        
        progressIndicator.setPrefSize(38, 53);
        progressIndicator.setProgress(2);
       // AnchorPane.setTopAnchor(progressIndicator, 5.7);
       // AnchorPane.setLeftAnchor(progressIndicator, 80.0);

        // Add components to the AnchorPane
        getChildren().addAll(buttonBox, progressIndicator);
        setPrefSize(221, 90);
        
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setCursor(javafx.scene.Cursor.E_RESIZE);
        return button;
    }
}
