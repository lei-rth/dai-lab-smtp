package ch.heig.dai.lab.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;

public class FileReader {

    /**
     * Reads a JSON file containing victims and returns a list of strings.
     * 
     * The JSON file must be a list of strings, each string representing a valid email address.
     * 
     * If the number of groups is less than 1 or greater than the number of victims, an exception is thrown.
     * 
     * If an error occurs while reading the file, an exception is thrown.
     * 
     * @param file the file to read
     * @param encoding the encoding of the file
     * @param group the number of groups
     * @return a list of strings representing the victims
     */
    static public List<String> readVictims(File file, Charset encoding, int group) {
        List<String> victims = new ArrayList<String>();
        try {
            String jsonContent = Files.readString(file.toPath(), encoding); // Read the file
            JSONArray jsonArray = new JSONArray(jsonContent);

            // Check if the number of groups is valid
            if (group <= 0 || group > jsonArray.length()) {
                throw new IllegalArgumentException("Invalid number of groups"); 
            }

            // Check if the emails are valid
            for (int i = 0; i < jsonArray.length(); i++) {
                if (!PrankerUtil.validateEmail(jsonArray.getString(i))) {
                    throw new IllegalArgumentException("Invalid email address: " + jsonContent);
                }

                victims.add(jsonArray.getString(i));
            }
        } catch (Exception e) {
            System.out.println("Error reading victims file: " + e.getMessage());
        }

        return victims;
    }

    /**
     * Reads a JSON file containing messages and returns a list of Message objects.
     *
     * @param file the file to read
     * @param encoding the encoding of the file
     * @return a list of Message objects parsed from the file
     * @throws Exception if an error occurs while reading the file
     */
    static public List<Message> readMessages(File file, Charset encoding) {
        List<Message> messages = new ArrayList<>();
        try {
            String jsonContent = Files.readString(file.toPath(), encoding); // Read the file
            JSONArray jsonArray = new JSONArray(jsonContent);

            // Parse the messages
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonMessage = jsonArray.getJSONObject(i);
                messages.add(new Message(
                        jsonMessage.getString("subject"),
                        jsonMessage.getString("content")));
            }
        } catch (Exception e) {
            System.out.println("Error reading messages file: " + e.getMessage());
        }
        return messages;
    }
}
