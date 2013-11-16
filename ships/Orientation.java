package ships;

import debug.BSIO;

public enum Orientation
{
    VERTICAL, HORIZONTAL;

    public static Orientation getRandomOrientation()
    {
        return BSIO.getRandomInt(2) % 2 == 0 ? HORIZONTAL : VERTICAL;
    }

    /**
     * Returns the Orientation contained by the given string, if one exists.
     * This method should no longer be used.
     * @param sOrientation
     * @return
     */
    @Deprecated
    public static Orientation getOrientationFromString(String sOrientation)
    {
        if (sOrientation.equalsIgnoreCase("Horizontal"))
        {
            return HORIZONTAL;
        }
        else if (sOrientation.equalsIgnoreCase("Vertical"))
        {
            return VERTICAL;
        }
        else
        {
            throw new IllegalArgumentException("Could not create orientation from string " + sOrientation);
        }
    }
}