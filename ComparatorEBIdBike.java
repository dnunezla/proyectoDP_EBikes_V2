import java.util.*; 

/**
 * Compares two {@link ElectricBike} objects based on their id string in ascending order.
 * This can be used for sorting lists of electric bikes.
 * @author DP Clasess
 * @version 2026.10.13
 */
public class ComparatorEBIdBike implements Comparator<ElectricBike>
{
    /**
     * Compares its two {@link ElectricBike} arguments for order.
     * The comparison is based on the lexicographical order of their ids.
     * @param eb1 The first electric bike to be compared.
     * @param eb2 The second electric bike to be compared.
     * @return A negative integer, zero, or a positive integer as the first
     * bike's plate is less than, equal to, or greater than the second.
     */
    public int compare(ElectricBike eb1, ElectricBike eb2){  
          int result= 0;
        if (eb1== eb2) {
            result= 0;
        } else if (eb1== null) {
            result= 1;
        } else if (eb2== null) {
            result= -1;
        } else {
            String id1=eb1.getId_eB();
            String id2= eb2.getId_eB();
            
            if (id1==null && id2== null) {
                result= 0;
            } else if (id1== null) {
                result= 1;
            } else if (id2 ==null) {
                result=-1;
            } else {
                result= id1.compareTo(id2);
            }
        }
        return result;
    }
 }