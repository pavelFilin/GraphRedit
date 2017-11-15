package ru.feeleen.application.PL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PaintColorForm extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("paintColorPage.fxml"));
        primaryStage.setTitle("Change Color");
        primaryStage.setScene(new Scene(root, 400, 320));
        primaryStage.show();
    }
}
