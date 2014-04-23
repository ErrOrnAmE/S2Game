package beditor.model;

import java.awt.Dimension;

/**
* Map : a class to define the edited map
*
* @author Hugo PIGEON
* @version 1.0
*/

public class Map
{
	private Dimension dim;
	private int tiles[][];
	
	/** Constructor which makes a new map with the given dimensions
	* @param dim the map's dimensions
	*/
	public Map(Dimension dim)
	{
		this.dim = dim;
		this.tiles = new int[(int) dim.getWidth()][(int) dim.getHeight()];
		for(int x = 0 ; x < dim.getWidth() ; x++)
		{
			for(int y = 0 ; y < dim.getHeight() ; y++)
			{
				this.tiles[x][y] = 0;
			}
		}
	}
	
	/** Gives the tile at the given coordinates
	* @param x the X coordinate of the tile
	* @param y the Y coordinate of the tile
	*/
	public int getTile(int x, int y)
	{
		return this.tiles[y][x];
	}
	
	/** Changes the tile at the given coordinates
	* @param x the X coordinate of the tile to change
	* @param y the Y coordinate of the tile to change
	* @param tile the new tile
	*/
	public void setTile(int x, int y, int tile)
	{
		this.tiles[x][y] = tile;
	}
	
	/** Gives the map's width
	* @return the map's width
	*/
	public int getWidth()
	{
		return (int) this.dim.getWidth();
	}
	
	/** Gives the map's height
	* @return the map's height
	*/
	public int getHeight()
	{
		return (int) this.dim.getHeight();
	}
}
