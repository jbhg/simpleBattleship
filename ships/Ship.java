/*
 * This is my implementation of the Ship class. Notes as they happen:
 * 
 * **I'm moving the shoot method to the Ship class, at least in part. I am also
 * giving every ship a reference to the board that it's on. I shall call the
 * board "GPSboard", as it is reasonable to expect each ship to know where it
 * is in the world (a Ship is not a mere data structure) and when a user "shoots"
 * a ship, it is intuitive that the board itself is shot, and if a ship is there,
 * it may respond however it would like.
 * 
 * Ideas for board_ships in the future:
 * "DoubleHullBattleShip" would have to be hit in two different board_squares
 * before either is visible;
 * "SunkSubmarine" would be hit on one turn, but would not be visible until the 
 * next turn (hence, a turn variable in the board);
 * "PintoShip" would be 5 spaces long, and would have a 25% chance of "exploding"
 * every time it is hit;
 * "MineShip" could either extend from Ship; or both Ship and Mine could extend
 * from "AquaWarMachine"; one could lay down mines in the opponent's water and
 * deploy them at the beginning of the game.
 */
package ships;

import java.util.ArrayList;
import java.util.List;

import logic.BSCoordinate;
import logic.BSSquare;
import boards.Board;
import debug.BSIO;
import debug.Debug;

/**
 * 
 * @author joelgreenberg
 */
public abstract class Ship implements IShip
{

    public enum ORIENTATION
    {
        VERTICAL, HORIZONTAL;

        public static ORIENTATION getRandomOrientation()
        {
            return BSIO.getRandomInt(2) % 2 == 0 ? HORIZONTAL : VERTICAL;
        }

        public static ORIENTATION getOrientationFromString(String sOrientation)
        {
            if (sOrientation.equals("Horizontal"))
            {
                return HORIZONTAL;
            } else if (sOrientation.equals("Vertical"))
            {
                return VERTICAL;
            } else
            {
                System.err
                        .println("There was an error in determining Ship.ORIENTATION from ["
                                + sOrientation
                                + "]. Returning random orientation.");
                return getRandomOrientation();
            }
        }
    }

    protected ORIENTATION         orientation;
    protected BSCoordinate        startCoord;
    protected List<BSSquare> squares;
    protected Board               GPSboard;

    public Ship(Board board, BSCoordinate startCoord, ORIENTATION orientation)
    {
        Debug.println("Trying to create a ship of type " + getName() + " at coordinates " + startCoord);
        this.GPSboard = board;
        this.startCoord = startCoord;
        this.orientation = orientation;
        setArrayOfBattleSquares(startCoord, BSSquare.Status.KNOWN_SHIP);
    }

    private void setArrayOfBattleSquares(BSCoordinate startCoord, BSSquare.Status nInitialStatus)
    {
        // Now we establish an array of board_squares over which the ship sits.
        List<BSSquare> allSquares = new ArrayList<BSSquare>();

        // Instance 1: the change is in the x-coordinate.
        if (this.orientation == ORIENTATION.HORIZONTAL)
        {
            for (int i = 0; i < getLength(); i++)
            {
                allSquares.add(new BSSquare(startCoord.x() + i, startCoord.y(), nInitialStatus));
            }
        } // Instance 2: the change is in the y-coordinate.
        else if (this.orientation == ORIENTATION.VERTICAL)
        {
            for (int i = 0; i < getLength(); i++)
            {
                allSquares.add(new BSSquare(startCoord.x(), startCoord.y() + i,
                        nInitialStatus));
            }
        }
        setSquares(allSquares);
    }

    /**
     * drawShip is a data-oriented way of showing all of the information in the
     * ship, as it currently stands.
     * 
     * This could be used by a program to render a graphical implementation of
     * the ship, as well as a text-version of the same.
     * 
     * @return
     */
    public List<BSSquare> drawShip()
    {
        return getSquares();
    }

