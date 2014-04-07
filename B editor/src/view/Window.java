package view;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
* Classe Fenetre : permet de gérer la fenêtre affichant l'éditeur de niveaux
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
    	this.initWindow();
    	this.initMenu();
    	  
    	this.setVisible(true);
	}
	
	private void initWindow()
	{
   		this.setTitle("B editor");
    	this.setSize(800, 600);
   		this.setLocationRelativeTo(null);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initMenu()
	{
		this.menuBar = new JMenuBar();
    	
    	this.fileMenu = new JMenu("Fichier");
    	this.menuBar.add(this.fileMenu);
    	
    	this.quitFileMenu = new JMenuItem("Quitter");
    	this.fileMenu.add(this.quitFileMenu);
    	// permet de quitter lors du clic sur le bouton quitter
    	this.quitFileMenu.addActionListener(new ActionListener()
    		{
    			public void actionPerformed(ActionEvent arg0)
    			{
    				System.exit(0);
    			}
    		});
    	
    	this.setJMenuBar(menuBar); 
	}
}
