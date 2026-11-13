import java.util.List;
import java.util.ArrayList;

/**
 * Model the common elements of an Electric bike (EB) that operates 
 * within the simulation, moving towards a target and potentially recharging.
 * * @author David J. Barnes and Michael Kölling
 * @author DP classes 
 * @version 2026.10.13
 */

  public class ElectricBike
{
    // instance variables - replace the example below with your own
    private String id_eB;
    private EBCompany company;
    private Location location;
    private Location targetLocation;
    private Location rechargingLocation;
    private int idleCount;
    private int batteryCapacity;
    private int batteryLevel;
    private int wsCharged;
    private int chargesCount;
    private float chargesCost;
    private List<Dock> docksUsed;
    
    /**
     * Constructor of class ElectricBike.
     * @param company The EB's operating company. Must not be null.
     * @param location The EB's starting {@link Location}. Must not be null.
     * @param targetLocation The EB's final destination {@link Location}. Must not be null.
     * @param id The identificator of the bike.
     * @param batteryCapacity The maximum capacity of the battery.
     * @throws NullPointerException If company, location, or targetLocation is null.
     * @throws IllegalArgumentException If id is invalid.
     */
    public ElectricBike(EBCompany company, Location location, Location targetLocation, String id, int batteryCapacity)
    {
        if (company==null) {
            throw new NullPointerException("Company cannot be null");
        }
        if (location ==null) {
            throw new NullPointerException("Location cannot be null");
        }
        if (targetLocation== null) {
            throw new NullPointerException("TargetLocation cannot be null");
        }
        if (id ==null || id.length()!= 6) {
            throw new IllegalArgumentException("The id must have exactly 6 characters.");
        }
        if (batteryCapacity< 0) {
            throw new IllegalArgumentException("Battery capacity cannot be negative.");
        }

        this.company= company;
        this.location=location;
        this.targetLocation=targetLocation;
        this.rechargingLocation= null;
        this.id_eB= id;
        this.batteryCapacity=batteryCapacity;
        this.batteryLevel= batteryCapacity;
        this.idleCount=0;
        this.wsCharged=0;
        this.chargesCount= 0;
        this.chargesCost= 0;
        this.docksUsed=new ArrayList<Dock>();
    }
    
    /**
     * @return The id of the electric bike.
     */
    public String getId_eB(){
        return id_eB;
    }
    /**
     * @return The company of the electric bike.
     */
    public EBCompany getCompany(){
        return company;
    }
    /**
     * @return The location of the electric bike.
     */
    public Location getLocation(){
        return location;
    }
    /**
     * @return The count of times the bike has been idle.
     */
    public int getIdleCount(){
        return idleCount;
    }
    /**
     * @return The maximum battery capacity.
     */
    public int getBatteryCapacity(){
        return batteryCapacity;
    }
    /**
     * @return The current battery level.
     */
    public int getBatteryLevel(){
        return batteryLevel;
    }
    /**
     * @return The number of charging stations visited.
     */
    public int getWsCharged(){
        return wsCharged;
    }
    /**
     * @return The total count of charges.
     */
    public int getChargesCount(){
        return chargesCount;
    }
    /**
     * @return The  cost of charges.
     */
    public float getChargesCost(){
        return chargesCost;
    }
    /**
     * @return The simulation step in which the bike arrived at its target location.
     */
     public Integer getArrivingStep()
    {
        int step= EBikeDemo.MAXSTEPS;
        if(location.equals(targetLocation)){
            step= (EBikeDemo.MAXSTEPS-1)- idleCount;
        }
        return step;
    }
    public String locationToString(){
        return location+", "+"0-0"/*Hay que cambiarlo*/+", "+ targetLocation;
    }
    @Override
    public String toString(){
        return"("+getClass().getName()+ ": "+getId_eB()+ ", " +getBatteryCapacity()+ ", " 
        +getBatteryLevel() +", " +getChargesCount() +", " +getChargesCost() +"€, " +getIdleCount()+
        ", " +getLocation()+", "+locationToString()+")";
    }
     /**
     * Set the current location.
     * @param location Where it is. Must not be null.
     * @throws NullPointerException If location is null.
     */
    public void setLocation(Location location)
    {
        if (location== null) {
            throw new NullPointerException("Location cannot be null");
        }
        this.location= location;
    }

    /**
     * Get the final target location.
     * @return Where this bike is ultimately headed.
     */
    public Location getTargetLocation()
    {
        return targetLocation;
    }

    /**
     * Get the temporary recharging location.
     * @return The {@link Location} of the next {@link BikeStation} to visit, or null if no recharge is planned.
     */
    public Location getRechargingLocation()
    {
        return rechargingLocation;
    }


    /**
     * Set the required final target location.
     * @param location Where to go. Must not be null.
     * @throws NullPointerException If location is null.
     */
    public void setTargetLocation(Location location)
    {
        if (location==null) {
            throw new NullPointerException("Location cannot be null");
        }
        targetLocation= location;
    }

    /**
     * Calculates the optimal route for the bike. 
     * If there isn't enough battery to reach the target, it attempts to find an intermediate 
     * {@link BikeStation} and sets it as the {@code rechargingLocation}.
     */
    public void calculateRoute()
    {
        rechargingLocation=null;
        if (!enoughBattery(targetLocation)) {
            calculateRechargingPosition();
        }
    }

