package fahamu;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Popup;

public class ExpenditureCategoryUI {

    static Popup popup;
    static GridPane newExpenditureUI;

    static ObservableList<ExpensesList> expensesListObservableList;
    private static ListView<String> categoryListView;

    ExpenditureCategoryUI() {
        addExpenditureCategory();
        setNewExpenditureUI();
    }

    //new expenditure UI
    private static void setNewExpenditureUI() {

        //input control
        Label chooseCategoryLabel = new Label("Choose Category :");
        Label chooseDateLabel = new Label("Choose Date :");
        Label descriptionLabel = new Label("Description :");
        Label amountLabel = new Label("Amount");
        Label allExpenditureCategoryLabel = new Label("EXPENDITURE CATEGORIES");

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        submitButton.setDefaultButton(true);

        ComboBox<String> categoryChooser = new ComboBox<>();
        categoryChooser.setItems(ExpenditureCategoryData.getCategoryList());
        categoryChooser.setMinWidth(195);

        DatePicker datePicker = new DatePicker();
        TextArea descriptionTextArea = new TextArea();

        TextField amountTextField = new TextField();
        amountTextField.setMinWidth(195);

        categoryListView = new ListView<>();
        categoryListView.setItems(ExpenditureCategoryData.getCategoryList());
        categoryListView.setStyle("-fx-base: #f3622d");

        //table view to show records of all expenses
        TableView<ExpensesList> expensesListTableView = new TableView<>();
        expensesListObservableList = FXCollections.observableArrayList();
        expensesListTableView.setItems(expensesListObservableList);
        ExpenditureCategoryData.getExpensesDetails();
        expensesListTableView.setStyle("-fx-base: #f3622d");

        TableColumn<ExpensesList, String> dateColumn = new TableColumn<>("Date");
        TableColumn<ExpensesList, String> nameColumn = new TableColumn<>("Expenditure");
        TableColumn<ExpensesList, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<ExpensesList, String> userColumn = new TableColumn<>("User Register");
        TableColumn<ExpensesList, String> amountColumn = new TableColumn<>("Amount(TZS)");

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        expensesListTableView.getColumns().addAll(
                dateColumn,
                nameColumn,
                descriptionColumn,
                userColumn,
                amountColumn
        );

        chooseCategoryLabel.setFont(new Font(14));
        chooseDateLabel.setFont(new Font(14));
        descriptionLabel.setFont(new Font(14));
        amountLabel.setFont(new Font(14));
        allExpenditureCategoryLabel.setFont(new Font(20));
        chooseCategoryLabel.setMinWidth(150);
        chooseDateLabel.setMinWidth(150);
        descriptionLabel.setMinWidth(150);
        amountLabel.setMinWidth(150);

        descriptionTextArea.setWrapText(true);

        //put input control in container
        HBox chooseCategoryHBox = new HBox();
        HBox chooseDateHBox = new HBox();
        HBox descriptionHBox = new HBox();
        HBox amountHBox = new HBox();
        HBox submitExpenseHBox = new HBox();

        chooseCategoryHBox.setSpacing(50);
        chooseDateHBox.setSpacing(50);
        descriptionHBox.setSpacing(50);
        amountHBox.setSpacing(50);
        submitExpenseHBox.setSpacing(50);
        chooseCategoryHBox.getChildren().addAll(chooseCategoryLabel, categoryChooser);
        chooseDateHBox.getChildren().addAll(chooseDateLabel, datePicker);
        descriptionHBox.getChildren().addAll(descriptionLabel, descriptionTextArea);
        amountHBox.getChildren().addAll(amountLabel, amountTextField);
        submitExpenseHBox.getChildren().addAll(submitButton, cancelButton);

        VBox vBox = new VBox();
        vBox.setSpacing(4);
        vBox.setPadding(new Insets(0, 4, 0, 0));
        vBox.getChildren().addAll(
                chooseCategoryHBox,
                chooseDateHBox,
                descriptionHBox,
                amountHBox,
                submitExpenseHBox,
                allExpenditureCategoryLabel,
                categoryListView
        );

        newExpenditureUI = new GridPane();
        newExpenditureUI.setPadding(new Insets(10, 10, 10, 10));
        newExpenditureUI.setId("new_expenditure");
        RowConstraints row0 = new RowConstraints();
        ColumnConstraints col0 = new ColumnConstraints(400);
        ColumnConstraints col1 = new ColumnConstraints();
        newExpenditureUI.getRowConstraints().add(row0);
        newExpenditureUI.getColumnConstraints().addAll(col0, col1);
        row0.setVgrow(Priority.ALWAYS);
        col1.setHgrow(Priority.ALWAYS);
        newExpenditureUI.add(vBox, 0, 0);
        newExpenditureUI.add(expensesListTableView, 1, 0);

        //implements submitt buttons
        submitButton.setOnAction(event -> {
            if (categoryChooser.getSelectionModel().isEmpty()) {
                categoryChooser.setItems(ExpenditureCategoryData.getCategoryList());
                categoryChooser.requestFocus();
                categoryChooser.show();
            } else if (datePicker.getEditor().getText().isEmpty()) {
                datePicker.requestFocus();
                datePicker.show();
            } else if (descriptionTextArea.getText().isEmpty()) descriptionTextArea.requestFocus();
            else if (amountTextField.getText().isEmpty()) amountTextField.requestFocus();
            else {
                try {
                    int amount = Integer.parseInt(amountTextField.getText());
                } catch (Throwable t) {
                    amountTextField.requestFocus();
                    throw new IllegalArgumentException("Illegal input");
                }

                //insert data into expenses table
                String date = datePicker.getValue().getYear()
                        + "-" + datePicker.getValue().getMonthValue()
                        + "-" + datePicker.getValue().getDayOfMonth();
                String name = categoryChooser.getSelectionModel().getSelectedItem();
                String descr = descriptionTextArea.getText();
                String user = "admin";
                float amount = Float.parseFloat(amountTextField.getText());
                ExpenditureCategoryData.insertExpenditureData(
                        date,
                        name,
                        descr,
                        user,
                        amount
                );

                //update the table
                expensesListTableView.getItems().clear();
                ExpenditureCategoryData.getExpensesDetails();

                //clear the contents
                categoryChooser.getSelectionModel().clearSelection();
                datePicker.getEditor().clear();
                descriptionTextArea.clear();
                amountTextField.clear();
            }
        });

        cancelButton.setOnAction(event -> {
            //clear the contents
            categoryChooser.getSelectionModel().clearSelection();
            datePicker.getEditor().clear();
            descriptionTextArea.clear();
            amountTextField.clear();
        });

        categoryChooser.setOnMouseClicked(event -> {
            categoryChooser.getItems().clear();
            categoryChooser.setItems(ExpenditureCategoryData.getCategoryList());
        });
    }

