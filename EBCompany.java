import java.util.ArrayList;
import java.util.List;
/**
 * Models the operation of an Electric Bike (EB) Company. 
 * It manages a fleet of {@link ElectricBike}s and a network of {@link BikeStation}s.
 * * @author David J. Barnes and Michael Kölling
 * @author DP classes
 * @version 2026.10.13
 */
public class EBCompany
{
    // instance variables - replace the example below with your own
    public static final int MOVINGCOST=5;

    private String name;
    private List<ElectricBike> subscribedBikes;
    private List<BikeStation> stations;
    /**
     * Constructor for objects of class EVCompany.
     * @param name The name of the company.
     */
    public EBCompany(String name)
    {
        // initialise instance variables
        this.name=name;
        stations= new ArrayList<BikeStation> ();
        subscribedBikes= new ArrayList<ElectricBike> ();
    }

    /**
     * Adds an {@link ElectricBike} to the company's fleet.
     * @param bike The electric bike to add.
     */
    public void addElectricBike(ElectricBike eB){
        if(eB==null){
             throw new IllegalArgumentException("ElectricBike cannot be null");
        }else if (!subscribedBikes.contains(eB)){
            subscribedBikes.add(eB);
        }

    }

    /**
     * Adds a {@link BikeStation} to the company's network.
     * @param station The bike station to add.
     */
    public void addBikeStation(BikeStation station){
        if(station==null){
             throw new IllegalArgumentException("Station cannot be null");
        }else if (!stations.contains(station)){
            stations.add(station);
        }

    }

    public String getName(){
        return name;
    }

    /**
     * Retrieves a {@link BikeStation} by its unique ID.
     * @param id The ID of the station to find.
     * @return The {@link BikeStation} with the matching ID, or {@code null} if not found.
     */
    public BikeStation getBikeStation(String id)
    {
        BikeStation foundStation= null;
        BikeStation station= null;
        int i=0;
        while (i<stations.size() && foundStation==null) {
            station = stations.get(i);
            if (station.getId().equals(id)) {
                foundStation= station;
            }
            i++;
        } 
        return foundStation;

    }

    /**
     * Retrieves a {@link BikeStation} by its {@link Location}.
     * @param location The {@link Location} of the station to find.
     * @return The {@link BikeStation} at the matching location, or {@code null} if not found.
     */
    public BikeStation getBikeStation(Location location)
    {
        BikeStation foundStation= null;
        BikeStation station=null;
        int i= 0;
        while (i<stations.size() && foundStation==null) {
            station= stations.get(i);
            if (station.getLocation().equals(location)) {
                foundStation= station;
            }
            i++;
        }
        return foundStation;
    }

    /**
     * @return The total number of managed {@link BikeStation}s.
     */
    public int getNumberOfStations(){
        return stations.size();
    }

    /**
     * Clears all managed bikes and stations, resetting the company to an empty state.
     */
    public void reset(){
        subscribedBikes.clear();
        stations.clear();
    }

}