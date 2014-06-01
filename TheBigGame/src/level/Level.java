package level;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.*;

import player.Player;
import core.*;
import item.*;
import math.*;
import gui.*;

/**
* this class define a game level
*
*@author B Team  
*@version 1.0
*/

public class Level  implements KeyListener {
	
	

	private Tile[][] tiles;
	private int mapWidth;
	private int mapHeight;
	
	private Image background;
	
	private int offsetX;
	private int offsetY;
	
	//private Item[] items;
	
	private GameControleur controleur;

	public int width, height;
    //attributs map
    private String [] file; 
    private int nbTilesWidthWorld, nbTilesHeightWorld; 
    private int nbTilesX, nbTilesY; 
    private int widthTile, heightTile;
    private int nbItem; 
    private String collisionType; 
    private Coordinate start;
    private Coordinate finish; 
    private String [][] props;
    private int [][] map1Int; 
    private Tile[][] map1Tile; 
    private int [][] map2Int; 
    private Tile [][] map2Tile; 
    private String [][] itemInfo; 
    private Item [][] mapItem; 
	private BufferedImage img;
	private double timeElapsed;
	public static boolean pause;

	private String nomMap;
	private String nextMap;
	private BufferedImage imgKey;
	private BufferedImage imgLollipop;
	
	private Player player;
	
	/** Constructor of the level class
	*/
	public Level() {
		fromFile("/map/1.map");
		init(); 
	}
	
	/** Constructor of the level class
	@param map : the name of the file
	*/
	public Level(String map, String nextMap, GameControleur controleur) {
		nomMap = map;
		this.nextMap = nextMap;
		fromFile("/map/" + map);
		init();
		timeElapsed = 0;
		pause = false;
		this.controleur = controleur;
		imgKey = GameView.loadImage("images/key.png");
		imgLollipop = GameView.loadImage("images/lollipop.png");
	}
	
	public void retry() {
		fromFile("/map/"+nomMap);
		init();
		timeElapsed = 0;
		pause = false;
	}

