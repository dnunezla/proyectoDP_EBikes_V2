import java.util.*; 

/**
 * Compares two {@link Dock} objects based on their **ID** string in **ascending** order.
 * This is typically used as a final tie-breaker in multi-criteria sorting.
 * @author DP Clasess
 * @version 2026.10.13
 */
public class ComparatorDocks implements Comparator<Dock>
{
    /**
     * Compares its two {@link Dock} arguments for order.
     * The comparison is based on the lexicographical order of their IDs.
     * @param c1 The first dock to be compared.
     * @param c2 The second dock to be compared.
     * @return A negative integer, zero, or a positive integer as the first 
     * dock's ID is lexicographically less than, equal to, or greater than the second.
     */
    public int compare(Dock d1, Dock d2){  
        int result= d2.getChargingSpeed()-d1.getChargingSpeed();
        if (result==0) {
            result = Float.compare(d1.getChargingFee(), d2.getChargingFee());
            if (result == 0) {
                result = d1.getIdDock().compareTo(d2.getIdDock());
            }
        }
        return result; 
    } 
}
