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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

public class View {

    static class XCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("(empty)");


        String lastItem;

        public XCell() {
            super();
            hbox.getChildren().addAll(label);

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

        public View(Stage stage,String origin){
            stage.setTitle("Login");

            Scene scene1 = new Scene(new Group(),900,600);

            stage.setScene(scene1);
            stage.show();

            GridPane gridPane = new GridPane();
            Label headerLabel = new Label("View");

            headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            gridPane.add(headerLabel, 0,0,2,1);
            GridPane.setHalignment(headerLabel, HPos.LEFT);
            GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
            gridPane.setAlignment(Pos.CENTER);

            gridPane.setPadding(new Insets(40, 40, 40, 40));

            gridPane.setHgap(10);

            gridPane.setVgap(10);
            ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
            columnOneConstraints.setHalignment(HPos.RIGHT);

            ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
            columnTwoConstrains.setHgrow(Priority.ALWAYS);

            gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

            gridPane.setAlignment(Pos.CENTER);

            gridPane.setPadding(new Insets(40, 40, 40, 40));



            // Add Back Button
            Button backButton = new Button("Back");
            backButton.setPrefHeight(40);
            backButton.setDefaultButton(true);
            backButton.setPrefWidth(100);
            gridPane.add(backButton, 1, 0, 2, 1);
            GridPane.setHalignment(backButton, HPos.RIGHT);
            GridPane.setMargin(backButton, new Insets(20, 0,20,0));

            ObservableList<String> list = FXCollections.observableArrayList();

            String result = MyClass.Server(1,7,origin,"Null","Null");
            if(result.equals("-1")){
                showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "something went wrong oops!");
            }else{
                System.out.println(result);
                if(!result.equals("")) {
                    String[] x = result.split("www");
                    for (int i = 0; i < x.length; i += 7) {
                        if(x[i + 5].equals("Null"))x[i + 5]="";
                        if(x[i + 6].equals("Null"))x[i + 6]="";
                        list.add(x[i] + "\t\t" + x[i + 1] + "\t\t" + x[i + 2]
                                + "\t\t" + x[i + 3] + "\t\t" + x[i + 4]
                                + "\t\t" + x[i + 5] + "\t\t" + x[i + 6]);
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
            gridPane.add(lv, 0,2,53,15);

            backButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ClientArea h = new ClientArea(stage);
                }
            });


            Group root = (Group)scene1.getRoot();
            root.getChildren().add(gridPane);

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
