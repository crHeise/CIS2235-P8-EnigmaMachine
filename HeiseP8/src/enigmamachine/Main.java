/*
* Programmer: Chris Heise (crheise@icloud.com)
* School: Central New Mexico Community College
* Course: CIS 2235 Java Programming I
* Instructor: Ivonne Nelson
* Date: 22 April 2021
*
* Program: P8 Enigma Machine with Inheritance
* Purpose: Use multiple inheritance to represent Enigma
*           machine encoding and FXML to create a form.
* File: Main.java
*/

package enigmamachine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("EnigmaForm.fxml"));
        primaryStage.setTitle("Chris Heise CIS 2235-R01 P8: Enigma with Polymorphism");
        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
