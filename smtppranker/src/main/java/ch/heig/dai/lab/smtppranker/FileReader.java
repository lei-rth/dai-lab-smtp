package ch.heig.dai.lab.smtppranker;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;

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

    public String[] readByGroup(File file, Charset encoding, int group) {
        String content = readFile(file, encoding);
        if (content != null) {
            String[] lines = content.split("\n");
            int totalLines = lines.length;

            if (group > 0 && group <= totalLines) {
                int groupSize = totalLines / group;
                groupSize = (groupSize <= 5) ? groupSize : 5;
                String[] victimGroups = new String[group];

                for (int i = 0; i < group; i++) {
                    int start = i * groupSize;
                    int end = (i == group - 1) ? totalLines : start + groupSize;
                    victimGroups[i] = String.join("\n", Arrays.copyOfRange(lines, start, end));
                }

                return victimGroups;
            }
        }
        return new String[0];
    }
}
