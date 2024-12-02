package ch.heig.dai.lab.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class PrankerUtil {

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

    static public boolean validateEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        return email.matches(emailPattern);
    }
}
