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
 * File: EnigmaInterface.java
 */

package enigmamachine;

public interface EnigmaInterface {
    void setMessage(String m, int k);
    void setMessage(String m);
    void setCodedMessage(String cm, int k);
    String getMessage();
    String getCodedMessage();
    int getKey();
}
