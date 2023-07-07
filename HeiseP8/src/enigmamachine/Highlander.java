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
 * File: Highlander.java
 */

package enigmamachine;

/**
 * Class representing a type of Enigma encoding.
 * Inherits from Enigma.
 */
public class Highlander extends Enigma {
    private int key;
    private String codedMessage;
    private StringBuilder sbMessage;
    //private int code;
    private int[] codes;
    private int wrapCode;

    /**
     * Class constructor.
     */
    public Highlander() {
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
        //Now do Highlander encoding
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
        //Now do Highlander encoding
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
        //First decode with Highlander
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
        codes = new int[codedMessage.length()];

        //Convert string chars to ints (ASCII values)
        for (int i = 0; i < codedMessage.length(); ++i){
            codes[i] = codedMessage.charAt(i);
        }

        for (int i = codes.length-1; i >= 0; --i){
            //Add 37 to the first char, 19 to second, 86 to third
            //Then add (char[n-3]-char[n-2]) to char[n]

            //Shift first three chars: use the sequence 37, 19, 86
            switch(i){
                case 0:
                    codes[i] += 37;
                    break;
                case 1:
                    codes[i] += 19;
                    break;
                case 2:
                    codes[i] += 86;
                    break;
                default:
                    codes[i] += (codes[i-3] - codes[i - 2]);
            }

            //Wrap if outside acceptable range
            if (codes[i] > 126){
                wrapCode = 32 + (codes[i] - 127);
                codes[i] = wrapCode;
            }
            else if (codes[i] < 32){
                wrapCode = 126 - (31- codes[i]);
                codes[i] = wrapCode;
            }

            sbMessage.append((char)codes[i]);
        }

        sbMessage.reverse();
        codedMessage = sbMessage.toString();
    }

    /**
     * Decodes a message.
     */
    private void decode(){
        //Reverse of encode
        sbMessage = new StringBuilder();
        codes = new int[codedMessage.length()];

        for (int i = 0; i < codes.length; ++i){
            //Convert string chars to ints
            codes[i] = codedMessage.charAt(i);

            //Shift first three chars: use the sequence 37, 19, 86
            switch(i){
                case 0:
                    codes[i] -= 37;
                    break;
                case 1:
                    codes[i] -= 19;
                    break;
                case 2:
                    codes[i] -= 86;
                    break;
                default:
                    codes[i] -= (codes[i-3] - codes[i - 2]);
            }

            if (codes[i] > 126){
                wrapCode = 32 + (codes[i] - 127);
                codes[i] = wrapCode;
            }
            else if (codes[i] < 32){
                wrapCode = 126 - (31 - codes[i]);
                codes[i] = wrapCode;
            }

            sbMessage.append((char)codes[i]);
        }

        codedMessage = sbMessage.toString();
    }
}
