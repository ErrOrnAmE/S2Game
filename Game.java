import vue.*;
import model.*;

class Game{

	public void main
	{
		Player player = new Player();
		boolean isOpen=true;
		//Attributs pour la gestion FPS
		final int TICKS_PER_SECOND = 50;
		final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
		final int MAX_FRAMESKIP = 10;

		static long next_game_tick = System.nanoTime();
		long Dt; //le delta-t utilise par la gravite
		int loops;
		
		//Effectuer les fonctionnalités du jeu tant qu'une fenêtre est ouverte
		//Make the functionalities of the game features a window is opened
		while(isOpen)
		{
			//Effectuer les fonctionnalités du menu si la fenêtre menu est ouverte
			//Make the functionalities of menu if the menu window is open
			if(Display.menu()==true)
			{
				Display.afficherMenu();
				//Aller au menu crédit si le bouton crédit est appuyer
				//Go to the menu credit if button credit is press
				if (Display.buttonCredit.isClicked(mouse)==true)
				{
					Display.setMenu(false);
					Display.setCredit(true);
				}
				//Aller au menu highscore si le bouton highscore est appuyer
				//Go to the menu highscore if button highscore is press
				else if (Display.buttonHighScore.isClicked(mouse)==true)
				{
					Display.setMenu(false);
					Display.setHighScore(true);
				}
				//Aller au menu option si le bouton option est appuyer
				//Go to the menu option if button option is press
				else if (Display.buttonOption.isClicked(mouse)==true)
				{
					Display.setMenu(false);
					Display.setOption(true);
				}
				//Aller au menu aide si le bouton aide est appuyer
				//Go to the menu help if button help is press
				else if (Display.buttonHelp.isClicked(mouse)==true)
				{
					Display.setMenu(false);
					Display.setHelp(true);
				}
				//Aller au menu choisir niveau si le bouton choisir niveau est appuyer
				//Go to the menu choose level if button choose level is press
				else if (Display.buttonChooseLevel.isClicked(mouse)==true)
				{
					Display.setMenu(false);
					Display.setChooseLevel(true);
				}
			}
			//Effectuer les fonctionnalités du menu highscore si la fenêtre highscore est ouverte
			//Make the functionalities of menu highscore if the highscore window is open
			else if(Display.highScore()==true)
			{
				Dispaly.afficherHighScore();
				
				//Aller au menu si le bouton retour est appuyer
				//Go to the menu if button return is press
				if (Display.buttonReturn.isClicked(mouse)==true)
				{
					Display.setHighScore(false);
					Display.setMenu(true);
				}
			}
			//Effectuer les fonctionnalités du menu crédit si la fenêtre crédit est ouverte
			//Make the functionalities of menu credit if the credit window is open
			else if(Display.credit()==true)
			{
				Display.afficherCredit();
				
				//Aller au menu si le bouton retour est appuyer
				//Go to the menu if button return is press
				if (Display.buttonReturn.isClicked(mouse)==true)
				{
					Display.setCredit(false);
					Display.setMenu(true);
				}
			}
			//Effectuer les fonctionnalités du menu aide si la fenêtre aide est ouverte
			//Make the functionalities of menu help if the help window is open
			else if(Display.help()==true)
			{
				Display.afficherHelp();
				
				//Aller au menu si le bouton retour est appuyer
				//Go to the menu if button return is press
				if (Display.buttonReturn.isClicked(mouse)==true)
				{
					Display.setHelp(false);
					Display.setMenu(true);
				}
			}
			//Effectuer les fonctionnalités du menu choisir niveau si la fenêtre choisir niveau est ouverte
			//Make the functionalities of menu choose level if the choose level window is open
			else if(Display.chooseLevel()==true)
			{
				Display.afficherChooseLevel();
				
				//si le bouton du niveau 1 est appuyer alors jouer
				if(Display.buttonsLevel[0].isClicked(mouse))
				{
					Level.play(1);
				}
				//du bouton du niveau 2 au dernier niveau incrémenter i et lancer le niveau correspondant au bouton
				for(int i=1; i<=nbLevel; i++)
				{
					if(Display.buttonsLevel[i].isClicked(mouse)==true)
					{
						String password = Display.afficherDemandeDuMDP();
						//si le mot de passe est bon
						if(Level.password==password)
						{
							Level.play(i);
						}
					}
				}
			}
			//Effectuer les fonctionnalités du menu option si la fenêtre option est ouverte
			//Make the functionalities of menu option if the option window is open
			else if(Display.option()==true)
			{
				Display.afficherOption();
				
				//Choix de la résolution full ou pixel (par box)
				//Choose of size resolution (by box)
				if(Display.buttonSizeResolutionFull.isClicked(mouse))
				{
					Display.sizeResolutionFull();
					ParametersResolutionFull();
				}
				else if(Display.buttonSizeResolutionPixel.isClicked(mouse))
				{
					Display.sizeResolutionPixel();
					Parameters.sizeResolutionPixel();
				}
				
				//Choix de la taille de l'écran (par box)
				//Choose size of screen (by box)
				if(Display.sizeScreenOne.isClicked(mouse))
				{
					Display.sizeScreenOne();
					Parameters.sizeScreenOne();
				}
				else if(Display.sizeScreenTwo.isClpicked(mouse))
				{
					Display.sizeScreenTwo();
					Parameters.sizeScreenTwop();
				}
				
				//Choix des touches (par box)
				//Choose key (by box)
				if(Display.chooseKeyOne.isClicked(mouse))
				{
					Display.chooseKeyOne();
					Parameters.chooseKeyOne();
				}
				else if(Display.chooseKeyTwo.isClicked(mouse))
				{
					Display.chooseKeyTwo();
					Parameters.chooseKeyTwo();
				}
				
				//Choix de la texture (par box)
				//Choose of texture (by box)
				if(Display.textureOne.isClicked(mouse))
				{
					Display.textureOne();
					Parameters.textureOne();
				}
				else if(Display.textureTwo.isClicked(mouse))
				{
					Display.textureTwo();
					Parameters.textureTwo();
				}
				
				if(Display.curseurVolM.isClicked(mouse))
				{
					switch(Display.curseur)
					{
						case 100:
							Display.curseur(100);
							Parameters.musique(100);
						break;
						case 75:
							Display.curseur(75);
							Parameters.musique(75);
						break;
						case 50:
							Display.curseur(50);
							Parameters.musique(50);
						break;
						case 25:
							Display.curseur(25);
							Parameters.musique(25);
						break;
						case 0:
							Display.curseur(0);
							Parameters.musique(0);
						break;
					}
				}
			}
			//Effectuer les fonctionnalités d'un niveau si le niveau est ouvert
			//Make the functionalities of level if the level window is open
			else if(Level.isOpen()==true)
			{
				//Effectuer les fonctionnalités du niveau tant que le jeu n'est pas fini
				//Make the functionalities of level while the game isn't finished
				while(Level.isFinished()==false)
				{
					//Effectuer les fonctionnalités du menu pause si la fenêtre pause est ouverte sinon faire les fonctionnalitées du niveau
					//Make the functionalities of menu pause if the pause window is open
					if(Level.pause()==true)
					{
					
					}
					else
					{
						//Fonction pour le régulateur de la vitesse du jeu
						//Le jeu sera mis a jour environ 50 fois par seconde, et le rendu sera fait aussi vite que possible
						loops = 0;
						Dt = System.nanoTime()
						while(System.nanoTime() > next_game_tick && loops < MAX_FRAMESKIP)
						{
								Dt = System.nanoTime() - Dt;
								update_game(Dt);

								next_game_tick += SKIP_TICKS;
								loops++;
						}
						display_game();
					}
				}
			}
		}
	}
}