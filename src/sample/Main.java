package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("banking project bank of HNZ");
        primaryStage.getIcons().add(new Image("file:image\\icon.jpg"));
        Button Button1 = new Button("Exit");
        Button Button2 = new Button("Sign Up");
        Button Button3 = new Button("Log in");
        Button1.setPrefHeight(40);
        Button1.setDefaultButton(true);
        Button1.setPrefWidth(100);
        Button2.setPrefHeight(40);
        Button2.setDefaultButton(true);
        Button2.setPrefWidth(100);
        Button3.setPrefHeight(40);
        Button3.setDefaultButton(true);
        Button3.setPrefWidth(100);
        Label label1 = new Label("Welcom to Our program");
        Label label2 = new Label("By Nima Zare and Hamid Reza Zehtab");
        Font f1 = new Font("Comic Sans MS" , 18);
        Font f2 = new Font("Comic Sans MS" , 15);
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(120, 120, 120, 120));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        label1.setFont(f1);
        label2.setFont(f2);
        gridPane.setHalignment(Button1, HPos.CENTER);
        gridPane.setHalignment(Button1, HPos.CENTER);
        gridPane.setHalignment(Button2, HPos.CENTER);
        gridPane.setHalignment(Button2, HPos.CENTER);
        gridPane.setHalignment(Button3, HPos.CENTER);
        gridPane.setHalignment(Button3, HPos.CENTER);
        gridPane.setHalignment(label1, HPos.CENTER);
        gridPane.setHalignment(label1, HPos.CENTER);
        gridPane.setHalignment(label2, HPos.CENTER);
        gridPane.setHalignment(label2, HPos.CENTER);
        gridPane.add(Button1,0,2,2,1);
        gridPane.add(Button2,1,1,1,1);
        gridPane.add(Button3,0,1,1,1);
        gridPane.add(label1,0,0,2,1);
        gridPane.add(label2,0,3,2,1);

        Scene scene = new Scene(gridPane);
        scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();


        Button1.setOnAction(value ->  {

            System.exit(0);
        });

        Button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SignUp signup = new SignUp(primaryStage);
            }
        });
        Button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LogIn login = new LogIn(primaryStage);

            }
        });







    }


    public static void main(String[] args) {
        System.out.println("Server-Client...");
        launch(args);
    }
}
