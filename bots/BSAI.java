/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bots;

import battleship.BSCoordinate;

/**
 *
 * @author joelgreenberg
 */
public interface BSAI {

    public void initialize();
    public BSCoordinate nextshot();
    public int remaining_shooting_coordinates();
    public void placeships();
    public void setHit(BSCoordinate coord);
    public void setSunk(BSCoordinate coord);

}
