package sample;

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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class closeAccount
{
    public closeAccount(Stage stage,String origin)
    {
        stage.setTitle("close account");

        Scene scene1 = new Scene(new Group(),500,400);

        stage.setScene(scene1);
        stage.show();

        GridPane gridPane = new GridPane();
        Label headerLabel = new Label("close account");

        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.LEFT);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
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


        //ImageIcon img = new ImageIcon("C:\\Users\\FRSDR\\Documents\\GitHub\\NH-Final-project\\image\\icon.jpg");
        //scene1.setIconImage(img.getImage());
        //scene1.getContentPane().setBackground(Color.yellow);


        //add destination label
        Label destinationlabel = new Label("destination");
        GridPane.setHalignment(destinationlabel, HPos.LEFT);
        gridPane.add(destinationlabel, 0,3);

        // add destination code Text Field
        TextField destinationField = new TextField();
        destinationField.setPrefHeight(40);
        gridPane.add(destinationField, 1,3);

        // Add Password Label
        Label passwordLabel = new Label("Password");
        GridPane.setHalignment(passwordLabel, HPos.LEFT);
        gridPane.add(passwordLabel, 0, 5);

        // Add Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        gridPane.add(passwordField, 1, 5);

        // Add Back Button
        Button backButton = new Button("Back");
        backButton.setPrefHeight(40);
        backButton.setDefaultButton(true);
        backButton.setPrefWidth(100);
        gridPane.add(backButton, 1, 8, 2, 1);
        GridPane.setHalignment(backButton, HPos.RIGHT);
        GridPane.setMargin(backButton, new Insets(20, 0,20,0));

        // Add close account Button
        Button submitButton = new Button("close account");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 8, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0,20,0));



        Group root = (Group)scene1.getRoot();
        root.getChildren().add(gridPane);

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(destinationField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a destination");
                    return;
                }
                if(passwordField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a password");
                    return;
                }



                //    showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Registration Successful!", "Welcome " + nameField.getText());
                //    File file1 = new File("C:\\Users\\FRSDR\\Documents\\GitHub\\NH-Final-project\\data\\"+nameField.getText()+".txt");


                String destination=destinationField.getText();
                String password = passwordField.getText();
                String result =  MyClass.Server(2,4,MyClass.NationalCode,"Null",origin+"www"+password+"www" +destination);

                if(result.equals("1")){
                    showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "success", "your account closed successfully.");

                    ClientArea h=new ClientArea(Main.primaryStage);
                }else if(result.equals("0")){
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Incorrect Password");
                }else if(result.equals("-2")){
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "There is no destination account.");
                }
                else{
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "something went wrong oops!");
                }
            }




        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ClientArea h=new ClientArea(Main.primaryStage);
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