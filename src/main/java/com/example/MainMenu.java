package com.example;

/**
 * @author [@NF]
 * @email [fel.no@proton.me]
 * @create date 20-01-2023 13:25:44
 * @modify date 20-01-2023 13:25:44
 * @desc [description]
 */

 import javafx.animation.TranslateTransition;
 import javafx.application.Application;
 import javafx.beans.property.BooleanProperty;
 import javafx.beans.property.SimpleBooleanProperty;
 import javafx.event.ActionEvent;
 import javafx.fxml.FXML;
 import javafx.fxml.FXMLLoader;
 import javafx.scene.Parent;
 import javafx.scene.Scene;
 import javafx.scene.control.Button;
 import javafx.scene.control.Label;
 import javafx.scene.layout.AnchorPane;
 import javafx.scene.paint.Color;
 import javafx.scene.shape.Circle;
 import javafx.scene.shape.Rectangle;
 import javafx.stage.Stage;
 
 public class MainMenu extends Application {
 
     Stage stage = new Stage();
     static boolean darkMode = true;
     int playFieldSize = 3;

    static String lightStyle;
    static String darkStyle;
     
     //ANCHOR - start
     public static void main(String[] args) throws Exception {
         launch();
     }
 
     private static class ToggleSwitch extends Parent {
 
         int width,height;
         
         private BooleanProperty switchedOn = new SimpleBooleanProperty(true);
         private TranslateTransition translateAnimation = new TranslateTransition();
 
         public ToggleSwitch(int width, int height) {
 
             this.width = width;
             this.height = height;
             //ANCHOR Draws the circle and the Rectangle
             Rectangle background = new Rectangle(this.width, this.height);
             background.setArcWidth(this.height);
             background.setArcHeight(this.height);
             background.setFill(Color.LIGHTGRAY);
 
             Circle trigger = new Circle(height / 2);
             trigger.setCenterX(background.getArcWidth() / 2);
             trigger.setCenterY(background.getArcHeight() / 2);
             trigger.setFill(Color.GRAY);
             trigger.setStroke(Color.LIGHTGRAY);
 
             translateAnimation.setNode(trigger);
 
             
             getChildren().addAll(background, trigger);
 
             //ANCHOR Adds a listener to switch on or off.
             switchedOn.addListener((obs, oldState, newState) -> {
                 boolean isOn = newState.booleanValue();
                 translateAnimation.setToX(!isOn ? width - width / 2 : 0);
                 translateAnimation.play();
             });
 
             //ANCHOR Refreshes the color if the trigger clicked.
             setOnMouseClicked(event -> {
                 switchedOn.set(!switchedOn.get());
                 darkMode = !darkMode;
                 if (darkMode) {
                     trigger.setFill(Color.GRAY);
                 } else {
                     trigger.setFill(Color.rgb(219,168,0));
                 }
                 refreshColor();
             });
         }
     }
 
     static Scene scene;
 
     @FXML
     private Button btn3;
 
     @FXML
     private Button minus;
 
     @FXML
     private Label oPoints;
 
     @FXML
     private AnchorPane pane;
 
     @FXML
     private Button plus;
 
     @FXML
     private Label size;
 
     @FXML
     private Button reset;
 
     @FXML
     private Label xpoints;
 
     @FXML
     void minuspressed(ActionEvent event) {
         if(playFieldSize > 3)
         --playFieldSize;
         updateSize();
     }
 
     private void updateSize() {
         size.setText(Integer.toString(playFieldSize));
     }
 
 
     @FXML
     void plusPressed(ActionEvent event) {
         if(playFieldSize < 10)
         ++playFieldSize;
         updateSize();
     }
 
     @FXML
     void pressedPlay(ActionEvent event) {
         showPlayField(playFieldSize);
     }
 
 
     @FXML
     void rpressed(ActionEvent event) {
         oPoints.setText("0");
         xpoints.setText("0");
     }
 
     //ANCHOR Starts the menu.
     @Override
     public void start(Stage stage) throws Exception { 
        pane = new AnchorPane();
        try {
            pane = FXMLLoader.load(getClass().getResource("menu.fxml"));
        } catch (Exception e) {
            System.out.println("Failed to Load FXML");
            pane.getChildren().add(new Label("Failed to Load FXML"));
        }   
         scene = new Scene(pane, 600, 400);
         stage.setResizable(false);
         ToggleSwitch darkSwitch = new ToggleSwitch(50, 25);
         darkSwitch.setTranslateX((scene.getWidth()-darkSwitch.width)-10);
         darkSwitch.setTranslateY(10);
         pane.getChildren().add(darkSwitch);
         darkStyle = (getClass().getResource("darkStyle.css").toExternalForm());
         lightStyle = (getClass().getResource("lightStyle.css").toExternalForm());
         scene.getStylesheets().add(darkStyle);
         stage.setTitle("Tic-Tak-Toe Main Menu");
         stage.setScene(scene);
         stage.show();
     }
 
     //ANCHOR Show the play field.
     public void showPlayField(int size) {
         PlayField play = new PlayField(xpoints, oPoints, size, darkMode,lightStyle, darkStyle);
         play.start(stage);
     }
 
     //ANCHOR Refreshes the color if dark mode is enabled.
     public static void refreshColor() {
         if (!darkMode) {
             scene.getStylesheets().remove(darkStyle);
             scene.getStylesheets().add((lightStyle));
         }
         else {
             scene.getStylesheets().remove(lightStyle);
             scene.getStylesheets().add(darkStyle);
         }
     }
 }
 