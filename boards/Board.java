/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import logic.BSCoordinate;
import logic.BSSquare;
import ships.Orientation;
import ships.Ship;
import debug.Debug;

/**
 *
 * @author joelgreenberg
 */
public abstract class Board {

    //Constants--these constants govern the status of the individual
    //coordinates on the board.
    //The statuses of individual board_squares are stored in BSSquare.
    public Board(int x_length, int y_length, boolean displayAll, String name) {
        x_dim = x_length;
        y_dim = y_length;
        board_ships = new ArrayList<Ship>();
        displayUndercoverShips = displayAll || Debug.DEBUG;
        board_squares = new HashMap<BSCoordinate, BSSquare>();
        this.name = name;
        initialize();
    }
    public final int x_dim, y_dim;
    public static final int B_GAMELOST = 5,
            B_HIT = 6,
            B_MISS = 7,
            B_ALREADYHIT = 8,
            B_ERROR = 9,
            B_HIT_SUNK = 55;
    protected int turns;
    protected String name;
	protected ArrayList<Ship> board_ships;
	protected HashMap<BSCoordinate, BSSquare> board_squares;
    protected int misses, hits;
    protected boolean gameInProgress, displayUndercoverShips;
	protected BSCoordinate explosive;

    public static String getStringFromIntStatus(int i) {
        switch (i) {
            case B_GAMELOST:
                return "Game Lost.";
            case B_HIT:
                return "Hit";
            case B_MISS:
                return "Miss";
            case B_ALREADYHIT:
                return "Target already tried!";
            case B_ERROR:
                return "Error";
            case B_HIT_SUNK:
                return "Sunk ship. Game not over.";
            default:
                return "Unknown...";
        }
    }
    //Protected variables.

    public ArrayList<Ship> getShips() {
        return board_ships;
    }
    /*
     * This method sets the status of a square.
     */

    
    public boolean updateBoardSquare(BSCoordinate coord, BSSquare.Status status) {
        if (!getBoardSquares().containsKey(coord)) {
            return false;
        } else {
            getBoardSquares().remove(coord);
            getBoardSquares().put(coord,new BSSquare(coord, status));
            return true;
        }
    }
    
    @Deprecated
    public boolean updateBoardSquare(int x, int y, BSSquare.Status status) {
        return updateBoardSquare(new BSCoordinate(x,y), status);
    }

    public void initialize() {
        setExplosive(new BSCoordinate(-1, -1));
        hits = 0;
        misses = 0;
        turns = 0;
        gameInProgress = false;
        board_ships.clear();
        getBoardSquares().clear();
        for (int i = 0; i < x_dim; i++) {
            for (int j = 0; j < y_dim; j++) {
                getBoardSquares().put(new BSCoordinate(i, j), new BSSquare(i, j, BSSquare.Status.UNKNOWN));
            }
        }
    }

    public boolean setExplosive(int x, int y) {
        if (x >= x_dim || x < 0 || y >= y_dim || y < 0) {
            explosive = new BSCoordinate(-1, -1);
            return false;
        } else {
            explosive = new BSCoordinate(x, y);
            return true;
        }

    }
    
	public boolean setExplosive(BSCoordinate nextshot) {
		return setExplosive(nextshot.x(), nextshot.y());
	}

    /*
     * This method places a ship.
     * @return true if the ship was placed adequately; false otherwise.
     */
    public boolean placeship(Ship s) {

        Debug.print(s);
        //Let's break ths thing down.

        //First, we need to "kick out" any unfair board_ships. A ship doesn't do a good job of this, because (as seen in the AP Computer Science curriculum) infinite coordinates should be allowed.
        if (s.get_X_start() < 0 || s.get_X_end() >= x_dim || s.get_Y_start() < 0 || s.get_Y_end() >= y_dim || gameInProgress) {
            Debug.println("FALSE -- ship appears to be out of bounds:" + s.get_X_start() + "," + s.get_Y_start() + " to " + s.get_X_end() + "," + s.get_Y_end() + "...board coords: " + x_dim + "," + y_dim + "...gameinprogress = " + gameInProgress);
            return false;

        } else {
            //ship is legitimate, so we need to make sure it doesn't overlap with other board_ships.
            for (int i = 0; i < board_ships.size(); i++) {
                if (board_ships.get(i).overlap(s)) {
                    return false;
                }
            }

            //we are now convinced that this ship is okay to be placed. we will:

            //1. we'll add its information to the board, for the sake of painting.
            for (int m = 0; m < s.drawShip().size(); m++) {
                updateBoardSquare(s.drawShip().get(m).getCoordinate(), BSSquare.Status.KNOWN_SHIP);
            }

            //2. we also need to maintain information about the ship.
            board_ships.add(s);

            //F. We are now convinced that the ship can be placed.
            return true;
        }
    }

    public int shoot(BSCoordinate c) {
        Debug.print("Shoot method called. Coords = " + c);
        return shoot(c.x(), c.y());
    }

