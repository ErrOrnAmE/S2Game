package controller;

import view.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.*;
import java.awt.*;
import javax.swing.*;

/**
* Classe ButtonEventsManager : traite les événements générés par les boutons et le menu
*
* @author Hugo PIGEON
* @version 1.0
*/

public class ButtonEventsManager
{
	private MainWindow win;
	
	/** Constructeur pour lequel il faut préciser la fenêtre
	* @param window la fenêtre du programme
	*/
	public ButtonEventsManager(MainWindow window)
	{
		this.win = window;
		
		// permet de créer une nouvelle carte lors du clic sur le bouton "nouvelle carte" du menu
		this.win.getNewFileMenu().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				win.switchNewMap();
				
				win.revalidate();
			}
		});
		
    	// permet de quitter lors du clic sur le bouton "quitter" du menu
    	this.win.getQuitFileMenu().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
    			System.exit(0);
    		}
    	});
    		
    	// permet de sélectionner un fichier pour le choix du tileset
    	this.win.getNewMapPan().getTilesetButton().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
    			JFileChooser chooser = new JFileChooser();
    			String filesAllowed[] = new String[3];
    			filesAllowed[0] = "jpg";
    			filesAllowed[1] = "png";
    			filesAllowed[2] = "bmp";
    			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, BMP & PNG images", filesAllowed);
    			chooser.setFileFilter(filter);
    			chooser.showOpenDialog(win);
    			win.getNewMapPan().setTilesetPath(chooser.getSelectedFile().getName());
    		}
    	});
    	
    	// permet de valider la création de la nouvelle carte
    	this.win.getNewMapPan().getConfirmButton().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
    			if(win.getNewMapPan().getTilesetPath() != "")
    			{
    				NewMapPan nmp = win.getNewMapPan();
    				win.switchMap(new MapPan(new Dimension(nmp.getMapWidth(), nmp.getMapHeight()), new Dimension(nmp.getTileWidth(), nmp.getTileHeight()), nmp.getTilesetPath()));
    			}
    			else
    			{
    				JOptionPane errorMsg = new JOptionPane();
    				errorMsg.showMessageDialog(win, "Vous devez sélectionner un tileset", "Erreur", JOptionPane.ERROR_MESSAGE);
    			}
    		}
    	});
	}
}
