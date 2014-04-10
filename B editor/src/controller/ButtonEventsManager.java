package controller;

import view.*;
import model.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;

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
    	
    	// permet d'enregistrer la carte lors du clic sur le bouton "enregistrer" du menu
    	this.win.getSaveFileMenu().addActionListener(new ActionListener()
    	{
    		public void actionPerformed(ActionEvent arg0)
    		{
    			try
    			{
    				Map map = win.getMapPan().getMap();
    				
    				File mapFile = new File("newMap.map");
					mapFile.createNewFile();
					FileWriter mapFileWriter = new FileWriter(mapFile);
					for(int y = 0 ; y < map.getHeight() ; y++)
					{
						for(int x = 0 ; x < map.getWidth() ; x++)
						{
							mapFileWriter.write(map.getTile(x, y) + " ");
						}
						mapFileWriter.write("\n");
					}
					
					// System.out.println("Carte enregistrée");
					//////////////////////////
					// AFFICHER UN MESSAGE CARTE ENREGISTREE
					//////////////////////////
					mapFileWriter.close();
    			}
    			catch(NullPointerException e)
    			{
    				JOptionPane errorMsg = new JOptionPane();
    				errorMsg.showMessageDialog(win, "Aucune carte n'a été créée, l'enregistrement n'a donc pas pu s'effectuer", "Erreur", JOptionPane.ERROR_MESSAGE);
    			}
				catch(Exception e)
				{
    				JOptionPane errorMsg = new JOptionPane();
    				errorMsg.showMessageDialog(win, "La carte n'a pas pu être enregistrée", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
    		}
    	});
    		
    	// permet de sélectionner un fichier pour le choix du tileset
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
    			chooser.showOpenDialog(win);
    			win.getNewMapPan().setTilesetPath(chooser.getSelectedFile().getName());
    			
    			//////////////////////
    			// TRAITER LE CAS OU L'UTILISATEUR CLIQUE SUR ANNULER DANS LE CHOOSER
    			//////////////////////
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
