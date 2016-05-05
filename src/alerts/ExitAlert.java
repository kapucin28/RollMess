package alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

/**
 * Created by TIMBULI REMUS K@puc!n on 05-May-16.
 */
public class ExitAlert {

    private Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit Program", ButtonType.OK, ButtonType.CANCEL);

    public ExitAlert() {
        exitAlert(alert);
    }

    private void exitAlert(Alert alert) {
        this.alert = alert;
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            System.exit(0);
        } else {
            alert.close();
        }
    }
}
