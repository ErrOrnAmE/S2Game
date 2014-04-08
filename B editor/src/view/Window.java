package view;

import model.*;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
* Classe Window : permet de gérer la fenêtre affichant l'éditeur de niveaux
*
* @author Hugo PIGEON
* @version 1.0
*/

public class Window extends JFrame
{	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem quitFileMenu;

	/** Constructeur permettant de créer la fenêtre
	*/
	public Window()
	{
		this.add(new MapPan(10, 10, 32, 32));	
	
    	this.initWindow();
    	this.initMenu();
    	
    	this.setVisible(true);
	}
	
	/** Initialise la fenêtre
	*/
	private void initWindow()
	{
   		this.setTitle("B editor");
    	this.setSize(800, 600);
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
    	
    	this.quitFileMenu = new JMenuItem("Quitter");
    	this.fileMenu.add(this.quitFileMenu);
    	
    	this.setJMenuBar(menuBar); 
	}
	
	public JMenuItem getQuitFileMenu()
	{
		return this.quitFileMenu;
	}
}
