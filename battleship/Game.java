/*
 * This is the overlap between the user experience and the innner guts of the
 * battleship program. The Board has a thorough understanding of its role, as
 * does the Ship.
 *
 * Game will represent an experimental, text-based interaction between the
 * user and the board.
 */
package battleship;

/**
 *
 * @author joelgreenberg
 */
public class Game {

    //Establish global constants.
    public final int shipsperboard = 5;
    public final int board_x = 7;
    public final int board_y = 7;
    //Establish the "standard" variables for this game.
    private UBoard userboard;
    private CBoard compboard;

    public Game() {
        userboard = new UBoard(board_x, board_y);
        compboard = new CBoard(board_x, board_y);
    }

    public void start() {
        //We begin the game. The first thing that must be done is to place the board_ships.
        placeships();

        //Next, we must "lock" the board, to ensure that the gameplay is not interfered with.
        beginGame();

        int i = 1, j = 1;
        //Now people must shoot both ways.

        int ss = compboard.shoot(0, 0);
        System.out.println("USER Shot taken at 0,0: " + Board.getStringFromIntStatus(ss));
        ss = compboard.shoot(0, 1);

        System.out.println("USER Shot taken at 0,1: " + Board.getStringFromIntStatus(ss));
        ss = compboard.shoot(0, 2);

        System.out.println("USER Shot taken at 0,2: " + Board.getStringFromIntStatus(ss));

        while (!gameOver()) {

            //user shoots. in brackets to make code more readable.
            {
                // int x = BSIO.getInt("X-Coord to shoot");
                // int y = BSIO.getInt("Y-Coord to shoot");
                int x = BSIO.getRandomInt(board_x);
                int y = BSIO.getRandomInt(board_y);
                int shotstatus = compboard.shoot(x, y);
                while (shotstatus != Board.B_MISS && shotstatus != Board.B_HIT && shotstatus != Board.B_GAMELOST && shotstatus != Board.B_HIT_SUNK) {
//                    System.out.println("inner while loop executed.");
                    x = BSIO.getRandomInt(board_x);
                    y = BSIO.getRandomInt(board_y);
//                    x = BSIO.getInt("X-Coord to shoot");
//                    y = BSIO.getInt("Y-Coord to shoot");
                    shotstatus = compboard.shoot(x, y);
                }

//                System.out.println("USER Shot taken at " + x + "," + y + ": " + Board.getStringFromIntStatus(shotstatus));

            }
            //computer shoots. in brackets in case user just won the game.
            if (!gameOver()) {
                {
                    int x = BSIO.getRandomInt(board_x);
                    int y = BSIO.getRandomInt(board_y);
                    int shotstatus = userboard.shoot(x, y);
                    while (shotstatus != Board.B_MISS && shotstatus != Board.B_HIT && shotstatus != Board.B_GAMELOST && shotstatus != Board.B_HIT_SUNK) {
//                        System.out.println("inner while loop executed.");
                        x = BSIO.getRandomInt(board_x);
                        y = BSIO.getRandomInt(board_y);
                        shotstatus = userboard.shoot(x, y);
                    }

//                    System.out.println("COMP Shot taken at " + x + "," + y + ": " + Board.getStringFromIntStatus(shotstatus));

                }
            }
        }

        Debug.println("Winners: User-" + !userboard.hasLost() + ", Computer-" + !compboard.hasLost());

    }

    private void placeships() {

        //     System.out.println("--- TESTING HORIZONTAL/VERTICAL FUNCTIONS.");
        //    System.out.println(new Battleship(2,1,Ship.HORIZONTAL));
        //  System.out.println(new Battleship(2,1,2,4));
        //    System.out.println("--- TESTING HORIZONTAL/VERTICAL FUNCTIONS.");

        //Debug.println((new Battleship(0, 1, 0, 4)));


        //Debug.println((new Battleship(1, 1, 4, 1)));


//        System.out.println("--");
//        System.out.println(userboard.placeship(new Battleship(2, 1, 2, 4)));


        System.out.println(compboard.placeship(new Battleship(compboard, 2, 1, Ship.VERTICAL)));
//        System.out.println(compboard.placeship(new Carrier(15, 3, 15, Ship.VERTICAL)));
//        System.out.println(compboard.placeship(new Destroyer(20, 17, Ship.HORIZONTAL)));
//        System.out.println(compboard.placeship(new Cruiser(28, 3, Ship.HORIZONTAL)));
        System.out.println(compboard.placeship(new Submarine(compboard, 0, 0, Ship.HORIZONTAL)));
//
        System.out.println(userboard.placeship(new Battleship(compboard, 2, 1, Ship.VERTICAL)));
//        System.out.println(userboard.placeship(new Carrier(15, 3, 15, Ship.VERTICAL)));
//        System.out.println(userboard.placeship(new Destroyer(20, 17, Ship.HORIZONTAL)));
//        System.out.println(userboard.placeship(new Cruiser(28, 3, Ship.HORIZONTAL)));
        System.out.println(userboard.placeship(new Submarine(compboard, 0, 0, Ship.HORIZONTAL)));

//        System.out.println(compboard);

////
////        System.out.println(userboard.board_ships.size());
////
////        System.out.println(userboard.placeship(new Battleship(2, 5, 2, 29)));
////        //System.out.println(userboard.board_ships.size());
////
////        System.out.println("--");
////        //System.out.println(userboard);
////
////        System.out.println("Shooting .  .  . ");
////        System.out.println("Shooting status: " + userboard.shoot(2, 6));
////        System.out.println("Shooting status: " + userboard.shoot(3, 6));
    }

    private void beginGame() {
        userboard.beginGame();
        compboard.beginGame();
        Debug.println("Game begun.");
    }

    private boolean gameOver() {
        return userboard.hasLost() || compboard.hasLost();
    }

    public Board user() {
        return userboard;
    }

    public Board comp() {
        return compboard;
    }
}


