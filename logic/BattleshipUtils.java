package logic;

import java.util.Random;

/**
 * @author jbg
 * @since 11/16/13 5:07 PM
 */
public class BattleshipUtils
{
    private static Random random = new Random();
    public static int getRandomNextInt(int range) {
        if (range < 0)
        {
            return -1;
        } else
        {
            return random.nextInt(range);
        }
    }

    /**
     * Use Configuration.logger instead.
     */
    @Deprecated
    public static void print(String s) {
        Configuration.logger.debug(s);
    }

    /**
     * Use Configuration.logger instead.
     */
    @Deprecated
    public static void println(String s) {
        Configuration.logger.debug(s);
    }

    /**
     * Use Configuration.logger instead.
     */
    @Deprecated
    public static void print(Object s) {
        Configuration.logger.debug(s);
    }

    /**
     * Use Configuration.logger instead.
     */
    @Deprecated
    public static void println(Object s) {
        Configuration.logger.debug(s);
    }
}
