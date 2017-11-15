package ru.feeleen.application.BLL;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class paintPageController {

    @FXML
    MenuItem menuItemSave;
    @FXML
    Canvas canvas;
    @FXML
    ImageView imageView;


    @FXML
    Button buttonLine;
    @FXML
    Button buttonPencial;
    @FXML
    Button buttonCircle;
    @FXML
    Button buttonColor;

    @FXML
    Slider sliderWidth;

    @FXML
    TextField textFieldWidth;

    private GraphicsContext graphics;

    private DrawMode drawMode;

    private WritableImage wim;
    private File file = new File("CanvasImage.png");

    public Color color;

    private double x;
    private double y;

    private boolean lineMode;

    private paintColorPageController colorController;

    @FXML
    void initialize() {
        color = Color.BLACK;
        buttonColor.setStyle("-fx-background-color: rgb(" + (int) (color.getRed() * 256) + ", " + (int) (color.getGreen() * 256) + ", " + (int) (color.getBlue() * 256) + ")");
        wim = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        drawMode = DrawMode.PENCIAL;
        graphics = canvas.getGraphicsContext2D();
        graphics.setStroke(color);
        graphics.setLineWidth(sliderWidth.getValue());

        canvas.setOnMousePressed(e -> {
            draw(e, drawMode);
        });

        imageView.setOnMousePressed(event -> draw(event, drawMode));


        textFieldWidth.setText(Integer.toString((int) sliderWidth.getValue()));
    }

    public void changeWidth(MouseEvent mouseEvent) {
        double lineWidth = ((Slider) mouseEvent.getSource()).getValue();
        setLineWidth(lineWidth);
    }

    public void textFieldchangeWidth(KeyEvent keyEvent) {
        if (keyEvent.getCode() != KeyCode.ENTER && keyEvent.getCode() != KeyCode.RIGHT && keyEvent.getCode() != KeyCode.LEFT) {
            TextField temp = (TextField) keyEvent.getSource();
            String lineWidth = temp.getText();
            lineWidth = checkOnNumber(lineWidth);
            if (lineWidth != null && !lineWidth.equals("")) {
                double widthTemp = Double.parseDouble(lineWidth);
                if (widthTemp > 50) {
                    widthTemp = 50;
                }
                setLineWidth(widthTemp);
            }
        }
    }

    public void buttonMode(ActionEvent actionEvent) {
        lineMode = false;
        Button temp = (Button) actionEvent.getSource();
        String s = temp.getText();
        drawMode = DrawMode.valueOf(s.toUpperCase());
    }

    @FXML
    private void changeColor(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../PL/paintColorPage.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            paintColorPageController colorController = (paintColorPageController) fxmlLoader.getController();
            colorController.setParentPageController(this);
            stage.setTitle("getColor");
            colorController.setParameters();
            stage.showAndWait();
            graphics.setStroke(color);
            buttonColor.setStyle("-fx-background-color: rgb(" + (int) (color.getRed() * 256) + ", " + (int) (color.getGreen() * 256) + ", " + (int) (color.getBlue() * 256) + ")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void save(ActionEvent actionEvent) {
        canvas.snapshot(null, wim);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
        } catch (Exception s) {
        }
    }

    private String checkOnNumber(String string) {
        return string.replaceAll("[^0-9]", "");
    }

    private void setLineWidth(double lineWidth) {
        sliderWidth.setValue((int) lineWidth);
        graphics.setLineWidth((int) lineWidth);
        textFieldWidth.setText(Integer.toString((int) lineWidth));
    }

    private void draw(MouseEvent mouseEvent, DrawMode mode){
        switch (mode) {
            case PENCIAL: drawPencial(mouseEvent); break;
            case LINE: drawLine(mouseEvent);
        }
    }

    private void drawLine(MouseEvent mouseEvent) {
        if (!lineMode){
            x = mouseEvent.getX();
            y = mouseEvent.getY();
            lineMode = true;
        } else {
            graphics.strokeLine(x, y, x = mouseEvent.getX(), y = mouseEvent.getY());
            lineMode = false;
        }

    }

    private void drawPencial(MouseEvent mouseEvent){
        graphics.beginPath();
        graphics.lineTo(mouseEvent.getX(), mouseEvent.getY());
        graphics.stroke();

        canvas.setOnMouseDragged(e -> {
            if (drawMode == DrawMode.PENCIAL) {
                graphics.lineTo(e.getX(), e.getY());
                graphics.stroke();
            }
        });
    }


}
