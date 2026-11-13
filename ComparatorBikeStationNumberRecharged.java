import java.util.*;
/**
 * Write a description of class ComparatorBikeStationNumerRecharged here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
    public class ComparatorBikeStationNumberRecharged implements Comparator<BikeStation>
{
    public int compare(BikeStation s1, BikeStation s2) {
        int result= s1.getNumberEBRecharged()-s2.getNumberEBRecharged();
         if (result==0) {
            result=s1.getId().compareTo(s2.getId());
        }
        return result;
    }
}