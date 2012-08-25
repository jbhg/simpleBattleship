/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.Dimension;

public class BSButton extends javax.swing.JButton {
    /**
	 * 
	 */
	public BSButton (String label) {
        super();
        setText(label);
        setSize(new Dimension(30,40));
    }

}
