package ch.heig.dai.lab.util;

public class Message {
    private String subject; // The subject of the email
    private String content; // The content of the email

    public Message(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }
}
