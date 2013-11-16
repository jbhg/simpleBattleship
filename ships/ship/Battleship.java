package ships.ship;

import logic.BSCoordinate;
import boards.Board;
import ships.Orientation;
import ships.Ship;

/**
 * 
 * @author joelgreenberg
 */
public class Battleship extends Ship
{
    public Battleship(Board board, int x_start, int y_start,
            Orientation orientation)
    {
        super(board, new BSCoordinate(x_start, y_start), orientation);
    }

    @Override
    public String getName()
    {
        return "Battleship";
    }
    
    @Override
    public int getLength()
    {
        return 4;
    }
}
