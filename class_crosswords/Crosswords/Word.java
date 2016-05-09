package Crosswords;

import java.util.ArrayList;

/**
 * A word in a crossword puzzle, with a list of possible locations
 * where it could be placed.
 * 
 * @author zeil
 *
 */
public class Word {
    private String value;
    private ArrayList<Placement> possible;
    private int placedAt;
    private int numPossible;
    
    
    public Word(String string, ArrayList<Location> crosswordLocations) {
        value = string;
        placedAt = -1;
        possible = new ArrayList<Placement>();
        for (int j = 0; j < crosswordLocations.size(); ++j)
        {
            Location possibleLoc = crosswordLocations.get(j);
          if (possibleLoc.length == value.length()) {
            possible.add (new Placement(possibleLoc));
          }
        }
        numPossible = possible.size();
    }
    
    
    
    /**
     * @return the string value of this word
     */
    public String getValue() {
        return value;
    }



    /**
     * @return the possible placements of this word
     */
    public ArrayList<Placement> getPossibilities() {
        return possible;
    }






    /**
     * @return the number of possible placements for this word
     *          that have not been suppressed
     */
    public int getNumPossibilities() {
        return numPossible;
    }




    /**
     * Suppress any possible placements that are incompatible
     * with the given layout
     * 
     * @param layout  a puzzle layout with zero or more words filled in
     * @param suppressionLevel  a positive integer
     */
    public void suppressPossibilities (Template layout, 
            int suppressionLevel)
    {
        // Suppress any possible locations
        // that would conflict with the latest change
        for (int k = 0; k < possible.size(); ++k) {
            if (possible.get(k).suppressed <= 0
                    && ! layout.legal(possible.get(k).loc, value)) {
                possible.get(k).suppressed = suppressionLevel;
                --numPossible;
            }
        }
    }

    /**
     * Restore any possible placements that were previously suppressed
     * with the given suppression level
     * 
     * @param suppressionLevel  a positive integer
     */
    public void restorePossibilities (int suppressionLevel)
    {
      // Restore any possible locations
      // that were suppressed at this same level of the solution.
        for (int k = 0; k < possible.size(); ++k) {
          if (possible.get(k).suppressed == suppressionLevel) {
            possible.get(k).suppressed = 0;
            ++numPossible;
          }
        }
    }


    /**
     * @return the index of the possible placement selected for this word
     */
    public int getPlacedAt() {
        return placedAt;
    }



    /**
     * @param placedAt set the index of the placement selected for this word
     */
    public void setPlacedAt(int placedAt) {
        this.placedAt = placedAt;
    }



    public String toString() {
      String s = value + " " + placedAt + " " + numPossible;
      if (placedAt >= 0)
        s = s + " " + possible.get(placedAt);
      return s;
    }
  }