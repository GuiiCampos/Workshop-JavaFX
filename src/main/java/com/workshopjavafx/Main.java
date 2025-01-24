package com.workshopjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        ScrollPane root = loader.load();

        root.setFitToHeight(true);
        root.setFitToWidth(true);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sample JavaFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
