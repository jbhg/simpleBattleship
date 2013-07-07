package ships;

import debug.BSIO;

public enum Orientation
{
    VERTICAL, HORIZONTAL;

    public static Orientation getRandomOrientation()
    {
        return BSIO.getRandomInt(2) % 2 == 0 ? HORIZONTAL : VERTICAL;
    }

    public static Orientation getOrientationFromString(String sOrientation)
    {
        if (sOrientation.equals("Horizontal"))
        {
            return HORIZONTAL;
        } else if (sOrientation.equals("Vertical"))
        {
            return VERTICAL;
        } else
        {
            throw new IllegalArgumentException("Could not create orientation from string " + sOrientation);
        }
    }
}