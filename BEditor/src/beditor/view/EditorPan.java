package beditor.view;

import beditor.model.*;
import beditor.controller.*;
import javax.swing.*;
import java.awt.*;

/**
* EditorPan : a class to dispay the editor
*
* @author Hugo PIGEON
* @version 1.1
*/

public class EditorPan extends JPanel
{
	private MainWindow mainWindow;
	private MapPan layer1, layer2, itemsLayer, startFinishLayer, currentlyEdited;
	private TilesetPan tilesetPan;
	private TilePropertiesPan tilePropertiesPan;
	private ItemPropertiesPan itemPropertiesPan;
	private JScrollPane mapScroll, tilesetScroll, propertiesScroll;
	private JSplitPane propertiesSplit, editorSplit;
	
	private final Dimension MAP_SCROLL_SIZE = new Dimension(512, 550);
	private final Dimension TILESET_SCROLL_SIZE = new Dimension(512, 500);
	private final Dimension PROPERTIES_SCROLL_SIZE = new Dimension(512, 218);
	private final int SPLIT_EDITOR_LOCATION = (int) MAP_SCROLL_SIZE.getWidth();
	private final int SPLIT_PROPERTIES_LOCATION = (int) TILESET_SCROLL_SIZE.getHeight();
	private final String GREEN_LABEL = "Starting point = green = left click";
	private final String RED_LABEL = "Finishing point = red = right click";
	
	/** Constructor to make a new map
	* @param mapDim the map's dimensions
	* @param tileDim a tile's dimensions
	* @param tilesetPath the path to the tileset file
	* @param collisionType the map's collision type
	* @param mainWindow the MainWindow containing the EditorPan
	*/
	public EditorPan(Dimension mapDim, Dimension tileDim, String tilesetPath, int collisionType, MainWindow mainWindow)
	{
		this.mainWindow = mainWindow;
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		
		this.layer1 = new MapPan(new Dimension(mapDim), tileDim, collisionType, MapPanType.REGULAR, this);
		this.layer2 = new MapPan(new Dimension(mapDim), tileDim, collisionType, MapPanType.REGULAR, this);
		this.itemsLayer = new MapPan(new Dimension(mapDim), tileDim, collisionType, MapPanType.ITEM, this);
		this.startFinishLayer = new MapPan(new Dimension(mapDim), tileDim, collisionType, MapPanType.START_FINISH, this);
		this.currentlyEdited = this.layer1;
		
		this.tilesetPan = new TilesetPan(tilesetPath, tileDim, this);
		this.tilePropertiesPan = new TilePropertiesPan(this);
		this.itemPropertiesPan = new ItemPropertiesPan(this);
		
		this.mapScroll = new JScrollPane(this.currentlyEdited);
		this.mapScroll.setPreferredSize(MAP_SCROLL_SIZE);
		this.tilesetScroll = new JScrollPane(this.tilesetPan);
		this.propertiesScroll = new JScrollPane(this.tilePropertiesPan);
		
		this.propertiesSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.tilesetScroll, this.propertiesScroll);
		this.propertiesSplit.setDividerLocation(SPLIT_PROPERTIES_LOCATION);
		this.propertiesSplit.setContinuousLayout(true);
		this.editorSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.mapScroll, this.propertiesSplit);
		this.editorSplit.setDividerLocation(SPLIT_EDITOR_LOCATION);
		this.editorSplit.setContinuousLayout(true);
		this.add(this.editorSplit, BorderLayout.CENTER);
	}
	
	/** Allows to edit the layer 1
	*/
	public void editLayer1()
	{
		this.currentlyEdited = this.layer1;
		this.mapScroll.setViewportView(this.currentlyEdited);
		this.propertiesScroll.setViewportView(this.tilePropertiesPan);
		this.currentlyEdited.reloadMap();
	}
	
	/** Allows to edit the layer 2
	*/
	public void editLayer2()
	{
		this.currentlyEdited = this.layer2;
		this.mapScroll.setViewportView(this.currentlyEdited);
		this.propertiesScroll.setViewportView(this.tilePropertiesPan);
		this.currentlyEdited.reloadMap();
	}
	
	/** Allows to edit the items layer
	*/
	public void editItemsLayer()
	{
		this.currentlyEdited = this.itemsLayer;
		this.mapScroll.setViewportView(this.currentlyEdited);
		this.propertiesScroll.setViewportView(this.itemPropertiesPan);
		this.currentlyEdited.reloadMap();
	}
	
	/** Allows to edit the layer related to the starting and finishing points
	*/
	public void editStartFinishLayer()
	{
		JPanel helpPan = new JPanel();
		helpPan.setLayout(new GridLayout(2, 1));
		JLabel green = new JLabel(GREEN_LABEL);
		JLabel red = new JLabel(RED_LABEL);
		helpPan.add(green);
		helpPan.add(red);
		
		this.currentlyEdited = this.startFinishLayer;
		this.mapScroll.setViewportView(this.currentlyEdited);
		this.propertiesScroll.setViewportView(helpPan);
		this.currentlyEdited.reloadMap();
	}
	
	/** Gives the selected tile in the tileset
	* @return the selected tile in the tileset
	*/
	public Sprite getSelectedTile()
	{
		return this.tilesetPan.getSelectedSprite();
	}
	
	/** Gives the currently edited MapPan related to this EditorPan
	* @return the currently edited MapPan related to this EditorPan
	*/
	public MapPan getCurrentlyEdited()
	{
		return this.currentlyEdited;
	}
	
	/** Gives the layer 1
	* @return the layer 1
	*/
	public MapPan getLayer1()
	{
		return this.layer1;
	}
	
	/** Gives the layer 2
	* @return the layer 2
	*/
	public MapPan getLayer2()
	{
		return this.layer2;
	}
	
	/** Gives the items layer
	* @return the items layer
	*/
	public MapPan getItemsLayer()
	{
		return this.itemsLayer;
	}
	
	/** Gives the layer related to the starting and finishing points
	* @return the layer related to the starting and finishing points
	*/
	public MapPan getStartFinishLayer()
	{
		return this.startFinishLayer;
	}
	
	/** Gives the TilesetPan related to this EditorPan
	* @return the TilesetPan related to this EditorPan
	*/
	public TilesetPan getTilesetPan()
	{
		return this.tilesetPan;
	}
	
	/** Gives the TilePropertiesPan related to this EditorPan
	* @return the TilePropertiesPan related to this EditorPan
	*/
	public TilePropertiesPan getTilePropertiesPan()
	{
		return this.tilePropertiesPan;
	}
	
	/** Gives the ItemPropertiesPan related to this EditorPan
	* @return the ItemPropertiesPan related to this EditorPan
	*/
	public ItemPropertiesPan getItemPropertiesPan()
	{
		return this.itemPropertiesPan;
	}
	
	/** Gives the MainWindow containing this EditorPan
	* @return the MainWindow containing this EditorPan
	*/
	public MainWindow getMainWindow()
	{
		return this.mainWindow;
	}
}
