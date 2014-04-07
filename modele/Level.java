
/**
* classe qui définit un niveau
*
* @author Thomas PICARD 
* @version 1.0
*/

public class Level 
{
	private coordinate coordinate;
	private Item key;
	private Item power;  


	public Character(Coordinate start)
	{
		this.coordinate = new coordinate(start.getX(),start.getY());   
	}
	
	public void recoverKey(Item theKey)
	{
		this.key = theKey;
	}


	public boolean haveKey()
	{
		return this.key != null; 
	}


	public void recoverPower(Item thePower)
	{
		this.power = thePower;
	}

	public boolean havePower()
	{
		return this.power !=null;
	}
	
	public Coordinate getCoordinate(int newCoord)
	{
		return this.coordinate;
	}
	
	
	public void setCoordinate ( Coordinate newCoordinate)
	{
		this.coordinate = newCoordinate;
	}
}
