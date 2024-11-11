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
        String[] victimsContent = new FileReader().readByGroup(new File(victims), StandardCharsets.UTF_8, groups);

        String messagesContent = new FileReader().readFile(new File(messages), StandardCharsets.UTF_8);

        System.out.println(victimsContent);
        for (String victim : victimsContent) {
            System.out.println(victim);
            System.out.println();
        }
    }
}
