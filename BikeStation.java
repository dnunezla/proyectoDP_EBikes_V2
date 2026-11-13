import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Models an Electric Vehicle Charging Station.
 * A charging station contains multiple {@link Dock} units.
 * @author DP classes 
 * @version 2026.10.13
 */
  public class BikeStation
{
    // instance variables - replace the example below with your own
    private String idStation;
    private Location location;
    private List<Dock> docks;

    /**
     * Constructor for objects of class BikeStation.
     * //TODO complete the JavaDoc comments
     */
    public BikeStation()
    {
         idStation= "CC00";
         location= new Location(0,0);
         docks= new ArrayList<Dock>();
    }

    /**
     * Constructor for objects of class BikeStation.
     * //TODO complete the JavaDoc comments
     */
    public BikeStation(String id, Location location)
    {    if (id==null || id.length()!= 4) {
            throw new IllegalArgumentException("The idStation must have 4 characters");
        }
        if (location==null) {
            throw new NullPointerException("Location must not be null");
        }
         this.idStation= id;
         this.location= location;
         docks= new ArrayList<Dock>();
    }
    
    /**
     * Retrieves the first free {@link Dock} found at the station.
     * @return A free {@link Dock}, or possibly throws an exception if none are found (depending on stream implementation details).
     * **Note: The implementation assumes at least one free dock exists if called.**
     */
    public Dock getFreeDock()
    {   
        Dock freeDock=null;
        int i=0;
        while (i<docks.size() && freeDock== null) {
            Dock currentDock = docks.get(i);
            if (currentDock.isFree()) {
                freeDock= currentDock;
            }
            i++;
        }
        return freeDock;       
    }

    /**
     * @return The unique identifier of the charging station.
     */
    public String getId()
    { 
        return idStation;
    }
    
    /**
     * Adds a new {@link Dock} to the station.
     * @param dock The new Dock unit.
     */
    public void addDock(Dock dock)
    {
       if(dock!=null && !docks.contains(dock)){
           docks.add(dock);
       }
    }
    
    /**
     * Set the current location of the charging station.
     * @param location Where it is. Must not be null.
     * @throws NullPointerException If location is null.
     */
    public void setLocation(Location location)
    {
        if(location==null){
            throw new NullPointerException("Location must not be null");
        }
        this.location=location;
    }
    
    public Location getLocation() {
        Location result = location;
        return result;
    }

    public List<Dock> getDocks() {
        List<Dock> result = docks;
        return result;
    }

    @Override
    public String toString() {
        String result = "Station " + idStation + " in " + location;
        return result;
    }
    @Override
    public boolean equals(Object obj)
    {
        boolean equal=false;
        if (this==obj) {
            equal=true;
        } 
        else {
            if (obj!=null && getClass()==obj.getClass()) {
                BikeStation other=(BikeStation) obj;
                if(idStation.equals(other.idStation) && location.equals(other.location)) {
                    equal=true;
                }
            }
        }
        return equal;
    }
    /**    
    *@return The total number of electric bikes recharged across 
    *all docks in this station.
    */
    public int getNumberEBRecharged(){
        int total=0;
        Dock dock=null;
        int i=0;
        for(i=0; i<docks.size();i++){
            dock= docks.get(i);
            total= total+dock.getNumberEBRecharged();
        }
        return total;
    }
}
