package Crosswords;

/**
 * A description of a possible placement of an item in
 * a crossword puzzle template.
 * 
 * @author zeil
 *
 */
public class Placement {
    Location loc;
    int suppressed;
    
    Placement (Location locat)
    {
      loc = locat;
      suppressed = 0;
    }

    public String toString () {
      return loc.toString();
    }
  }