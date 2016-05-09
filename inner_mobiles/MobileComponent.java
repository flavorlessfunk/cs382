
/**
 * A building block of a mobile.
 * 
 * @author zeil
 *
 */
public class MobileComponent 
{
    private int ID;
    
    public MobileComponent (int IDNum)
    {
        ID = IDNum;
    }
    
    /**
     * Each component has a unique ID # 
     * @return ID number of this component
     */
    public int getIDNumber() {return ID;}
    
    /**
     * Get the total weight of this component and of any
     * other components hanging from it.
     * 
     * @return total weight
     */
    public double getWeight() {return 0.0;}

    /**
     * Get the width of this component.
     */
    public double getWidth() {return 0.0;}

    /**
     * Get the position P at which this component must be 
     * tied in order for it to be balanced, where 0 <= P <= getWidth()
     *   
     * @return the balance point P
     */
    public double getBalancePoint() {return 0.0;}
    
}