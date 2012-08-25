package ships;

import boards.Board;


/**
 *
 * @author joelgreenberg
 */
public class Battleship extends Ship {

    public Battleship(Board board, int x_start, int y_start, int x_end, int y_end, int initialstatus) {
        super(board, 4, x_start, y_start, x_end, y_end, initialstatus, "Battleship");
    }

    public Battleship(Board board, int x_start, int y_start, int x_end, int y_end) {
        super(board, 4, x_start, y_start, x_end, y_end, "Battleship");
    }

    public Battleship(Board board, int x_start, int y_start, int orientation) {
        super(board, 4, x_start, y_start, orientation, "Battleship");
    }
}
