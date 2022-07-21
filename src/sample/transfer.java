package sample;

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
import javafx.stage.Stage;
import javafx.stage.Window;

public class transfer {
    public transfer(Stage stage,String origin) {
        stage.setTitle("transfer");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(120, 120, 120, 120));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
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
        //add destination label
        Label destinationLabel = new Label("Destination");
        GridPane.setHalignment(destinationLabel, HPos.LEFT);
        gridPane.add(destinationLabel, 0,5);

        // add destination code Text Field
        TextField destinationField = new TextField();
        destinationField.setPrefHeight(40);
        gridPane.add(destinationField, 1,5);

        //add amount label
        Label amountLabel = new Label("Amount");
        GridPane.setHalignment(amountLabel, HPos.LEFT);
        gridPane.add(amountLabel, 0,8);

        // add amount code Text Field
        TextField amountField = new TextField();
        amountField.setPrefHeight(40);
        gridPane.add(amountField, 1,8);

        // Add Password Label
        Label passwordLabel = new Label("Password");
        GridPane.setHalignment(passwordLabel, HPos.LEFT);
        gridPane.add(passwordLabel, 0, 11);

        // Add Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        gridPane.add(passwordField, 1, 11);

        // Add Back Button
        Button backButton = new Button("Back");
        backButton.setPrefHeight(40);
        backButton.setDefaultButton(true);
        backButton.setPrefWidth(100);
        gridPane.add(backButton, 1, 14, 2, 1);
        GridPane.setHalignment(backButton, HPos.RIGHT);
        GridPane.setMargin(backButton, new Insets(20, 0,20,0));

        // Add Submit Button
        Button submitButton = new Button("Transfer");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 14, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0,20,0));


        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(destinationField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a destination");
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

                String result = MyClass.Server(1,6,MyClass.NationalCode,passwordField.getText()
                        ,origin+"www"+destinationField.getText()+"www"+amountField.getText()+"www"+"Null"+"www"+"Null");

                if(result.equals("1")){
                    showAlert(Alert.AlertType.INFORMATION , gridPane.getScene().getWindow(), "success", "successfully transferred." );
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
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ClientArea h = new ClientArea(Main.primaryStage);

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