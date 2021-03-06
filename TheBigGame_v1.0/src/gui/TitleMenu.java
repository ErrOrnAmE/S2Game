package gui;

import java.awt.event.KeyEvent;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


import core.*;

/**
*This class define menu TitleMenu
*
*@author B Team
*@version 1.0
*/
public class TitleMenu extends Menu {
    public static final int CHOOSELEVEL_GAME_ID = 1000;
    public static final int OPTION_GAME_ID = 1002;
    public static final int HIGHSCORE_GAME_ID = 1004;
    public static final int CREDIT_GAME_ID = 1003;
    public static final int EXIT_GAME_ID = 1001;

    //public static final int CANCEL_JOIN_ID = 1004;
    public static final int PERFORM_JOIN_ID = 1005;
    public static final int RESTART_GAME_ID = 1006;
	
	private BufferedImage img;

	private static int selectedItem;
	private int nbItem = 6;
	
	private GameControleur controleur;
	
	/**Constructor of class TitleMenu
	* 
	*@param gameWidth representing the size of the width game
	*@param gameHeight representing the size of the height game
	*@param controleur representing the controleur
	*/
	public TitleMenu(int gameWidth, int gameHeight, GameControleur controleur) {
        this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		this.selectedItem = -1;
		this.controleur = controleur;

        addButton(new Button(CHOOSELEVEL_GAME_ID, 0, (gameWidth - 128) / 2, 280));
        addButton(new Button(OPTION_GAME_ID, 2, (gameWidth - 128) / 2, 320));
        addButton(new Button(HIGHSCORE_GAME_ID, 3, (gameWidth - 128) / 2, 360));
        addButton(new Button(CREDIT_GAME_ID, 4, (gameWidth - 128) / 2, 400));
        addButton(new Button(EXIT_GAME_ID, 1, (gameWidth - 128) / 2, 440));
		
		img = GameView.loadImage("images/TITLESCREEN.png");
		//addButton(new Button(PERFORM_JOIN_ID, 5, (gameWidth - 128) / 2 - 40, 270 + selectedItem * 40));
    }
	
	/**Method to draw picture
	* 
	*@param g representing Graphics2D
	*@param screenWidth representing the size of width screen
	*@param screenHeight representing the size of height screen
	*/
	public void draw(Graphics2D g, int screenWidth, int screenHeight) {
		g.drawImage(img,0,0,null);
	
		super.draw(g,screenWidth,screenHeight);
	}
	
	/**Method to update mouse
	* 
	*@param mouseButtons representing the mouse buttons
	*/
	public void update(MouseButtons mouseButtons) {
		super.update(mouseButtons);
		for (Button button : buttons) {
            if (button.isClicked()) {
				buttonPressed(button);
			}
        }
	}
	
	/**Method to know if button is pressed
	* 
	*@param button representing the button
	*/ 
	public void buttonPressed(Button button) {
		//System.out.println(button.getId());
		if (button.getId() == CHOOSELEVEL_GAME_ID) {
			ChooseMenu chooseMenu = new ChooseMenu(800, 600, controleur);
			controleur.addMenu(chooseMenu);
			setSelectedItem(-1);
			isClosed = true;
			//System.out.println("Start Button");
		}
		
		if (button.getId() == OPTION_GAME_ID){
			OptionMenu option = new OptionMenu(800, 600, controleur);
			controleur.addMenu(option);
			setSelectedItem(-1);

		}

		if (button.getId() == HIGHSCORE_GAME_ID){
			HighscoreMenu hihscore = new HighscoreMenu(800, 600, controleur);
			controleur.addMenu(hihscore);
			setSelectedItem(-1);
		}

		if (button.getId() == CREDIT_GAME_ID){
			CreditMenu credit = new CreditMenu(800, 600, controleur);
			controleur.addMenu(credit);
			setSelectedItem(-1);
		}

		if (button.getId() == EXIT_GAME_ID) {
			System.exit(42);
		}
    }

	/**Method to know if key is pressed
	* 
	*@param e representing the keyEvent
	*/
	public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.selectedItem--;
            if (this.selectedItem < 0) {
                this.selectedItem = nbItem-1;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.selectedItem++;
            if (this.selectedItem > nbItem-1) {
                this.selectedItem = 0;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume();
            buttons.get(this.selectedItem).postClick();
        }
    }
	
	/**Method to know if key released
	* 
	*@param arg0 representing the keyEvent
	*/
	public void keyReleased(KeyEvent arg0) {
    }

    /**Method to know the key type
    * 
    *@param arg0 representing the KeyEvent
    */
    public void keyTyped(KeyEvent arg0) {
    }

    /**Getter to get selectedItem
    * 
    *@return the selectedItem
    */
    public static int getSelectedItem(){
    	return selectedItem;
    }

    /**Setter to set selectedItem
    * 
    *@param selectedItem representing the item selected
    */
    public void setSelectedItem(int selectedItem){
    	this.selectedItem = selectedItem;
    }
}
