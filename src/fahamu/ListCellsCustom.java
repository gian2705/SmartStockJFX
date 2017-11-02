package fahamu;

import javafx.scene.control.ListCell;

public class ListCellsCustom extends ListCell<String> {


    ListCellsCustom() {
    }

    @Override
    protected void updateItem(String string, boolean empty) {
        super.updateItem(string, empty);

        if (empty || string == null){
            setText(null);
            setGraphic(null);

        }else setText(string);
    }

}
