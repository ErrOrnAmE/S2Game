package beditor.controller;

import beditor.view.*;
import beditor.model.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;

/**
* ButtonEventsManager : a class which manages the events related to buttons
*
* @author Hugo PIGEON
* @version 1.1
*/

public class ButtonEventsManager
{
	private MainWindow win;
	
	private final String MAP_SAVED_MSG = "The map has been successfully saved";
	private final String MAP_SAVED_TITLE = "Map saved";
	private final String MAP_NOT_CREATED_MSG = "No map has been created, nothing saved";
	private final String MAP_NOT_CREATED_TITLE = "Error";
	private final String MAP_NOT_SAVED_MSG = "The map couldn't be saved";
	private final String MAP_NOT_SAVED_TITLE = "Error";
	private final String TILESET_NOT_SELECTED_MSG = "You must select a tileset";
	private final String TILESET_NOT_SELECTED_TITLE = "Error";
	private final String NO_EDITED_MAP_MSG = "There is no edited map, you can't access to the properties";
	private final String NO_EDITED_MAP_TITLE = "Error";
	
	/** Constructor which needs the window
	* @param window the program's window
	*/
	public ButtonEventsManager(MainWindow window)
	{
		this.win = window;
		
		// Called when clicking on "new map"
		this.win.getNewFileMenu().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				win.switchNewMap();
			
				win.revalidate();
			}
		});
		
    	// Called when clicking on "quit"
    	this.win.getQuitFileMenu().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
    			System.exit(0);
    		}
    	});
		
    	// Called when clicking on "open"
    	this.win.getOpenFileMenu().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
    			String [] file = new String[1000];
    			String path = "";
    			int i = 0;
    			
				JFileChooser chooser = new JFileChooser();
				String filesAllowed[] = new String[1];
				filesAllowed[0] = "map";
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Map file (.map)", filesAllowed);
				chooser.setFileFilter(filter);
				int choice = chooser.showOpenDialog(win);
				if(choice == JFileChooser.APPROVE_OPTION)
					path = chooser.getSelectedFile().getPath();
	    		
	    		if(path != "")
	    		{
					//reading the text file	
					try
					{
						FileInputStream fstream = new FileInputStream(path);
						 
						// Get the object of DataInputStream
						DataInputStream in = new DataInputStream(fstream);
						BufferedReader br = new BufferedReader(new InputStreamReader(in));
						String strLine;
						 
						//Read File Line By Line
						while ((strLine = br.readLine()) != null)
						{
							file[i] = strLine; 
							i++;
						}
						 
						//Close the input stream
						in.close();
						 
					}
					catch (Exception e)
					{
						System.err.println("Error: " + e.getMessage());
					}
				
					final int mapWidth = Integer.parseInt(file[2]);
					final int mapHeight = Integer.parseInt(file[4]);
					final int nbTilesY = Integer.parseInt(file[6]);
					final int nbTilesX = Integer.parseInt(file[8]);
					final int tileWidth = Integer.parseInt(file[10]);
					final int tileHeight = Integer.parseInt(file[12]);
					final int nbItem = Integer.parseInt(file[14]);
					final String tilesetPath = file[22];
					String collision = file[16];
					int collisionType = 0;
					if(collision.equals("rubber"))
						collisionType = 1;
				
					win.switchMap(new EditorPan(new Dimension(mapWidth, mapHeight), new Dimension(tileWidth, tileHeight), tilesetPath, collisionType, win));
		
					String [] coordinate = file[18].split(" "); 
					Point start = new Point(Integer.parseInt(coordinate[0]), Integer.parseInt(coordinate[1]));
					coordinate = file[20].split(" "); 
					Point finish = new Point(Integer.parseInt(coordinate[0]), Integer.parseInt(coordinate[1]));
				
					win.getEditorPan().getStartFinishLayer().getMap().setTile(0, 0, -1);
					win.getEditorPan().getStartFinishLayer().getMap().setTile(0, 1, -1);
					win.getEditorPan().getStartFinishLayer().getMap().setTile((int) start.getX(), (int) start.getY(), 0);
					win.getEditorPan().getStartFinishLayer().getMap().setTile((int) finish.getX(), (int) finish.getY(), 1);
				
					// loading solid and breakable properties of the tiles
					String props[][] = new String [nbTilesY*nbTilesX][4];
					for(int j=24 ; j < (nbTilesY*nbTilesX)+24 ; j++)
					{
						String [] prop = file[j].split("[\\s\\s\\s]");
						for(int k=0; k<4; k++)
						{
							props[j-24][k] = prop[k]; 
						}
						Point coord = new Point(Integer.parseInt(props[j-24][1]) - (win.getEditorPan().getTilesetPan().getTilesetWidth() * (Integer.parseInt(props[j-24][1]) / win.getEditorPan().getTilesetPan().getTilesetWidth())), Integer.parseInt(props[j-24][1]) / win.getEditorPan().getTilesetPan().getTilesetWidth());
						win.getEditorPan().getTilesetPan().getTileset().getTile(coord).setSolid(props[j-24][2].equals("solid"));
						win.getEditorPan().getTilesetPan().getTileset().getTile(coord).setBreakable(props[j-24][3].equals("breakable"));
					}
				
					// loading layer 1
					int map1Int[][] = new int[mapWidth][mapHeight];
		
					for(int j=25+(nbTilesY*nbTilesX); j<25+(nbTilesY*nbTilesX)+mapHeight; j++) 
					{
						String [] tile = file[j].split("\\s"); 
					
						for (int q=0; q<tile.length; q++)
						{	
							if(tile[q].equals(""))
								map1Int[q][j-(25+(nbTilesY*nbTilesX))] = -1;
							else 
								map1Int[q][j-(25+(nbTilesY*nbTilesX))] = Integer.parseInt(tile[q]); 
						}

						if(tile.length < mapWidth) 
						{
							for(int x=tile.length; x<mapWidth; x++) 
							{
								map1Int[x][j-(25+(nbTilesY*nbTilesX))] = -1; 
							}
						}
					}
		
					// loading layer 2
					int map2Int[][] = new int [mapWidth][mapHeight];
		
					for(int j=26+((nbTilesY*nbTilesX)+mapHeight); j<26+((nbTilesY*nbTilesX)+mapHeight*2); j++) 
					{
						String [] tile = file[j].split("\\s"); 

						for (int q=0; q<tile.length; q++)
						{	
								if(tile[q].equals(""))
									map2Int[q][j-(26+(nbTilesY*nbTilesX)+mapHeight)] = -1;
								else 
									map2Int[q][j-(26+(nbTilesY*nbTilesX)+mapHeight)] = Integer.parseInt(tile[q]);
						}

						if(tile.length < mapWidth) 
						{
							for(int x=tile.length; x<mapWidth; x++) 
							{
								map2Int[x][j-(26+(nbTilesY*nbTilesX)+mapHeight)] = -1; 
							}
						}

					}
				
					// adding the two layers to the edited map	
					for(int y = 0 ; y < mapHeight ; y++)
					{
						for(int x = 0 ; x < mapWidth ; x++)
						{
							win.getEditorPan().getLayer1().getMap().setTile(x, y, map1Int[x][y]);
							win.getEditorPan().getLayer2().getMap().setTile(x, y, map2Int[x][y]);
						}
					}
				
					// loading items
					String itemInfo[][] = new String[nbItem][4]; 
		
					for(int counter=27+((nbTilesY*nbTilesX)+(mapHeight*2)); counter < 27+((nbTilesY*nbTilesX)+(mapHeight*2) + nbItem); counter++)
					{
						String [] info = file[counter].split("[\\s]"); 
						for(int o=0; o <= 3; o++)
						{
							itemInfo[counter-(27+((nbTilesY*nbTilesX)+(mapHeight*2)))][o] = info[o]; 
							
						}
					
						final int cpt = counter-(27+((nbTilesY*nbTilesX)+(mapHeight*2)));
						win.getEditorPan().getItemsLayer().getMap().setTile(Integer.parseInt(itemInfo[cpt][1]), Integer.parseInt(itemInfo[cpt][2]), Integer.parseInt(itemInfo[cpt][3]));
						Point coord = new Point(Integer.parseInt(itemInfo[cpt][3]) - (win.getEditorPan().getTilesetPan().getTilesetWidth() * (Integer.parseInt(itemInfo[cpt][3]) / win.getEditorPan().getTilesetPan().getTilesetWidth())), Integer.parseInt(itemInfo[cpt][1]) / win.getEditorPan().getTilesetPan().getTilesetWidth());
						if(itemInfo[cpt][0].equals("goldenkey"))
							win.getEditorPan().getTilesetPan().getTileset().getItem(coord).setType(ItemType.KEY);
						else if(itemInfo[cpt][0].equals("lollipop"))
							win.getEditorPan().getTilesetPan().getTileset().getItem(coord).setType(ItemType.LOLLIPOP);
						else if(itemInfo[cpt][0].equals("door"))
							win.getEditorPan().getTilesetPan().getTileset().getItem(coord).setType(ItemType.DOOR);
						else
							win.getEditorPan().getTilesetPan().getTileset().getItem(coord).setType(ItemType.NOT_AN_ITEM);
					}
				
					win.getEditorPan().getCurrentlyEdited().reloadMap();
				}
    		}
    	});
    	
    	// Called when clicking on "save the map"
    	this.win.getSaveFileMenu().addActionListener(new ActionListener()
    	{
    		
    		public void actionPerformed(ActionEvent arg0)
    		{
    			try
    			{
    				Map mapLayer1 = win.getEditorPan().getLayer1().getMap();
    				Map mapLayer2 = win.getEditorPan().getLayer2().getMap();
    				Map mapItemsLayer = win.getEditorPan().getItemsLayer().getMap();
    				Map mapStartFinish = win.getEditorPan().getStartFinishLayer().getMap();
    				
    				JFileChooser chooser = new JFileChooser();
					String filesAllowed[] = new String[1];
					filesAllowed[0] = "map";
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Map file (.map)", filesAllowed);
					chooser.setFileFilter(filter);
					int choice = chooser.showSaveDialog(win);
					if(choice == JFileChooser.APPROVE_OPTION)
					{
						File mapFile = new File(chooser.getSelectedFile().getPath() + ".map");
						mapFile.createNewFile();
						FileWriter mapFileWriter = new FileWriter(mapFile);
				
						mapFileWriter.write("Tilemapping Version 1.0\n");
						mapFileWriter.write("#nbTilesLengthWorld\n");
						mapFileWriter.write(mapLayer1.getWidth() + "\n");
						mapFileWriter.write("#nbTilesWidthWorld\n");
						mapFileWriter.write(mapLayer1.getHeight() + "\n");
						mapFileWriter.write("#nbTilesX\n");
						mapFileWriter.write(win.getEditorPan().getTilesetPan().getTilesetWidth() + "\n");
						mapFileWriter.write("#nbTilesY\n");
						mapFileWriter.write(win.getEditorPan().getTilesetPan().getTilesetHeight() + "\n");
						mapFileWriter.write("#lengthTile\n");
						mapFileWriter.write((int) win.getEditorPan().getLayer1().getTilesDim().getWidth() + "\n");
						mapFileWriter.write("#widthTile\n");
						mapFileWriter.write((int) win.getEditorPan().getLayer1().getTilesDim().getHeight() + "\n");
						mapFileWriter.write("#nbItem\n");
						int nbItem = 0;
						for(int y = 0 ; y < mapItemsLayer.getHeight() ; y++)
						{
							for(int x = 0 ; x < mapItemsLayer.getWidth() ; x++)
							{
								if(mapItemsLayer.getTile(x, y) != -1)
								{
									final int indexY = mapItemsLayer.getTile(x, y) / win.getEditorPan().getTilesetPan().getTilesetWidth();
									final int indexX = mapItemsLayer.getTile(x, y) - (win.getEditorPan().getTilesetPan().getTilesetWidth() * indexY);
									
									if(win.getEditorPan().getTilesetPan().getTileset().getItem(new Point(indexX, indexY)).getType() != ItemType.NOT_AN_ITEM)
										nbItem++;
								}
							}
						}
						mapFileWriter.write(nbItem + "\n");
						mapFileWriter.write("#collisionType\n");
						if(mapLayer1.getCollisionType() == 0)
							mapFileWriter.write("normal\n");
						else if(mapLayer1.getCollisionType() == 1)
							mapFileWriter.write("rubber\n");
						int startX = -1, startY = -1, finishX = -1, finishY = -1;
						boolean foundStart = false, foundFinish = false;
						for(int x = 0 ; x < mapStartFinish.getWidth() && (!foundStart || !foundFinish) ; x++)
						{
							for(int y = 0 ; y < mapStartFinish.getHeight() && (!foundStart || !foundFinish) ; y++)
							{
								if(mapStartFinish.getTile(x, y) == 0)
								{
									startX = x;
									startY = y;
									foundStart = true;
								}
								else if(mapStartFinish.getTile(x, y) == 1)
								{
									finishX = x;
									finishY = y;
									foundFinish = true;
								}
							}
						}
						mapFileWriter.write("#coordinateStart\n");
						mapFileWriter.write(startX + " " + startY + "\n");
						mapFileWriter.write("#coordinateFinish\n");
						mapFileWriter.write(finishX + " " + finishY + "\n");
						mapFileWriter.write("#image\n");
						String tilesetPath = win.getEditorPan().getTilesetPan().getTilesetPath();
						tilesetPath = tilesetPath.substring(tilesetPath.lastIndexOf("/") + 1);
						tilesetPath = tilesetPath.substring(tilesetPath.lastIndexOf("\\") + 1);
						mapFileWriter.write(tilesetPath + "\n");
						mapFileWriter.write("#tileset\n");
						for(int y = 0 ; y < win.getEditorPan().getTilesetPan().getTilesetHeight() ; y++)
						{
							for(int x = 0 ; x < win.getEditorPan().getTilesetPan().getTilesetWidth() ; x++)
							{
								mapFileWriter.write("tile " + (x + y * win.getEditorPan().getTilesetPan().getTilesetWidth()));
								if(win.getEditorPan().getTilesetPan().getTileset().getTile(new Point(x, y)).getSolid())
									mapFileWriter.write(" solid ");
								else
									mapFileWriter.write(" empty ");
								if(win.getEditorPan().getTilesetPan().getTileset().getTile(new Point(x, y)).getBreakable())
									mapFileWriter.write("breakable\n");
								else
									mapFileWriter.write("notBreakable\n");
							}
						}
					
						mapFileWriter.write("#level_layer1\n");
						for(int y = 0 ; y < mapLayer1.getHeight() ; y++)
						{
							for(int x = 0 ; x < mapLayer1.getWidth() ; x++)
							{
								if(mapLayer1.getTile(x, y) == -1)
									mapFileWriter.write(" ");
								else
									mapFileWriter.write(mapLayer1.getTile(x, y) + " ");
							}
							mapFileWriter.write("\n");
						}
						mapFileWriter.write("#level_layer2\n");
						for(int y = 0 ; y < mapLayer2.getHeight() ; y++)
						{
							for(int x = 0 ; x < mapLayer2.getWidth() ; x++)
							{
								if(mapLayer2.getTile(x, y) == -1)
									mapFileWriter.write(" ");
								else
									mapFileWriter.write(mapLayer2.getTile(x, y) + " ");
							}
							mapFileWriter.write("\n");
						}
						mapFileWriter.write("#items\n");
						for(int y = 0 ; y < mapItemsLayer.getHeight() ; y++)
						{
							for(int x = 0 ; x < mapItemsLayer.getWidth() ; x++)
							{
								if(mapItemsLayer.getTile(x, y) != -1)
								{
									final int indexY = mapItemsLayer.getTile(x, y) / win.getEditorPan().getTilesetPan().getTilesetWidth();
									final int indexX = mapItemsLayer.getTile(x, y) - (win.getEditorPan().getTilesetPan().getTilesetWidth() * indexY);
									
									if(win.getEditorPan().getTilesetPan().getTileset().getItem(new Point(indexX, indexY)).getType() == ItemType.KEY)
										mapFileWriter.write("goldenkey " + x + " " + y + " " + mapItemsLayer.getTile(x, y) + "\n");
									else if(win.getEditorPan().getTilesetPan().getTileset().getItem(new Point(indexX, indexY)).getType() == ItemType.LOLLIPOP)
										mapFileWriter.write("lollipop " + x + " " + y + " " + mapItemsLayer.getTile(x, y) + "\n");
									else if(win.getEditorPan().getTilesetPan().getTileset().getItem(new Point(indexX, indexY)).getType() == ItemType.DOOR)
										mapFileWriter.write("door " + x + " " + y + " " + mapItemsLayer.getTile(x, y) + "\n");
								}
							}
						}
						mapFileWriter.write("#end");
						
						mapFileWriter.close();
					
						JOptionPane savedMsg = new JOptionPane();
						savedMsg.showMessageDialog(win, MAP_SAVED_MSG, MAP_SAVED_TITLE, JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
    					JOptionPane errorMsg = new JOptionPane();
    					errorMsg.showMessageDialog(win, MAP_NOT_SAVED_MSG, MAP_NOT_SAVED_TITLE, JOptionPane.ERROR_MESSAGE);
					}
    			}
    			catch(NullPointerException e)
    			{
    				JOptionPane errorMsg = new JOptionPane();
    				errorMsg.showMessageDialog(win, MAP_NOT_CREATED_MSG, MAP_NOT_CREATED_TITLE, JOptionPane.ERROR_MESSAGE);
    			}
				catch(Exception e)
				{
    				JOptionPane errorMsg = new JOptionPane();
    				errorMsg.showMessageDialog(win, MAP_NOT_SAVED_MSG, MAP_NOT_SAVED_TITLE, JOptionPane.ERROR_MESSAGE);
				}     
    		}
    	});
    		
    	// Called when clicking on "browse" for the tileset
    	this.win.getNewMapPan().getTilesetButton().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
    			JFileChooser chooser = new JFileChooser();
    			String filesAllowed[] = new String[2];
    			filesAllowed[0] = "jpg";
    			filesAllowed[1] = "png";
    			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG images", filesAllowed);
    			chooser.setFileFilter(filter);
    			int choice = chooser.showOpenDialog(win);
    			if(choice == JFileChooser.APPROVE_OPTION)
	    			win.getNewMapPan().setTilesetPath(chooser.getSelectedFile().getPath());
    		}
    	});
    	
    	// Called when clicking on "create" for the new map
    	this.win.getNewMapPan().getConfirmButton().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
    			if(win.getNewMapPan().getTilesetPath() != "")
    			{
    				NewMapPan nmp = win.getNewMapPan();
    				win.switchMap(new EditorPan(new Dimension(nmp.getMapWidth(), nmp.getMapHeight()), new Dimension(nmp.getTileWidth(), nmp.getTileHeight()), nmp.getTilesetPath(), nmp.getSelectedCollision(), win));
    			}
    			else
    			{
    				JOptionPane errorMsg = new JOptionPane();
    				errorMsg.showMessageDialog(win, TILESET_NOT_SELECTED_MSG, TILESET_NOT_SELECTED_TITLE, JOptionPane.ERROR_MESSAGE);
    			}
    		}
    	});
		
		// Called when clicking on "how to use BEditor"
		this.win.getHelpHowToMenu().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				win.setHowToWindow(new HowToWindow(win));
			}
		});
		
		// Called when clicking on "about"
		this.win.getHelpAboutMenu().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				win.setAboutWindow(new AboutWindow(win));
			}
		});
	}
	
	/** Adds the listeners for the TilePropertiesPan shown in the editor
	*/
	public void addTilePropertiesPanListeners()
	{	
    	// Called when clicking on the "empty" radio button in the properties
    	this.win.getEditorPan().getTilePropertiesPan().getEmptyRadio().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
	    		win.getEditorPan().getTilesetPan().getTileset().getTile(win.getEditorPan().getTilesetPan().getSelectedSpriteIndex()).setSolid(false);
    		}
    	});
    	
    	// Called when clicking on the "solid" radio button in the properties
    	this.win.getEditorPan().getTilePropertiesPan().getSolidRadio().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
	    		win.getEditorPan().getTilesetPan().getTileset().getTile(win.getEditorPan().getTilesetPan().getSelectedSpriteIndex()).setSolid(true);
    		}
    	});
    	
    	// Called when clicking on the "not breakable" radio button in the properties
    	this.win.getEditorPan().getTilePropertiesPan().getNotBreakableRadio().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
	    		win.getEditorPan().getTilesetPan().getTileset().getTile(win.getEditorPan().getTilesetPan().getSelectedSpriteIndex()).setBreakable(false);
    		}
    	});
    	
    	// Called when clicking on the "breakable" radio button in the properties
    	this.win.getEditorPan().getTilePropertiesPan().getBreakableRadio().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
	    		win.getEditorPan().getTilesetPan().getTileset().getTile(win.getEditorPan().getTilesetPan().getSelectedSpriteIndex()).setBreakable(true);
    		}
    	});
	}
	
	/** Adds the listeners for the ItemPropertiesPan shown in the editor
	*/
	public void addItemPropertiesPanListeners()
	{	
		// Called when clicking on the "not an item" radio button in the properties
    	this.win.getEditorPan().getItemPropertiesPan().getNotAnItemRadio().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
	    		win.getEditorPan().getTilesetPan().getTileset().getItem(win.getEditorPan().getTilesetPan().getSelectedSpriteIndex()).setType(ItemType.NOT_AN_ITEM);
    		}
    	});
	
    	// Called when clicking on the "key" radio button in the properties
    	this.win.getEditorPan().getItemPropertiesPan().getKeyRadio().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
	    		win.getEditorPan().getTilesetPan().getTileset().getItem(win.getEditorPan().getTilesetPan().getSelectedSpriteIndex()).setType(ItemType.KEY);
    		}
    	});
    	
    	// Called when clicking on the "lollipop" radio button in the properties
    	this.win.getEditorPan().getItemPropertiesPan().getLollipopRadio().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
	    		win.getEditorPan().getTilesetPan().getTileset().getItem(win.getEditorPan().getTilesetPan().getSelectedSpriteIndex()).setType(ItemType.LOLLIPOP);
    		}
    	});
    	
    	// Called when clicking on the "door" radio button in the properties
    	this.win.getEditorPan().getItemPropertiesPan().getDoorRadio().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
	    		win.getEditorPan().getTilesetPan().getTileset().getItem(win.getEditorPan().getTilesetPan().getSelectedSpriteIndex()).setType(ItemType.DOOR);
    		}
    	});
	}
	
	/** Adds the listeners for the "map" menu
	*/
	public void addMapMenuListeners()
	{
		//Called when clicking on "layer 1"
		this.win.getMapLayer1Menu().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if(win.getEditorPan() != null)
				{
					win.getEditorPan().editLayer1();
				}
				else
				{
    				JOptionPane errorMsg = new JOptionPane();
    				errorMsg.showMessageDialog(win, NO_EDITED_MAP_MSG, NO_EDITED_MAP_TITLE, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//Called when clicking on "layer 2"
		this.win.getMapLayer2Menu().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if(win.getEditorPan() != null)
				{
					win.getEditorPan().editLayer2();
				}
				else
				{
    				JOptionPane errorMsg = new JOptionPane();
    				errorMsg.showMessageDialog(win, NO_EDITED_MAP_MSG, NO_EDITED_MAP_TITLE, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//Called when clicking on "items layer"
		this.win.getMapItemsLayerMenu().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if(win.getEditorPan() != null)
				{
					win.getEditorPan().editItemsLayer();
				}
				else
				{
    				JOptionPane errorMsg = new JOptionPane();
    				errorMsg.showMessageDialog(win, NO_EDITED_MAP_MSG, NO_EDITED_MAP_TITLE, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//Called when clicking on "starting and finishing points"
		this.win.getMapStartFinishMenu().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if(win.getEditorPan() != null)
				{
					win.getEditorPan().editStartFinishLayer();
				}
				else
				{
    				JOptionPane errorMsg = new JOptionPane();
    				errorMsg.showMessageDialog(win, NO_EDITED_MAP_MSG, NO_EDITED_MAP_TITLE, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//Called when clicking on "properties"
		this.win.getMapPropertiesMenu().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if(win.getEditorPan() != null)
				{
					win.setMapPropertiesWindow(new MapPropertiesWindow(win));
				}
				else
				{
    				JOptionPane errorMsg = new JOptionPane();
    				errorMsg.showMessageDialog(win, NO_EDITED_MAP_MSG, NO_EDITED_MAP_TITLE, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	/** Adds the listeners for the window allowing to change the map's properties
	*/
	public void addMapPropertiesWindowListeners()
	{
		// Called when clicking on the "apply" button
    	win.getMapPropertiesWindow().getApplyButton().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
    			win.getEditorPan().getLayer1().setMapDim(win.getMapPropertiesWindow().getMapWidth(), win.getMapPropertiesWindow().getMapHeight());
    			win.getEditorPan().getLayer2().setMapDim(win.getMapPropertiesWindow().getMapWidth(), win.getMapPropertiesWindow().getMapHeight());
    			win.getEditorPan().getItemsLayer().setMapDim(win.getMapPropertiesWindow().getMapWidth(), win.getMapPropertiesWindow().getMapHeight());
    			win.getEditorPan().getStartFinishLayer().setMapDim(win.getMapPropertiesWindow().getMapWidth(), win.getMapPropertiesWindow().getMapHeight());
    		}
    	});
    	
    	// Called when clicking on the "close" button
    	win.getMapPropertiesWindow().getCloseButton().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
	    		win.getMapPropertiesWindow().dispose();
    			win.setMapPropertiesWindow(null);
    		}
    	});
	}
	
	/** Adds the listeners for the "about" window
	*/
	public void addAboutWindowListeners()
	{    	
    	// Called when clicking on the "close" button
    	win.getAboutWindow().getCloseButton().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
	    		win.getAboutWindow().dispose();
    			win.setAboutWindow(null);
    		}
    	});
	}
	
	/** Adds the listeners for the "how to" window
	*/
	public void addHowToWindowListeners()
	{    	
    	// Called when clicking on the "close" button
    	win.getHowToWindow().getCloseButton().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
	    		win.getHowToWindow().dispose();
    			win.setHowToWindow(null);
    		}
    	});
	}
}
