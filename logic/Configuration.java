package logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: jbg
 * Date: 11/16/13
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class Configuration
{
    public final static Logger logger = LogManager.getLogger(Configuration.class.getName());
    public enum Runmode { PRODUCTION, DEBUG };
    public final static Runmode runmode = Runmode.DEBUG;
}
