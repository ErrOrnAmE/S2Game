package gui;

import java.util.*;
import core.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Button {
	
	private List<ButtonListener> listeners;
	
	private boolean isPressed = false;
	
	
	private final int x;
	private final int y;
	private final int w;
	private final int h;
	
	private final int id;
	private int ix;
	private int iy;
	
	private boolean performClick = false;
	
	private static BufferedImage choosel = GameView.loadImage("images/choose_level.png");
	private static BufferedImage chooselPressed = GameView.loadImage("images/choose_levelp.png");
	private static BufferedImage exit = GameView.loadImage("images/exit.png");
	private static BufferedImage exitPressed = GameView.loadImage("images/exitp.png"); 	
	private static BufferedImage option = GameView.loadImage("images/option.png");
	private static BufferedImage optionPressed = GameView.loadImage("images/optionp.png"); 	
	private static BufferedImage credit = GameView.loadImage("images/credit.png");
	private static BufferedImage creditPressed = GameView.loadImage("images/creditp.png");
	private static BufferedImage highscore = GameView.loadImage("images/highscore.png");
	private static BufferedImage highscorePressed = GameView.loadImage("images/highscorep.png");
	private static BufferedImage level_1 = GameView.loadImage("images/level_1.png");
	private static BufferedImage level_1p = GameView.loadImage("images/level_1p.png");
	private static BufferedImage back = GameView.loadImage("images/back.png");
	private static BufferedImage backp = GameView.loadImage("images/backp.png");
	private static BufferedImage continues = GameView.loadImage("images/continue.png");
	private static BufferedImage continuep = GameView.loadImage("images/continuep.png");
	private static BufferedImage retry = GameView.loadImage("images/retry.png");
	private static BufferedImage retryp = GameView.loadImage("images/retryp.png");
	private static BufferedImage exitGame = GameView.loadImage("images/exit_game.png");
	private static BufferedImage exitGamep = GameView.loadImage("images/exit_gamep.png"); 	

	private static int nbFiles;

	public Button(int id, int buttonImageIndex, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.w = 128; // A changer
		this.h = 24; // A changer
        this.ix = buttonImageIndex; // A changer
        this.iy = buttonImageIndex; // A changer
        try {
			nbFiles = ChooseMenu.getListFile();
		}
		catch (Exception e){
		
		}
	}

	public Button(int id, int buttonImageIndex, int x, int y, int w, int h) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.w = w; // A changer
		this.h = h; // A changer
        this.ix = buttonImageIndex; // A changer
        this.iy = buttonImageIndex; // A changer
	}
	
	public void update(MouseButtons mouseButtons) {

        int mx = mouseButtons.getX();
        int my = mouseButtons.getY();
    	isPressed = false;
	    if (mx >= x && my >= y && mx < (x + w) && my < (y + h)) {
	        if (mouseButtons.isReleased(1)) {
	            postClick();
	            //System.out.println("YEP");
	        } else if (mouseButtons.isDown(1)) {
	        	//System.out.println("Nope");
	            isPressed = true;
	        }
	    }

	    if (performClick) {
	        if (listeners != null) {
	            for (ButtonListener listener : listeners) {
	                listener.buttonPressed(this);
	            }
	        }
	        performClick = false;
	    }
    }
	
	public void postClick() {
        performClick = true;
    }
	
	public void draw(Graphics2D g) {
			if(getId() == TitleMenu.CHOOSELEVEL_GAME_ID)
			{
				if (isPressed || TitleMenu.getSelectedItem() == 0)
				{
					g.drawImage(chooselPressed, x, y, null);
					//System.out.println("TitleMenu : " + TitleMenu.getSelectedItem());
					//System.out.println("OptionMenu : " + OptionMenu.getSelectedItem());
				}
				else
				{
					g.drawImage(choosel, x, y, null);
					//System.out.println("nope");
				 }
			}
			else if (getId() == TitleMenu.EXIT_GAME_ID)
			{
				if (isPressed || TitleMenu.getSelectedItem() == 4)
				{
					g.drawImage(exitPressed, x, y, null);
				}
				else
				{
					g.drawImage(exit, x, y, null);
				 }
			}
			else if (getId() == TitleMenu.OPTION_GAME_ID)
			{
				if (isPressed || TitleMenu.getSelectedItem() == 1)
				{
					g.drawImage(optionPressed, x, y, null);
				}
				else
				{
					g.drawImage(option, x, y, null);
				 }
			}
			else if (getId() == TitleMenu.HIGHSCORE_GAME_ID){
				if (isPressed || TitleMenu.getSelectedItem() == 2)
				{
					g.drawImage(highscorePressed, x, y, null);
				}
				else{
					g.drawImage(highscore, x, y, null);
				}
			}
			else if (getId() == TitleMenu.CREDIT_GAME_ID){
				if (isPressed || TitleMenu.getSelectedItem() == 3){
					g.drawImage(creditPressed, x, y, null);
				}
				else {
					g.drawImage(credit, x, y, null);
				}
			}
			else if (getId() == ChooseMenu.BACK_GAME_ID || getId() == OptionMenu.BACK_GAME_ID) {
				if (isPressed || OptionMenu.getSelectedItem() == 2)
				{
					g.drawImage(backp, x, y, null);
				}
				else {
					g.drawImage(back, x, y, null);
				}
			}
			else if (getId() == PauseMenu.CONTINUE_GAME_ID) {
				if (isPressed) {
					g.drawImage(continuep, x, y, null);
				}
				else {
					g.drawImage(continues, x, y, null);
				}
			}
			else if (getId() == PauseMenu.RETRY_GAME_ID) {
				if (isPressed) {
					g.drawImage(retryp, x, y, null);
				}
				else {
					g.drawImage(retry, x, y, null);
				}
			}
			else if (getId() == PauseMenu.EXIT_TO_TITLEMENU_GAME_ID) {
				if (isPressed) {
					g.drawImage(exitGamep, x, y, null);
				}
				else {
					g.drawImage(exitGame, x, y, null);
				}
			}
			else {
					if (isPressed){
						g.drawImage(level_1p, x, y, null);
						Font.draw(g, ""+getId(), x+25, y+32);
					}
					else {
						g.drawImage(level_1, x, y, null);
						Font.draw(g, ""+getId() , x+25, y+32);
					}
			}
    }
	
	public boolean isPressed() {
        return isPressed;
    }
    
    public boolean isClicked() {
    	return performClick;
    }
	
	public void addListener(ButtonListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<ButtonListener>();
        }
        listeners.add(listener);
    }
	
	public int getId() {
        return id;
    }
}
