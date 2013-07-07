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
public class CBoardAI  implements BSAI {

    private int shots;
    public static final int ORIENTATION_SAME = 4, ORIENTATION_X_NEIGHBORS = 5, ORIENTATION_Y_NEIGHBORS = 6, ORIENTATION_NONE = 8, ORIENTATION_Y_COLINEAR = 19, ORIENTATION_X_COLINEAR = 67;
    private Board board, oppboard;
    private ArrayList<BSCoordinate> shooting_coordinates, place_ship_coordinates, coordinates_shot, hit_list;

    public CBoardAI(Board myboard, Board oppboard) {
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
        place_ship_coordinates = new ArrayList<BSCoordinate>();
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
        Debug.println("\n\n" + shots + "nextshot() called. coordinates_shot = " + coordinates_shot.size() + ", shooting_coordinates = " + shooting_coordinates.size() + ", hit_list = " + hit_list.size() + ", place_ship_coordinates = " + place_ship_coordinates.size());
        BSCoordinate current;
        if (hit_list.size() == 0) {
            Debug.println("next shot coming from the AI...");
            current = shooting_coordinates.get(BSIO.getRandomInt(shooting_coordinates.size()));
            coordinates_shot.add(current);
            shooting_coordinates.remove(current);
            return current;
        } else {
            switch (hit_list.size()) {
                case 1: //only one ship has been hit...so we will pursue its neighbors
                    return nextShot_onehit();
                case 2: //There are 2 hit ships, most likely next to one another. We seek their linear neighbors.
                    return nextShot_twohits(hit_list.get(0), hit_list.get(1));
                default:
                    return nextShot_multiplehits(hit_list.get(0), hit_list.get(hit_list.size() - 1));
            } //ends switch statement
        }
    }

    private BSCoordinate nextShot_onehit() {
        Debug.println("Case 1 in the AI");
        BSCoordinate current;
        if (place_ship_coordinates.size() != 0) {
            current = place_ship_coordinates.get(BSIO.getRandomInt(place_ship_coordinates.size()));
            coordinates_shot.add(current);
            place_ship_coordinates.remove(current);
        } else { //resume shooting randomly...
            current = shooting_coordinates.get(BSIO.getRandomInt(shooting_coordinates.size()));
            coordinates_shot.add(current);
            shooting_coordinates.remove(current);
        }
        return current;
    }

    private BSCoordinate nextShot_twohits(BSCoordinate hit1, BSCoordinate hit2) {
        Debug.println("Case 2 in the AI");
        BSCoordinate current;
        if (orientation(hit1, hit2) == ORIENTATION_X_NEIGHBORS) {
            Debug.println("\tx neighbors");
            BSCoordinate left = new BSCoordinate(Math.max(hit1.x(), hit2.x()) + 1, hit1.y());
            BSCoordinate right = new BSCoordinate(Math.min(hit1.x(), hit2.x()) - 1, hit1.y());
            if (place_ship_coordinates.contains(left) && place_ship_coordinates.contains(right)) { //both right and left exist
                if (BSIO.getRandomInt(2) == 0) { //...so pick one randomly
                    current = left;
                    coordinates_shot.add(0, left);
                    place_ship_coordinates.remove(left);
                } else {
                    current = right;
                    coordinates_shot.add(right);
                    place_ship_coordinates.remove(right);
                }
            } else if (place_ship_coordinates.contains(left)) { //only a left neighbor
                current = left;
                coordinates_shot.add(0, left);
                place_ship_coordinates.remove(left);
            } else if (place_ship_coordinates.contains(right)) { //only a right neighbor
                current = right;
                coordinates_shot.add(right);
                place_ship_coordinates.remove(right);
            } else //an apparent dead-end...
            {
                dump();

                Debug.println("NULL 1: " + hit1 + hit2 + orientation(hit1, hit2));
                current = null;
            }
        } else if (orientation(hit1, hit2) == ORIENTATION_Y_NEIGHBORS) {
            Debug.println("\ty neighbors");

            BSCoordinate below = new BSCoordinate(hit1.x(), Math.max(hit1.y(), hit2.y()) + 1);
            BSCoordinate above = new BSCoordinate(hit1.x(), Math.min(hit1.y(), hit2.y()) - 1);

            if (place_ship_coordinates.contains(below) && place_ship_coordinates.contains(above)) { //both right and left exist
                if (BSIO.getRandomInt(2) == 0) { //...so pick one randomly
                    current = below;
                    coordinates_shot.add(below);
                    place_ship_coordinates.remove(below);
                } else {
                    current = above;
                    coordinates_shot.add(0, above);
                    place_ship_coordinates.remove(above);
                }
            } else if (place_ship_coordinates.contains(below)) { //only a left neighbor
                current = below;
                coordinates_shot.add(below);
                place_ship_coordinates.remove(below);
            } else if (place_ship_coordinates.contains(above)) { //only a right neighbor
                current = above;
                coordinates_shot.add(0, above);
                place_ship_coordinates.remove(above);
            } else //an apparent dead-end...
            {
                dump();

                Debug.println("NULL 2: " + hit1 + hit2 + orientation(hit1, hit2));

                current = null;
            }
        } else {
            Debug.println("\tnot neighbors: " + hit1 + " " + hit2);

            current = place_ship_coordinates.get(BSIO.getRandomInt(place_ship_coordinates.size()));
            coordinates_shot.add(current);
            place_ship_coordinates.remove(current);
        }
        return current;
    }

