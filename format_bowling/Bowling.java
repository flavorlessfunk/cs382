import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class Bowling {



    void processTourney(BufferedReader in) {
        Scanner scan = new Scanner(in);

        // Get tourney size and allocate arrays accordingly
        int n = scan.nextInt();        
        String[] teams = new String[n];
        double[] lastYear = new double[n];
        double[] thisYear = new double[n];
        int nteams = 0;

        //Search for the largest percentage change
        double bestSoFar = 0;
        String bestTeamSoFar = null;

        for (int line = 0; line < 2*n; ++line) {
            try {
                // Read an individual entry
                String name = scan.next(); // This is never actually used
                String team = scan.next();
                double indivLastYear = scan.nextDouble();
                double indivThisYear = scan.nextDouble();

                // See if we already have data on the first member of this team
                int found = -1;
                for (int i = 0; i < nteams && found < 0; ++i) {
                    if (teams[i].equals(team)) {
                        found = i;
                    }
                }

                // If not, record this as the first member
                if (found < 0) {
                    teams[nteams] = team;
                    lastYear[nteams] = indivLastYear;
                    thisYear[nteams] = indivThisYear;
                    ++nteams;
                } else {
                    // This is the 2nd member. Print the detail line
                    String teamName = team + "        ";
                    teamName = teamName.substring(0, 8);
                    double prev = lastYear[found] + indivLastYear;
                    double next = thisYear[found] + indivThisYear;
                    double pct = 100.0 * (next - prev) / prev;
                    System.out.print(teamName);
                    System.out.printf(" %.1f %.1f %.1f", prev, next, pct);
                    System.out.println("%");

                    // Is this the best so far?
                    if (bestTeamSoFar == null || bestSoFar < pct) {
                        bestTeamSoFar = team;
                        bestSoFar = pct;
                    }
                }
            } catch (InputMismatchException ex) {
                System.out.println ("Input format error");
                System.exit(2);
            } catch (NoSuchElementException ex) {
                System.out.println ("Input format error");
                System.exit(2);
            }
        }
        // Print the winner
        System.out.println();
        System.out.print("The most-improved team is " + bestTeamSoFar 
                + " with an improvement of ");
        System.out.printf("%.2f", bestSoFar);
        System.out.println("%.");
    }

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.err.println ("Usage: java Bowling inputFileName");
            System.exit(1);
        }
        BufferedReader input = new BufferedReader (
                new FileReader(args[0]));
        Bowling b = new Bowling();
        b.processTourney(input);
    }
}