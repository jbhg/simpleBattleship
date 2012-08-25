/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package debug;

/**
 *
 * @author joelgreenberg
 */
public class Debug {

    public static final boolean DEBUG = true;

    public static void print(String s) {
        if (DEBUG) {
            System.out.print(s);
        }
    }

    public static void println(String s) {
        if (DEBUG) {
            System.out.println(s);
        }
    }

        public static void print(Object s) {
        if (DEBUG) {
            System.out.print(s);
        }
    }

    public static void println(Object s) {
        if (DEBUG) {
            System.out.println(s);
        }
    }
}
