package com.example;

/**
 * @author [@NF]
 * @email [fel.no@proton.me]
 * @create date 05-01-2023 16:30:55
 * @modify date 18-01-2023 09:19:52
 * @desc [description]
 */

 import javafx.application.Application;
 import javafx.scene.Scene;
 import javafx.scene.control.Button;
 import javafx.scene.control.Label;
 //import javafx.scene.control.Label;
 import javafx.scene.layout.GridPane;
 import javafx.stage.Stage;
 
 public class PlayField extends Application {
 
     private String winner = null;
     private int playFieldSize = 10;
     private Label xpoints, opoints;
     private boolean darkMode;
     private String lightStyle, darkStyle;
     private boolean isX = true;
 
     PlayField(Label xpoints, Label opoints, int size, boolean darkMode, String lightStyle, String darkStyle) {
         this.xpoints = xpoints;
         this.opoints = opoints;
         this.playFieldSize = size;
         this.darkMode = darkMode;
         this.darkStyle = darkStyle;
         this.lightStyle = lightStyle;
     }
 
     private Button[][] buttons = new Button[playFieldSize][playFieldSize];
     GridPane root = new GridPane();
 
     // ANCHOR - start play field
     @Override
     public void start(Stage primaryStage) {
         Scene scene = new Scene(root, playFieldSize * 100, playFieldSize * 100);
         for (int i = 0; i < playFieldSize; i++) {
             for (int j = 0; j < playFieldSize; j++) {
                 buttons[i][j] = new Button();
                 buttons[i][j].setOnAction(e -> play(e));
                 buttons[i][j].setWrapText(true);
                 if (!darkMode)
                     buttons[i][j].getStylesheets().add(lightStyle);
                 else
                     buttons[i][j].getStylesheets().add(darkStyle);
                 buttons[i][j].setMinSize(100, 100);
                 root.add(buttons[i][j], j, i);
             }
         }
         primaryStage.setResizable(false);
         primaryStage.setTitle("Tic-Tac-Toe");
         primaryStage.setScene(scene);
         primaryStage.show();
     }
 
     // ANCHOR - play
     private void play(javafx.event.ActionEvent e) {
         Button button = (Button) e.getSource();
         if (button.getText().isEmpty()) {
             if (isX) {
                 button.setText("X");
             } else {
                 button.setText("O");
             }
             isX = !isX;
         }
 
         if (checkForWinner()) {
             who();
             lockButtons();
         }
     }
 
     // ANCHOR - checkWinnner
     private boolean checkForWinner() {
         String[][] board = new String[playFieldSize][playFieldSize];
         for (int i = 0; i < playFieldSize; i++) {
             for (int j = 0; j < playFieldSize; j++) {
                 board[i][j] = buttons[i][j].getText();
             }
         }
         if (checkDiagonal(board) || checkDiagonal(reverseBoard(board)) || checkRows(board) || checkColums(board))
             return true;
 
         return false;
     }
 
     private boolean checkRows(String[][] board) {
         String tempwinner = "";
         int counter = playFieldSize;
             for (int row = 0; row < board.length; row++) {
                 if (counter > 0) {
                 for (int colum = 0; colum < board[row].length; colum++) {
                     if (board[row][colum].equals(board[row][0]) && !board[row][colum].isEmpty()) {
                         tempwinner = board[row][colum];
                         counter--;
                     } else {
                         tempwinner = "";
                         counter = playFieldSize;
                         break;
                     }
                 }
             }
         }
         if (!tempwinner.isEmpty()) {
             System.out.println("Winner: " + tempwinner);
             this.winner = tempwinner;
             return true;
         }
         return false;
     }
 
     private boolean checkColums(String[][] board) {
         String tempwinner = "";
         int counter = playFieldSize;
             for (int colum = 0; colum < board.length; colum++) {
                 if (counter > 0) {
                 for (int row = 0; row < board[colum].length; row++) {
                     if (board[row][colum].equals(board[0][colum]) && !board[row][colum].isEmpty()) {
                         tempwinner = board[row][colum];
                         counter--;
                     } else {
                         tempwinner = "";
                         counter = playFieldSize;
                         break;
                     }
                 }
             }
         }
         if (!tempwinner.isEmpty()) {
             System.out.println("Winner: " + tempwinner);
             this.winner = tempwinner;
             return true;
         }
         return false;
     }
 
     private boolean checkDiagonal(String[][] board) {
         String tempwinner = "";
         for (int i = 0; i < board.length; i++) {
             if (board[i][i].equals(board[0][0]) && !board[i][i].isEmpty()) {
                 tempwinner = board[i][i];
             } else {
                 tempwinner = "";
                 break;
             }
         }
 
         if (!tempwinner.isEmpty()) {
             System.out.println("Winner: " + tempwinner);
             this.winner = tempwinner;
             return true;
         }
         return false;
     }
 
     private String[][] reverseBoard(String[][] board) {
         int rows = board.length;
         int cols = board[0].length;
         String[][] reversedBoard = new String[rows][cols];
         for (int i = 0; i < rows; i++) {
             for (int j = 0; j < cols; j++) {
                 reversedBoard[i][j] = board[i][cols - j - 1];
             }
         }
         return reversedBoard;
     }
 
     // ANCHOR - who is the winner
     public void who() {
         if (winner.equals("X"))
             xpoints.setText(Integer.toString(Integer.parseInt(xpoints.getText()) + 1));
         else if (winner.equals("O"))
             opoints.setText(Integer.toString(Integer.parseInt(opoints.getText()) + 1));
         else
             System.out.println("Irgendein Fehler");
 
     }
 
     // ANCHOR - lock Buttons
     public void lockButtons() {
         for (int i = 0; i < playFieldSize; i++) {
             for (int j = 0; j < playFieldSize; j++) {
                 buttons[i][j].setDisable(true);
             }
         }
     }
 }