package vue;

import javax.swing.JFrame;

/**
* Classe Fenetre
*
* @author Hugo PIGEON
* @version 1.0
*/

public class Fenetre extends JFrame
{	
	public Fenetre()
	{
   		this.setTitle("B editor");
    	this.setSize(800, 600);
   		this.setLocationRelativeTo(null);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    	this.setVisible(true);
	}
}
