/**
 * 
 */
package imitation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author zeil
 *
 */
public class Vocabulary {
    
    public Vocabulary ()
    {
        startingWords = new ArrayList<String>();
        followers = new HashMap<String, ArrayList<String>>();
    }

    public void addStartingWord(String word) {
        startingWords.add(word);
    }

    public void addWordPair(String lastWord, String word) {
        ArrayList<String> follow = followers.get(lastWord);
        if (follow == null) {
            follow = new ArrayList<String>();
            followers.put (lastWord, follow);
        }
        follow.add(word);
    }

    
    /**
     * Returns a list of all known starting words, in arbitrary order.
     * If no starting words have been added, returns a list of length 0.
     **/
    public List<String> getStartingWords() {
        return startingWords;
    }

    /**
     * Returns a list of all words that are known to have followed lastWord,
     * in arbitrary order. If no such words exist, returns a list of length 0.
     **/
    public List<String> getWordsThatCanFollow(String lastWord) {
        ArrayList<String> follow = followers.get(lastWord);
        if (follow == null) {
            follow = new ArrayList<String>();
        }
        return follow;
    }
    
    private ArrayList<String> startingWords;
    private HashMap<String, ArrayList<String>> followers;
    

}
