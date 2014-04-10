package controller;

import model.*;
import view.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
* Classe MouseEventsManager : traite les événements générés par la souris
*
* @author Hugo PIGEON
* @version 1.0
*/

public class MouseEventsManager extends MouseAdapter
{
	private Sprite sprite;
	private Map map;
	private boolean checkClicks;
	private boolean checkMoves;
	
	/** Constructeur pour lequel il faut préciser le sprite observé
	* @param sprite le sprite observé
	* @param map la carte à modifier
	* @param checkClicks mettre à true pour gérer les événements de clic
	* @param checkMoves mettre à true pour gérer les événements de mouvement
	*/
	public MouseEventsManager(Sprite sprite, Map map, boolean checkClicks, boolean checkMoves)
	{
		this.sprite = sprite;
		this.map = map;
		this.checkClicks = checkClicks;
		this.checkMoves = checkMoves;
	}

	/** Cette méthode est appelée lors d'un clic de la souris
	* @param e l'événement traité
	*/
	public void mouseClicked(MouseEvent e)
	{
		if(this.checkClicks)
		{
			if(this.sprite.getPanel() instanceof TilesetPan)
			{
				((TilesetPan) this.sprite.getPanel()).setSelectedSprite(this.sprite);
			}
			else if(this.sprite.getPanel() instanceof MapPan)
			{
				TilesetPan tp = ((MapPan) this.sprite.getPanel()).getTilesetPan();
			
				final int x = tp.getSelectedSpriteIndex().getX();
				final int y = tp.getSelectedSpriteIndex().getY();
				final int tileX = this.sprite.getPosition().getX() / this.sprite.getWidth();
				final int tileY = this.sprite.getPosition().getY() / this.sprite.getHeight();
			
				this.map.setTile(tileX, tileY, x + ((MapPan) this.sprite.getPanel()).getTilesetPan().getTilesetWidth() * y);
				((MapPan) this.sprite.getPanel()).reloadMap();
			}
			
			this.sprite.getPanel().revalidate();
		}
	}

	/** Cette méthode est appelée lorsque la souris entre sur le sprite
	* @param e l'événement traité
	*/
	public void mouseEntered(MouseEvent e)
	{
		if(this.checkMoves)
			this.sprite.getImage().setBorder(BorderFactory.createLineBorder(Color.white, 2));
	}
	
	/** Cette méthode est appelée lorsque la souris quitte le sprite
	* @param e l'événement traité
	*/
	public void mouseExited(MouseEvent e) 
	{
		if(this.checkMoves)
			this.sprite.getImage().setBorder(BorderFactory.createLineBorder(Color.black));
	}
}
