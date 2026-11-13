import java.util.*; 
/**
 * Write a description of class ComparatorEBArrivingStep here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ComparatorEBArrivingStep implements Comparator<ElectricBike>
{
    public int compare(ElectricBike eb1, ElectricBike eb2) {
        int result=eb1.getArrivingStep().compareTo(eb2.getArrivingStep());
        if (result==0){
            result=eb1.getId_eB().compareTo(eb2.getId_eB());
        }
        return result;
    }
} 