package ch.heig.dai.lab.smtppranker;

public class Main {
    /**
     * Starts the SMTP Pranker.
     * 
     * @param args Program arguments. The first argument is the path to the victims list file, 
     * the second argument is the path to the messages list file, 
     * and the third argument is the number of groups.
     */
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
