package twitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SocialNetwork {

    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        Map<String, Set<String>> followsGraph = new HashMap<>();

        for (Tweet tweet : tweets) {
            String[] words = tweet.getText().split(" ");
            String currentUser = null;

            for (String word : words) {
                if (word.startsWith("@")) {
                    String mentionedUser = word.substring(1); // Remove "@" symbol
                    if (currentUser != null) {
                        // Add an edge from the current user to the mentioned user
                        followsGraph.computeIfAbsent(currentUser, k -> new HashSet<>()).add(mentionedUser);
                    }
                } else {
                    currentUser = word; // Update the current user
                }
            }
        }

        return followsGraph;
    }

    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        Map<String, Integer> influenceCount = new HashMap<>();

        for (Set<String> followers : followsGraph.values()) {
            for (String follower : followers) {
                influenceCount.put(follower, influenceCount.getOrDefault(follower, 0) + 1);
            }
        }

        List<String> influencers = new ArrayList<>(followsGraph.keySet());

        // Sort influencers by influence count (descending order)
        influencers.sort((user1, user2) -> influenceCount.get(user2) - influenceCount.get(user1));

        return influencers;
    }
}
