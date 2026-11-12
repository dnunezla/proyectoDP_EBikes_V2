import java.util.*;

/**
 * Model a location in a two-dimensional grid city using (x, y) coordinates.
 * @author David J. Barnes and Michael Kölling
 * @author DP classes 
 * @version 2026.10.13
 */
public class Location
{
    // instance variables - replace the example below with your own
    private int x;
    private int y;

     /**
     * Model a location in the city.
     * @param x The x coordinate. Must be non-negative.
     * @param y The y coordinate. Must be non-negative.
     * @throws IllegalArgumentException If a coordinate is negative.
     */
    public Location(int x, int y)
    {
        if(x< 0 || x>EBikeDemo.MAXX-1) {
            throw new IllegalArgumentException(
                        "Negative x-coordinate or x gets over the maximum: " + x);
        }
        if(y< 0 || y>EBikeDemo.MAXY-1) {
            throw new IllegalArgumentException(
                        "Negative y-coordinate or y gets over the maximum: " + y);
        }        
        this.x = x;
        this.y = y;
    }
    
    /**
     * Generates the next location to visit in a direct line (Manhattan-style diagonal/straight move) 
     * from this location to the destination.
     * @param destination The {@link Location} we want to get to.
     * @return The next {@link Location} one step closer to the destination. Returns the destination
     * if this location is already the destination.
     */
    public Location nextLocation(Location destination)
    {    
        int x1 = x;
        int y1 = y;
        if(destination==null) {
            throw new IllegalArgumentException("destination cannot be null");
        }else{
             if(x1< destination.getX()){
               x1=x1+1;
              }else if(x1>destination.getX()){
               x1=x1-1;
              }
             if(y1<destination.getY()){
               y1=y1+1;
              }else if(y1>destination.getY()){
                y1=y1-1;
              }

        return new Location(x1, y1);
      }
    }

    /**
     * Determines the number of movements required to get from here to the destination, 
     * using the Chebyshev distance (maximum of the absolute differences of the coordinates).
     * @param destination The required destination {@link Location}.
     * @return The number of movement steps.
     */
    public int distance(Location destination)
    {
        int result;
        
        if(destination==null) {
            throw new IllegalArgumentException("destination cannot be null");
        }
        int differenceX= destination.getX()-this.x;
        int differenceY= destination.getY()-this.y;
    
        if (differenceX<0){
            differenceX= -differenceX;
        }
        if (differenceY<0){
            differenceY= -differenceY;
        }
        
        if(differenceX< differenceY){
            result=differenceY;
        }else {
            result=differenceX;
        }
        return result;
    }
    
    /**
     * Implement content equality for locations.
     * @param other The object to compare with.
     * @return {@code true} if the other object is a {@link Location} and has the same x and y coordinates, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other)
    {
        boolean result;
        if (this==other){
             result=true;
        } else if(other==null || getClass()!=other.getClass() ){
            result=false;
            }else{
                Location loc = (Location) other;
                result = (this.x==loc.getX()) && (this.y==loc.getY());
            }
        
        return result;
    }
    
    /**
     * @return A string representation of the location in the format "x-y".
     */
    @Override
    public String toString()
    {
        return x + "-" + y;
    }

    /**
     * Generates a hash code for the location.
     * It uses the top 16 bits for the y value and the bottom 16 bits for the x value
     * to ensure a unique hash code for most grid sizes.
     * @return A hashcode for the location.
     */
    @Override
    public int hashCode()
    {
        return (y << 16) + x;
    }
     /**
     * @return The x coordinate.
     */
    public int getX()
    {
        return x;
    }

    /**
     * @return The y coordinate.
     */
    public int getY()
    {
        return y;
    }
}