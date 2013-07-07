/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bots;

import java.util.ArrayList;
import java.util.List;

import logic.BSCoordinate;
import logic.BSSquare;

import debug.BSIO;
import debug.Debug;

import boards.Board;

import ships.Battleship;
import ships.Carrier;
import ships.Cruiser;
import ships.Destroyer;
import ships.Orientation;
import ships.Ship;
import ships.SteelSubmarine;
import ships.Submarine;

/**
 *
 * @author joelgreenberg
 */
public class CBoardAIRecursive implements BSAI {

    private int shots;
    public static final int ORIENTATION_SAME = 4, ORIENTATION_X_NEIGHBORS = 5, ORIENTATION_Y_NEIGHBORS = 6, ORIENTATION_NONE = 8, ORIENTATION_Y_COLINEAR = 19, ORIENTATION_X_COLINEAR = 67;
    private Board board, oppboard;
    private ArrayList<BSCoordinate> shooting_coordinates, //the "pool" of coordinates.
            place_ship_coordinates_primary, //coordinates we suspect to have stuff we want
            place_ship_coordinates_secondary, //coordinates we suspect to have stuff we want
            coordinates_shot, //a history, hits and misses.
            hit_list;               //a current list of known, non-sunk squares.

    public CBoardAIRecursive(Board myboard, Board oppboard) {
        shots = 0;
        this.board = myboard;
        this.oppboard = oppboard;
        initialize();
    }

    /*
     * Places BSCoordinates in the necessary ArrayList.
     */
    @Override
	public void initialize() {
        shooting_coordinates = new ArrayList<BSCoordinate>();
        place_ship_coordinates_primary = new ArrayList<BSCoordinate>();
        place_ship_coordinates_secondary = new ArrayList<BSCoordinate>();
        coordinates_shot = new ArrayList<BSCoordinate>();
        hit_list = new ArrayList<BSCoordinate>();
        //First, we will initialize the "shooting_coordinates" list.
        for (int i = 0; i < board.x_dim; i++) {
            for (int j = 0; j < board.y_dim; j++) {
                if (i % 2 != j % 2) {
                    shooting_coordinates.add(new BSCoordinate(i, j));
                }
            }
        }
    }

    /*
     * returns a randomly-selected BSCoordinate.
     * then removes that coordinate from the list.
     *
     * @return random coordinate
     */
    @Override
	public BSCoordinate nextshot() {
        clearsunk();
        clearOutOfBounds();
        shots++;
        Debug.println("\n\n" + shots + "nextshot() called. coordinates_shot = " + coordinates_shot.size() + ", shooting_coordinates = " + shooting_coordinates.size() + ", hit_list = " + hit_list.size() + ", place_ship_coordinates = " + place_ship_coordinates_primary.size());
        BSCoordinate current;
        if (hit_list.size() == 0) {
            Debug.println("next shot coming from the AI's pool...");
            current = shooting_coordinates.get(BSIO.getRandomInt(shooting_coordinates.size()));
            coordinates_shot.add(current);
            shooting_coordinates.remove(current);
            place_ship_coordinates_primary.clear();
            place_ship_coordinates_secondary.clear();
            dump();
            return current;
        } else {
            if (place_ship_coordinates_primary.size() > 0) {
                Debug.println("from the primary array");
                dump();
                return place_ship_coordinates_primary.remove(0);
            } else if (place_ship_coordinates_secondary.size() > 0) {
                Debug.println("from the secondary array");
                dump();
                return place_ship_coordinates_secondary.remove(0);
            } else {
                Debug.println("next shot coming from the AI's pool...");
                current = shooting_coordinates.get(BSIO.getRandomInt(shooting_coordinates.size()));
                coordinates_shot.add(current);
                shooting_coordinates.remove(current);
                dump();
                return current;
            }
        }
    }

