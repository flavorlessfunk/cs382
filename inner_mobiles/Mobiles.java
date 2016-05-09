import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Mobiles
{
    private int maxID;
    private ArrayList<MobileComponent> components;
    
    public Mobiles (BufferedReader input)
    {
        maxID = -1;
        components = new ArrayList<MobileComponent>();
        Scanner inScan = new Scanner(input).useDelimiter("[\\s()]+");
        parse(inScan); 
        inScan.close();
    }
    
    private MobileComponent parse (Scanner in)
    {
        try {
            MobileComponent mc = null;
            String componentType = in.next("[BD]");
            if (componentType.equals("B")) {
                // This is a bar
                int idNum = in.nextInt();
                double width = in.nextDouble();
                MobileComponent left = parse (in);
                MobileComponent right = parse (in);
                mc = new Bar (idNum, width, left, right);
                maxID = Math.max(maxID, idNum);
            } else {
                // This is a decorative object
                int idNum = in.nextInt();
                double weight = in.nextDouble();
                mc = new DecorativeWeight(idNum, weight);
            }
            components.add(mc);
            return mc;
        } catch (InputMismatchException e1) {
            return  null;
        } catch (NoSuchElementException e2) {
            return  null;
        } catch (IllegalStateException e3) {
            return  null;
        }
    }

    public static void main(final String[] args) throws IOException
    {
        BufferedReader in = null;
        if (args.length > 0) {
            in = new BufferedReader(new FileReader(args[0]));
        } else {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        Mobiles mobile = new Mobiles(in);
	mobile.solve();
    }

    private void solve()
    {
        Bar[] bars = new Bar[maxID+1];
        for (int i = 0; i < maxID+1; ++i)
            bars[i] = null;
        for (int i = 0; i < components.size(); ++i) {
            MobileComponent mc = components.get(i);
            int id = mc.getIDNumber();
            if (mc.getWidth() > 0.0) {
                // Must be a bar
                bars[id] = (Bar)mc;
            }
        }
        for (int i = 0; i < maxID+1; ++i) {
            if (bars[i] != null) {
                double tie = bars[i].getBalancePoint();
                System.out.print ("Bar " + i + " should be tied ");
                System.out.format("%.2f", tie);
                System.out.println (" from the left.");
            }
        }
    }
}