    /*
     * @return one of the B_ - variables, based on a Board status.
     *
     * The idea behind this method is that a User Interface "shoots" the board,
     * and then the board "shoots" the ship, if a ship exists, and returns
     * whatever the ship would like.
    
     */
    public int shoot(int x, int y) {
        //We need to "lock" the board. that is, no more board_ships should be able
        //to be placed.
        if (!gameInProgress) {
            beginGame();
        }
        if (!getBoardSquares().containsKey(new BSCoordinate(x, y))) {
            return B_ERROR;
        } //If there's a ship there.
        else if (getBoardSquares().get(new BSCoordinate(x, y)).status() == BSSquare.Status.KNOWN_SHIP) {

            int hitshipindex = -1;

            for (int i = 0; i < board_ships.size(); i++) {
                for (int j = 0; j < board_ships.get(i).getSquares().size(); j++) {
                    if (board_ships.get(i).getSquares().get(j).x() == x && board_ships.get(i).getSquares().get(j).y() == y) {
                        hitshipindex = i;
                    }
                }
            }
            //We hand off the ship's reaction to being shot to itself.
            //After all, it should know its own makeup.
            int result = board_ships.get(hitshipindex).shoot(x, y);
            if (result == Board.B_HIT_SUNK) {
                if (hasLost()) {
                    result = B_GAMELOST;
                } else {
                    result = B_HIT_SUNK;
                }
            }//see if we lost the game

            return result;

        } //If there's no ship.
        else if (getBoardSquares().get(new BSCoordinate(x, y)).status() == BSSquare.Status.UNKNOWN) {
            updateBoardSquare(new BSCoordinate(x, y), BSSquare.Status.MISS);
            misses++;
            return B_MISS;
        } //If, regardless of whether there's a ship or not, 
        else if (getBoardSquares().get(new BSCoordinate(x, y)).status() == BSSquare.Status.MISS || getBoardSquares().get(new BSCoordinate(x, y)).status() == BSSquare.Status.HIT || getBoardSquares().get(new BSCoordinate(x, y)).status() == BSSquare.Status.SUNK) {
            misses++;
            return B_ALREADYHIT;
        } else {
            System.out.println("There is an error in the shoot method, with the status of the coordinate at " + x + " " + y);
            return B_ERROR;
        }
    }

    public HashMap<BSCoordinate, BSSquare> drawBoard() {
        return getBoardSquares();
    }

    public boolean hasLost() {

        //Iterate over all the board_ships, and see if they've all had each coordinate be hit.
        for (int i = 0; i < board_ships.size(); i++) {
//            Debug.println("Testing if the board has lost. Ships:" + board_ships.size() + "; ship " + i + " sunk? " + isSunk(i));
            if (!isSunk(i)) {
                return false;
            }
        }
        Debug.println("--\tGAME OVER\t--");
        return true;
    }

    private boolean isSunk(int index) {
        for (int j = 0; j < board_ships.get(index).drawShip().size(); j++) {
            if (board_ships.get(index).drawShip().get(j).status() != BSSquare.Status.SUNK && board_ships.get(index).drawShip().get(j).status() != BSSquare.Status.HIT) {
                return false;
            }

        }
        return true;
    }

    public void beginGame() {
        gameInProgress = true;
    }

    public boolean gameInProgress() {
        return gameInProgress;
    }

    @Override
    public String toString() {
        String s = "This board is of dimensions " + x_dim + "x" + y_dim + ", with " + board_ships.size() + " ships. Hits: " + hits + "/Misses: " + misses + ".\n";
        for (int i = 0; i <
                board_ships.size(); i++) {
            s = s + "\nShip " + (i + 1) + ":\n------\n" + board_ships.get(i).toString();
        }

        return s;
    }

    public boolean sink(BSCoordinate coord) {
        Ship s = null;
        for (int i = 0; i < board_ships.size(); i++) {
            for (int j = 0; j < board_ships.get(i).drawShip().size(); j++) {
                if (board_ships.get(i).drawShip().get(j).getCoordinate().equals(coord)) {
                    s = board_ships.get(i);
                }
            }
        }
        if (s == null) {
            return false;
        } else {
            for (int i = 0; i < s.drawShip().size(); i++) {
                this.shoot(s.drawShip().get(i).getCoordinate());
            }
            return true;
        }
    }

	public void increaseHitsByOne() {
		hits++;
	}

	/**
	 * @return the mapping of coordinates to their entries.
	 */
	public HashMap<BSCoordinate, BSSquare> getBoardSquares() {
		return board_squares;
	}

	/**
	 * @return the explosive's coordinate object.
	 */
	public BSCoordinate getExplosive() {
		return explosive;
	}
	
    public boolean displayUndercoverShips() {
		return displayUndercoverShips;
	}
    
    public String getBoardName() {
		return name;
	}
    
    public List<Ship> getBoardShips() {
		return board_ships;
	}

    public static boolean isLegalBoardPlacement(Board board, BSCoordinate originalCoordinate, int length, Orientation shipOrientation)
    {
        if(shipOrientation == Orientation.HORIZONTAL)
        {
            return board.x_dim >= originalCoordinate.x() + length - 1;
        }
        else if(shipOrientation == Orientation.VERTICAL)
        {
            return board.y_dim >= originalCoordinate.y() + length - 1;
        }
        else
        {
            return false;
        }
    }
}
