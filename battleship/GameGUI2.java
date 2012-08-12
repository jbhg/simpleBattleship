package battleship;

///*
// * This is the overlap between the user experience and the innner guts of the
// * battleship program. The Board has a thorough understanding of its role, as
// * does the Ship.
// *
// * Game will represent an experimental, text-based interaction between the
// * user and the board.
// */
//package comp106_assn3;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Container;
//import java.awt.FlowLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.util.ArrayList;
//import javax.swing.JComboBox;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//import javax.swing.border.LineBorder;
//
///**
// *
// * @author joelgreenberg
// */
//public class GameGUI2 extends JFrame implements ActionListener, MouseListener {
//
//    //Establish global constants.
//    public final int shipsperboard = 5;
//    public static final String[] shipnames = {"Battleship", "Cruiser", "Carrier", "Destroyer", "Submarine"};
//    public final int board_x = 10;
//    public final int board_y = 10;
//    //Establish the "standard" variables for this game.
//    private UBoard userboard;
//    private CBoard compboard;
//    private BoardPainter userpaint, comppaint;
//    //GUI - specific stuff
//    private JTextField xcoord, ycoord, statusfield;
//    private JPanel playingBoardPanel;
//    private JComboBox combo_ship, combo_orientation;
//    private Container content;
//    private JPanel controlpanel;
//    private BSButton button_shoot, button_placeship, button_quitgame;
//    private ArrayList<String> ships_to_be_placed;
//
//    public GameGUI2() {
//        userboard = new UBoard(board_x, board_y);
//        compboard = new CBoard(board_x, board_y);
//        userpaint = new BoardPainter(userboard, this);
//        comppaint = new BoardPainter(compboard, this);
//
//
//        //Initialize things that are used in the GUI, too.
//        content = getContentPane();
//        controlpanel = new JPanel();
//        button_shoot = new BSButton("Shoot");
//        button_placeship = new BSButton("Place Ship");
//        button_quitgame = new BSButton("Quit Game");
//        ships_to_be_placed = new ArrayList<String>();
//        for (int i = 0; i < shipnames.length; i++) {
//            ships_to_be_placed.add(shipnames[i]);
//        }
//
//    }
//
//    public JPanel getPlayingBoardPanel() {
//        return playingBoardPanel;
//    }
//
//    public void guiStart() {
//        setSize(900, 500);
//        setLocation(100, 100);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//        //Establish the content pane. Name it 'content'
//        //content.setSize(new Dimension(200,200)); This line did not fix the problem of size.
//        content.setLayout(new BorderLayout());
//        controlpanel.setLayout(new BorderLayout());
//
//
//        statusfield = new JTextField("Player's move to make.", 30);
//        statusfield.setEditable(false);
//        statusfield.setBorder(new LineBorder(Color.yellow));
//        content.add(statusfield, BorderLayout.NORTH);
//
//        playingBoardPanel = new JPanel();
//        playingBoardPanel.setLayout(new FlowLayout());
//
//        //Draw a canvas in the content pane.
//
//        //user.repaint();
//        //computer.repaint();
//        playingBoardPanel.add(userpaint, BorderLayout.WEST);
//        playingBoardPanel.add(comppaint, BorderLayout.EAST);
//        content.add(playingBoardPanel, BorderLayout.CENTER);
//
//        controlpanel.setSize(50, 275);
//        //      controlpanel.setBorder(new LineBorder(Color.orange, 5));
//        controlpanel.setLayout(new FlowLayout());
//
//        combo_ship = new JComboBox();
//        for (int i = 0; i < ships_to_be_placed.size(); i++) {
//            combo_ship.addItem(ships_to_be_placed.get(i));
//
//        }
////          combo_ship.removeItem("Battleship");
//        combo_ship.addActionListener(this);
//
//        combo_orientation = new JComboBox();
//        combo_orientation.addItem("Horizontal");
//        combo_orientation.addItem("Vertical");
//
//
//        xcoord = new JTextField("x", 3);
//        xcoord.setEditable(false);
//        xcoord.addActionListener(this);
//        ycoord = new JTextField("y", 3);
//        ycoord.setEditable(false);
//        ycoord.addActionListener(this);
//
//        controlpanel.add(xcoord);
//        controlpanel.add(ycoord);
//
//        button_shoot.addActionListener(this);
//        button_placeship.addActionListener(this);
//        button_quitgame.addActionListener(this);
//
//
//
//        controlpanel.add(xcoord);
//        controlpanel.add(ycoord);
//        controlpanel.add(combo_orientation);
//        controlpanel.add(combo_ship);
//        controlpanel.add(button_placeship);
//        controlpanel.add(button_shoot);
//        controlpanel.add(button_quitgame);
//
//        content.add(controlpanel, BorderLayout.SOUTH);
//
//        setVisible(true);
//    }
//
//    public void start() {
//        guiStart();
//        //We begin the game. The first thing that must be done is to place the board_ships.
//        placeships();
//
//        //Next, we must "lock" the board, to ensure that the gameplay is not interfered with.
//        beginGame();
//
//        while (!gameOver()) {
//
//            //user shoots. in brackets to make code more readable.
//            {
//                int x = BSIO.getInt("X-Coord to shoot");
//                int y = BSIO.getInt("Y-Coord to shoot");
////                int x = BSIO.getRandomInt(board_x);
////                int y = BSIO.getRandomInt(board_y);
//                int shotstatus = compboard.shoot(x, y);
//                while (shotstatus != Board.B_MISS && shotstatus != Board.B_HIT && shotstatus != Board.B_GAMELOST && shotstatus != Board.B_HIT_SUNK) {
//                    Debug.println("inner while loop executed:" + shotstatus);
////                    x = BSIO.getRandomInt(board_x);
////                    y = BSIO.getRandomInt(board_y);
//                    x = BSIO.getInt("X-Coord to shoot");
//                    y = BSIO.getInt("Y-Coord to shoot");
//
//                    shotstatus = compboard.shoot(x, y);
//                }
//                comppaint.repaint();
//            }
//            //computer shoots. in brackets in case user just won the game.
//            if (!gameOver()) {
//                {
//                    int x = BSIO.getRandomInt(board_x);
//                    int y = BSIO.getRandomInt(board_y);
//                    int shotstatus = userboard.shoot(x, y);
//                    while (shotstatus != Board.B_MISS && shotstatus != Board.B_HIT && shotstatus != Board.B_GAMELOST && shotstatus != Board.B_HIT_SUNK) {
////                        Debug.println("inner while loop executed.");
//                        x = BSIO.getRandomInt(board_x);
//                        y = BSIO.getRandomInt(board_y);
//                        shotstatus = userboard.shoot(x, y);
//                    }
//                }
//            }
//        }
//
//        comppaint.repaint();
//        userpaint.repaint();
//
//        print("Winners: User-" + !userboard.hasLost() + ", Computer-" + !compboard.hasLost());
//
//    }
//
//    private void placeships() {
//        xcoord.setEditable(true);
//        ycoord.setEditable(true);
//        button_shoot.setVisible(false);
//
//        compboard.placeship(new Battleship(compboard, 2, 1, Ship.VERTICAL));
//        compboard.placeship(new Carrier(compboard, 0, 3, Ship.VERTICAL));
//        compboard.placeship(new Submarine(compboard, 0, 0, Ship.HORIZONTAL));
//        compboard.placeship(new Destroyer(compboard, 8, 0, Ship.HORIZONTAL));
//        compboard.placeship(new Cruiser(compboard, 6, 6, Ship.HORIZONTAL));
//        comppaint.repaint();
//
//        boolean shipplaced;
//
//        shipplaced = userboard.placeship(new Battleship(userboard, BSIO.getInt("Battleship x: "), BSIO.getInt("Battleship y: "), BSIO.getInt("Verical (1) or Horizontal (2)?")));
//        while (!shipplaced) {
//            shipplaced = userboard.placeship(new Battleship(userboard, BSIO.getInt("Battleship x: "), BSIO.getInt("Battleship y: "), BSIO.getInt("Verical (1) or Horizontal (2)?")));
//        }
//        userpaint.repaint();
//        shipplaced = false;
//
//        shipplaced = userboard.placeship(new Cruiser(userboard, BSIO.getInt("Cruiser x: "), BSIO.getInt("Cruiser y: "), BSIO.getInt("Verical (1) or Horizontal (2)?")));
//        while (!shipplaced) {
//            shipplaced = userboard.placeship(new Cruiser(userboard, BSIO.getInt("Cruiser x: "), BSIO.getInt("Cruiser y: "), BSIO.getInt("Verical (1) or Horizontal (2)?")));
//        }
//        userpaint.repaint();
//        shipplaced = false;
//
//        shipplaced = userboard.placeship(new Submarine(userboard, BSIO.getInt("Submarine x: "), BSIO.getInt("Submarine y: "), BSIO.getInt("Verical (1) or Horizontal (2)?")));
//        while (!shipplaced) {
//            shipplaced = userboard.placeship(new Submarine(userboard, BSIO.getInt("Submarine x: "), BSIO.getInt("Submarine y: "), BSIO.getInt("Verical (1) or Horizontal (2)?")));
//        }
//        userpaint.repaint();
//        shipplaced = false;
//
//        shipplaced = userboard.placeship(new Destroyer(userboard, BSIO.getInt("Destroyer x: "), BSIO.getInt("Destroyer y: "), BSIO.getInt("Verical (1) or Horizontal (2)?")));
//        while (!shipplaced) {
//            shipplaced = userboard.placeship(new Destroyer(userboard, BSIO.getInt("Destroyer x: "), BSIO.getInt("Destroyer y: "), BSIO.getInt("Verical (1) or Horizontal (2)?")));
//        }
//        userpaint.repaint();
//        shipplaced = false;
//
//        shipplaced = userboard.placeship(new Carrier(userboard, BSIO.getInt("Carrier x: "), BSIO.getInt("Carrier y: "), BSIO.getInt("Verical (1) or Horizontal (2)?")));
//        while (!shipplaced) {
//            shipplaced = userboard.placeship(new Carrier(userboard, BSIO.getInt("Carrier x: "), BSIO.getInt("Carrier y: "), BSIO.getInt("Verical (1) or Horizontal (2)?")));
//        }
//        userpaint.repaint();
//
//
//    }
//
//    private void beginGame() {
//        xcoord.setEditable(false);
//        ycoord.setEditable(false);
//        combo_ship.setVisible(false);
//        combo_orientation.setVisible(false);
//        button_placeship.setVisible(false);
//
//        button_shoot.setVisible(true);
//
//        setVisible(true);
//
//        userboard.beginGame();
//        compboard.beginGame();
//        Debug.println("Game begun.");
//    }
//
//    private boolean gameOver() {
//        return userboard.hasLost() || compboard.hasLost();
//    }
//
//    public Board user() {
//        return userboard;
//    }
//
//    public Board comp() {
//        return compboard;
//    }
//
//    public void actionPerformed(ActionEvent e) {
////        System.out.println("\t"+e);
//
//        if (e.getActionCommand().equals("Quit Game")) {
//            System.exit(0);
//        } else if (e.getActionCommand().equals("Decrease Size")) {
//            // userboard.decreaseBoard();
//            // com.decreaseBoard();
//            print("Decreased the size of the board. Happy battleshipping!");
//        } else if (e.getActionCommand().equals("Increase Size")) {
//            //user.increaseBoard();
////            computer.increaseBoard();
//            print("Increased the size of the board. Happy battleshipping!");
//        } else if (e.getActionCommand().equals("Place Ship")) {
//            print("Attempting to place a ship of type " + combo_ship.getSelectedItem() + " at coordinates " + xcoord.getText() + "," + ycoord.getText() + ", " + (String) combo_orientation.getSelectedItem());
//        }
//
//    }
//
//    public void print(String s) {
//        Debug.println(s);
//        statusfield.setText(s);
//    }
//
//    public void printCoords(int x, int y) {
//        xcoord.setText(new Integer(x).toString());
//        ycoord.setText(new Integer(y).toString());
//
//    }
//
//    public void printCoords(String x, String y) {
//        xcoord.setText(x);
//        ycoord.setText(y);
//
//    }
//
//    public void mousePressed(MouseEvent e) {
//        Debug.println("-----\nAttempting to print coordinates from a board: " + e.getX() + "," + e.getY());
//        Debug.println("Computer: \t");
//        comppaint.mouseClicked(e);
//        Debug.println("User: \t");
//        userpaint.mouseClicked(e);
//        Debug.println("-----");
//    }
//
//    public void mouseEntered(MouseEvent e) {
//    }
//
//    public void mouseExited(MouseEvent e) {
//    }
//
//    public void mouseClicked(MouseEvent e) {
//    }
//
//    public void mouseReleased(MouseEvent e) {
//    }
//}
//
//
