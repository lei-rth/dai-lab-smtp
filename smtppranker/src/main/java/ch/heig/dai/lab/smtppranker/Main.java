package ch.heig.dai.lab.smtppranker;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java -jar smtppranker.jar <victims list> <messages list> <number of groups>");
            return;
        } else if (Integer.parseInt(args[2]) <= 0) {
            System.out.println("Number of groups must be greater than 0");
            return;
        }

        SmtpPranker pranker = new SmtpPranker(args[0], args[1], Integer.parseInt(args[2]));

        pranker.run();
    }
}
