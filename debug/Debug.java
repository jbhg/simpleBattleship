/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package debug;

import logic.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author joelgreenberg
 */
public class Debug {

    public static final boolean DEBUG = (Configuration.Runmode.DEBUG.equals(Configuration.runmode));

    @Deprecated
    public static void print(String s) {
        Configuration.logger.debug(s);
    }

    public static void println(String s) {
        Configuration.logger.debug(s);
    }

    @Deprecated
    public static void print(Object s) {
        Configuration.logger.debug(s);
    }

    public static void println(Object s) {
        Configuration.logger.debug(s);
    }
}
