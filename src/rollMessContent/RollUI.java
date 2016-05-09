package rollMessContent;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

/**
 * Created by TIMBULI REMUS K@puc!n on 07-May-16.
 */
public class RollUI extends Pane {

    // Menu variables---------------------------------------------------------------------------------------------------
    private final MenuBar menuBar = new MenuBar();
    private final Menu fileMenu = new Menu("_File");
    private final Menu editMenu = new Menu("_Edit");
    private final MenuItem save = new MenuItem("Save As...");
    private final MenuItem load = new MenuItem("Load...");
    private final MenuItem exit = new MenuItem("Exit");
    private final MenuItem clear = new MenuItem("Clear Text");
    private final SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
    //------------------------------------------------------------------------------------------------------------------
    private final GridPane root = new GridPane();
    // Root pane variables----------------------------------------------------------------------------------------------
    private Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
    //------------------------------------------------------------------------------------------------------------------

    // Constructor------------------------------------------------------------------------------------------------------
    public RollUI() {
        layoutSetup();
    }
    //------------------------------------------------------------------------------------------------------------------

    // RollUI layout method---------------------------------------------------------------------------------------------
    private void layoutSetup() {
        getChildren().add(root);
    }
    //------------------------------------------------------------------------------------------------------------------
}