    public boolean overlap(Ship s)
    {
        for (int i = 0; i < s.drawShip().size(); i++)
        {
            for (int j = 0; j < getSquares().size(); j++)
            {
                // Debug.println("2 pairs? : [" + i + ";" + j + "]:" +
                // s.drawShip().get(i).x() + " " + s.drawShip().get(i).y() +
                // " and " + board_squares.get(j).x() + " " +
                // board_squares.get(j).y());
                if (s.drawShip().get(i).x() == getSquares().get(j).x()
                        && s.drawShip().get(i).y() == getSquares().get(j).y())
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public BSCoordinate getFirstCoordinate()
    {
        return startCoord;
    }

    @Override
    public ORIENTATION getOrientation()
    {
        return orientation;
    }

    @Deprecated
    public int get_X_end()
    {
        return get_X_start() + (orientation == ORIENTATION.HORIZONTAL ? getLength() - 1 : 0);
    }

    @Deprecated
    public int get_X_start()
    {
        return this.startCoord.x();
    }

    @Deprecated
    public int get_Y_end()
    {
        return get_Y_start() + (orientation == ORIENTATION.VERTICAL ? getLength() - 1 : 0);
    }

    @Deprecated
    public int get_Y_start()
    {
        return this.startCoord.y();
    }

    @Override
    public String toString()
    {
        String s = getName() + " (length " + getLength()
                + ") has the following makeup: \n";
        for (int i = 0; i < getSquares().size(); i++)
        {
            s = s
                    + "["
                    + getSquares().get(i).x()
                    + ","
                    + getSquares().get(i).y()
                    + ": "
                    + getSquares().get(i).status()
                    + "/"
                    + BSSquare.getStringFromSquare(getSquares().get(i).status())
                    + "]\n";
        }
        return s;
    }

    @Override
    public boolean isSunk()
    {
        Debug.print("isSunk? Hits remaining: " + hitsRemaining());
        for (BSSquare s : getSquares())
        {
            if (s.status() != BSSquare.Status.HIT && s.status() != BSSquare.Status.SUNK)
            {
                return false;
            }
        }
        return true;
    }

    public int shoot(int x, int y)
    {
        for (int i = 0; i < getSquares().size(); i++)
        {
            if (getSquares().get(i).x() == x && getSquares().get(i).y() == y)
            {
                getSquares().get(i).setStatus(BSSquare.Status.HIT);

            }
        }
        GPSboard.updateBoardSquare(x, y, BSSquare.Status.HIT);
        GPSboard.increaseHitsByOne();

        if (isSunk())
        {
            Debug.print("\n\n----\tAbout to print a ship deemed as sunk.\n"
                    + this);
            // We need to make the ship PRINT as if it's sunk:
            for (int i = 0; i < drawShip().size(); i++)
            {
                // Debug.print("Coords of sunk ship:" +
                // board_ships.get(hitshipindex).drawShip().get(i).x() + "," +
                // board_ships.get(hitshipindex).drawShip().get(i).y() + ":" +
                // board_ships.get(hitshipindex).drawShip().get(i).status());

                // board_ships.get(hitshipindex).board_squares.get(i).updateBoardSquare(BSSquare.S_HIT_AND_SUNK_SHIP);
                drawShip().get(i).setStatus(BSSquare.Status.SUNK);
                GPSboard.updateBoardSquare(drawShip().get(i).x(), drawShip().get(i).y(), BSSquare.Status.SUNK);
                // Debug.println("\tCoords of sunk ship:" +
                // board_ships.get(hitshipindex).drawShip().get(i).x() + "," +
                // board_ships.get(hitshipindex).drawShip().get(i).y() + ":" +
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

    public int hitsRemaining()
    {
        int hitsremaining = 0;
        for (BSSquare s : getSquares())
        {
            if (s.status() == BSSquare.Status.KNOWN_SHIP || s.status() == BSSquare.Status.UNKNOWN)
            {
                hitsremaining++;
            }
        }
        return hitsremaining;
    }

    /**
     * @param squares
     *            the squares to set
     */
    public void setSquares(List<BSSquare> squares)
    {
        this.squares = squares;
    }

    /**
     * @return the squares
     */
    public List<BSSquare> getSquares()
    {
        return squares;
    }
}
/*
 * @return one of the B_ - variables, based on a Board status.
 * 
 * The idea behind this method is that a User Interface "shoots" the board, and
 * then the board "shoots" the ship, if a ship exists, and returns whatever the
 * ship would like.
 * 
 * This allows board_ships to act however they wish upon being shot. For
 * example, a "DoubleHullBattleShip" would have to be hit in two different
 * board_squares before either is visible; or, a "SunkSubmarine" would be hit on
 * one turn, but would not be visible until the next turn (it would have to
 * communicate with the board more thoroughly), as the ship was sunk, but rose
 * to the top of the water upon its being shot.
 */
// public int shoot(int x, int y) {
//
// for (int i = 0; i < squares.size(); i++) {
// if (squares.get(i).x() == x && squares.get(i).y() == y) {
// squares.get(i).setStatus(BSSquare.S_HIT_SHIP);
// if (isSunk()) {
// for (int k = 0; k < squares.size(); k++) {
// squares.get(i).setStatus(BSSquare.S_HIT_AND_SUNK_SHIP);
// }
// return Board.B_HIT_SUNK;
// } else {
// return Board.B_HIT;
// }
// }
// }
// //If this is reached, the ship does not exist here.
// return BSSquare.Status.MISS;
// }

