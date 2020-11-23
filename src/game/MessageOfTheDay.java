package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


// add exceptions
public class MessageOfTheDay {

    private final static String URL_CS230 = "http://cswebcat.swansea.ac.uk/puzzle";
    private final static String URL_CS230_SOLUTION = "http://cswebcat.swansea.ac.uk/message?solution=CS-230";
    private String message = "";

    /**
     * initialises and creates message
     */
    public MessageOfTheDay(){
        message = decryption(httpRequestSend(URL_CS230));
    }

    /**
     * Decrypts encrypted message from @URL_CS230 and returns it
     * @param cypherText - encrypted message
     * @return decrypted message
     */
    private String decryption(String cypherText){
        String plainText = "";
        int shift = 1;
        for(int i=0; i < cypherText.length();i++) {

            char character = (char)(((int)cypherText.charAt(i) - shift + 26 - 65) % 26 + 65); // need to find another way
            plainText += character;

            if((shift *= -1) > 0 ) {
                shift ++;
            }else{
                shift --;
            }
        }
        plainText += plainText.length() + 6;
        return httpRequestSend( URL_CS230_SOLUTION + plainText);
    }


    /**
     * fetches a message from the URL both encrypted and solution message
     * link https://alvinalexander.com/blog/post/java/java-how-read-from-url-string-text/
     * @param uniformedResourceLocator - the URL that the message of the day is found
     * @return the message
     */
    private String httpRequestSend(String uniformedResourceLocator){
        String text = "";
        try {
            URL url = new URL(uniformedResourceLocator);

            //create url connection with url
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader cant use Scanner well i havn't figured it out
            BufferedReader scan = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from urlconnection via scan
            while((line = scan.readLine()) != null){
                text += line ;
            }

        } catch (Exception e) {
            System.out.println("The error is: "+e);
        }
        return text;
    }

    /**
     * Returns the message as a toString
     * @return The message of the day as a String
     */
    @Override
    public String toString() {
        return message;
    }

    /**
     * can remove this
     * Returns the message as a getter
     * @return The message of the day as a String
     */
    public String getMessageOfTheDay(){
        return message;
    }

    /*
    public static  void main(String[] args) {
        System.out.println(new MessageOfTheDay());
        // variable = (condition) ? expressionTrue :  expressionFalse;shift = shift > 0 ? (shift++) : (shift--);
    }
     */
}
