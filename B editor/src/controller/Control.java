package controller;

import view.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
* Classe Control : traite les entrées
*
* @author Hugo PIGEON
* @version 1.0
*/

public class Control
{
	private Window win;
	
	/** Constructeur pour lequel il faut préciser la fenêtre
	* @param window la fenêtre du programme
	*/
	public Control(Window window)
	{
		this.win = window;
		
    	// permet de quitter lors du clic sur le bouton quitter
    	this.win.getQuitFileMenu().addActionListener(new ActionListener()
    		{
    			public void actionPerformed(ActionEvent arg0)
    			{
    				System.exit(0);
    			}
    		});
	}
}
