/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.ship;

import logic.BSCoordinate;
import boards.Board;
import ships.Orientation;
import ships.Ship;

/**
 * 
 * @author joelgreenberg
 */
public class Submarine extends Ship
{
    public Submarine(Board board, int x_start, int y_start,
            Orientation orientation)
    {
        super(board, new BSCoordinate(x_start, y_start), orientation);
    }

    @Override
    public String getName()
    {
        return "Submarine";
    }

    @Override
    public int getLength()
    {
        return 3;
    }
}
