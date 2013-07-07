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
public class Submarine extends Ship
{

    public Submarine(Board board, int x_start, int y_start, int x_end,
            int y_end, int initialstatus)
    {
        super(board, 3, x_start, y_start, x_end, y_end, initialstatus,
                "Submarine");
    }

    public Submarine(Board board, int x_start, int y_start, int x_end, int y_end)
    {
        super(board, 3, x_start, y_start, x_end, y_end, "Submarine");
    }

    public Submarine(Board board, int x_start, int y_start,
            ORIENTATION orientation)
    {
        super(board, 3, x_start, y_start, orientation, "Submarine");
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
