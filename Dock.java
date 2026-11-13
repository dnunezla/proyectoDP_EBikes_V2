import java.util.ArrayList;
import java.util.List;
/**
 * Model a dock unit within a {@link BikeStation}.
 * It tracks its charging capabilities, fee, and the electric bikes it has recharged.
 * @author David J. Barnes and Michael Kölling
 * @author DP classes 
 * @version 2026.10.13
 */
public class Dock
{
    // instance variables - replace the example below with your own
    private String idDock;
    private int chargingSpeed;
    private float chargingFee;
    private List<ElectricBike> bikesRecharged;
    private float amountCollected;
    private boolean free;
     
     /**
     * Constructor for objects of class Dock.
     * //TODO complete the JavaDoc comments
    */
    public Dock(String id, int speed, float fee)
    {
        if(id==null || id.length()!= 7){
            throw new IllegalArgumentException("THe idDock has not 7 characters or it points at null");
        }
        if (speed < 0) {
            throw new IllegalArgumentException("Charging speed cannot be negative");
        }
        if (fee < 0) {
            throw new IllegalArgumentException("Charging fee cannot be negative");
        }
        this.idDock= id;
        this.chargingSpeed= speed;
        this.chargingFee= fee;
        this.bikesRecharged = new ArrayList<ElectricBike>();
        this.amountCollected = 0;
        this.free=true;
    }

    
    /**
     * Returns a string representation of the dock, including its ID, speed, fee, and the number of EVs recharged.
     * @return A string representation of the dock.
     */
    @Override
    public String toString()
    {
        String result="Dock " + idDock + " [speed=" + chargingSpeed + ", fee=" + chargingFee + ", free=" + free + "]";
        return result;
    }    

    /**
     * @return The total number of {@link ElectricBike}s that have been recharged by this dock.
     */
    public int getNumberEBRecharged(){
       int result=0;
       if (bikesRecharged!= null) {
            result=bikesRecharged.size();
       }
       return result;
    }
    
    /**
     * Simulates the charging process for an {@link ElectricBike}.
     * Increases the amount collected and registers the bike as recharged.
     * @return The cost of the recharge.
     * @param bike The electric bike to recharge.
     */
    public float recharge(ElectricBike bike){
        float cost = 0;
        if (bike==null) {
            throw new IllegalArgumentException("ElectricBike cannot be null");
        }
        if (!bikesRecharged.contains(bike)) {
              addBikeRecharged(bike);
        }
        int whToCharge= bike.getBatteryCapacity()- bike.getBatteryLevel();
        if(whToCharge>0) {
        cost= whToCharge*chargingFee;
        updateAmountCollected(cost);
        }
        return cost;
    }
    /**
     * @return The identifier of the dock.
     */
    public String getIdDock() {
        return this.idDock;
    }
    /**
     * @return The charging speed of the dock.
     */
    public int getChargingSpeed() {
        return this.chargingSpeed;
    }
    /**
     * @return The fee for recharging.
     */
    public float getChargingFee() {
        return this.chargingFee;
    }
    /**
     * @return The list of recharged bikes.
     */
    public List<ElectricBike> getBikesRecharged() {
        return this.bikesRecharged;
    }
    /**
     * @return The total amount collected.
     */
    public float getAmountCollected() {
        return amountCollected;
    }
    /**
     * Checks if the dock is free.
     * @return true if free, if not false .
     */
    public boolean isFree() {
        return this.free;
    }
    /**
     * Sets the availability status of the dock.
     * @param free The new free status.
     */
    public void setFree(boolean free) {
        this.free = free;
    }
    /**
     * Adds an electric bike to the list of recharged bikes if it is not null.
     * @param bike The electric bike to add.
     */
    public void addBikeRecharged(ElectricBike bike) {
        if (bike!=null && !bikesRecharged.contains(bike)) {
            this.bikesRecharged.add(bike);
        }
    }

    public void updateAmountCollected(float amount) {
        this.amountCollected= this.amountCollected+ amount;
    }

    
}
