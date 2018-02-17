package fahamu.Ui;

import javafx.application.Platform;
import javafx.scene.control.ListCell;

public class ListCellsCustom extends ListCell<String> {


    ListCellsCustom() {
    }

    @Override
    protected void updateItem(String string, boolean empty) {
        Platform.runLater(() -> {
            super.updateItem(string, empty);

            if (empty || string == null) {
                setText(null);
                setGraphic(null);

            } else setText(string);
        });

    }

}
