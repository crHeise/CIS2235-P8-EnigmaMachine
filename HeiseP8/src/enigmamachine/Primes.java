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
 * File: Primes.java
 */

package enigmamachine;

/**
 * Class representing a type of Enigma encoding.
 * Inherits from Enigma.
 */
public class Primes extends Enigma {
    private int key;
    private String codedMessage;
    private StringBuilder sbMessage;
    private StringBuilder sbSubString;
    private final int[] primes = {11,13,17,19,23};
    private int count;
    private int wrapCode;
    private int[] codes;
    private int num;

    /**
     * Class constructor.
     */
    public Primes() {
        key = 1;
    }

    /**
     * Sets the message to be encoded and
     * uses a key chosen by the user.
     * @param m the message to be encoded
     * @param k the key to be used when encoding
     */
    @Override
    public void setMessage(String m, int k) {
        //Do Base Enigma encoding first
        super.setMessage(m, k);
        codedMessage = super.getCodedMessage();
        key = k;
        //Now do Primes encoding
        encode();
    }

    /**
     * Sets the message to be encoded and
     * uses a randomly generated key.
     * @param m the message to be encoded
     */
    @Override
    public void setMessage(String m) {
        //Do Base Enigma encoding first
        super.setMessage(m);
        codedMessage = super.getCodedMessage();
        key = super.getKey();
        //Now do Primes encoding
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
        //First decode with Primes
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
        sbSubString = new StringBuilder();
        codes = new int[codedMessage.length()];
        count = 0;

        //First pass
        for (int i = 0; i < codedMessage.length(); ++i){
            //Convert char at index to ASCII value
            codes[i] = codedMessage.charAt(i);

            //Add the primes to the chars in order, looping through primes as necessary
            codes[i] += primes[count];
            count++;

            if (count == primes.length){
                count = 0;
            }

            //Wrap if outside acceptable range
            if (codes[i] > 126){
                wrapCode = 32 + (codes[i] - 127);
                codes[i] = wrapCode;
            }
        }

        //Second pass

        //Shift characters using key
        for (int i = 0; i < codes.length; ++i){
            codes[i] += key;

            //Wrap if outside acceptable range
            if (codes[i] > 126){
                wrapCode = 32 + (codes[i] - 127);
                codes[i] = wrapCode;
            }
        }

        //Third pass
        count = 0;
        num = key%4+2;
        //Separate coded message into substrings num characters long
        for (int i = 0; i < codes.length; ++i){
            sbSubString.append((char)codes[i]);
            count++;

            //Once substring is num long, add it to message and start a new substring
            if(count == num || i == codes.length-1){
                //Reverse the substring before adding
                sbSubString.reverse();
                sbMessage.append(sbSubString.toString());
                sbSubString = new StringBuilder();
                count = 0;
            }
        }

        codedMessage = sbMessage.toString();
    }

    /**
     * Decodes a message.
     */
    private void decode(){
        sbMessage = new StringBuilder();
        sbSubString = new StringBuilder();
        codes = new int[codedMessage.length()];
        count = 0;

        //Reverse of encode

        //First pass (third step of encode)
        num = key%4+2;

        for (int i = 0; i < codedMessage.length(); ++i){
            sbSubString.append(codedMessage.charAt(i));
            count++;

            if(count == num || i == codedMessage.length()-1){
                sbSubString.reverse();
                sbMessage.append(sbSubString.toString());
                sbSubString = new StringBuilder();
                count = 0;
            }
        }

        codedMessage = sbMessage.toString();

        //Second pass (second step of encode)
        for (int i = 0; i < codedMessage.length(); ++i){
            codes[i] = codedMessage.charAt(i);
            codes[i] -= key;

            //Wrap if outside acceptable range
            if (codes[i] < 32){
                wrapCode = 126 - (31 - codes[i]);
                codes[i] = wrapCode;
            }
        }

        count = 0;
        sbMessage = new StringBuilder();

        //Third pass (first step of encode)
        for (int i = 0; i < codedMessage.length(); ++i){
            codes[i] -= primes[count];
            count++;

            if (count == primes.length){
                count = 0;
            }

            //Wrap if outside acceptable range
            if (codes[i] < 32){
                wrapCode = 126 - (31 - codes[i]);
                codes[i] = wrapCode;
            }

            sbMessage.append((char)codes[i]);
        }

        codedMessage = sbMessage.toString();
    }
}
