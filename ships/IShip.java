package ships;

import logic.BSCoordinate;

public interface IShip
{
    public String getName();
    
    public int getLength();
    
    public Ship.ORIENTATION getOrientation();
    
    public BSCoordinate getFirstCoordinate();

    public boolean isSunk();
}
