package beditor.view;

import beditor.model.*;
import beditor.controller.*;
import javax.swing.*;
import java.awt.*;

/**
* EditorPan : a class to dispay the editor
*
* @author Hugo PIGEON
* @version 1.0
*/

public class EditorPan extends JPanel
{
	private MapPan mapPan;
	private TilesetPan tilesetPan;
	
	private final Dimension MAPSCROLL_SIZE = new Dimension(512, 700);
	private final Dimension TILESETSCROLL_SIZE = new Dimension(512, 700);
	private final int SPLIT_DIVIDER_LOCATION = 512;
	
	/** Constructor to make a new map
	* @param mapDim the map's dimensions
	* @param tileDim a tile's dimensions
	* @param tilesetPath the path to the tileset file
	*/
	public EditorPan(Dimension mapDim, Dimension tileDim, String tilesetPath)
	{
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		
		this.mapPan = new MapPan(mapDim, tileDim, this);		
		this.tilesetPan = new TilesetPan(tilesetPath, tileDim, this);
		
		JScrollPane mapScroll = new JScrollPane(this.mapPan);
		mapScroll.setPreferredSize(MAPSCROLL_SIZE);
		JScrollPane tilesetScroll = new JScrollPane(this.tilesetPan);
		tilesetScroll.setPreferredSize(TILESETSCROLL_SIZE);
		
		JSplitPane editorSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mapScroll, tilesetScroll);
		editorSplit.setDividerLocation(SPLIT_DIVIDER_LOCATION);
		this.add(editorSplit, BorderLayout.CENTER);
	}
	
	/** Gives the selected tile in the tileset
	* @return the selected tile in the tileset
	*/
	public Sprite getSelectedTile()
	{
		return this.tilesetPan.getSelectedSprite();
	}
	
	/** Gives the MapPan related to this EditorPan
	* @return the MapPan related to this EditorPan
	*/
	public MapPan getMapPan()
	{
		return this.mapPan;
	}
	
	/** Gives the TilesetPan related to this EditorPan
	* @return the TilesetPan related to this EditorPan
	*/
	public TilesetPan getTilesetPan()
	{
		return this.tilesetPan;
	}
}