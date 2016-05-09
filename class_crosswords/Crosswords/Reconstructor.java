package Crosswords;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Reconstructor {


    public ArrayList<Location> locations;
    public int nWords;
    public Word[] problem;
    public Template startingLayout;






    public void trimPossibilities (Template layout, 
            int numAlreadyPlaced)
    {
        // For all unassigned words, suppress any possible locations
        // that would conflict with the latest change
        for (int i = numAlreadyPlaced; i < problem.length; ++i) {
            Word word = problem[i];
            word.suppressPossibilities(layout, numAlreadyPlaced);
        }
    }


    public void restorePossibilities (int numAlreadyPlaced)
    {
        // For all unassigned words, restore any possible locations
        // that were suppressed at this same level of the solution.
        for (int i = numAlreadyPlaced; i < problem.length; ++i) {
            Word word = problem[i];
            word.restorePossibilities(numAlreadyPlaced);
        }
    }


    void swap (Object[] a, int i, int j)
    {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }



    public boolean solve (int numAlreadyPlaced,
            Template layout)
    {
        boolean solved = false;
        if (numAlreadyPlaced < problem.length)
        {
            // Try to place another word somewhere

            // Find the word with the fewest possible positions remaining.
            //  With luck, we will often find words that "have" to 
            //  be placed in a specific location.
            int fewestPossible = problem[numAlreadyPlaced].getNumPossibilities();
            int bestWordIndex = numAlreadyPlaced;
            for (int i = numAlreadyPlaced+1; i < problem.length; ++i)
                if (problem[i].getNumPossibilities() < fewestPossible) {
                    fewestPossible = problem[i].getNumPossibilities();
                    bestWordIndex = i;
                }
            swap (problem, numAlreadyPlaced, bestWordIndex);

            // Try each possibility in turn
            Word word = problem[numAlreadyPlaced];

            for (int k = 0; (!solved) && k < word.getPossibilities().size(); ++k) {
                Placement trialPlacement = word.getPossibilities().get(k);
                if (trialPlacement.suppressed <= 0
                        && layout.legal(word.getPossibilities().get(k).loc, 
                                word.getValue())) {
                    String oldContents = layout.fetch(trialPlacement.loc);
                    layout.place(trialPlacement.loc, word.getValue());

                    ++numAlreadyPlaced;
                    word.setPlacedAt(k);

                    // System.err.println (word);
                    // System.err.println (layout);


                    trimPossibilities (layout, numAlreadyPlaced);

                    solved = solve (numAlreadyPlaced, layout);

                    if (!solved) {
                        layout.place (trialPlacement.loc, oldContents);
                        word.setPlacedAt(-1);
                        restorePossibilities (numAlreadyPlaced);
                        --numAlreadyPlaced;
                    }
                }
            }
        } else {
            // All words have been assigned. 
            // Have we filled in all the '.'s?
            solved = layout.isSolved();
            if (solved)
                System.out.print (layout);
        }
        return solved;
    }



    public void solve ()
    {
        boolean solved = false;
        solved = solve (0, startingLayout);
        if (!solved)     {
            System.out.println("No layout is possible.");
        }
    }


    public  Reconstructor(BufferedReader input) throws IOException
    {
        String line = input.readLine();
        nWords = Integer.parseInt(line);

        // 1. Read everything in

        ArrayList<String> words = new ArrayList<String>();
        for (int i = 0; i < nWords; ++i) {
            String w = input.readLine();
            words.add(w);
        }


        startingLayout = new Template(input);


        // 2. Analyze lines to determine possible locations where words can start
        locations = startingLayout.getStartingLocations();


        // 3. Associate with each word a list of possible placements within
        //    the puzzle
        problem = new Word[nWords];
        for (int i = 0; i < nWords; ++i) {
            Word word = new Word(words.get(i), locations);
            problem[i] = word;
            // System.err.println (word);
        }
    }


    public static void main (String[] argv) throws FileNotFoundException
    {
        BufferedReader inScan = null;
        if (argv.length > 0) {
            FileReader infile = new FileReader(argv[0]);
            inScan = new BufferedReader(infile);
        } else {
            inScan = new BufferedReader(new InputStreamReader(System.in));
        }

        try {
            Reconstructor cw = new Reconstructor(inScan);
            cw.solve();
        } catch (IOException e) {
            System.err.println ("Input format error: " + e);
        }
    }

}