	/** Method which take the name of the level and initialise the player
	*@param path : the name of level to load the map
	*/ 	
	public void fromFile(String path) {
        /********* CHARGE LA MAP ICI ***********/

		file = new String[400];
		loadMap(path);
		parseFile();	
		
		
		int collision = (collisionType == "normal") ? 1 : 2;
		player = new Player(start,finish,GameView.loadImage("images/bomb.png"),collision);
    }

/** Method which read the map file et take the level information 
*@param path : the name of the level to load map 
*/ 
	private void loadMap(String path)
    {
		int i = 0;  
		
		
		//lecture du fichier texte	
		try{
	         
	        //
	        FileInputStream fstream = new FileInputStream(System.getProperty("user.dir") + path);
	         
	         
	        // Get the object of DataInputStream
	        DataInputStream in = new DataInputStream(fstream);
	        BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        String strLine;
	         
	         
	        //Read File Line By Line
	        while ((strLine = br.readLine()) != null)   {
	            // Print the content on the console
	        	file[i] = strLine; 
				i++;
	        }
	         
	        //Close the input stream
	        in.close();
	         
        } catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

	/** Method which separate the information in attributes and separate the map in board
	*/
	private void parseFile() 
    {
		nbTilesWidthWorld = Integer.parseInt(file[2]); 
		nbTilesHeightWorld = Integer.parseInt(file[4]);
		nbTilesY = Integer.parseInt(file[6]);
		nbTilesX = Integer.parseInt(file[8]);
		widthTile = Integer.parseInt(file[10]);
		heightTile = Integer.parseInt(file[12]);   
		nbItem = Integer.parseInt(file[14]);
		collisionType = file[16];
		img = GameView.loadImage("images/"+ file[22]);
		
		String [] coordinate = file[18].split(" "); 
		start = new Coordinate (Integer.parseInt(coordinate[0])*widthTile, Integer.parseInt(coordinate[1])*heightTile);
		coordinate = file[20].split(" "); 
		finish = new Coordinate (Integer.parseInt(coordinate[0])*widthTile, Integer.parseInt(coordinate[1])*heightTile);
		
		props = new String [nbTilesY*nbTilesX][4];
		
		for(int i=24; i<(nbTilesY*nbTilesX)+24; i++)
		{
			String [] prop = file[i].split("[\\s\\s\\s]");
			for(int j=0; j<4; j++)
			{
				props[i-24][j] = prop[j]; 
			}
		}
		
		map1Int = new int [nbTilesWidthWorld][nbTilesHeightWorld];
		map2Int = new int [nbTilesWidthWorld][nbTilesHeightWorld];
		
		for(int i=25+(nbTilesY*nbTilesX); i<25+(nbTilesY*nbTilesX)+nbTilesHeightWorld; i++) 
		{


			String [] tile = file[i].split("\\s"); 

			

			for (int q=0; q<tile.length; q++)
			{	
					if(tile[q].equals(""))
					{
						map1Int[q][i-(25+(nbTilesY*nbTilesX))] = -1;
					}
					else 
					{
					//map1Int[q][i-(27+(nbTilesY*nbTilesX))] = 0;
					map1Int[q][i-(25+(nbTilesY*nbTilesX))] = Integer.parseInt(tile[q]); 
					}
			}

			if(tile.length < nbTilesWidthWorld) 
			{
				for(int x=tile.length; x<nbTilesWidthWorld; x++) 
				{
					map1Int[x][i-(25+(nbTilesY*nbTilesX))] = -1; 
				}
			}
		}
		
		for(int i=26+((nbTilesY*nbTilesX)+nbTilesHeightWorld); i<26+((nbTilesY*nbTilesX)+nbTilesHeightWorld*2); i++) 
		{
			String [] tile = file[i].split("\\s"); 

			for (int q=0; q<tile.length; q++)
			{	
					if(tile[q].equals(""))
					{
						map2Int[q][i-(26+(nbTilesY*nbTilesX)+nbTilesHeightWorld)] = -1;
					}
					else 
					{
					map2Int[q][i-(26+(nbTilesY*nbTilesX)+nbTilesHeightWorld)] = Integer.parseInt(tile[q]); 
					}
			}

			if(tile.length < nbTilesWidthWorld) 
			{
				for(int x=tile.length; x<nbTilesWidthWorld; x++) 
				{
					map2Int[x][i-(26+(nbTilesY*nbTilesX)+nbTilesHeightWorld)] = -1; 
				}
			}

		}
		
		itemInfo = new String[nbItem][4]; 
		


		for(int counter=27+((nbTilesY*nbTilesX)+(nbTilesHeightWorld*2)); counter < 27+((nbTilesY*nbTilesX)+(nbTilesHeightWorld*2) + nbItem); counter++)
		{
			//System.out.println(file[counter] + " " ) ; 
			String [] info = file[counter].split("[\\s]"); 
			for(int o=0; o <= 3; o++)
			{
				itemInfo[counter-(27+((nbTilesY*nbTilesX)+(nbTilesHeightWorld*2)))][o] = info[o]; 
			}

			
		}

		/*for(int x = 0; x <nbItem ;x++) 
		{
			System.out.println(itemInfo[x][0] + "+" + itemInfo[x][1] + "+" + itemInfo[x][2]); 
		}*/
		
    }

	/** Method which initialise the tile and the item in the map 
	*/ 
    public void init() {
        /********* INITIALISE DES TRUCS ICI ***********/
        //System.out.println(nbTilesWidthWorld + " " + nbTilesHeightWorld);
    	map1Tile = new Tile[nbTilesWidthWorld][nbTilesHeightWorld]; 
    	map2Tile = new Tile[nbTilesWidthWorld][nbTilesHeightWorld];

		mapWidth = nbTilesWidthWorld*widthTile;
		mapHeight = nbTilesHeightWorld*heightTile;

		//System.out.println("CHECKPOINT 1");
		for(int i=0; i<nbTilesWidthWorld; i++)
		{
			for(int j=0; j<nbTilesHeightWorld; j++) 
			{
				//System.out.println(i + " " + j);
				boolean breakable = false; 
				boolean solid = false; 
				if(map1Int[i][j] >= 0)
				{
					if(props[map1Int[i][j]][2].equals("solid"))
					{
						solid = true; 
					}

					if(props[map1Int[i][j]][3].equals("breakable"))
					{
						breakable = true; 
					}				
					if(map1Int[i][j] >= 0) 
					{				
					map1Tile[i][j] = new Tile (i*widthTile, j*heightTile, img,map1Int[i][j], widthTile,heightTile,solid,breakable); 
					} 
				}
			} 
		}
		//System.out.println("CHECKPOINT 2");
		for(int i=0; i<nbTilesWidthWorld; i++)
		{
			for(int j=0; j<nbTilesHeightWorld; j++) 
			{
				boolean breakable = false; 
				boolean solid = false; 
				if(map2Int[i][j] >= 0 )
				{
					if(props[map2Int[i][j]][2].equals("solid"))
					{
						solid = true; 
					}

					if(props[map2Int[i][j]][3].equals("breakable"))
					{
						breakable = true; 
					}
					map2Tile[i][j] = new Tile (i*widthTile,j*heightTile,img,map2Int[i][j],widthTile,heightTile,solid,breakable); 
				}
			} 
				
			
			
		}
		
		mapItem = new Item [nbTilesWidthWorld][nbTilesHeightWorld];

		for(int j=0; j< nbItem; j++)
		{
			if(itemInfo[j][0].equals("door"))
			{
				mapItem[Integer.parseInt(itemInfo[j][1])][Integer.parseInt(itemInfo[j][2])] = 
											new Door(Integer.parseInt(itemInfo[j][1]),Integer.parseInt(itemInfo[j][2]),Integer.parseInt(itemInfo[j][3]),img,widthTile, heightTile);
			}
			if(itemInfo[j][0].equals("lollipop")) 
			{
				mapItem[Integer.parseInt(itemInfo[j][1])][Integer.parseInt(itemInfo[j][2])] = 
											new Lollipop(Integer.parseInt(itemInfo[j][1]),Integer.parseInt(itemInfo[j][2]),Integer.parseInt(itemInfo[j][3]),img,widthTile, heightTile);
			}
			if(itemInfo[j][0].equals("goldenkey")) 
			{
				mapItem[Integer.parseInt(itemInfo[j][1])][Integer.parseInt(itemInfo[j][2])] = 
											new GoldenKey(Integer.parseInt(itemInfo[j][1]),Integer.parseInt(itemInfo[j][2]),Integer.parseInt(itemInfo[j][3]),img,widthTile, heightTile);
			}
		}


    }

/** Method which drow the map 
*@param g 
*@param screenWidth which is the screen width
*@param screenHeight which is the screen height 
*/ 
	public void draw(Graphics2D g, int screenWidth, int screenHeight) {
		
		width = screenWidth;
		height = screenHeight;
		
		int mapPixelWidth = GameView.tilesToPixels(getWidth(), widthTile);
		int mapPixelHeight = GameView.tilesToPixels(getHeight(), heightTile);

		// get the scrolling position of the map based on the player

		offsetX = (int)player.getCoord().getX() - screenWidth/2;
		offsetX = (offsetX < 0) ? 0 : offsetX;
		offsetX = (offsetX > mapWidth - screenWidth -1) ? mapWidth - screenWidth -1 : offsetX;

		offsetY = (int)player.getCoord().getY() - screenHeight/2;
		offsetY = (offsetY < 0) ? 0 : offsetY;
		offsetY = (offsetY > mapHeight - screenHeight - 1) ? mapHeight - screenHeight -1 : offsetY;
		
		//System.out.print("Player: " + player.getX() + " : " + player.getY());
		//System.out.println("   Offset: " + offsetX + " : " + offsetY);

		// draw parallax background image
		if (background != null) {
			int x = offsetX * (screenWidth - background.getWidth(null)) / (screenWidth - mapWidth);
			int y = offsetY * (screenHeight - background.getHeight(null)) / (screenHeight - mapHeight);
			g.drawImage(background, x, y, null);
		}


		int firstTileX = GameView.pixelsToTiles(offsetX, widthTile);
		int lastTileX = GameView.pixelsToTiles(offsetX + screenWidth, widthTile);
		int firstTileY = GameView.pixelsToTiles(offsetY, heightTile);
		int lastTileY = GameView.pixelsToTiles(offsetY + screenHeight, heightTile);
		
		//System.out.println("FirstTile: " + firstTileX + " : " + firstTileY + " LastTile: " + lastTileX + " : " + lastTileY);
		

		for (int y = firstTileY; y <= lastTileY; y++) {
			for (int x = firstTileX; x <= lastTileX; x++) {
				Tile tile1 = map1Tile[x][y];
				Tile tile2 = map2Tile[x][y];
				Item item = mapItem [x][y]; 
				if (tile1 != null) {
					tile1.draw(g,GameView.tilesToPixels(x, widthTile)-offsetX,  GameView.tilesToPixels(y, heightTile)-offsetY);
					// http://javamarioplatformer.codeplex.com/SourceControl/latest#src/devforrest/mario/core/GameRenderer.java
				}
				if (tile2 != null) {
					tile2.draw(g,GameView.tilesToPixels(x, widthTile)-offsetX,  GameView.tilesToPixels(y, heightTile)-offsetY);
					// http://javamarioplatformer.codeplex.com/SourceControl/latest#src/devforrest/mario/core/GameRenderer.java
				}
				if(mapItem[x][y] != null) 
				{
					item.draw(g,GameView.tilesToPixels(x, widthTile)-offsetX,  GameView.tilesToPixels(y, heightTile)-offsetY); 
				}
			}
		}

		player.draw(g,offsetX,offsetY);
		
		int secondes = (int)timeElapsed/1000;
		int minutes = secondes/60;
		secondes %= 60;
		int miettes = (int)timeElapsed - (minutes*60 + secondes)*1000;
		Font.draw(g,minutes+":"+secondes+"\""+miettes, 10,10);
		
		if (player.hasGoldenKey)
			g.drawImage(imgKey,screenWidth-100,20,null);
		
		if (player.hasLollipop)
			g.drawImage(imgLollipop,screenWidth-150, 20, null);
	}
    
    public int getOffsetX() {
    	return offsetX;
    }
    
    public int getOffsetY() {
    	return offsetY;
    }
    
	public void update(double Dt) {
		if (!pause)
		{
			timeElapsed += Dt;
			player.update(player.getCoord(),Dt,map1Tile,mapItem,widthTile,heightTile,mapHeight);
			if (player.getCoord().getX() > mapWidth - 73) {
				player.setCoordX(mapWidth-73);
			}
			if (player.getCoord().getY() > mapHeight - 73) {
				player.setCoordY(mapHeight-73);
			}
			if (player.getCoord().getX() < 0) {
				player.setCoordX(0);
			}
			if (player.getCoord().getY() < 0) {
				player.setCoordY(0);
			}
			
			if (player.isWinner()) {
				String name = JOptionPane.showInputDialog("Enter your name : ");
				System.out.println(name);
				
				if (nextMap != null) {
					JOptionPane.showMessageDialog(null,"You finished the level!\nPassword to next level: " + ChooseMenu.getHashValue("/map/"+nextMap), " " , JOptionPane.INFORMATION_MESSAGE );
				} else {
					JOptionPane.showMessageDialog(null,"You finished the game!!! Congratulation!", " " , JOptionPane.INFORMATION_MESSAGE );
				}
				
				controleur.setLevel(null);
				TitleMenu titleMenu = new TitleMenu(width, height, controleur);
				controleur.addMenu(titleMenu);
			}
		}
    }
    
	
    public Tile[][] getTiles() {
		return tiles;
	}
    
    public int getWidth() {
		return widthTile;
	}
    
    public int getHeight() {
		return heightTile;
	}
    
	/** Method which get tile 
*@param x 
*@param y
*@return tile 
*/  
    public Tile getTile(int x, int y) {
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			return null;
		} else {
			if(tiles[x][y] != null) {
				return tiles[x][y];
			} else {
				return null;
			}
		}
	}

	/**Method which get tile image 
*@param x
*@param y 
*@return image 
*/ 
    public BufferedImage getImage(int x, int y) {
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			return null;
		} else {
			if(tiles[x][y] != null) {
			     return tiles[x][y].getImage();
			} else {
				return null;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
		int key = e.getKeyCode();
		if ((key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_P) && !pause) {
			PauseMenu pauses = new PauseMenu(800, 600, controleur,this);
			pause = !pause;
			controleur.addMenu(pauses);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
    
	/** Method which get the player 
*@return the player  
*/ 
    public Player getPlayer() {
		return player;
	}
    
	/** Method which change the player 
*@param player : the new player 
*/  
    public void setPlayer(Player player) {
		this.player = player;
	}
	
	public double getTimeElapsed() {
		return timeElapsed;
	}
}
