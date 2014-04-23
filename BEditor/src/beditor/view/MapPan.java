package beditor.view;

import beditor.model.*;
import beditor.controller.*;
import javax.swing.*;
import java.awt.*;

/**
* MapPan : a class to dispay the editor
*
* @author Hugo PIGEON
* @version 1.0
*/

public class MapPan extends JPanel
{
	private Sprite tiles[][];
	private Map map;
	private TilesetPan tilesetPan;
	private Dimension tileDim;
	
	/** Constructor to make a new map
	* @param mapDim the map's dimensions
	* @param tileDim a tile's dimensions
	* @param tilesetPath the path to the tileset file
	*/
	public MapPan(Dimension mapDim, Dimension tileDim, String tilesetPath)
	{
		this.tileDim = tileDim;
	
		this.setBackground(Color.white);
		this.setLayout(new GridLayout(0, 2));
		
		JPanel tilesPan = new JPanel();
		tilesPan.setLayout(new GridLayout((int) mapDim.getHeight(), (int) mapDim.getWidth(), 0, 0));
		tilesPan.setPreferredSize(new Dimension((int) (mapDim.getWidth() * tileDim.getWidth()), (int) (mapDim.getHeight() * tileDim.getHeight())));
		
		this.map = new Map(mapDim);
		
		this.tiles = new Sprite[(int) mapDim.getWidth()][(int) mapDim.getHeight()];
		
		for(int x = 0 ; x < this.map.getWidth() ; x++)
		{
			for(int y = 0 ; y < this.map.getHeight() ; y++)
			{
				int coordX = x * (int) tileDim.getWidth();
				int coordY = y * (int) tileDim.getHeight();
				
    			this.tiles[x][y] = new Sprite(this, new Coordinate(coordX, coordY), tileDim, true, true);
    			this.tiles[x][y].setBorder(BorderFactory.createLineBorder(Color.black));
    			tilesPan.add(this.tiles[x][y].getImage());
			}
		}
		
		this.tilesetPan = new TilesetPan(tilesetPath, tileDim, this);
		
		JScrollPane tilesScroll = new JScrollPane(tilesPan);
		tilesScroll.setPreferredSize(new Dimension(512, 700));
		JScrollPane tilesetScroll = new JScrollPane(this.tilesetPan);
		tilesetScroll.setPreferredSize(new Dimension(512, 700));
		this.add(tilesScroll);
		this.add(tilesetScroll);
		
		this.reloadMap();
	}
	
	/** Gives the map
	* @return the MapPan's map
	*/
	public Map getMap()
	{
		return this.map;
	}
	
	/** Gives the tile at the specified coordinates (in tiles)
	* @param c the tile's coordinates
	* @return the tile as a Sprite
	*/
	public Sprite getTile(Coordinate c)
	{
		return this.tiles[c.getX()][c.getY()];
	}
	
	/** Gives the number of tiles on X axis
	* @return the number of tiles on X axis
	*/
	public int getTilesNumberX()
	{
		return this.tiles.length;
	}
	
	/** Gives the number of tiles on Y axis
	* @return the number of tiles on Y axis
	*/
	public int getTilesNumberY()
	{
		int ret = -1;
		
		if(this.getTilesNumberX() >= 0)
			ret = this.tiles[0].length;
		
		return ret;
	}
	
	/** Gives the selected tile in the tileset
	* @return the selected tile in the tileset
	*/
	public Sprite getSelectedTile()
	{
		return this.tilesetPan.getSelectedSprite();
	}
	
	/** Gives the TilesetPan related to this MapPan
	* @return the TilesetPan related to this MapPan
	*/
	public TilesetPan getTilesetPan()
	{
		return this.tilesetPan;
	}
	
	/** Loads the map from the model
	*/
	public void reloadMap()
	{
		for(int x = 0 ; x < this.getTilesNumberX() ; x++)
		{
			for(int y = 0 ; y < this.getTilesNumberY() ; y++)
			{
				if(this.map.getTile(x, y) != -1)
					this.tiles[y][x].setImage(new ImageIcon(this.tilesetPan.getSpriteImage(this.map.getTile(x, y))));
				else
					this.tiles[y][x].setImage(null);
			}
		}
	}
}
