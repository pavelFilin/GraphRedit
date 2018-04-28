package ru.feeleen.application.PL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.feeleen.application.BLL.paintPageController;

public class PaintForm extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("paintPage1.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
       // Parent root = FXMLLoader.load(getClass().getResource("paintPage1.fxml"));
        primaryStage.setTitle("My Paint");
        primaryStage.setScene(new Scene(root1, 746, 726));
        paintPageController colorController = (paintPageController) fxmlLoader.getController();
        primaryStage.show();
    }

    public void st(String... args){
        launch(args);
    }
}
