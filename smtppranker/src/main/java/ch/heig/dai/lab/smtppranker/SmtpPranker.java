package ch.heig.dai.lab.smtppranker;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;

import ch.heig.dai.lab.util.*;
import io.github.cdimascio.dotenv.Dotenv;

public class SmtpPranker {
    final private String smtpHost;
    final private int smtpPort;

    private String victimsPath;
    private String messagesPath;
    private int groups;

    public SmtpPranker(String victimsPath, String messagesPath, int groups) {
        Dotenv dotenv = Dotenv.load();
        this.smtpHost = dotenv.get("SMTP_HOST", "localhost");
        this.smtpPort = Integer.parseInt(dotenv.get("SMTP_PORT", "1025"));

        this.victimsPath = victimsPath;
        this.messagesPath = messagesPath;
        this.groups = groups;
    }

    /**
     * Send an email to a list of victims with a given message.
     * 
     * @param sender  the sender of the email
     * @param victims the list of victims to send the email to
     * @param message the message to send
     */
    public void send(String sender, List<String> victims, Message message) {
        try (Socket socket = new Socket(smtpHost, smtpPort);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))) {

            reader.readLine();
            // Send HELO command
            writer.write("HELO " + smtpHost + "\r\n");
            writer.flush();

            // Send MAIL FROM command
            writer.write("MAIL FROM: <" + sender + ">\r\n");
            writer.flush();
            reader.readLine();

            // Send RCPT TO command
            for (String victim : victims) {
                writer.write("RCPT TO: <" + victim + ">\r\n");
                writer.flush();
                reader.readLine();
            }
            // Send DATA command
            writer.write("DATA\r\n");
            writer.flush();
            reader.readLine();

            // Send email body
            writer.write("Content-Type: text/html; charset=UTF-8\r\n");
            writer.write("From: " + sender + "\r\n");
            writer.write("To: " + String.join(", ", victims) + "\r\n");
            writer.write("Subject: " + message.getSubject() + "\r\n");
            writer.write("\r\n"); // Blank line between headers and body
            writer.write("<html><body style=\"font-family: Arial, sans-serif;\">" +
                    message.getContent() + "</body></html>\r\n");
            writer.write(".\r\n"); // End of data
            writer.flush();
            reader.readLine();

            // Send QUIT command
            writer.write("QUIT\r\n");
            writer.flush();
            reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the victims and messages files and sends an email to each group of victims.
     * 
     * The email body is chosen randomly from the messages file.
     * 
     * If there are no victims or messages found, the run method will exit without doing anything.
     * 
     */
    public void run() {
        List<String> victims;
        List<Message> messages;

        try {
            victims = FileReader.readVictims(new File(victimsPath), StandardCharsets.UTF_8, groups);
            if (victims.isEmpty()) {
                System.out.println("No victims found in the victims file");
                return;
            }
            messages = FileReader.readMessages(new File(messagesPath), StandardCharsets.UTF_8);
            if (messages.isEmpty()) {
                System.out.println("No messages found in the messages file");
                return;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        List<String> senders = new ArrayList<>(groups);
        List<List<String>> victimsByGroup = PrankerUtil.splitVictimsByGroup(victims, senders, groups); // Split victims into groups

        // Send emails
        Random random = new Random();
        for (int i = 0; i < groups; i++) {
            this.send(senders.get(i), victimsByGroup.get(i), messages.get(random.nextInt(messages.size())));

            System.out.println("Email sent successfully to MailDev for group " + (i + 1) + " !");
        }
    }
}
