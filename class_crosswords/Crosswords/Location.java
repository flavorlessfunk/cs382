package Crosswords;
/**
 * A location in a crossword puzzle where a word could be
 * started.
 * 
 * @author zeil
 *
 */
public class Location {
    /**
     * Line number (row)
     */
    public int line;
    
    /**
     * Column number
     */
    public int column;
    
    /**
     * How many characters long is this word?
     */
    public int length;
    
    /**
     * Is the word placed vertically or horizontally ? 
     * (true -> vertically)
     */
    public boolean vertical;
    

    public Location(int row, int col, boolean vert, int len) {
        line = row;
        column = col;
        vertical = vert;
        length = len;
    }


    public String toString () {
      return "[(" + line + "," + column + ")@" 
        + length + " " + vertical + "]";
    }
  }