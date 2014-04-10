package model;

import java.awt.Dimension;

/**
* Classe Map : définit la carte à éditer
*
* @author Hugo PIGEON
* @version 1.0
*/

public class Map
{
	private Dimension dim;
	private int tiles[][];
	
	/** Constructeur permettant de définir une carte en précisant sa largeur et sa hauteur
	* @param dim les dimensions de la carte
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
	
	/** Permet d'obtenir le tile aux coordonnées précisées ou -1 si les coordonnées ne sont pas valides
	* @param x la coordonnée en x du tile à récupérer
	* @param y la coordonnée en y du tile à récupérer
	*/
	public int getTile(int x, int y)
	{
		int ret = -1;
		
		if(x >= 0 && x < this.dim.getWidth() && y >= 0 && y < this.dim.getHeight())
			ret = this.tiles[y][x];
			
		return ret;
	}
	
	/** Permet de modifier le tile aux coordonnées précisées si les coordonnées sont valides
	* @param x la coordonnée en x du tile à modifier
	* @param y la coordonnée en y du tile à modifier
	* @param tile le nouveau tile
	*/
	public void setTile(int x, int y, int tile)
	{
		if(x >= 0 && x < this.dim.getWidth() && y >= 0 && y < this.dim.getHeight())
			this.tiles[x][y] = tile;
	}
	
	/** Permet d'obtenir la largeur de la carte
	* @return la largeur de la carte
	*/
	public int getWidth()
	{
		return (int) this.dim.getWidth();
	}
	
	/** Permet d'obtenir la hauteur de la carte
	* @return la hauteur de la carte
	*/
	public int getHeight()
	{
		return (int) this.dim.getHeight();
	}
}
