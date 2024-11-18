package ch.heig.dai.lab.smtppranker;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmtpPranker {
    private String victimsPath;
    private String messagesPath;
    private int groups;

    public SmtpPranker(String victimsPath, String messagesPath, int groups) {
        this.victimsPath = victimsPath;
        this.messagesPath = messagesPath;
        this.groups = groups;
    }

    public void send(String sender, List<String> victims, String message) {
        String smtpHost = "localhost"; // MailDev SMTP server
        int smtpPort = 1025;

        try (Socket socket = new Socket(smtpHost, smtpPort);
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))) {

            System.out.println(reader.readLine());

            // Send HELO command
            writer.println("HELO " + smtpHost);
            System.out.println(reader.readLine());

            // Send MAIL FROM command
            writer.println("MAIL FROM: <" + sender + ">");
            System.out.println(reader.readLine());

            // Send RCPT TO command
            for (String victim : victims) {
                writer.println("RCPT TO: <" + victim + ">");
                System.out.println(reader.readLine());
            }
            // Send DATA command
            writer.println("DATA");
            System.out.println(reader.readLine());

            // Send email body
            writer.write("Content-Type: text/plain; charset=UTF-8\r\n");
            writer.write("From: " + sender + "\r\n");
            writer.write("To: " + String.join(", ", victims) + "\r\n");
            writer.write("Subject: /!\\ IMPORTANT /!\\\r\n");
            writer.write("\r\n"); // Blank line between headers and body
            writer.write(message + "\r\n");
            writer.write(".\r\n"); // End of data
            writer.flush();
            System.out.println(reader.readLine());

            // Send QUIT command
            writer.println("QUIT");
            System.out.println(reader.readLine());

            System.out.println("Email sent successfully to MailDev!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void run() {
        String[] victims = new FileReader().readVictims(new File(victimsPath), StandardCharsets.UTF_8, groups);
        String[] senders = new String[groups];
        String messagesContent = new FileReader().readFile(new File(messagesPath), StandardCharsets.UTF_8);
        String[] messages = messagesContent.split("\n");
        Random random = new Random();
        for (int i = 0; i < groups; i++) {
            senders[i] = victims[i].split("\n")[0];
            victims[i] = victims[i].substring(victims[i].indexOf("\n") + 1);
        }

        for (int i = 0; i < groups; i++) {
            System.out.println("Sending from " + senders[i] + " to\n" + victims[i]);
            List<String> victimsList = new ArrayList<String>(
                    Arrays.asList(victims[i].split("\n")));
            send(senders[i], victimsList, messages[random.nextInt(messages.length)]);
        }
    }
}
