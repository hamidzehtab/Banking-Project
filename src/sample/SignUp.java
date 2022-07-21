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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp
{
    String emailresult = "-1";

    public SignUp(Stage stage)
    {
        stage.setTitle("Sign Up");

        Scene scene1 = new Scene(new Group(),500,600);

        stage.setScene(scene1);
        stage.show();

        GridPane gridPane = new GridPane();
        Label headerLabel = new Label("Bank of HNZ Sign Up Form");

        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.LEFT);
        GridPane.setMargin(headerLabel, new Insets(0, 0,20,0));
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


        // Add Name Label
        Label nameLabel = new Label("Full Name");
        GridPane.setHalignment(nameLabel, HPos.LEFT);
        gridPane.add(nameLabel, 0,1);

        // Add Name Text Field
        TextField nameField = new TextField();
        nameField.setPrefHeight(40);
        gridPane.add(nameField, 1,1);

        // Add Email Label
        Label emailLabel = new Label("Email Address");
        GridPane.setHalignment(emailLabel, HPos.LEFT);
        gridPane.add(emailLabel, 0, 2);

        // Add Email Text Field
        TextField emailField = new TextField();
        emailField.setPrefHeight(40);
        gridPane.add(emailField, 1, 2);

        // Add Password Label
        Label passwordLabel = new Label("Password");
        GridPane.setHalignment(passwordLabel, HPos.LEFT);
        gridPane.add(passwordLabel, 0, 3);

        // Add Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefHeight(40);
        gridPane.add(passwordField, 1, 3);

        // Add confirmPassword Label
        Label confirmPasswordLabel = new Label("Confirm Password");
        GridPane.setHalignment(confirmPasswordLabel, HPos.LEFT);
        gridPane.add(confirmPasswordLabel, 0, 4);

        // Add confirmPassword Field
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPrefHeight(40);
        gridPane.add(confirmPasswordField, 1, 4);


        //add national code label
        Label nationalcodelabel = new Label("national code");
        GridPane.setHalignment(nationalcodelabel, HPos.LEFT);
        gridPane.add(nationalcodelabel, 0,5);

        // add national code Text Field
        TextField nationalCodeField = new TextField();
        nationalCodeField.setPrefHeight(40);
        gridPane.add(nationalCodeField, 1,5);

        //add phone number
        Label phoneNumberlabel = new Label("phone number");
        GridPane.setHalignment(phoneNumberlabel, HPos.LEFT);
        gridPane.add(phoneNumberlabel, 0,6);

        // add phone number Text Field
        TextField phoneNumberField = new TextField();
        phoneNumberField.setPrefHeight(40);
        gridPane.add(phoneNumberField, 1,6);

        //add code email
        Label codeEmaillabel = new Label("code email");
        GridPane.setHalignment(codeEmaillabel, HPos.LEFT);
        gridPane.add(codeEmaillabel, 0,8);

        // add code email Text Field
        TextField codeEmailField = new TextField();
        codeEmailField.setPrefHeight(40);
        codeEmailField.setDisable(true);
        gridPane.add(codeEmailField, 1,8);



        Button sendEmailButton = new Button("Send Email");
        sendEmailButton.setPrefHeight(40);
        sendEmailButton.setDefaultButton(true);
        sendEmailButton.setPrefWidth(215);
        gridPane.add(sendEmailButton, 0, 10, 4, 1);
        GridPane.setMargin(sendEmailButton, new Insets(0, 10,0,0));
        GridPane.setHalignment(sendEmailButton, HPos.RIGHT);

        // Add Back Button
        Button backButton = new Button("Back");
        backButton.setPrefHeight(40);
        backButton.setDefaultButton(true);
        backButton.setPrefWidth(100);
        gridPane.add(backButton, 1, 11, 2, 1);
        GridPane.setHalignment(backButton, HPos.RIGHT);

        // Add Submit Button
        Button submitButton = new Button("Sign Up");
        submitButton.setPrefHeight(40);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 11, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        submitButton.setDisable(true);



        Group root = (Group)scene1.getRoot();
        root.getChildren().add(gridPane);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main h = new Main();
                try {
                    h.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        sendEmailButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(emailField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your email address");
                    return;
                }
                if(!isValidemail(emailField.getText())) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a valid email " +
                            "address");
                    return;
                }
                emailresult = MyClass.Server(1,8,"Null","Null",
                        emailField.getText());
                submitButton.setDisable(false);
                sendEmailButton.setDisable(true);
                codeEmailField.setDisable(false);
                if(!emailresult.equals("-1")) {

                    showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Successful!", "Email sended successfully.");
                }else{
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "something went wrong oops!");
                }
            }
        });


        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!codeEmailField.getText().equals(emailresult)) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Incorrect Code");
                    return;
                }

                if(nameField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your name");
                    return;
                }
                if(emailField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your email address");
                    return;
                }
                if(!isValidemail(emailField.getText())) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a valid email " +
                            "address");
                    return;
                }
                if(passwordField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a password");
                    return;
                }
                if(confirmPasswordField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please confirm password");
                    return;
                }
                if(!(confirmPasswordField.getText().equals(passwordField.getText()))) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", " password do not match");
                    return;
                }
                if(nationalCodeField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a national code");
                    return;
                }
                if(!isValidNationalCode(nationalCodeField.getText())) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a valid national code");
                    return;
                }
                if(!validateMobileNumber(phoneNumberField.getText())) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a valid phone number");
                    return;
                }
                if(phoneNumberField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a phone number");
                    return;
                }
                String natianlCode = nationalCodeField.getText();

                    codeEmailField.setDisable(false);
                    emailField.setDisable(true);
                    String result = MyClass.Server(1, 5, "Null", "Null",
                            natianlCode + "www" + nameField.getText() + "www" + emailField.getText() + "www"
                                    + passwordField.getText() + "www" + phoneNumberField.getText());
                    if (result.equals("1")) {
                        MyClass.NationalCode = natianlCode;
                        ClientArea h = new ClientArea(Main.primaryStage);
                    } else if (result.equals("0")) {
                        showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "your account already exist!");
                    } else {
                        showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "something went wrong oops!");
                    }

                    ClientArea hd = new ClientArea(Main.primaryStage);
                    MyClass.NationalCode = nationalCodeField.getText();
                    showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Registration Successful!", "Welcome " + nameField.getText());

            }
        });


        //Main m = new Main();
    }
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public static boolean isValidNationalCode(String nationalCode)
    {
        if (!nationalCode.matches("^\\d{10}$"))
            return false;

        int sum = 0;

        for (int i = 0; i < 9; i++)
        {
            sum += Character.getNumericValue(nationalCode.charAt(i)) * (10 - i);
        }

        int lastDigit = Integer.parseInt(String.valueOf(nationalCode.charAt(9)));
        int divideRemaining = sum % 11;

        return ((divideRemaining < 2 && lastDigit == divideRemaining) ||   (divideRemaining >= 2 && (11 - divideRemaining) == lastDigit ));
    }

    private Pattern regexPattern;
    private Matcher regMatcher;


    public static boolean isValidemail(String email){
        String emailFormate ="^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\."
                + "[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        Pattern p = Pattern.compile(emailFormate);
        if(email == null){
            return false;
        }
        return p.matcher(email).matches();
    }

    public boolean validateMobileNumber(String mobileNumber) {

        return true ;
    }

}
