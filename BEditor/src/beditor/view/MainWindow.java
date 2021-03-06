package beditor.view;

import beditor.model.*;
import beditor.controller.*;
import javax.swing.*;
import java.awt.*;

/**
* Window : class for the window which displays the editor
*
* @author Hugo PIGEON
* @version 1.1
*/

public class MainWindow extends JFrame
{	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem quitFileMenu, newFileMenu, openFileMenu, saveFileMenu;
	private JMenu mapMenu;
	private JMenuItem mapPropertiesMenu, mapLayer1Menu, mapLayer2Menu, mapItemsLayerMenu, mapStartFinishMenu;
	private JMenu helpMenu;
	private JMenuItem helpHowToMenu, helpAboutMenu;
	private EditorPan editorPan;
	private NewMapPan newMapPan;
	private ButtonEventsManager buttons;
	private MapPropertiesWindow mapPropertiesWindow;
	private AboutWindow aboutWindow;
	private HowToWindow howToWindow;
	
	private final String WINDOW_TITLE = "BEditor";
	private final Dimension WINDOW_SIZE = new Dimension(1024, 768);
	private final String FILE_MENU = "File";
	private final String NEW_MAP_MENU = "New map";
	private final String OPEN_MAP_MENU = "Open an existing map";
	private final String SAVE_MAP_MENU = "Save the map";
	private final String QUIT_MENU = "Quit";
	private final String MAP_MENU = "Map";
	private final String MAP_PROPERTIES_MENU = "Properties";
	private final String MAP_LAYER_1_MENU = "Edit layer 1";
	private final String MAP_LAYER_2_MENU = "Edit layer 2";
	private final String MAP_ITEMS_LAYER_MENU = "Edit items layer";
	private final String MAP_START_FINISH_MENU = "Edit starting and finishing points";
	private final String HELP_MENU = "Help";
	private final String HELP_HOW_TO_MENU = "How to use BEditor";
	private final String HELP_ABOUT_MENU = "About";
	
	/** Constructor to make a new window
	*/
	public MainWindow()
	{	
		this.newMapPan = new NewMapPan();
		this.setContentPane(this.newMapPan);
	
    	this.initWindow();
    	this.initMenu();
    	
		this.buttons = new ButtonEventsManager(this);
    	
    	this.setVisible(true);
	}
	