    private BSCoordinate nextShot_multiplehits(BSCoordinate hit1, BSCoordinate hit2) {
        Debug.println("Case MULTIPLE in the AI");
        BSCoordinate current;
        if (orientation(hit1, hit2) == ORIENTATION_X_COLINEAR || orientation(hit1, hit2) == ORIENTATION_X_NEIGHBORS) {
            Debug.println("\tx neighbors");
            BSCoordinate left = new BSCoordinate(Math.max(hit1.x(), hit2.x()) + 1, hit1.y());
            BSCoordinate right = new BSCoordinate(Math.min(hit1.x(), hit2.x()) - 1, hit1.y());
            if (place_ship_coordinates.contains(left) && place_ship_coordinates.contains(right)) { //both right and left exist
                if (BSIO.getRandomInt(2) == 0) { //...so pick one randomly
                    current = left;
                    coordinates_shot.add(left);
                    place_ship_coordinates.remove(left);
                } else {
                    current = right;
                    coordinates_shot.add(right);
                    place_ship_coordinates.remove(right);
                }
            } else if (place_ship_coordinates.contains(left)) { //only a left neighbor
                current = left;
                coordinates_shot.add(left);
                place_ship_coordinates.remove(left);
            } else if (place_ship_coordinates.contains(right)) { //only a right neighbor
                current = right;
                coordinates_shot.add(right);
                place_ship_coordinates.remove(right);
            } else //an apparent dead-end...
            {
                dump();

                Debug.println("NULL 3: " + hit1 + hit2 + orientation(hit1, hit2));
                current = null;
            }
        } else if (orientation(hit1, hit2) == ORIENTATION_Y_COLINEAR || orientation(hit1, hit2) == ORIENTATION_Y_NEIGHBORS) {
            Debug.println("\ty neighbors");

            BSCoordinate below = new BSCoordinate(hit1.x(), Math.max(hit1.y(), hit2.y()) + 1);
            BSCoordinate above = new BSCoordinate(hit1.x(), Math.min(hit1.y(), hit2.y()) - 1);

            if (place_ship_coordinates.contains(below) && place_ship_coordinates.contains(above)) { //both right and left exist
                if (BSIO.getRandomInt(2) == 0) { //...so pick one randomly
                    current = below;
                    coordinates_shot.add(below);
                    place_ship_coordinates.remove(below);
                } else {
                    current = above;
                    coordinates_shot.add(above);
                    place_ship_coordinates.remove(above);
                }
            } else if (place_ship_coordinates.contains(below)) { //only a left neighbor
                current = below;
                coordinates_shot.add(below);
                place_ship_coordinates.remove(below);
            } else if (place_ship_coordinates.contains(above)) { //only a right neighbor
                current = above;
                coordinates_shot.add(above);
                place_ship_coordinates.remove(above);
            } else //an apparent dead-end...
            {
                dump();
                Debug.println("NULL 4" + hit1 + hit2 + orientation(hit1, hit2));
                current = null;
            }
        } else {
            Debug.println("\tnot neighbors: " + hit1 + " " + hit2 + " " + orientation(hit1, hit2));
            current = place_ship_coordinates.get(BSIO.getRandomInt(place_ship_coordinates.size()));
            coordinates_shot.add(current);
            place_ship_coordinates.remove(current);
        }
        return current;
    }

    public void clearOutOfBounds() {
        ArrayList<BSCoordinate> temp = new ArrayList<BSCoordinate>();
        for (int i = 0; i < place_ship_coordinates.size(); i++) {
            if (place_ship_coordinates.get(i) == null || place_ship_coordinates.get(i).x() < 0 || place_ship_coordinates.get(i).x() >= board.x_dim || place_ship_coordinates.get(i).y() < 0 || place_ship_coordinates.get(i).y() >= board.y_dim) {
                temp.add(place_ship_coordinates.get(i));
            }
        }

        Debug.println("clearing out of bounds coordinates: " + temp.size());

        for (int i = 0; i < temp.size(); i++) {
            place_ship_coordinates.remove(temp.get(i));
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
        hit_list.add(c);
        place_ship_coordinates.add(new BSCoordinate(c.x(), c.y() - 1));
        place_ship_coordinates.add(new BSCoordinate(c.x(), c.y() + 1));
        place_ship_coordinates.add(new BSCoordinate(c.x() + 1, c.y()));
        place_ship_coordinates.add(new BSCoordinate(c.x() - 1, c.y()));
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
            place_ship_coordinates.clear();
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
            place_ship_coordinates.clear();
        }
    }

    private int orientation(BSCoordinate a, BSCoordinate b) {
        if (a.equals(b)) {
            return ORIENTATION_SAME;
        } else if (Math.abs(a.x() - b.x()) == 1 && Math.abs(a.y() - b.y()) == 0) {
            return ORIENTATION_X_NEIGHBORS;
        } else if (Math.abs(a.x() - b.x()) == 0 && Math.abs(a.y() - b.y()) == 1) {
            return ORIENTATION_Y_NEIGHBORS;
        } else if (Math.abs(a.x() - b.x()) >= 1 && Math.abs(a.y() - b.y()) == 0) {
            return ORIENTATION_X_COLINEAR;
        } else if (Math.abs(a.x() - b.x()) == 0 && Math.abs(a.y() - b.y()) >= 1) {
            return ORIENTATION_Y_COLINEAR;
        } else {
            return ORIENTATION_NONE;
        }
    }

    private void dump() {
        Debug.println("coordinates_shot: " + coordinates_shot);
        Debug.println("hit_list: " + hit_list);
        Debug.println("place_ship_coordinates: " + place_ship_coordinates);
        Debug.println("shooting_coordinates: " + shooting_coordinates);

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