    private void clearOutOfBounds() {
        ArrayList<BSCoordinate> temp = new ArrayList<BSCoordinate>();
        for (int i = 0; i < place_ship_coordinates_primary.size(); i++) {
            if (place_ship_coordinates_primary.get(i) == null || place_ship_coordinates_primary.get(i).x() < 0 || place_ship_coordinates_primary.get(i).x() >= board.x_dim || place_ship_coordinates_primary.get(i).y() < 0 || place_ship_coordinates_primary.get(i).y() >= board.y_dim) {
                temp.add(place_ship_coordinates_primary.get(i));
            }
        }

        Debug.println("clearing out of bounds coordinates: " + temp.size());

        for (int i = 0; i < temp.size(); i++) {
            place_ship_coordinates_primary.remove(temp.get(i));
        }
    }

    @Override
	public int remaining_shooting_coordinates() {
        return shooting_coordinates.size();
    }

    @Override
	public void placeships() {
        boolean shipplaced;

        board.placeship(new SteelSubmarine(board, 0, 0, Orientation.getRandomOrientation()));

        shipplaced = false;
        shipplaced = board.placeship(new Carrier(board, BSIO.getRandomInt(board.x_dim), BSIO.getRandomInt(board.y_dim), Orientation.getRandomOrientation()));
        while (!shipplaced) {
            shipplaced = board.placeship(new Carrier(board, BSIO.getRandomInt(board.x_dim), BSIO.getRandomInt(board.y_dim), Orientation.getRandomOrientation()));
        }

        shipplaced = false;
        shipplaced = board.placeship(new Battleship(board, BSIO.getRandomInt(board.x_dim), BSIO.getRandomInt(board.y_dim), Orientation.getRandomOrientation()));
        while (!shipplaced) {
            shipplaced = board.placeship(new Battleship(board, BSIO.getRandomInt(board.x_dim), BSIO.getRandomInt(board.y_dim), Orientation.getRandomOrientation()));
        }

        shipplaced = false;
        shipplaced =
                board.placeship(new Submarine(board, BSIO.getRandomInt(board.x_dim), BSIO.getRandomInt(board.y_dim), Orientation.getRandomOrientation()));
        while (!shipplaced) {
            shipplaced = board.placeship(new Submarine(board, BSIO.getRandomInt(board.x_dim), BSIO.getRandomInt(board.y_dim), Orientation.getRandomOrientation()));

        }

        shipplaced = false;
        shipplaced =
                board.placeship(new Destroyer(board, BSIO.getRandomInt(board.x_dim), BSIO.getRandomInt(board.y_dim), Orientation.getRandomOrientation()));
        while (!shipplaced) {
            shipplaced = board.placeship(new Destroyer(board, BSIO.getRandomInt(board.x_dim), BSIO.getRandomInt(board.y_dim), Orientation.getRandomOrientation()));

        }

        shipplaced = false;
        shipplaced =
                board.placeship(new Cruiser(board, BSIO.getRandomInt(board.x_dim), BSIO.getRandomInt(board.y_dim), Orientation.getRandomOrientation()));
        while (!shipplaced) {
            shipplaced = board.placeship(new Cruiser(board, BSIO.getRandomInt(board.x_dim), BSIO.getRandomInt(board.y_dim), Orientation.getRandomOrientation()));
        }

        board.setExplosive(nextshot());

    }

