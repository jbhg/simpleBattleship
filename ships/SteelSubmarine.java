/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ships;

import logic.BSCoordinate;
import logic.BSSquare;
import debug.Debug;
import boards.Board;

/**
 * 
 * @author joelgreenberg
 */
public class SteelSubmarine extends Ship
{

    private boolean      hitonce;
    private BSCoordinate firsthit;
    private int          steelsub_hits;

    public SteelSubmarine(Board board, int x_start, int y_start,
            ORIENTATION orientation)
    {
        super(board, new BSCoordinate(x_start, y_start), orientation);
        sunksubinit();
    }

    private void sunksubinit()
    {
        hitonce = false;
        firsthit = null;
        steelsub_hits = 0;
    }

    @Override
    public int shoot(int x, int y)
    {
        for (int i = 0; i < getSquares().size(); i++)
        {
            if (getSquares().get(i).x() == x && getSquares().get(i).y() == y)
            {
                getSquares().get(i).setStatus(BSSquare.S_MISS);
            }
        }
        GPSboard.updateBoardSquare(x, y, BSSquare.S_MISS);
        if (!hitonce)
        {
            firsthit = new BSCoordinate(x, y);
            hitonce = true;
            steelsub_hits++;
            return Board.B_MISS;
        } else
        {
            if (steelsub_hits == 1)
            {

                for (int i = 0; i < getSquares().size(); i++)
                {
                    if (getSquares().get(i).x() == firsthit.x()
                            && getSquares().get(i).y() == firsthit.y())
                    {
                        getSquares().get(i).setStatus(BSSquare.S_HIT_SHIP);
                    }
                }
                GPSboard.updateBoardSquare(firsthit.x(), firsthit.y(),
                        BSSquare.S_HIT_SHIP);
                GPSboard.increaseHitsByOne();
            }

            for (int i = 0; i < getSquares().size(); i++)
            {
                if (getSquares().get(i).x() == x
                        && getSquares().get(i).y() == y)
                {
                    getSquares().get(i).setStatus(BSSquare.S_HIT_SHIP);
                }
            }
            GPSboard.updateBoardSquare(x, y, BSSquare.S_HIT_SHIP);
            GPSboard.increaseHitsByOne();
            steelsub_hits++;

            if (isSunk())
            {
                Debug.print("\n\n----\tAbout to print a ship deemed as sunk.\n"
                        + this);
                // We need to make the ship PRINT as if it's sunk:
                for (int i = 0; i < drawShip().size(); i++)
                {
                    // Debug.print("Coords of sunk ship:" +
                    // board_ships.get(hitshipindex).drawShip().get(i).x() + ","
                    // + board_ships.get(hitshipindex).drawShip().get(i).y() +
                    // ":" +
                    // board_ships.get(hitshipindex).drawShip().get(i).status());

                    // board_ships.get(hitshipindex).board_squares.get(i).updateBoardSquare(BSSquare.S_HIT_AND_SUNK_SHIP);
                    drawShip().get(i).setStatus(BSSquare.S_HIT_AND_SUNK_SHIP);
                    GPSboard.updateBoardSquare(drawShip().get(i).x(),
                            drawShip().get(i).y(), BSSquare.S_HIT_AND_SUNK_SHIP);
                    // Debug.println("\tCoords of sunk ship:" +
                    // board_ships.get(hitshipindex).drawShip().get(i).x() + ","
                    // + board_ships.get(hitshipindex).drawShip().get(i).y() +
                    // ":" +
                    // board_ships.get(hitshipindex).drawShip().get(i).status());
                    // Debug.println("\t\t" +
                    // getStringFromIntStatus(board_ships.get(hitshipindex).board_squares.get(i).status()));
                }

                return Board.B_HIT_SUNK;
            } else
            {
                return Board.B_HIT;
            }
        }
    }

    @Override
    public String getName()
    {
        return "SteelSubmarine";
    }

    @Override
    public int getLength()
    {
        // TODO Auto-generated method stub
        return 4;
    }
}
