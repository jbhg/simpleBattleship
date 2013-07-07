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
public class BSSquare
{

    // Establish the constants for the status of the game-board coordinates.
    public enum Status
    {
        UNKNOWN(1, Color.LIGHT_GRAY), MISS(2, Color.BLUE), HIT(3, Color.MAGENTA), KNOWN_SHIP(
                4, Color.DARK_GRAY), SUNK(17, Color.RED);

        private final int   nValue;
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
    }

    private BSCoordinate coord;
    private Status       status;

    public static String getStringFromSquare(Status status)
    {
        return status.toString();
    }

    public BSSquare(BSCoordinate coord, Status status)
    {
        this.coord = coord;
        this.status = status;        
    }
    
    @Deprecated
    public BSSquare(int x, int y, Status status)
    {
        this(new BSCoordinate(x,y), status);
    }

    public int x()
    {
        return coord.x();
    }

    public int y()
    {
        return coord.y();
    }

    public Status status()
    {
        return status;
    }

    public void setStatus(Status newstatus)
    {
        status = newstatus;
    }

    @Override
    public String toString()
    {
        return "[" + x() + "," + y() + ": " + status() + "]";
    }

    public BSCoordinate getCoordinate()
    {
        return coord;
    }
}
