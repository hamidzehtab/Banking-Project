package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Loan {
    public Loan(Stage stage, String origin) {
        stage.setTitle("Loan");
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(120, 120, 120, 120));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        gridPane.setAlignment(Pos.CENTER);

        gridPane.setPadding(new Insets(40, 40, 40, 40));


        //add origin account label
        Label originAccountLabel = new Label("Origin Account");
        GridPane.setHalignment(originAccountLabel, HPos.LEFT);
        gridPane.add(originAccountLabel, 0,3);

        // add origin Account code Text Field
        TextField originAccountField = new TextField();
        originAccountField.setPrefHeight(40);
        gridPane.add(originAccountField, 1,3);

        originAccountField.setText(origin);
        originAccountField.setDisable(true);


        Label label_types = new Label("type of Loan");

        String types[] = { "30s", "60s" , "120s","240s"};

        ComboBox combo_box =
                new ComboBox(FXCollections.observableArrayList(types));
        combo_box.setPrefHeight(40);
        gridPane.add(combo_box, 1, 5, 2, 1);
        label_types.setFont(Font.font("Comic Sans MS", 12));
        GridPane.setHalignment(label_types, HPos.LEFT);
        gridPane.add(label_types, 0,5,2,1);


        //add amount label
        Label amountLabel = new Label("Amount");
        GridPane.setHalignment(amountLabel, HPos.LEFT);
        gridPane.add(amountLabel, 0,7);

        // add amount code Text Field
        TextField amountField = new TextField();
        amountField.setPrefHeight(40);
        gridPane.add(amountField, 1,7);

        // Add Password Label
        Label passwordLabel = new Label("Password");
        GridPane.setHalignment(passwordLabel, HPos.LEFT);
        gridPane.add(passwordLabel, 0, 9);

        // Add Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        gridPane.add(passwordField, 1, 9);

        // Add Back Button
        Button backButton = new Button("Back");
        backButton.setPrefHeight(40);
        backButton.setDefaultButton(true);
        backButton.setPrefWidth(100);
        gridPane.add(backButton, 1, 11, 2, 1);
        GridPane.setHalignment(backButton, HPos.RIGHT);
        GridPane.setMargin(backButton, new Insets(20, 0,20,0));

        // Add Submit Button
        Button submitButton = new Button("Loan");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 11, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0,20,0));

        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ClientArea h = new ClientArea(Main.primaryStage);

            }
        });
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(combo_box.getValue().equals("")) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter type of loan");
                    return;
                }
                if(amountField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a amount");
                    return;
                }
                if(passwordField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a password");
                    return;
                }



                String result = MyClass.Server(1,9,MyClass.NationalCode,"Null"
                        ,origin+"www"+passwordField.getText()+"www"+combo_box.getValue()+"www"+amountField.getText());

                if(result.equals("1")){
                    showAlert(Alert.AlertType.INFORMATION , gridPane.getScene().getWindow(), "success", "you get loan successfully." );
                    ClientArea c = new ClientArea(Main.primaryStage);
                }else if(result.equals("0")){
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "incorrect password!");
                }else if(result.equals("-3")){
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "you don't have enough money!");
                }else if(result.equals("-2")){
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "destination doesn't exist!");
                }else{
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "something went wrong!");
                }

            }
        });
    }
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}