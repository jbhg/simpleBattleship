package ships;

import boards.Board;

/**
 * 
 * @author joelgreenberg
 */
public class Battleship extends Ship
{
    public Battleship(Board board, int x_start, int y_start,
            ORIENTATION orientation)
    {
        super(board, 4, x_start, y_start, orientation, "Battleship");
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