    /**
     * Gets a string representation of the planned route, including the recharging stop if one exists.
     * @return A string showing the route: {@code currentLocation -> [rechargingLocation ->] targetLocation}.
     */
    public String getStringRoute()
    {
       return location+ " -> " +targetLocation;
    }

    /**
     * Checks if the current battery level is sufficient to cover a given distance.
     * @param target Destination location to check.
     * @return true if batteryLevel >= distance *EBCompany.MOVINGCOST, false otherwise.
     */
    public boolean enoughBattery(Location target)
    {
        boolean hasEnough=false;
        if (target!= null) {
            int requiredBattery=location.distance(target)*EBCompany.MOVINGCOST;
            if (batteryLevel>=requiredBattery){
                hasEnough= true;
            }
        }
        return hasEnough;
    }

    /**
     * Determines the optimal intermediate {@link BikeStation} to visit for recharging
     * if the bike cannot reach the final target directly.
     * Sets {@code rechargingLocation} to the chosen station's location.
     */
    public void calculateRechargingPosition()
    {
        Location result=null;
        int maxDist=batteryLevel / EBCompany.MOVINGCOST;
        int minTotalDistance=maxDist;

        int startX= 0;
        if (location.getX()- maxDist>0) {
            startX =location.getX()- maxDist;
        }
        int startY= 0;
        if (location.getY() - maxDist>0) {
            startY= location.getY()-maxDist;
        }
        int endX=EBikeDemo.MAXX - 1;
        if (location.getX()+maxDist < EBikeDemo.MAXX-1) {
            endX= location.getX() + maxDist;
        }
        int endY=EBikeDemo.MAXY- 1;
        if (location.getY()+maxDist < EBikeDemo.MAXY-1) {
            endY= location.getY() + maxDist;
        }
        
        Location loc = null;

        for (int x = startX; x<=endX; x++) {
            for (int y = startY; y<=endY; y++) {
                loc = new Location(x, y);
                if (company.getBikeStation(loc)!= null && !loc.equals(location) && enoughBattery(loc)) {
                    int totalDist=location.distance(loc)+ loc.distance(targetLocation);
                    if (result == null || totalDist< minTotalDistance) {
                        result= loc;
                        minTotalDistance= totalDist;
                    }
                }
            }
        }
    
      rechargingLocation= result;
    }

    /**
     * Get the Manhattan-like distance to the final target location from the current location.
     * @return The distance to the target location.
     */
    public int distanceToTheTargetLocation()
    {
        return location.distance(targetLocation);
    }

    /**
     * Simulates the recharging process when the bike arrives at a {@code rechargingLocation}.
     * The battery is charged to full capacity, the cost is calculated, and the route is recalculated.
     * //TODO complete the JavaDoc comments
     */
    public void recharge()
    {
        BikeStation station = this.company.getBikeStation(this.location);
        if (station != null) {
            Dock freeDock=station.getFreeDock();
            if (freeDock!=null) {
                int neededWh= batteryCapacity-batteryLevel;
                float cost= freeDock.recharge(this);
                
                batteryLevel=batteryCapacity;
                wsCharged= wsCharged+neededWh;
                incrementCharges();
                incrementChargesCost(cost);
                docksUsed.add(freeDock);
                
                calculateRoute();
            }
        }
        
    } 

    /**
     * Increments the count of recharges performed by this bike.
     * //TODO complete the JavaDoc comments
     */
    public void incrementCharges()
    {
        chargesCount++;
    }

    /**
     * Adds a cost amount to the total charges cost.
     * //TODO complete the JavaDoc comments
     */
    public void incrementChargesCost(float cost)
    {
        chargesCost=chargesCost+cost;
    }   

    /**
     * Carries out a single step of the bike's actions.
     * Moves one step towards the target (recharging or final) or stays idle.
     * //TODO complete the JavaDoc comments
     */
    public void act()
    {
        if (location.equals(targetLocation)) {
            idleCount++;
        } else if (rechargingLocation==null && !enoughBattery(targetLocation)) {
            idleCount++;
        } else {
            Location destination=targetLocation;
            if (rechargingLocation!=null) {
                destination= rechargingLocation;
            }

            Location nextStep=location.nextLocation(destination);
            location=nextStep;
            reduceBatteryLevel();

            if (rechargingLocation!=null && location.equals(rechargingLocation)) {
                recharge();
            }
        }
    }

    /**
     * Reduces the battery level by the cost of one movement step (defined in {@link EBCompany#MOVINGCOST}).
     * Ensures the battery level does not go below zero.
     */
    public void reduceBatteryLevel()
    {
        if(batteryLevel-EBCompany.MOVINGCOST >0){
            batteryLevel = batteryLevel-EBCompany.MOVINGCOST;
        }else{
            batteryLevel=0;
        }
    }
    @Override
    public boolean equals(Object obj)
    {
        boolean result=false;
        if (this==obj) {
            result=true;
        } else if (obj!=null && getClass()==obj.getClass()) {
            ElectricBike other=(ElectricBike) obj;
            result=id_eB.equals(other.getId_eB());
        }
        return result;
    }
    

   
}
