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
* @version 1.1
*/

public class MouseEventsManager extends MouseAdapter
{
	private Sprite sprite;
	private Map map;
	private boolean checkClicks;
	private boolean checkMoves;
	
	private final String NOT_AN_ITEM_MSG = "You can't place it in this layer, it's not an item";
	private final String NOT_AN_ITEM_TITLE = "Error";
	
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
				((TilesetPan) this.sprite.getPanel()).getEditorPan().getTilePropertiesPan().revalidate();
				((TilesetPan) this.sprite.getPanel()).getEditorPan().getItemPropertiesPan().revalidate();
			}
			else if(this.sprite.getPanel() instanceof MapPan)
			{
				TilesetPan tp = ((MapPan) this.sprite.getPanel()).getEditorPan().getTilesetPan();
			
				final int indexX = (int) tp.getSelectedSpriteIndex().getX();
				final int indexY = (int) tp.getSelectedSpriteIndex().getY();
				final int tileX = (int) this.sprite.getPosition().getX() / this.sprite.getWidth();
				final int tileY = (int) this.sprite.getPosition().getY() / this.sprite.getHeight();
			
				if(((MapPan) this.sprite.getPanel()).getType().equals(MapPanType.REGULAR))
				{
					if(e.getButton() == MouseEvent.BUTTON1)
						this.map.setTile(tileX, tileY, indexX + ((MapPan) this.sprite.getPanel()).getEditorPan().getTilesetPan().getTilesetWidth() * indexY);
					else
						this.map.setTile(tileX, tileY, -1);
						
					((MapPan) this.sprite.getPanel()).reloadMap();
				}
				else if(((MapPan) this.sprite.getPanel()).getType().equals(MapPanType.ITEM))
				{
					if(e.getButton() == MouseEvent.BUTTON1)
					{
						if(((MapPan) this.sprite.getPanel()).getEditorPan().getTilesetPan().getTileset().getItem(new Point(indexX, indexY)).getType() != ItemType.NOT_AN_ITEM)
						{
							this.map.setTile(tileX, tileY, indexX + ((MapPan) this.sprite.getPanel()).getEditorPan().getTilesetPan().getTilesetWidth() * indexY);
						}
						else
						{
							JOptionPane notAnItemMsg = new JOptionPane();
							notAnItemMsg.showMessageDialog(((MapPan) this.sprite.getPanel()).getEditorPan().getMainWindow(), NOT_AN_ITEM_MSG, NOT_AN_ITEM_TITLE, JOptionPane.ERROR_MESSAGE);
						}
					}
					else
						this.map.setTile(tileX, tileY, -1);
						
					((MapPan) this.sprite.getPanel()).reloadMap();
				}
				else if(((MapPan) this.sprite.getPanel()).getType().equals(MapPanType.START_FINISH))
				{
					if(e.getButton() == MouseEvent.BUTTON1)
					{
						for(int x = 0 ; x < this.map.getWidth() ; x++)
						{
							for(int y = 0 ; y < this.map.getHeight() ; y++)
							{
								if(this.map.getTile(x, y) == 0)
									this.map.setTile(x, y, -1);
							}
						}
						this.map.setTile(tileX, tileY, 0); // 0 is for starting point
					}
					else
					{
						for(int x = 0 ; x < this.map.getWidth() ; x++)
						{
							for(int y = 0 ; y < this.map.getHeight() ; y++)
							{
								if(this.map.getTile(x, y) == 1)
									this.map.setTile(x, y, -1);
							}
						}
						this.map.setTile(tileX, tileY, 1); // 1 is for finishing point
					}
					((MapPan) this.sprite.getPanel()).reloadMap();
				}
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
