package Crosswords;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * A pattern for a crossword puzzle.
 * 
 * @author zeil
 *
 */
public class Template {
    private char[][] chars;
    private int width;
    private int height;
    

    /**
     * Create a template, reading it from an input stream
     * 
     * @param inStream input stream for the tempalte
     */
    public Template (BufferedReader inStream)
    {
        Scanner input = new Scanner(inStream);
        String line = input.nextLine();
        ArrayList<String> lines = new ArrayList<String>();
        width = line.length();
        try {
            while (line.length() > 0) {
                lines.add(line);
                line = input.nextLine();
            }
        } catch (NoSuchElementException ex) {
            // Do nothing - we've simply hit end of input
        }
        height = lines.size();
        chars = new char[height][width];

        for (int y = 0; y < height; ++y) {
          for (int x = 0; x < width; ++x)
            chars[y][x] = lines.get(y).charAt(x);
        }
        
    }
    
    
    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }




    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }



    /**
     * Retrieve characters from a template. A '.' denotes an
     * empty space. A '#' is a blocked space that can never contain
     * characters. An alphabetic character indicates a portion of a
     * word that has been inserted into the puzzle.
     * 
     * @param row row number
     * @param col column number
     * @return a character from the indicated position 
     */
    public char getChar(int row, int col) {
        return chars[row][col];
    }

    /**
     * Assign a character to the indicated position in the template.
     * 
     * @param row row number
     * @param col column number
     * @param c a character to put into the indicated position 
     */
    public void setChar(int row, int col, char c) {
        this.chars[row][col] = c;
    }
    
    
    /**
     * Compute a list of all possible locations at which words
     * could be started.
     * 
     * @return list of locations
     */
    public ArrayList<Location> getStartingLocations()
    {
        ArrayList<Location> locations = new ArrayList<Location>();
        for (int i = 0; i < height; ++i)
            for (int j = 0; j < width; ++j) {
                if (getChar(i,j) == '.' &&
                        (j == 0 || getChar(i, j-1) == '#')) {
                    // a horizontal word goes here
                    int len = 0;
                    while (j + len < width && getChar(i, j+len) == '.')
                        ++len;
                    Location loc = new Location(i, j, false, len);
                    if (loc.length > 1)
                        locations.add (loc);
                }
                if (getChar(i, j) == '.' &&
                        (i== 0 || getChar(i-1, j) == '#')) {
                    // a vertical word goes here
                    int len = 0;
                    while (i + len < height && getChar(i+len, j) == '.')
                        ++len;
                    Location loc = new Location(i, j, true, len);
                    if (loc.length > 1)
                        locations.add (loc);
                }
            }
        return locations;
    }
    
    /**
     * Checks to see if word can be inserted at the indicated
     * location without conflicting with any already-inserted 
     * characters
     * 
     * @param loc  a location
     * @param word a word to insert 
     * @return true if it is legal
     */
    public boolean legal(Location loc, String word)
    {
      boolean OK = true;
      if (loc.vertical) {
        for (int i = 0; OK && i < loc.length; ++i) {
          char c = getChar(loc.line+i, loc.column);
          OK = (c == '.') || (c == word.charAt(i));
        }
      } else {
        for (int i = 0; OK && i < loc.length; ++i) {
          char c = getChar(loc.line, loc.column+i);
          OK = (c == '.') || (c == word.charAt(i));
        }
      }
      return OK;
    }
    
    
    /**
     * Insert a word at the indicated location
     * 
     * Precondition: legal(loc, word)
     * 
     * @param loc  a location
     * @param word a word to insert 
     */
    public void place (Location loc, String word)
    // Update the layout by placing the word at the indicated location
    {
      if (loc.vertical) {
        for (int i = 0; i < loc.length; ++i) {
          setChar(loc.line+i, loc.column, word.charAt(i));
        }
      } else {
        for (int i = 0; i < loc.length; ++i) {
          setChar(loc.line, loc.column+i, word.charAt(i));
        }
      }
    }
    
    /**
     * Extract the characters currently in a location
     * These may be a full word, an empty slot, or
     * a mixture of characters placed at intersecting locations.
     * 
     * @param loc  a location
     *  
     */
    public String fetch (Location loc)
    {
        StringBuffer buf = new StringBuffer();
        if (loc.vertical) {
            for (int i = 0; i < loc.length; ++i) {
                buf.append (getChar(loc.line+i, loc.column));
            }
        } else {
            for (int i = 0; i < loc.length; ++i) {
                buf.append (getChar(loc.line, loc.column+i));
            }
        }
        return buf.toString();
    }

    
    /**
     * A template is solved if all '.' characters have been
     * overwritten by alphabetic chars.
     * 
     * @return true if the puzzle is solved
     */
    public boolean isSolved () {
        boolean solved = true;
        for (int y = 0; solved && y < height; ++y)
            for (int x = 0; solved && x < width; ++x)
                solved = getChar(y, x) != '.';
        return solved;
    }
    
    
    
    public String toString()
    {
      StringBuffer sb = new StringBuffer();
      for (int y = 0; y < height; ++y) {
        for (int x = 0; x < width; ++x) {
          sb.append(chars[y][x]);
        }
        sb.append("\n");
      }
      return sb.toString();
    }
    
  }