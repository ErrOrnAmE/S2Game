package beditor.controller;

import beditor.model.*;
import beditor.view.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
* MouseEventsManager : a class which manages the events related to the mouse
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
	
	/** Constructor which needs the observed sprite, the map and options
	* @param sprite the observed sprite
	* @param map the map to change
	* @param checkClicks give true to allow mouse click events
	* @param checkMoves give true to allow mouse moves events
	*/
	public MouseEventsManager(Sprite sprite, Map map, boolean checkClicks, boolean checkMoves)
	{
		this.sprite = sprite;
		this.map = map;
		this.checkClicks = checkClicks;
		this.checkMoves = checkMoves;
	}

	/** Called when clicking on the sprite
	* @param e the generated event
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

	/** Called when the mouse enters the sprite
	* @param e the generated event
	*/
	public void mouseEntered(MouseEvent e)
	{
		if(this.checkMoves)
			this.sprite.getImage().setBorder(BorderFactory.createLineBorder(Color.white, 2));
	}
	
	/** Called when the mouse leaves the sprite
	* @param e the generated event
	*/
	public void mouseExited(MouseEvent e) 
	{
		if(this.checkMoves)
			this.sprite.getImage().setBorder(BorderFactory.createLineBorder(Color.black));
	}
}
