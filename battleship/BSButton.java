/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship;

/**
 *
 * @author joelgreenberg
 */
import javax.swing.JButton;
import java.awt.Dimension;

public class BSButton extends javax.swing.JButton {
    public BSButton (String label) {
        super();
        setText(label);
        setSize(new Dimension(30,40));
    }

}
