package ch.heig.dai.lab.smtppranker;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class FileReader {

    /**
     * Read the content of a file with a given encoding.
     * 
     * @param file     the file to read.
     * @param encoding
     * @return the content of the file as a String, or null if an error occurred.
     */
    public String readFile(File file, Charset encoding) {
        try (var reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file),
                        encoding))) {
            String content = "";
            String line;

            while ((line = reader.readLine()) != null) {
                content += line + "\n";
            }

            return content;
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }

        return null;
    }

    public String[] readVictims(File file, Charset encoding, int group) {
        String content = readFile(file, encoding);
        if (content != null) {
            String[] lines = content.split("\n");
            List<String> emails = new ArrayList<String>(Arrays.asList(lines));
            int totalLines = lines.length;

            for (String line : lines) {
                if (!this.validateEmail(line)) {
                    throw new IllegalArgumentException("Invalid email address: " + line);
                }
            }

            if (group > 0 && group <= totalLines) {
                Random random = new Random();
                String[] victimGroups = new String[group];
                int start = 0;

                for (int i = 0; i < group; i++) {
                    int groupSize = random.nextInt(4) + 2;
                    int end = start + groupSize;
                    
                    List<String> subList = new ArrayList<>();
                    for (int j = start; j < end; j++) {
                        subList.add(emails.get(j % emails.size()));
                    }

                    victimGroups[i] = String.join("\n", subList);
                    start = end;
                }

                return victimGroups;
            }
        }
        return new String[0];
    }

    public boolean validateEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        
        return email.matches(emailPattern);
    }
}
