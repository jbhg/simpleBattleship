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

import logic.BSSquare;

import debug.Debug;

import boards.Board;

/**
 *
 * @author joelgreenberg
 */
public abstract class Ship {

    public static final int UNKNOWN = 0, VERTICAL = 2, HORIZONTAL = 1;
    
    public enum ORIENTATION
    {
        VERTICAL, HORIZONTAL;

        public static ORIENTATION getRandomOrientation()
        {
            return VERTICAL;
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
                return getRandomOrientation();
            }
        }
    }
    
    protected int length, x_start, y_start, x_end, y_end;
    protected ArrayList<BSSquare> squares;
    protected String shipname;
    protected Board GPSboard;

    public Ship(Board board, int length, int x_start, int y_start, ORIENTATION orientation, String name) {
        this(board, length, x_start, y_start, orientation == ORIENTATION.VERTICAL ? x_start : x_start + length - 1,
                orientation == ORIENTATION.HORIZONTAL ? y_start : y_start + length - 1, BSSquare.S_LIVE_SHIP, name);
    }    
    
    @Deprecated
    public Ship(Board board, int length, int x_start, int y_start, int orientation, String name) {
        this(board, length, x_start, y_start, orientation == VERTICAL ? x_start : x_start + length - 1,
                orientation == HORIZONTAL ? y_start : y_start + length - 1, BSSquare.S_LIVE_SHIP, name);
    }

    public Ship(Board board, int length, int x_start, int y_start, int x_end, int y_end, String name) {
        this(board, length, x_start, y_start, x_end, y_end, BSSquare.S_LIVE_SHIP, name);
    }

    public Ship(Board board, int length, int x_st, int y_st, int x_en, int y_en, int initialstatus, String name) {

        //First, we establish the length of this ship.
        this.length = length;

        GPSboard = board;
        shipname = name;

        //Make sure the board_ships' coordinates aren't 'backward'. Then place them.
        if (x_st > x_en) {
            this.x_end = x_st;
            this.x_start = x_en;
        } else {
            this.x_start = x_st;
            this.x_end = x_en;
        }

        if (y_st > y_en) {
            this.y_end = y_st;
            this.y_start = y_en;
        } else {
            this.y_start = y_st;
            this.y_end = y_en;
        }

        Debug.println("SHIP: Coordinates in the constructor are: start: " + x_start + " " + y_start + "; end: " + x_end + " " + y_end);

        //Now we establish an array of board_squares over which the ship sits.
        setSquares(new ArrayList<BSSquare>());

        //Instance 1: the change is in the x-coordinate.
        if (x_end - x_start != 0 && y_end - y_start == 0) {
            //Debug.println("Variance is in x-direction.");
            for (int i = 0; i < length; i++) {
                //Debug.println("L1: We're about to encode the following into " + shipname + ": [" + (x_start + i) + "," + y_start + "]");
                getSquares().add(new BSSquare(x_start + i, y_start, initialstatus));
            }
        } //Instance 2: the change is in the y-coordinate.
        else if (x_end - x_start == 0 && y_end - y_start != 0) {
            //Debug.println("Variance is in y-direction.");
            for (int i = 0; i < length; i++) {
                //Debug.println("L2: We're about to encode the following into " + shipname + ": [" + x_start + "," + (y_start + i) + "]");
                getSquares().add(new BSSquare(x_start, y_start + i, initialstatus));
            }
        } else {
            setSquares(null);
        }
    }

    /**
     * drawShip is a data-oriented way of showing all of the information in the
     * ship, as it currently stands.
     *
     * This could be used by a program to render a graphical implementation of the ship, as well as a text-version of the same.
     * @return
     */
    public ArrayList<BSSquare> drawShip() {
        return getSquares();
    }
    
    @Deprecated
    public static int orientationInt(String s) {
        if (s.equals("Horizontal")) {
            return HORIZONTAL;
        } else if (s.equals("Vertical")) {
            return VERTICAL;
        } else {
            return UNKNOWN;
        }
    }

    /**
     * overlap will see if other board_ships overlap with this one.
     * 
     * @param s another ship
     * @return 
     */
    public int length() {
        return length;
    }

    public boolean overlap(Ship s) {
        for (int i = 0; i < s.drawShip().size(); i++) {
            for (int j = 0; j < getSquares().size(); j++) {
                //Debug.println("2 pairs? : [" + i + ";" + j + "]:" + s.drawShip().get(i).x() + " " + s.drawShip().get(i).y() + " and " + board_squares.get(j).x() + " " + board_squares.get(j).y());
                if (s.drawShip().get(i).x() == getSquares().get(j).x() && s.drawShip().get(i).y() == getSquares().get(j).y()) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getShipname() {
        return shipname;
    }

    public int get_X_end() {
        return x_end;
    }

    public int get_X_start() {
        return x_start;
    }

    public int get_Y_end() {
        return y_end;
    }

    public int get_Y_start() {
        return y_start;
    }

    @Override
    public String toString() {
        String s = shipname + " (length " + length + ") has the following makeup: \n";
        for (int i = 0; i < getSquares().size(); i++) {
            s = s + "[" + getSquares().get(i).x() + "," + getSquares().get(i).y() + ": " + getSquares().get(i).status() + "/" + BSSquare.getStringFromSquare(getSquares().get(i).status()) + "]\n";
        }
        return s;
    }

    public boolean isSunk() {
        Debug.print("isSunk? Hits remaining: " + hitsRemaining());
        for (BSSquare s : getSquares()) {
            if (s.status() != BSSquare.S_HIT_SHIP && s.status() != BSSquare.S_HIT_AND_SUNK_SHIP) {
                return false;
            }
        }
        return true;
    }

        public int shoot(int x, int y) {
            for (int i = 0; i < getSquares().size(); i++) {
                if (getSquares().get(i).x() == x && getSquares().get(i).y() == y) {
                    getSquares().get(i).setStatus(BSSquare.S_HIT_SHIP);

                }
            }
            GPSboard.updateBoardSquare(x, y, BSSquare.S_HIT_SHIP);
            GPSboard.increaseHitsByOne();

            if (isSunk()) {
                Debug.print("\n\n----\tAbout to print a ship deemed as sunk.\n" + this);
                //We need to make the ship PRINT as if it's sunk:
                for (int i = 0; i < drawShip().size(); i++) {
    //                    Debug.print("Coords of sunk ship:" + board_ships.get(hitshipindex).drawShip().get(i).x() + "," + board_ships.get(hitshipindex).drawShip().get(i).y() + ":" + board_ships.get(hitshipindex).drawShip().get(i).status());

    //                    board_ships.get(hitshipindex).board_squares.get(i).updateBoardSquare(BSSquare.S_HIT_AND_SUNK_SHIP);
                    drawShip().get(i).setStatus(BSSquare.S_HIT_AND_SUNK_SHIP);
                    GPSboard.updateBoardSquare(drawShip().get(i).x(), drawShip().get(i).y(), BSSquare.S_HIT_AND_SUNK_SHIP);
    //                    Debug.println("\tCoords of sunk ship:" + board_ships.get(hitshipindex).drawShip().get(i).x() + "," + board_ships.get(hitshipindex).drawShip().get(i).y() + ":" + board_ships.get(hitshipindex).drawShip().get(i).status());
    //                    Debug.println("\t\t" + getStringFromIntStatus(board_ships.get(hitshipindex).board_squares.get(i).status()));
                }

                return Board.B_HIT_SUNK;
            } else {
                return Board.B_HIT;
            }

        }

    public int hitsRemaining() {
        int hitsremaining = 0;
        for (BSSquare s : getSquares()) {
            if (s.status() == BSSquare.S_LIVE_SHIP || s.status() == BSSquare.S_UNKNOWN) {
                hitsremaining++;
            }
        }
        return hitsremaining;
    }

	/**
	 * @param squares the squares to set
	 */
	public void setSquares(ArrayList<BSSquare> squares) {
		this.squares = squares;
	}

	/**
	 * @return the squares
	 */
	public ArrayList<BSSquare> getSquares() {
		return squares;
	}
}
/*
 * @return one of the B_ - variables, based on a Board status.
 *
 * The idea behind this method is that a User Interface "shoots" the board,
 * and then the board "shoots" the ship, if a ship exists, and returns
 * whatever the ship would like.
 *
 * This allows board_ships to act however they wish upon being shot. For example,
 * a "DoubleHullBattleShip" would have to be hit in two different board_squares
 * before either is visible; or, a "SunkSubmarine" would be hit on one
 * turn, but would not be visible until the next turn (it would have to
 * communicate with the board more thoroughly), as the ship was sunk, but
 * rose to the top of the water upon its being shot.
 */
//    public int shoot(int x, int y) {
//
//        for (int i = 0; i < squares.size(); i++) {
//            if (squares.get(i).x() == x && squares.get(i).y() == y) {
//                squares.get(i).setStatus(BSSquare.S_HIT_SHIP);
//                if (isSunk()) {
//                    for (int k = 0; k < squares.size(); k++) {
//                        squares.get(i).setStatus(BSSquare.S_HIT_AND_SUNK_SHIP);
//                    }
//                    return Board.B_HIT_SUNK;
//                } else {
//                    return Board.B_HIT;
//                }
//            }
//        }
//        //If this is reached, the ship does not exist here.
//        return BSSquare.S_MISS;
//    }

