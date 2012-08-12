/*
 * This is intended to be a low-fidelity keyboard inputter for use in debugging.
 *
 */
package battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 *
 * @author joelgreenberg
 */
public class BSIO {

    public static String getString(String prompt) {
        /*
         * The following code for input is taken from
         * http://www.devdaily.com/java/edu/pj/pj010005/
         * without permission.
         */
        boolean tried = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = null;

        while (str == null)//this is my modification.
        {//this is my modification.

            //  read the username from the command-line; need to use try/catch with the
            //  readLine() method
            try {
                if (!tried) {
                    System.out.print(prompt + " ");
                    tried = true;
                } else {
                    System.out.print("Try again. " + prompt + " ");
                }
                str = br.readLine();
            } catch (IOException ioe) {
                System.out.println("IO error trying to read your name!");
                System.exit(1);
            }
        }//this is my modification.
            /* End of stolen code.
         */
        return str;
    }

    public static int getInt(String prompt) {
        /*
         * The following code for input is taken from
         * http://www.devdaily.com/java/edu/pj/pj010005/
         * without permission.
         */
        String s = null;
        ;
        Integer i = null;
        boolean b = false;

        do {
            s = getString(prompt);
            try {
                i = (Integer.parseInt(s));
                b = true;
            } catch (Exception e) {
            }

        } while (!b || s == null);
        return i;
    }

    public static int getRandomInt(int range) {
        if (range < 0) {
            return -1;
        } else {
            Random r = new Random();
            return r.nextInt(range);
        }
    }
}
