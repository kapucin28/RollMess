package alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import rollMessContent.RollUI;

import java.io.IOException;

/**
 * Created by TIMBULI REMUS K@puc!n on 05-May-16.
 */
public class ExitAlert {

    // Getting the socket variable--------------------------------------------------------------------------------------
    private RollUI rollUI = new RollUI();
    //------------------------------------------------------------------------------------------------------------------

    // Alert variable---------------------------------------------------------------------------------------------------
    private Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit Program", ButtonType.OK, ButtonType.CANCEL);
    //------------------------------------------------------------------------------------------------------------------

    // Constructor------------------------------------------------------------------------------------------------------
    public ExitAlert() {
        exitAlert(alert);
    }
    //------------------------------------------------------------------------------------------------------------------

    // Alert method-----------------------------------------------------------------------------------------------------
    private void exitAlert(Alert alert) {
        this.alert = alert;
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                rollUI.getChatSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        } else {
            alert.close();
        }
    }
    //------------------------------------------------------------------------------------------------------------------
}
