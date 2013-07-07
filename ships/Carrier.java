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
public class Carrier extends Ship
{
    public Carrier(Board board, int x_start, int y_start,
            ORIENTATION orientation)
    {
        super(board, 5, x_start, y_start, orientation, "Carrier");
    }

    @Override
    public String getName()
    {
        return "Carrier";
    }

    @Override
    public int getLength()
    {
        return 5;
    }
}
