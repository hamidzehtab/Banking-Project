package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;



public class ClientArea {
    StackPane root = new StackPane();
    Stage stage;


    static class XCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("(empty)");
        Pane pane1 = new Pane();
        Pane pane2 = new Pane();
        Pane pane3 = new Pane();
        Pane pane4 = new Pane();
        Pane pane5 = new Pane();

        Button button1 = new Button("View");
        Button button2 = new Button("bill payment");
        Button button3 = new Button("transfer");
        Button button4 = new Button("close account");
        Button button5 = new Button("loan");



        String lastItem;

        public XCell() {
            super();
            hbox.getChildren().addAll(label,pane1,button1,pane2,button2,pane3,button3,pane4,button4,pane5,button5);


            HBox.setHgrow(pane1, Priority.ALWAYS);
            HBox.setHgrow(pane2, Priority.ALWAYS);
            HBox.setHgrow(pane3, Priority.ALWAYS);
            HBox.setHgrow(pane4, Priority.ALWAYS);
            HBox.setHgrow(pane5, Priority.ALWAYS);

            button1.setPrefHeight(30);
            button1.setDefaultButton(true);
            button1.setPrefWidth(75);
            button2.setPrefHeight(30);
            button2.setDefaultButton(true);
            button2.setPrefWidth(90);
            button3.setPrefHeight(30);
            button3.setDefaultButton(true);
            button3.setPrefWidth(75);
            button4.setPrefHeight(30);
            button4.setDefaultButton(true);
            button4.setPrefWidth(90);
            button5.setPrefHeight(30);
            button5.setDefaultButton(true);
            button5.setPrefWidth(75);
            Font f1 = new Font("Comic Sans MS" , 11);
            label.setFont(f1);


            button1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String[] x = lastItem.split("\t\t");
                    View t = new View(Main.primaryStage,x[0]);
                }
            });
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    String[] x = lastItem.split("\t\t");
                    billPayment t = new billPayment(Main.primaryStage,x[0]);
                }
            });
            button3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String[] x = lastItem.split("\t\t");
                    transfer t = new transfer(Main.primaryStage,x[0]);

                    System.out.println(lastItem + " : " + event);
                }
            });
            button4.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    String[] x = lastItem.split("\t\t");
                    closeAccount c = new closeAccount(Main.primaryStage,x[0]);
                    /*
                    if(!close(x[0]).equals("-1")){
                        ClientArea c = new ClientArea(Main.primaryStage);
                    }
                    */
                }
            });
            button5.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String[] x = lastItem.split("\t\t");
                    Loan c = new Loan(Main.primaryStage,x[0]);
                }
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;
                label.setText(item!=null ? item : "<null>");
                setGraphic(hbox);
            }
        }
    }



    public ClientArea(Stage stage ) {
        stage.setTitle("Client Area");

        Scene scene1 = new Scene(new Group(),900,600);
        stage.setScene(scene1);
        stage.show();



        GridPane gridPane = new GridPane();
        Label headerLabel = new Label("Your Accounts in this Bank.");

        Button newAccountButton = new Button("new Account");
        newAccountButton.setPrefHeight(40);
        newAccountButton.setDefaultButton(true);
        newAccountButton.setPrefWidth(100);

        GridPane.setHalignment(newAccountButton, HPos.LEFT);
        gridPane.add(newAccountButton, 0, 1, 1, 1);



        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0,0,4,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        gridPane.setAlignment(Pos.CENTER);
        
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


        // Add Log out Button
        Button logOutButton = new Button("Log Out");
        logOutButton.setPrefHeight(40);
        logOutButton.setDefaultButton(true);
        logOutButton.setPrefWidth(100);
        gridPane.add(logOutButton, 1, 1, 1, 1);
        GridPane.setHalignment(logOutButton, HPos.LEFT);
        GridPane.setMargin(logOutButton, new Insets(20, 0,20,0));

        Group root = (Group)scene1.getRoot();
        root.getChildren().add(gridPane);
        ObservableList<String> list = FXCollections.observableArrayList();

        String result = MyClass.Server(1,2,MyClass.NationalCode,"Null","Null");
        if(result.equals("-1")){
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "something went wrong oops!");
        }else{
            System.out.println(result);
            if(!result.equals("")) {
                String[] x = result.split("sep");
                for (int i = 0; i < x.length; i += 4) {
                    list.add(x[i] + "\t\t" + x[i + 1] + "\t\t" + x[i + 2] + "\t\t" + x[i+3]);
                }
            }
        }

        ListView<String> lv = new ListView<>(list);
        lv.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new XCell();
            }
        });
        gridPane.add(lv, 0,2,53,1);


        newAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               NewAccount h = new NewAccount(Main.primaryStage);
            }
        });

        logOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MyClass.NationalCode = "";
                Main h = new Main();
                try {
                    h.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
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

