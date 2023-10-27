package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        List<Tweet> tweets = new ArrayList<>();
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        
        assertTrue("Expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("Expected empty list", influencers.isEmpty());
    }
    
    
    @Test
    public void testGuessFollowsGraphNoMentions() {
        List<Tweet> tweets = new ArrayList<>();
        Tweet tweet = new Tweet(1, "User1", "No mentions here", Instant.now());
        tweets.add(tweet);
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        
        assertTrue("No mentions, so the graph should be empty", followsGraph.isEmpty());
    }
    
    
    @Test
    public void testInfluencersWithNoFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("No followers, so the list of influencers should be empty", influencers.isEmpty());
    }
    
    @Test
    public void testInfluencersWithEqualFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("User1", new HashSet<>(List.of("User2", "User3")));
        followsGraph.put("User2", new HashSet<>(List.of("User1", "User3")));
        followsGraph.put("User3", new HashSet<>(List.of("User1", "User2")));
        
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        // Expected order can be any permutation of User1, User2, User3
        assertTrue("Influencers should be in any valid order", 
            influencers.equals(List.of("User1", "User2", "User3")) ||
            influencers.equals(List.of("User1", "User3", "User2")) ||
            influencers.equals(List.of("User2", "User1", "User3")) ||
            influencers.equals(List.of("User2", "User3", "User1")) ||
            influencers.equals(List.of("User3", "User1", "User2")) ||
            influencers.equals(List.of("User3", "User2", "User1")));
    }
    


    @Test
    public void testGuessFollowsGraphWithNoMentions() {
        List<Tweet> tweets = new ArrayList<>();
        Tweet tweet = new Tweet(1, "User1", "No mentions in this tweet", Instant.now());
        tweets.add(tweet);
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertTrue("No mentions, so the graph should be empty", followsGraph.isEmpty());
    }


    @Test
    public void testInfluencersWithEqualFollowersAndNoMentions() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("User1", new HashSet<>(List.of("User2", "User3")));
        followsGraph.put("User2", new HashSet<>(List.of("User1", "User3")));
        followsGraph.put("User3", new HashSet<>(List.of("User1", "User2")));

        List<String> influencers = SocialNetwork.influencers(followsGraph);

        // Expected order can be any permutation of User1, User2, User3
        assertTrue("Influencers should be in any valid order",
            influencers.equals(List.of("User1", "User2", "User3")) ||
            influencers.equals(List.of("User1", "User3", "User2")) ||
            influencers.equals(List.of("User2", "User1", "User3")) ||
            influencers.equals(List.of("User2", "User3", "User1")) ||
            influencers.equals(List.of("User3", "User1", "User2")) ||
            influencers.equals(List.of("User3", "User2", "User1")));
    }



}
