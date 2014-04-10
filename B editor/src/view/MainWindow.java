package view;

import model.*;
import controller.*;
import javax.swing.*;
import java.awt.*;

/**
* Classe Window : permet de gérer la fenêtre affichant l'éditeur de niveaux
*
* @author Hugo PIGEON
* @version 1.0
*/

public class MainWindow extends JFrame
{	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem quitFileMenu;
	private JMenuItem newFileMenu;
	private JMenuItem saveFileMenu;
	private MapPan mapPan;
	private NewMapPan newMapPan;

	/** Constructeur permettant de créer la fenêtre
	*/
	public MainWindow()
	{	
		this.newMapPan = new NewMapPan();
		this.setContentPane(this.newMapPan);
	
    	this.initWindow();
    	this.initMenu();
    	//this.setResizable(false);
    	
    	this.setVisible(true);
	}
	
	/** Initialise la fenêtre
	*/
	private void initWindow()
	{
   		this.setTitle("B Editor");
    	this.setSize(1024, 768);
   		this.setLocationRelativeTo(null);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/** Initialise le menu
	*/
	private void initMenu()
	{
		this.menuBar = new JMenuBar();
    	
    	this.fileMenu = new JMenu("Fichier");
    	this.menuBar.add(this.fileMenu);
    	
    	this.newFileMenu = new JMenuItem("Nouvelle carte");
    	this.fileMenu.add(this.newFileMenu);
    	
    	this.saveFileMenu = new JMenuItem("Enregistrer la carte");
    	this.fileMenu.add(this.saveFileMenu);
    	
    	this.quitFileMenu = new JMenuItem("Quitter");
    	this.fileMenu.add(this.quitFileMenu);
    	
    	this.setJMenuBar(menuBar); 
	}
	
	/** Permet d'obtenir le bouton "quitter" du menu
	* @return le bouton "quitter" du menu
	*/
	public JMenuItem getQuitFileMenu()
	{
		return this.quitFileMenu;
	}
	
	/** Permet d'obtenir le bouton "nouvelle carte" du menu
	* @return le bouton "nouvelle carte" du menu
	*/
	public JMenuItem getNewFileMenu()
	{
		return this.newFileMenu;
	}
	
	/** Permet d'obtenir le bouton "nouvelle carte" du menu
	* @return le bouton "nouvelle carte" du menu
	*/
	public JMenuItem getSaveFileMenu()
	{
		return this.saveFileMenu;
	}
	
	/** Permet d'obtenir le bouton "parcourir" de la sélection de tileset
	* @return le bouton "parcourir" de la sélection de tileset
	*/
	public JButton getTilesetButton()
	{
		return this.newMapPan.getTilesetButton();
	}
	
	/** Permet d'obtenir le mapPan
	* @return le mapPan de la fenêtre
	*/
	public MapPan getMapPan()
	{
		return this.mapPan;
	}
	
	/** Permet d'obtenir le newMapPan
	* @return le newMapPan de la fenêtre
	*/
	public NewMapPan getNewMapPan()
	{
		return this.newMapPan;
	}
	
	/** Permet de passer de l'écran NewMapPan à l'écran MapPan
	* @param mapPan le nouvel écran MapPan
	*/
	public void switchMap(MapPan mapPan)
	{
		this.mapPan = mapPan;
		this.setContentPane(this.mapPan);
		this.revalidate();
	}
	
	/** Permet de passer de l'écran MapPan à l'écran NewMapPan
	*/
	public void switchNewMap()
	{
		this.setContentPane(this.newMapPan);
		this.revalidate();
	}	
}