	/** Initializes the window
	*/
	private void initWindow()
	{
   		this.setTitle(WINDOW_TITLE);
    	this.setSize(WINDOW_SIZE);
   		this.setLocationRelativeTo(null);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/** Initializes the menu
	*/
	private void initMenu()
	{
		this.menuBar = new JMenuBar();
    	
    	this.fileMenu = new JMenu(FILE_MENU);
    	this.menuBar.add(this.fileMenu);
    	
    	this.newFileMenu = new JMenuItem(NEW_MAP_MENU);
    	this.fileMenu.add(this.newFileMenu);
    	
    	this.openFileMenu = new JMenuItem(OPEN_MAP_MENU);
    	this.fileMenu.add(this.openFileMenu);
    	
    	this.saveFileMenu = new JMenuItem(SAVE_MAP_MENU);
    	this.fileMenu.add(this.saveFileMenu);
    	
    	this.quitFileMenu = new JMenuItem(QUIT_MENU);
    	this.fileMenu.add(this.quitFileMenu);
    	
    	
    	this.helpMenu = new JMenu(HELP_MENU);
    	this.menuBar.add(this.helpMenu);
    	
    	this.helpHowToMenu = new JMenuItem(HELP_HOW_TO_MENU);
    	this.helpMenu.add(helpHowToMenu);
    	
    	this.helpAboutMenu = new JMenuItem(HELP_ABOUT_MENU);
    	this.helpMenu.add(helpAboutMenu);
    	
    	this.setJMenuBar(menuBar); 
	}
	
	/** Gives the menu's "quit" button
	* @return the menu's "quit" button
	*/
	public JMenuItem getQuitFileMenu()
	{
		return this.quitFileMenu;
	}
	
	/** Gives the menu's "open map" button
	* @return the menu's "open map" button
	*/
	public JMenuItem getOpenFileMenu()
	{
		return this.openFileMenu;
	}
	
	/** Gives the menu's "new map" button
	* @return the menu's "new map" button
	*/
	public JMenuItem getNewFileMenu()
	{
		return this.newFileMenu;
	}
	
	/** Gives the menu's "save map" button
	* @return the menu's "save map" button
	*/
	public JMenuItem getSaveFileMenu()
	{
		return this.saveFileMenu;
	}
	
	/** Gives the menu's "how to" button
	* @return the menu's "how to" button
	*/
	public JMenuItem getHelpHowToMenu()
	{
		return this.helpHowToMenu;
	}
	
	/** Gives the menu's "about" button
	* @return the menu's "about" button
	*/
	public JMenuItem getHelpAboutMenu()
	{
		return this.helpAboutMenu;
	}

	/** Gives the map menu's "layer 1" button
	* @return the map menu's "layer 1" button
	*/
	public JMenuItem getMapLayer1Menu()
	{
		return this.mapLayer1Menu;
	}

	/** Gives the map menu's "layer 2" button
	* @return the map menu's "layer 2" button
	*/
	public JMenuItem getMapLayer2Menu()
	{
		return this.mapLayer2Menu;
	}

	/** Gives the map menu's "items layer" button
	* @return the map menu's "items layer" button
	*/
	public JMenuItem getMapItemsLayerMenu()
	{
		return this.mapItemsLayerMenu;
	}

	/** Gives the map menu's "starting and finishing points" button
	* @return the map menu's "starting and finishing points" button
	*/
	public JMenuItem getMapStartFinishMenu()
	{
		return this.mapStartFinishMenu;
	}

	/** Gives the map menu's "properties" button
	* @return the map menu's "properties" button
	*/
	public JMenuItem getMapPropertiesMenu()
	{
		return this.mapPropertiesMenu;
	}
	
	/** Gives the window's EditorPan
	* @return the windows's EditorPan
	*/
	public EditorPan getEditorPan()
	{
		return this.editorPan;
	}
	
	/** Gives the window's NewMapPan
	* @return the windows's NewMapPan
	*/
	public NewMapPan getNewMapPan()
	{
		return this.newMapPan;
	}
	
	/** Switches from the NewMapPan screen to the EditorPan screen
	* @param editorPan the new EditorPan screen
	*/
	public void switchMap(EditorPan editorPan)
	{
		if(this.mapMenu == null)
		{
			this.menuBar.remove(this.helpMenu);
			this.mapMenu = new JMenu(MAP_MENU);
			this.menuBar.add(this.mapMenu);
			this.menuBar.add(this.helpMenu);
			
			this.mapLayer1Menu = new JMenuItem(MAP_LAYER_1_MENU);
			this.mapMenu.add(this.mapLayer1Menu);
			
			this.mapLayer2Menu = new JMenuItem(MAP_LAYER_2_MENU);
			this.mapMenu.add(this.mapLayer2Menu);
			
			this.mapItemsLayerMenu = new JMenuItem(MAP_ITEMS_LAYER_MENU);
			this.mapMenu.add(this.mapItemsLayerMenu);
			
			this.mapStartFinishMenu = new JMenuItem(MAP_START_FINISH_MENU);
			this.mapMenu.add(this.mapStartFinishMenu);
			
			this.mapPropertiesMenu = new JMenuItem(MAP_PROPERTIES_MENU);
			this.mapMenu.add(this.mapPropertiesMenu);
			
			this.buttons.addMapMenuListeners();
    	}
    	
		this.editorPan = editorPan;
		this.buttons.addTilePropertiesPanListeners();
		this.buttons.addItemPropertiesPanListeners();
		this.setContentPane(this.editorPan);
		this.revalidate();
	}
	
	/** Switches from the EditorPan screen to the NewMapPan screen
	*/
	public void switchNewMap()
	{
		this.setContentPane(this.newMapPan);
		this.revalidate();
	}
	
	/** Gives this window's ButtonEventsManager
	* @return this window's ButtonEventsManager
	*/
	public ButtonEventsManager getButtonEventsManager()
	{
		return this.buttons;
	}
	
	/** Gives the window allowing to set the map's properties
	* @return the window allowing to set the map's properties
	*/
	public MapPropertiesWindow getMapPropertiesWindow()
	{
		return this.mapPropertiesWindow;
	}
	
	/** Sets the window allowing to set the map's properties
	* @param mpWin the new window allowing to set the map's properties
	*/
	public void setMapPropertiesWindow(MapPropertiesWindow mpWin)
	{
		this.mapPropertiesWindow = mpWin;
		if(mpWin != null)
			this.buttons.addMapPropertiesWindowListeners();
	}
	
	/** Gives the window showing information about BEditor
	* @return the window showing information about BEditor
	*/
	public AboutWindow getAboutWindow()
	{
		return this.aboutWindow;
	}
	
	/** Sets the window showing information about BEditor
	* @param aWin the new window showing information about BEditor
	*/
	public void setAboutWindow(AboutWindow aWin)
	{
		this.aboutWindow = aWin;
		if(aWin != null)
			this.buttons.addAboutWindowListeners();
	}
	
	/** Gives the window showing how to use BEditor
	* @return the window showing how to use BEditor
	*/
	public HowToWindow getHowToWindow()
	{
		return this.howToWindow;
	}
	
	/** Sets the window showing how to use BEditor
	* @param htWin the new window showing how to use BEditor
	*/
	public void setHowToWindow(HowToWindow htWin)
	{
		this.howToWindow = htWin;
		if(htWin != null)
			this.buttons.addHowToWindowListeners();
	}
}