    //add expenditure category pop up
    private static void addExpenditureCategory() {
        //popup to set a new supplier
        popup = new Popup();
        //control fields
        Label expenditureNameLabel = new Label("EXPENDITURE NAME");
        expenditureNameLabel.setFont(new Font(14));

        TextField expenditureNameTextField = new TextField();


        Button addExpenditureButton = new Button("Add Expenditure");
        Button cancelButton = new Button("Cancel");

        addExpenditureButton.setStyle("-fx-base: #f3622d");
        cancelButton.setStyle("-fx-base: #f3622d");

        HBox hBox = new HBox();
        hBox.setSpacing(110);
        hBox.getChildren().addAll(addExpenditureButton, cancelButton);


        //container of pop up
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(8, 8, 8, 8));
        //gridPane.setPrefSize(300, 330);
        gridPane.setStyle("-fx-background-color: white");
        gridPane.setVgap(4);
        ColumnConstraints column0 = new ColumnConstraints(300);
        RowConstraints rw0 = new RowConstraints(30);
        RowConstraints rw1 = new RowConstraints(30);
        RowConstraints rw2 = new RowConstraints(30);

        gridPane.getColumnConstraints().addAll(column0);
        gridPane.getRowConstraints().addAll(rw0, rw1, rw2);
        gridPane.add(expenditureNameLabel, 0, 0);
        gridPane.add(expenditureNameTextField, 0, 1);
        gridPane.add(hBox, 0, 2);

        //make a black ribbon around the add supplier popup
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(4, 4, 4, 4));
        vBox.setStyle("-fx-background-color: #000000");
        vBox.getChildren().add(gridPane);

        //popup.setAutoHide(true);
        popup.centerOnScreen();
        popup.setAutoHide(true);
        popup.setAutoFix(true);
        popup.getContent().add(vBox);

        cancelButton.setOnAction(event -> {
            popup.hide();
            expenditureNameTextField.clear();
        });
        addExpenditureButton.setOnAction(event -> {
            if (expenditureNameTextField.getText().isEmpty()) expenditureNameTextField.requestFocus();
            else {
                String expenditure = expenditureNameTextField.getText().trim();
                //hide the popup
                popup.hide();
                //insert data into table and create a new supplier table
                ExpenditureCategoryData.insertNewCategory(expenditure);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Successful");
                alert.setTitle("Category Added");
                alert.show();
                expenditureNameTextField.clear();
                categoryListView.getItems().clear();
                categoryListView.setItems(ExpenditureCategoryData.getCategoryList());
            }
        });


    }

    //expenses data model
    public static class ExpensesList {
        private final SimpleStringProperty date;
        private final SimpleStringProperty name;
        private final SimpleStringProperty description;
        private final SimpleStringProperty user;
        private final SimpleFloatProperty amount;

        ExpensesList(
                String date,
                String name,
                String description,
                String user,
                float amount) {
            this.date = new SimpleStringProperty(date);
            this.name = new SimpleStringProperty(name);
            this.description = new SimpleStringProperty(description);
            this.user = new SimpleStringProperty(user);
            this.amount = new SimpleFloatProperty(amount);
        }

        public String getDate() {
            return date.get();
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getUser() {
            return user.get();
        }

        public void setUser(String user) {
            this.user.set(user);
        }

        public String getDescription() {
            return description.get();
        }

        public void setDescription(String description) {
            this.description.set(description);
        }

        public float getAmount() {
            return amount.get();
        }

        public void setAmount(float amount) {
            this.amount.set(amount);
        }
    }
}
