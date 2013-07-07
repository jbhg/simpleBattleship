/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.awt.Color;

/**
 *
 * @author joelgreenberg
 */
public class BSSquare {

    //Establish the constants for the status of the game-board coordinates.
    public enum Status
    {
        UNKNOWN(1, Color.LIGHT_GRAY), 
        MISS(2, Color.BLUE), 
        HIT(3, Color.MAGENTA), 
        KNOWN_SHIP(4, Color.DARK_GRAY), 
        SUNK(17, Color.RED);
        
        private final int nValue;
        private final Color color;
        
        Status(int nIntValue, Color cColor)
        {
            nValue = nIntValue;
            color = cColor;
        }
        
        public int getValue()
        {
            return nValue;
        }
        
        public Color getColor()
        {
            return color;
        }
        
        public static Status getStausFromInt(int n)
        {
            switch (n) {
                case 1:
                    return UNKNOWN;
                case 2:
                    return MISS;
                case 3:
                    return HIT;
                case 4:
                    return KNOWN_SHIP;
                case 17:
                    return SUNK;
                default:
                    return UNKNOWN;
            }
        }
    }
    
    public static final int S_UNKNOWN = Status.UNKNOWN.getValue();
    public static final int S_MISS = Status.MISS.getValue();
    public static final int S_HIT_SHIP = Status.HIT.getValue();
    public static final int S_LIVE_SHIP = Status.KNOWN_SHIP.getValue();
    public static final int S_HIT_AND_SUNK_SHIP = Status.SUNK.getValue();
    
    private BSCoordinate coord;
    private Status status;

    public static String getStringFromSquare(Status status) {
        return status.toString();
    }
    
    @Deprecated
    public static String getStringFromSquare(int i) {
        return Status.getStausFromInt(i).toString();
    }

    public BSSquare(int x, int y, Status status) {
        coord = new BSCoordinate(x, y);
        this.status = status;
    }

    public int x() {
        return coord.x();
    }

    public int y() {
        return coord.y();
    }

    public Status status() {
        return status;
    }

    public void setStatus(Status newstatus) {
        status = newstatus;
    }

    @Override
	public String toString() {
        return "[" + x() + "," + y() + ": " + status() + "]";
    }

    public BSCoordinate getCoordinate()
    {
        return coord;
    }
}
