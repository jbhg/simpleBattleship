/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ships;

import boards.Board;

/**
 * 
 * @author joelgreenberg
 */
public class Cruiser extends Ship
{
    public Cruiser(Board board, int x_start, int y_start,
            ORIENTATION orientation)
    {
        super(board, 3, x_start, y_start, orientation, "Cruiser");
    }

    @Override
    public String getName()
    {
        return "Cruiser";
    }

    @Override
    public int getLength()
    {
        return 3;
    }
}
