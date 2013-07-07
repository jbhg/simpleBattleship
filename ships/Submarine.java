/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ships;

import logic.BSCoordinate;
import boards.Board;

/**
 * 
 * @author joelgreenberg
 */
public class Submarine extends Ship
{
    public Submarine(Board board, int x_start, int y_start,
            ORIENTATION orientation)
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
