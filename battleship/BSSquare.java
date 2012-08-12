/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 *
 * @author joelgreenberg
 */
public class BSSquare {

    //Establish the constants for the status of the game-board coordinates.
    public static final int S_UNKNOWN = 1;
    public static final int S_MISS = 2;
    public static final int S_HIT_SHIP = 3;
    public static final int S_LIVE_SHIP = 4;
    public static final int S_HIT_AND_SUNK_SHIP = 17;
    private BSCoordinate coord;
    private int status;

    public static String getStringFromSquare(int i) {
        switch (i) {
            case S_UNKNOWN:
                return "Square status unknown.";
            case S_MISS:
                return "Sq. Miss.";
            case S_HIT_SHIP:
                return "Sq. Hit.";
            case S_LIVE_SHIP:
                return "Sq. Alive.";
            case S_HIT_AND_SUNK_SHIP:
                return "Sq. Sunk.";
            default:
                return "Sq. ERROR";
        }
    }

    public BSSquare(int x, int y, int status) {
        coord = new BSCoordinate(x, y);
        this.status = status;
    }

    public int x() {
        return coord.x();
    }

    public int y() {
        return coord.y();
    }

    public int status() {
        return status;
    }

    public void setStatus(int newstatus) {
        status = newstatus;
    }

    public String toString() {
        return "[" + x() + "," + y() + ": " + status() + "]";
    }

    public BSCoordinate getCoordinate()
    {
        return coord;
    }
}
