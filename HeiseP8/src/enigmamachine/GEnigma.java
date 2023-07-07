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
 * File: GEnigma.java
 */

package enigmamachine;

import java.util.Random;

/**
 * Class representing a type of Enigma encoding.
 * Inherits from Enigma.
 */
public class GEnigma extends Enigma{
    private int key;
    private int ranKey;
    private String codedMessage;
    private Random random;
    private StringBuilder sbMessage;

    /**
     * Class constructor.
     */
    public GEnigma(){
        random = new Random();
    }

    /**
     * Sets the message to be encoded and
     * uses a key chosen by the user.
     * @param m the message to be encoded
     * @param k the key to be used when encoding
     */
    @Override
    public void setMessage(String m, int k) {
        //Call Base Enigma to do initial encoding
        super.setMessage(m, k);
        codedMessage = super.getCodedMessage();
        key = super.getKey();
        //Now do G Enigma encoding
        encode();
    }

    /**
     * Sets the message to be encoded and
     * uses a randomly generated key.
     * @param m the message to be encoded
     */
    @Override
    public void setMessage(String m) {
        //Call Base Enigma to do the initial encoding
        super.setMessage(m);
        codedMessage = super.getCodedMessage();
        key = super.getKey();
        //Now do G Enigma encoding
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
        //First do G Enigma decoding
        decode();
        //Now do Base Enigma decoding
        super.setCodedMessage(codedMessage, key);
    }

    /**
     * Returns either a message to be encoded or
     * a message that was decoded.
     * @return the message that isn't coded
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    /**
     * Returns the coded message.
     * @return the coded message.
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

    /**
     * Encodes a message.
     */
    private void encode(){
        sbMessage = new StringBuilder();

        random.setSeed(key);

        for (int i = 0; i < codedMessage.length(); ++i){
            ranKey = random.nextInt(50)+1;

            //Shift each character using Base Enigma setMessage if index is even
                //Use Base Enigma setCodedMessage if index is odd

            //Check if index is even
            if(i % 2 == 0){
                super.setMessage(codedMessage, ranKey);
                sbMessage.append(super.getCodedMessage().charAt(i));
            }
            //If index is odd
            else{
                super.setCodedMessage(codedMessage, ranKey);
                sbMessage.append(super.getMessage().charAt(i));
            }

        }
        //Reverse the message, that is the coded message
        sbMessage.reverse();
        codedMessage = sbMessage.toString();
    }

    /**
     * Decodes a message.
     */
    private void decode(){
        //Reverse the encode process

        sbMessage = new StringBuilder();
        sbMessage.append(codedMessage);

        //Reverse characters to original order
        sbMessage.reverse();

        //Reassign now-reversed message back into codedMessage
        codedMessage = sbMessage.toString();

        //Clear sbMessage to use again
        sbMessage = new StringBuilder();

        random = new Random(key);

        for (int i = 0; i < codedMessage.length(); ++i){
            ranKey = random.nextInt(50)+1;

            //Check if index is even
            if(i % 2 == 0){
                super.setCodedMessage(codedMessage, ranKey);
                sbMessage.append(super.getMessage().charAt(i));
            }
            //If index is odd
            else{
                super.setMessage(codedMessage, ranKey);
                sbMessage.append(super.getCodedMessage().charAt(i));
            }
        }

        codedMessage = sbMessage.toString();
    }
}
