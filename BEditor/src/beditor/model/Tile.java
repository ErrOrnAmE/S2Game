package beditor.model;

import java.awt.Dimension;

/**
* Tile : a class to define a tile
*
* @author Hugo PIGEON
* @version 1.1
*/

public class Tile
{
	private boolean solid;
	private boolean breakable;
	
	/** Constructor which defines a new tile with its properties
	* @param solid true to make the tile solid
	* @param breakable true to make the tile breakable
	*/
	public Tile(boolean solid, boolean breakable)
	{
		this.solid = solid;
		this.breakable = breakable;
	}
	
	/** Is the tile solid ?
	* @return true if the tile is solid
	*/
	public boolean getSolid()
	{
		return this.solid;
	}
	
	/** Is the tile breakable ?
	* @return true if the tile is breakable
	*/
	public boolean getBreakable()
	{
		return this.breakable;
	}
	
	/** Makes the tile solid or not
	* @param solid true to make the tile solid
	*/
	public void setSolid(boolean solid)
	{
		this.solid = solid;
	}
	
	/** Makes the tile breakable or not
	* @param breakable true to make the tile breakable
	*/
	public void setBreakable(boolean breakable)
	{
		this.breakable = breakable;
	}
}
