/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 *
 * @author joelgreenberg
 */
public class Cruiser extends Ship {

    public Cruiser(Board board, int x_start, int y_start, int x_end, int y_end, int initialstatus) {
        super(board, 3, x_start, y_start, x_end, y_end, initialstatus, "Cruiser");
    }

    public Cruiser(Board board, int x_start, int y_start, int x_end, int y_end) {
        super(board, 3, x_start, y_start, x_end, y_end, "Cruiser");
    }

        public Cruiser(Board board, int x_start, int y_start, int orientation) {
        super(board, 3, x_start, y_start, orientation, "Cruiser");
    }
}
