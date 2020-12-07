package models;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Represents a message of the day to be displayed in game.
 * @author Andreas Eleftheriades
 * @version 1.0
 */
public class MessageOfTheDay {
    private final String CS230_STARTING_SOLUTION = "CS-230";
    private final int ALPHABET_LENGTH = 26;
    private final int ASCII_VALUE = 65;

    // link to get encrypted text
    private static final String URL_CS230 =
            "http://cswebcat.swansea.ac.uk/puzzle";
    // link to send decrypted text to get message of the day
    private static final String URL_CS230_SOLUTION =
            "http://cswebcat.swansea.ac.uk/message?solution=";
    // the message of the day to be displayed on the menu
    private String message = "";

    /**
     * Creates a message of the day. length
     */
    public MessageOfTheDay() {
        try {
            message = decryption(httpRequestSend(URL_CS230));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns the message of the day.
     * @return The message of the day.
     */
    @Override
    public String toString() {
        return message;
    }

    /**
     * Decrypts encrypted message from @URL_CS230 and returns it.
     * @param cypherText The encrypted message.
     * @return The decrypted message.
     */
    private String decryption(String cypherText) throws Exception {
        String plainText = ""; // holds the decrypted text
        plainText = plainText + CS230_STARTING_SOLUTION;
        int shift = 1; // used to decodes the encrypted text

        for (int i = 0; i < cypherText.length(); i++) {

            char character = (char) (((int) cypherText
                    .charAt(i) - shift + ALPHABET_LENGTH - ASCII_VALUE)
                    % ALPHABET_LENGTH + ASCII_VALUE); // the decryption
            plainText += character;

            if ((shift *= -1) > 0) {
                shift++;
            } else {
                shift--;
            }
        }
        plainText += plainText.length();
        return httpRequestSend(URL_CS230_SOLUTION + plainText);
    }


    /**
     * Fetches a message of the day.
     * @param uniformedResourceLocator The URL that the message
     *                                 of the day is found.
     * @return The message.
     */
    private String httpRequestSend(String uniformedResourceLocator)
            throws Exception {
        String text = "";

        URL url = new URL(uniformedResourceLocator);
        URLConnection urlConnection = url.openConnection();
        BufferedReader scan = new BufferedReader(
                new InputStreamReader(urlConnection.
                        getInputStream()));
        String line;
        while ((line = scan.readLine()) != null) {
            text += line;
        }
        return text;
    }

}

