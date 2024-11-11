package ch.heig.dai.lab.smtppranker;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class SmtpPranker {
    private String victims;
    private String messages;
    private int groups;

    public SmtpPranker(String victims, String messages, int groups) {
        this.victims = victims;
        this.messages = messages;
        this.groups = groups;
    }

    public void run() {
        String victimsContent = new FileReader().readFile(new File(victims), StandardCharsets.UTF_8);
        String messagesContent = new FileReader().readFile(new File(messages), StandardCharsets.UTF_8);

        System.out.println(victimsContent);
        System.out.println(messagesContent);
    }
}
