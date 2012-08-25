/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bots;

import java.util.List;

import ships.Ship;

import logic.BSCoordinate;

/**
 *
 * @author joelgreenberg
 */
public interface BSAI {

	/**
	 * Pre-processing for the board. Used to initialize structures, random number generators, etc.
	 */
    public void initialize();
    
    /**
     * @return the BSCoordinate that the AI wishes to designate as the next shot
     */
    public BSCoordinate nextshot();
    public int remaining_shooting_coordinates();
    
    /**
     * Deprecated -- use placeships(List<Ship> ships) instead.
     */
    @Deprecated
    public void placeships();
    
    /**
     * Places designated ships on board.
     * 
     * @param ships
     */
    public void placeships(List<Ship> ships);
    
    /**
     * Broadcast to the AI that a coordinate has been hit.
     * 
     * @param coord
     */
    public void setHit(BSCoordinate coord);
    
    /*
     * need to decide which of these is cleanest.
     */
    public void setSunk(BSCoordinate coord);
    public void setSunk(List<BSCoordinate> coords);

}
