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
 * File: EnigmaFormController.java
 */

package enigmamachine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("FieldMayBeFinal")
public class EnigmaFormController {
    @FXML
    private RadioButton rbRandomKey;
    @FXML
    private RadioButton rbChooseKey;
    @FXML
    private RadioButton rbGEnigma;
    @FXML
    private RadioButton rbPrimes;
    @FXML
    private RadioButton rbHighlander;
    @FXML
    private TextField tfEnigmaType;
    @FXML
    private TextField tfKey;
    @FXML
    private TextField tfKeyUsed;
    @FXML
    private TextArea taStartMessage;
    @FXML
    private TextField tfCodedMessage;
    @FXML
    private TextField tfMessage;

    private String codedMessage;
    private int key = 1;
    private int typeIndex = 0;
    private String enigmaType;

    private Enigma[] eMachines = new Enigma[3];
    private GEnigma geMachine = new GEnigma();
    private Primes pMachine = new Primes();
    private Highlander hMachine = new Highlander();

    @FXML
    private void initialize(){
        eMachines[0] = geMachine;
        eMachines[1] = pMachine;
        eMachines[2] = hMachine;
    }

    @FXML
    private void onActionAbout(ActionEvent event) {
        String about = "This Enigma Machine encodes and/or decodes messages."
                + "It uses the ASCII code of common symbols and letters on a keyboard"
                + "and a key between 1-50 to encode/decode.";
        JOptionPane.showMessageDialog(null, about);
    }

    @FXML
    private void onActionClear(ActionEvent event) {
        //Reset the form to default
        rbRandomKey.setSelected(true);
        rbGEnigma.setSelected(true);
        tfKey.clear();
        tfKey.setPromptText("Enter Key");
        tfKey.setVisible(false);
        tfKey.setEditable(false);
        taStartMessage.clear();
        taStartMessage.setPromptText("Enter Message to be Encoded Here");
        tfMessage.clear();
        tfCodedMessage.clear();
        tfKeyUsed.clear();
        tfEnigmaType.clear();
    }

    @FXML
    private void onActionDecode(ActionEvent event) {
        try{
            eMachines[typeIndex].setCodedMessage(codedMessage, key);

            //Display to user
            tfMessage.setText(eMachines[typeIndex].getMessage());
            tfCodedMessage.setText(codedMessage);
            tfKeyUsed.setText(String.valueOf(key));
            tfEnigmaType.setText(enigmaType);

        } catch (Exception exc){
            JOptionPane.showMessageDialog(null, exc.getMessage());
        }
    }

    @FXML
    private void onActionEncode(ActionEvent event) {
        //Figure out which enigma type was chosen
        if (rbGEnigma.isSelected()){
            typeIndex = 0;
            enigmaType = "G Enigma";
        }
        else if (rbPrimes.isSelected()){
            typeIndex = 1;
            enigmaType = "Primes Enigma";
        }
        else{
            typeIndex = 2;
            enigmaType = "Highlander Enigma";
        }

        try{
            String message;
            if (rbChooseKey.isSelected()){
                //User entered key
                key = Integer.parseInt(tfKey.getText());
                message = taStartMessage.getText();
                eMachines[typeIndex].setMessage(message, key);
            }
            else{
                //Random generated key
                message = taStartMessage.getText();
                eMachines[typeIndex].setMessage(message);
            }

            //Display to user
            tfMessage.setText(message);
            tfCodedMessage.setText(eMachines[typeIndex].getCodedMessage());
            tfKeyUsed.setText(String.valueOf(eMachines[typeIndex].getKey()));
            tfEnigmaType.setText(enigmaType);

        } catch (Exception exc){
            JOptionPane.showMessageDialog(null, exc.getMessage());
        }
    }

    @FXML
    private void onActionOpenFile(ActionEvent event) {
        //Create FileChooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setTitle("Open a Coded Message and key in a File");

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show the Open File Dialog
        File file = fileChooser.showOpenDialog(null);

        if(file != null)
        {
            try {
                String filename = file.getCanonicalPath();
                File myFile = new File(filename);
                Scanner inputFile = new Scanner(myFile);
                codedMessage = inputFile.nextLine();
                key = inputFile.nextInt();
                typeIndex = inputFile.nextInt();
                inputFile.close();

                //Set enigma type
                switch (typeIndex) {
                    case 0 -> {
                        enigmaType = "G Enigma";
                        rbGEnigma.setSelected(true);
                    }
                    case 1 -> {
                        enigmaType = "Primes Enigma";
                        rbPrimes.setSelected(true);
                    }
                    case 2 -> {
                        enigmaType = "Highlander Enigma";
                        rbHighlander.setSelected(true);
                    }
                }

                //Display to user, so they know open file worked
                tfMessage.clear();
                tfCodedMessage.setText(codedMessage);
                tfKeyUsed.setText(String.valueOf(key));
                tfEnigmaType.setText(enigmaType);

            } catch (IOException ex) {
                Logger.getLogger(EnigmaFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void onActionRBGroup1(ActionEvent event) {
        if(rbChooseKey.isSelected()){
            tfKey.setVisible(true);
            tfKey.setEditable(true);
        }
        else{
            tfKey.setVisible(false);
            tfKey.setEditable(false);
            tfKey.setPromptText("Enter Key");
        }
    }

    @FXML
    private void onActionSaveFile(ActionEvent event) {
        //Create FileChooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setTitle("Save a Coded Message in a File");

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show the Save File Dialog
        File file = fileChooser.showSaveDialog(null);

        if(file != null)
        {
            PrintWriter outputFile = null;
            try {
                String filename = file.getCanonicalPath();
                File myFile = new File(filename);
                outputFile = new PrintWriter(filename);
                outputFile.println(eMachines[typeIndex].getCodedMessage());
                outputFile.println(eMachines[typeIndex].getKey());
                outputFile.println(typeIndex);

                outputFile.close();

            } catch (IOException ex) {
                Logger.getLogger(EnigmaFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
