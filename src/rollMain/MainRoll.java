package rollMain;

import alerts.ExitAlert;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by TIMBULI REMUS K@puc!n on 04-May-16.
 */
public class MainRoll extends Application {

    // Pane & Scene setup-----------------------------------------------------------------------------------------------
    private Pane pane = new Pane();
    private Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
    private Scene scene = new Scene(pane, bounds.getWidth() / 3, bounds.getHeight() / 2);
    //------------------------------------------------------------------------------------------------------------------

    // Main method------------------------------------------------------------------------------------------------------
    public static void main(String[] args){
        Application.launch(args);
    }
    //------------------------------------------------------------------------------------------------------------------

    // Start method-----------------------------------------------------------------------------------------------------
    public void start(Stage stage){

        // CSS stylesheet-----------------------------------------------------------------------------------------------
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        //--------------------------------------------------------------------------------------------------------------

        // Stage setup--------------------------------------------------------------------------------------------------
        stage.setTitle("RollMess");
        stage.setX(bounds.getWidth() / 3);
        stage.setY(bounds.getHeight() / 3);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e ->{
            e.consume();
            new ExitAlert();
        });
        //--------------------------------------------------------------------------------------------------------------
    }
    //------------------------------------------------------------------------------------------------------------------
}
