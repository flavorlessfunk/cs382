/**
 * 
 */

/**
 * A decorative object that hangs from the "terminal"
 * points of the mobile. DecorativeWeights have the following
 * properties:
 *   1) They have a positive (non-zero) weight
 *   2) They hang from a bar, but nothing hangs from them.
 *   3) Their width is negligible compared to the bars, and so can
 *         be treated as zero. 
 *  
 * @author zeil
 *
 */
public class DecorativeWeight extends MobileComponent {
    
    private double weight;
    
    public DecorativeWeight(int ID, double wght)
    {
        super(ID);
        weight = wght;
    }

    /**
     * Get the total weight of this component and of any
     * other components hanging from it.
     * 
     * @return total weight
     */
    public double getWeight()
    {
        return weight;
    }

 }