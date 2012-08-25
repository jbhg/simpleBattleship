/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;

import logic.BSCoordinate;
import logic.BSSquare;

import debug.Debug;

import boards.Board;

/**
 *
 * @author joelgreenberg
 */
public class BoardPainter extends javax.swing.JComponent implements java.awt.event.MouseListener {

    private Board board;
    private GameGUI game;
    private int print_dimension;

    public int getPrint_dimension() {
        return print_dimension;
    }

    public BoardPainter(Board b, GameGUI g) {
        super();
        board = b;
        game = g;
        print_dimension = 20;
        setPreferredSize(new Dimension((board.x_dim + 1) * print_dimension, (board.y_dim + 1) * print_dimension));
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(this);


        //We may find it useful to click on this thing...
//        addMouseListener(g);
    }

    @Override
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Time to paint the information from the board.
        for (BSCoordinate coord : board.drawBoard().keySet()) {

            //Let's figure out what color to paint this guy. First, we test to see if:
            //  1. The board should be painted fairly; or if the square is NOT a live ship.
            if (board.displayUndercoverShips() || board.drawBoard().get(coord).status() != BSSquare.S_LIVE_SHIP) {
                g.setColor(squareColor(board.drawBoard().get(coord).status()));
            } else { //    2. In case we're supposed to conceal the locations of board_ships, we will do so.
                //if (board.drawBoard().get(coord).status() == BSSquare.S_LIVE_SHIP) {
                g.setColor(squareColor(BSSquare.S_UNKNOWN));
                //} else {g.setColor(squareColor(board.drawBoard().get(coord).status()));}
            }

            //Now let's paint it, based on its coordinates and the size we seek.
            g.fillRect((coord.x() + 1) * print_dimension, (coord.y() + 1) * print_dimension, print_dimension, print_dimension);
        }

        //Finally, we paint black lines over the board_squares to make a grid.
        g.setColor(Color.BLACK);
        //Paint a grid of board_squares.
        for (int i = 0; i < board.x_dim + 2; i++) {
            g.drawLine(i * print_dimension, print_dimension, i * print_dimension, (board.y_dim + 1) * print_dimension);
        }
        for (int i = 0; i < board.y_dim + 2; i++) {
            g.drawLine(print_dimension, i * print_dimension, (board.x_dim + 1) * print_dimension, i * print_dimension);
        }
        //Paint the coordinates in
        for (int i = 1; i < board.x_dim + 1; i++) {
            g.drawString(new Integer(i - 1).toString(),
                    (i * print_dimension) + 0 * (print_dimension * 1 / 4),
                    print_dimension * 3 / 4);
        }
        for (int i = 1; i < board.y_dim + 1; i++) {
            g.drawString(new Integer(i - 1).toString(),
                    print_dimension * 0,
                    ((i * print_dimension) + (print_dimension * 3 / 4)));

        }

    }

    public Board getBoard() {
        return board;
    }

    //
    public static Color squareColor(int i) {
        /*Given:
         *
        public static final int S_UNKNOWN = 1;
        public static final int B_MISS = 2;
        public static final int B_HIT = 3;
        public static final int S_LIVE_SHIP = 4;
         */
        switch (i) {
            case BSSquare.S_UNKNOWN:
                return Color.LIGHT_GRAY;
            case BSSquare.S_HIT_SHIP:
                return Color.MAGENTA;
            case BSSquare.S_MISS:
                return Color.BLUE;
            case BSSquare.S_LIVE_SHIP:
                return Color.DARK_GRAY;
            case BSSquare.S_HIT_AND_SUNK_SHIP:
                return Color.RED;
            default:
                Debug.print("ERROR! NUMBER GIVEN TO THE SWITCH (COLOR) METHOD IS " + i);
                return Color.YELLOW;
        }
    }

    //The following five methods are the implementations of MouseListener.
    @Override
	public void mouseClicked(MouseEvent event) {

        int x = ((event.getPoint().x) - 1) / print_dimension - 1;
        int y = ((event.getPoint().y) - 1) / print_dimension - 1;

        if (x >= 0 && y >= 0 && x < board.x_dim && y < board.y_dim) {
            game.printCoords(x, y);
            game.mouse_inputter(event, board.getBoardName());
        }
    }

    @Override
	public void mouseEntered(MouseEvent e) {
    }

    @Override
	public void mouseExited(MouseEvent e) {
    }

    @Override
	public void mousePressed(MouseEvent e) {
    }

    @Override
	public void mouseReleased(MouseEvent e) {
    }
}

