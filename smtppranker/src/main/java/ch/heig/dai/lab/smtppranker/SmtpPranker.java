package ch.heig.dai.lab.smtppranker;

public class SmtpPranker {
    private String victims;
    private String messages;
    private int groups;

    public SmtpPranker(String victims, String messages, int groups) {
        this.victims = victims;
        this.messages = messages;
        this.groups = groups;
    }
}
