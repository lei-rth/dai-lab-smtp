package ch.heig.dai.lab.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class PrankerUtil {

    /**
     * Splits a list of victims into a specified number of groups, and assigns 
     * a sender from the victims list to each group.
     * 
     * The method randomly determines the size of each group between 2 and 5, 
     * ensuring that each group has at least one sender.
     * 
     * @param victims the list of victim email addresses
     * @param senders the list to store the selected senders for each group
     * @param group the number of groups to split the victims into
     * @return a list of victim groups, each group being a list of victim email addresses
     */
    static public List<List<String>> splitVictimsByGroup(List<String> victims, List<String> senders, int group) {
        Random random = new Random();
        int currentIndex = 0;
        List<List<String>> victimGroups = new ArrayList<>();

        for (int i = 0; i < group; i++) {
            int groupSize = random.nextInt(4) + 2;
            List<String> subList = new ArrayList<>();

            // Add the current victim as sender
            senders.add(victims.get(currentIndex));

            // Add remaining victims to the group
            for (int j = 1; j < groupSize; j++) {
                subList.add(victims.get((currentIndex + j) % victims.size()));
            }

            victimGroups.add(subList);
            currentIndex = (currentIndex + groupSize) % victims.size();
        }

        return victimGroups;
    }

    /**
     * Checks if a given email address is valid
     *
     * @param email the email address to validate
     * @return true if the email address is valid, false otherwise
     */
    static public boolean validateEmail(String email) {
        // Regular expression for email validation
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        return email.matches(emailPattern);
    }
}
