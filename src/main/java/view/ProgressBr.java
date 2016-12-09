package view;

import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static javafx.application.Application.launch;

/**
 * Created by vladniculescu on 07/12/2016.
 */
public class ProgressBr extends ProgressIndicator {

    private final Stage dialogStage;
    //  private final ProgressBar pb = new ProgressBar();
    private final ProgressIndicator pin = new ProgressIndicator();

    public ProgressBr() {
        dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setResizable(false);
        dialogStage.initModality(Modality.WINDOW_MODAL);




        // PROGRESS BAR
        dialogStage.setMinHeight(150);
        dialogStage.setMinWidth(150);
        //   pb.setProgress(-1F);
        pin.setProgress(-1F);
        pin.setStyle(" -fx-progress-color: rgb(49,52,59)");

        final HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setAlignment(Pos.CENTER);
        //      hb.getChildren().addAll(pb, pin);
        hb.getChildren().add(pin);

        Scene scene = new Scene(hb);
        dialogStage.setScene(scene);

    }

    public void activateProgressBar(final Task<?> task) {
        //  pb.progressProperty().bind(task.progressProperty());
        pin.progressProperty().bind(task.progressProperty());
        dialogStage.show();
    }

    public void showProgressBar() {
        dialogStage.show();
    }

    public Stage getDialogStage() {
        return dialogStage;
    }
}





