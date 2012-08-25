/*
 * The BSCoordinate is a coordinate on the plane.
 */
package logic;

import java.util.List;

/**
 *
 * @author joelgreenberg
 */
public class BSCoordinate {

    private int x, y;

    public BSCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else {
            BSCoordinate obj = (BSCoordinate) o;
            return obj.x() == x && obj.y() == y;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.x;
        hash = 67 * hash + this.y;
        return hash;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }

    public BSCoordinate north() {
        return new BSCoordinate(x() - 1, y());
    }

    public BSCoordinate south() {
        return new BSCoordinate(x() + 1, y());
    }

    public BSCoordinate east() {
        return new BSCoordinate(x(), y() + 1);
    }

    public BSCoordinate west() {
        return new BSCoordinate(x(), y() - 1);
    }

    public int distance(BSCoordinate rhs) {
        if (rhs.x() != x && rhs.y() != y) {
            return -1;
        } else if (rhs.x() == x && rhs.y() == y) {
            return 0;
        } else if (rhs.x() == x && rhs.y() != y) {
            return Math.abs(rhs.y() - y);
        } else {
            return Math.abs(rhs.x() - x);
        }
    }

    /**
     * @param coords 
     * @pre coords is a list of colinear coordinates;
     * @return
     */
    public static BSCoordinate getEmptyCoordinates(List<BSCoordinate> coords){
    	return null;
    }
}
