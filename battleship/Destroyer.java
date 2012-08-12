/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 *
 * @author joelgreenberg
 */
public class Destroyer extends Ship {

    public Destroyer(Board board, int x_start, int y_start, int x_end, int y_end, int initialstatus) {
        super(board, 2, x_start, y_start, x_end, y_end, initialstatus, "Destroyer");
    }

    public Destroyer(Board board, int x_start, int y_start, int x_end, int y_end) {
        super(board, 2, x_start, y_start, x_end, y_end, "Destroyer");
    }

    public Destroyer(Board board, int x_start, int y_start, int orientation) {
        super(board, 2, x_start, y_start, orientation, "Destroyer");
    }
}
