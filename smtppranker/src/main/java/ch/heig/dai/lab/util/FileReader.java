package ch.heig.dai.lab.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;

public class FileReader {

    static public List<String> readVictims(File file, Charset encoding, int group) {
        List<String> victims = new ArrayList<String>();
        try {
            String jsonContent = Files.readString(file.toPath(), encoding);
            JSONArray jsonArray = new JSONArray(jsonContent);

            if (group <= 0 || group > jsonArray.length()) {
                throw new IllegalArgumentException("Invalid number of groups");
            }

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

    static public List<Message> readMessages(File file, Charset encoding) {
        List<Message> messages = new ArrayList<>();
        try {
            String jsonContent = Files.readString(file.toPath(), encoding);
            JSONArray jsonArray = new JSONArray(jsonContent);
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
