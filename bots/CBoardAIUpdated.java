package bots;

import java.util.ArrayList;
import java.util.List;

import ships.Ship;
import logic.BSCoordinate;

/**
 * 
 * @author joelgreenberg26@gmail.com
 * @since 2012-08-11
 * 
 */
public class CBoardAIUpdated implements BSAI {

	private List<BSCoordinate> coordsToShoot, // next coords
			coordsMissed, // misses
			coordsSunk; // hits-sunk

	/**
	 * Lists of hit coordinates; each list represents a distinct ship.
	 * 
	 * Invariant: all non-sunk hit coordinates are in this structure exactly
	 * once; if multiple coordinates could belong to the same ship, they will be
	 * placed in a list together.
	 */
	private List<List<BSCoordinate>> coordsHit;

	@Override
	public void initialize() {
		coordsToShoot = new ArrayList<BSCoordinate>();
		coordsMissed = new ArrayList<BSCoordinate>();
		coordsSunk = new ArrayList<BSCoordinate>();
		coordsHit = new ArrayList<List<BSCoordinate>>();

	}

	@Override
	public BSCoordinate nextshot() {
		if(coordsHit.size() > 0){ //shipwrecks exist
			// shoot square that is between two others.
			// else shoot square colinear with others.
			for(List<BSCoordinate> shipwreck : coordsHit){
			}
			
		}
		else{
			
		}
		return null;
	}

	@Override
	public int remaining_shooting_coordinates() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Deprecated
	@Override
	public void placeships() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHit(BSCoordinate coord) {
		if(coordsHit.size() > 0){
			for(List<BSCoordinate> possibleShip : coordsHit){
				if(hitIsPartOfShip(possibleShip, coord)){
					possibleShip.add(coord);
					return;
				}
			}
		}
		List<BSCoordinate> newShipwreck = new ArrayList<BSCoordinate>();
		newShipwreck.add(coord);
		coordsHit.add(newShipwreck);
	}
	
	private boolean hitIsPartOfShip(List<BSCoordinate> shipwreck, BSCoordinate hit){
		return false;
	}

	@Override
	public void setSunk(BSCoordinate coord) {
		// TODO Auto-generated method stub

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