    @Override
	public void setHit(BSCoordinate c) {
        hit_list.add(0, c);
        if (hit_list.size() > 1) {
            if (hit_list.get(0) == hit_list.get(1).north()) {
//                place_ship_coordinates_secondary.add(hit_list.get(0).east());
//                place_ship_coordinates_secondary.add(hit_list.get(0).west());
//                place_ship_coordinates_primary.add(0, hit_list.get(1).south());
                place_ship_coordinates_primary.add(0, hit_list.get(0).north());
            } else if (hit_list.get(0) == hit_list.get(1).south()) {
//                place_ship_coordinates_secondary.add(hit_list.get(0).east());
//                place_ship_coordinates_secondary.add(hit_list.get(0).west());
//                place_ship_coordinates_primary.add(0, hit_list.get(1).north());
                place_ship_coordinates_primary.add(0, hit_list.get(0).south());
            } else if (hit_list.get(0) == hit_list.get(1).east()) {
//                place_ship_coordinates_secondary.add(hit_list.get(0).north());
//                place_ship_coordinates_secondary.add(hit_list.get(0).south());
//                place_ship_coordinates_primary.add(0, hit_list.get(1).west());
                place_ship_coordinates_primary.add(0, hit_list.get(0).east());

            } else if (hit_list.get(0) == hit_list.get(1).west()) {
//                place_ship_coordinates_secondary.add(hit_list.get(0).north());
//                place_ship_coordinates_secondary.add(hit_list.get(0).south());
//                place_ship_coordinates_primary.add(0, hit_list.get(1).east());
                place_ship_coordinates_primary.add(0, hit_list.get(0).west());

            } else if (hit_list.get(0).distance(hit_list.get(1)) == 2) {
                if (hit_list.get(0).x() == hit_list.get(1).x()) {
                    place_ship_coordinates_primary.add(new BSCoordinate(hit_list.get(0).x(), (hit_list.get(0).y() + hit_list.get(0).y()) / 2));
                } else {
                    place_ship_coordinates_primary.add(new BSCoordinate((hit_list.get(0).x() + hit_list.get(0).x()) / 2, hit_list.get(0).x()));

                }
            } else {
                place_ship_coordinates_secondary.add(0, new BSCoordinate(c.x(), c.y() + 1));
                place_ship_coordinates_secondary.add(0, new BSCoordinate(c.x(), c.y() - 1));
                place_ship_coordinates_secondary.add(0, new BSCoordinate(c.x() - 1, c.y()));
                place_ship_coordinates_secondary.add(0, new BSCoordinate(c.x() + 1, c.y()));
            }
        } else {
            place_ship_coordinates_secondary.add(0, new BSCoordinate(c.x(), c.y() + 1));
            place_ship_coordinates_secondary.add(0, new BSCoordinate(c.x(), c.y() - 1));
            place_ship_coordinates_secondary.add(0, new BSCoordinate(c.x() - 1, c.y()));
            place_ship_coordinates_secondary.add(0, new BSCoordinate(c.x() + 1, c.y()));
        }
    }

    @Override
	public void setSunk(BSCoordinate c) {

        int sunkCounter = 0;

        for (int i = 0; i < hit_list.size(); i++) {
            if (oppboard.getBoardSquares().get(hit_list.get(i)).status() == BSSquare.Status.SUNK) {
                Debug.print("Removing: " + hit_list.get(i));
                hit_list.remove(i);
                sunkCounter++;
            }
        }

        hit_list.remove(c);
        sunkCounter++;


        Debug.println("sunkcounter: " + sunkCounter + ", hitlist: " + hit_list);

        if (hit_list.size() == 0) {
            place_ship_coordinates_primary.clear();
        }

    }

    public void clearsunk() {
        int sunkCounter = 0;

        for (int i = 0; i < hit_list.size(); i++) {
            if (oppboard.getBoardSquares().get(hit_list.get(i)).status() == BSSquare.Status.SUNK) {
                Debug.print("Removing: " + hit_list.get(i));
                hit_list.remove(i);
                sunkCounter++;
            }
        }

        Debug.println("sunkcounter: " + sunkCounter + ", hitlist: " + hit_list);

        if (hit_list.size() == 0) {
            place_ship_coordinates_primary.clear();
        }
    }

    /**
     * JBHG 8/11/2012: This should be made Static in the BSCoord class?
     * @param a
     * @param b
     * @return
     */


    private void dump() {
        Debug.println("-DUMP");
        Debug.println("coordinates_shot: " + coordinates_shot);
        Debug.println("hit_list: " + hit_list);
        Debug.println("place_ship_coordinates_primary: " + place_ship_coordinates_primary);
        Debug.println("place_ship_coordinates_secondary: " + place_ship_coordinates_secondary);
        Debug.println("shooting_coordinates: " + shooting_coordinates);
        Debug.println("----");
    }

	@Override
	public void placeships(List<Ship> ships) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSunk(List<BSCoordinate> coords) {
		// TODO Auto-generated method stub
		
	}
}
