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
 * File: Enigma.java
 */

package enigmamachine;

import java.util.Random;

/**
 * This is a virtual Enigma Machine that uses
 * ASCII codes and a key to encode/decode messages.
 */
public class Enigma implements EnigmaInterface {
    private String message;
    private String codedMessage;
    private StringBuilder sbMessage;
    private int key;
    private Random random;
    private char letter;
    private int code;
    private int wrapCode;

    /**
     * Class constructor.
     * Initializes key to 1.
     */
    public Enigma() {
        key = 1;
        random = new Random();
    }

    /**
     * Encodes a message.
     */
    private void encode(){
        sbMessage = new StringBuilder();

        for(int i = 0; i < message.length(); ++i){
            letter = message.charAt(i);

            //Cast char letter into an int to get its ASCII code
            code = letter;

            //Add code to get ASCII of coded char
            code += key;

            //Check if char code is outside valid range and wrap if necessary
            if (code > 126){
                wrapCode = 32 + (code - 127);
                sbMessage.append((char)wrapCode);
            }
            else{
                sbMessage.append((char)code);
            }
        }

        codedMessage = sbMessage.toString();
    }

    /**
     * Decodes a message.
     */
    private void decode(){
        sbMessage = new StringBuilder();

        for(int i = 0; i < codedMessage.length(); ++i){
            letter = codedMessage.charAt(i);

            //Cast coded letter into an int to get its ASCII code
            code = letter;

            //Subtract code to get ASCII of decoded char
            code -= key;

            //Check if char code is outside valid range and wrap if necessary
            if (code < 32){
                wrapCode = 126 - (31 - code);
                sbMessage.append((char)wrapCode);
            }
            else{
                sbMessage.append((char)code);
            }
        }

        message = sbMessage.toString();
    }

    /**
     * Sets the message to be encoded and
     * uses a key chosen by the user.
     * @param m the message to be encoded
     * @param k the key to be used when encoding
     */
    @Override
    public void setMessage(String m, int k) {
        message = m;

        //Make sure user's key is between 1 and 50
        if (k >= 1 && k <= 50){
            key = k;
        }

        encode();
    }

    /**
     * Sets the message to be encoded and
     * uses a randomly generated key.
     * @param m the message to be encoded
     */
    @Override
    public void setMessage(String m) {
        message = m;

        //Generate a random key that's between 1 and 50
        key = random.nextInt(50)+1;

        encode();
    }

    /**
     * Sets the coded message to be decoded and
     * uses the same key that was used to encode
     * the message.
     * @param cm the coded message to be decoded
     * @param k the key used to originally encode the message
     */
    @Override
    public void setCodedMessage(String cm, int k) {
        codedMessage = cm;
        key = k;
        decode();
    }

    /**
     * Returns either a message to be encoded or
     * a message that was decoded.
     * @return the message that isn't coded
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Returns the coded message.
     * @return the coded message
     */
    @Override
    public String getCodedMessage() {
        return codedMessage;
    }

    /**
     * Returns the key used to encode or decode the message.
     * @return the key a message was coded with
     */
    @Override
    public int getKey() {
        return key;
    }
}
