package ch.heig.dai.lab.smtppranker;

import java.io.*;
import java.nio.charset.Charset;

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
}
