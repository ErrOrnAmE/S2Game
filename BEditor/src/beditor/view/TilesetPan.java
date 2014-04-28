package beditor.view;

import beditor.model.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
* TilesetPan : a class to display the tileset
*
* @author Hugo PIGEON
* @version 1.0
*/

public class TilesetPan extends JPanel
{
	private Sprite sprites[][];
	private EditorPan editorPan;
	private int tilesetWidth;
	private int tilesetHeight;
	private Sprite selectedSprite;
	private BufferedImage tileset;

	/** Constructor which makes a new tileset
	* @param tilesetPath the tileset's path
	* @param tileSize the dimensions of one tile in the tileset
	* @param editorPan the panel which contains the tileset
	*/
	public TilesetPan(String tilesetPath, Dimension tileSize, EditorPan editorPan)
	{
		if(tilesetPath != "")
		{
			this.editorPan = editorPan;
			File file = new File(tilesetPath);
			try
			{
				this.tileset = ImageIO.read(file);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			this.tilesetWidth = (int) (this.tileset.getWidth() / tileSize.getWidth());
			this.tilesetHeight = (int) (this.tileset.getHeight() / tileSize.getHeight());
			
			this.sprites = new Sprite[this.tilesetWidth][this.tilesetHeight];
			
			for(int y = 0 ; y < this.tilesetHeight ; y++)
			{
				for(int x = 0 ; x < this.tilesetWidth ; x++)
				{
					this.sprites[x][y] = new Sprite(this, this.tileset, new Point((int) (x * tileSize.getWidth()), (int) (y * tileSize.getHeight())), tileSize, true, false);
					this.add(this.sprites[x][y].getImage());
				}
			}
			this.setSelectedSprite(sprites[0][0]);
			
			this.setLayout(new GridLayout(tilesetWidth + 1, tilesetHeight + 1, 0, 0));
		}
	}
	
	/** Selects a sprite in the tileset's tiles
	* @param sprite the selected sprite
	*/
	public void setSelectedSprite(Sprite sprite)
	{
		this.selectedSprite = sprite;
	}
	
	/** Gives the selected sprite
	* @return the selected sprite
	*/
	public Sprite getSelectedSprite()
	{
		return this.selectedSprite;
	}
	
	/** Gives the selected sprite's coordinates (in tiles)
	* @return the selected sprite's coordinates (in tiles)
	*/
	public Point getSelectedSpriteIndex()
	{
		boolean trouve = false;
		Point coord = new Point(-1, -1);
	
		for(int x = 0 ; x < this.tilesetWidth ; x++)
		{
			for(int y = 0 ; y < this.tilesetHeight ; y++)
			{
				if(this.sprites[x][y] == this.selectedSprite)
				{
					trouve = true;
					coord.setLocation(x, y);
				}
			}
		}
		
		return coord;
	}
	
	/** Gives the sprite of the tile at the specified index
	* @param index the tile's index
	*/
	public BufferedImage getSpriteImage(int index)
	{
		BufferedImage ret = this.tileset.getSubimage(0, 0, this.sprites[0][0].getWidth(), this.sprites[0][0].getHeight());
		boolean found = false;
		
		for(int y = 0 ; y < this.tilesetHeight && !found ; y++)
		{
			for(int x = 0 ; x < this.tilesetWidth && !found ; x++)
			{
				if(x + this.tilesetWidth * y == index)
				{
					found = true;
					ret =  this.tileset.getSubimage(x * this.sprites[0][0].getWidth(), y * this.sprites[0][0].getHeight(), this.sprites[0][0].getWidth(), this.sprites[0][0].getHeight());
				}
			}
		}
		
		return ret;
	}	
	
	/** Gives the tileset's width (in tiles)
	* @return the tileset's width (in tiles)
	*/
	public int getTilesetWidth()
	{
		return this.tilesetWidth;
	}
	
	/** Gives the panel which contains the tileset
	* @return the panel which contains the tileset
	*/
	public EditorPan getEditorPan()
	{
		return this.editorPan;
	}
	
	/** Called to refresh the tileset
	*/
	public void revalidate()
	{
		super.revalidate();
		for(int y = 0 ; y < this.tilesetHeight ; y++)
		{
			for(int x = 0 ; x < this.tilesetWidth ; x++)
			{
				this.sprites[x][y].setBorder(BorderFactory.createLineBorder(Color.white));
			}
		}
		if(this.selectedSprite != null)
			this.selectedSprite.setBorder(BorderFactory.createLineBorder(Color.black));
	}
}