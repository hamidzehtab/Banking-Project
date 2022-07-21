package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;


public class NewAccount {
    StackPane root = new StackPane();
    Stage stage;

    public NewAccount(Stage stage ) {
        stage.setTitle("New Account");
        Scene scene1 = new Scene(new Group(),600,500);
        stage.setScene(scene1);
        stage.show();



        GridPane gridPane = new GridPane();
        Label headerLabel = new Label("New Account page");




        Label label_types = new Label("type of Account");

        String types[] = { "checking_account", "saving_account"};

        ComboBox combo_box =
                new ComboBox(FXCollections.observableArrayList(types));
        combo_box.setPrefHeight(40);
        gridPane.add(combo_box, 1, 8, 2, 1);
        label_types.setFont(Font.font("Comic Sans MS", 12));
        GridPane.setHalignment(label_types, HPos.LEFT);
        gridPane.add(label_types, 0,8,2,1);

        Label aliasLabel = new Label("Alias");
        GridPane.setHalignment(aliasLabel, HPos.LEFT);
        aliasLabel.setFont(Font.font("Comic Sans MS", 12));

        gridPane.add(aliasLabel, 0,10);

        TextField aliasField = new TextField();
        aliasField.setPrefHeight(40);
        gridPane.add(aliasField, 1,10,2,1);


        // Add balance Label
        Label balanceLabel = new Label("Initial balance");
        GridPane.setHalignment(balanceLabel, HPos.LEFT);
        balanceLabel.setFont(Font.font("Comic Sans MS", 12));
        gridPane.add(balanceLabel, 0, 12);

        // Add balanceField Field
        TextField balanceField = new TextField();
        balanceField.setPrefHeight(40);
        gridPane.add(balanceField, 1, 12,2,1);

        // Add pass Label
        Label passLabel = new Label("password");
        GridPane.setHalignment(passLabel, HPos.LEFT);
        passLabel.setFont(Font.font("Comic Sans MS", 12));
        gridPane.add(passLabel, 0, 13);

        // Add passField Field
        PasswordField passField = new PasswordField();
        passField.setPrefHeight(40);
        gridPane.add(passField, 1, 13,2,1);

        
        Button submitButton = new Button("submit");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(150);
        GridPane.setHalignment(submitButton, HPos.RIGHT);
        gridPane.add(submitButton, 1, 14, 1, 1);

        // Add Back Button
        Button backButton = new Button("Back");
        backButton.setPrefHeight(40);
        backButton.setDefaultButton(true);
        backButton.setPrefWidth(150);
        gridPane.add(backButton, 2, 14, 1, 1);
        GridPane.setHalignment(backButton, HPos.LEFT);
        GridPane.setMargin(backButton, new Insets(20, 0,20,0));


        headerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,3,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        gridPane.setAlignment(Pos.CENTER);







        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        gridPane.setAlignment(Pos.CENTER);


        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        Group root = (Group)scene1.getRoot();
        root.getChildren().add(gridPane);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ClientArea c = new ClientArea(stage) ;
            }
        });

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(combo_box.getValue()=="") {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please select your account type.");
                    return;
                }
                if(aliasField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your alias.");
                    return;
                }
                if(balanceField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter Initial balance for your account.");
                    return;
                }
                if(passField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter password for your account.");
                    return;
                }

                String accno = MyClass.Server(2,3,MyClass.NationalCode,"Null"
                        ,combo_box.getValue()+"www"+aliasField.getText()+"www"+balanceField.getText()+"www"+passField.getText());
                if(accno=="-1"){
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "something went wrong oops!");
                }



                showAlert(Alert.AlertType.INFORMATION , gridPane.getScene().getWindow(), "Account Number", "Your Account Created successfully with account number : " + accno );
                ClientArea c = new ClientArea(Main.primaryStage);

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

