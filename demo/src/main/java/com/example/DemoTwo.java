
package com.example;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Cylinder;
import javafx.stage.Stage;

public class DemoTwo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final StringProperty greeting = new SimpleStringProperty("");
    private final StringProperty name = new SimpleStringProperty("");

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createContent(), 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Region createContent() {
        VBox results = new VBox(new Cylinder(40, 80));
        results.setAlignment(Pos.CENTER);
        return results;
    }

    // private Node createGreetingButton() {
    //     Button results = new Button("Hello");
    //     results.setOnAction(evt -> setGreeting());
    //     return results;
    // }

    // private Node createInputRow() {
    //     TextField textField = new TextField("");
    //     textField.textProperty().bindBidirectional(name);
    //     HBox hBox = new HBox(6, new Label("Name:"), textField);
    //     hBox.setAlignment(Pos.CENTER);
    //     return hBox;
    // }

    // private Node createOutputLabel() {
    //     Label results = new Label("");
    //     results.textProperty().bind(greeting);
    //     return results;
    // }

    private void setGreeting() {
        greeting.set("Hello " + name.get());
    }

}
