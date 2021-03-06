package core;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.JPanel;

import level.Level;
import gui.*;
import gui.Menu;

/**
*This class define GameControleur
*
*@author B Team
*@version 1.0
*/
public class GameControleur extends JPanel implements Runnable, KeyListener, MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private int panelWidth;
	private int panelHeight;
	private Graphics2D g;
	private Image image;
	private GameView gv;
	private Level level;
	private String nameLevel = null;
	private String nextLevel = null;
	
	private Stack<Menu> menuStack = new Stack<Menu>(); // Menu -> Sous-Menu, etc...
	
	public MouseButtons mouseButtons = new MouseButtons();
	private boolean mouseMoved = false;
	
	private boolean running = true;
	
	private long currentTime;
	private double dT;	//dT utilise par gravite et deplacements
	
	/**Constructor of class GameControleur
	* 
	*@param width representing the size of panel
	*@param height representing the size of panel
	*/
	public GameControleur(int width, int height) {
		this.panelWidth = width;
		this.panelHeight = height;
		
		gv = new GameView(width, height);
		level = null;
		Menu menu = new TitleMenu(width, height, this);
		addMenu(menu);
		
		this.currentTime = System.nanoTime();
		
		this.setFocusable(true);
		this.addKeyListener(this);
		this.addMouseMotionListener(this);
        this.addMouseListener(this);
	}
	
	public void gameAction() {
		gameUpdate(); // Update game state.
		gameRender(); // Draw to the double buffer.
		paintScreen(); // Draw double buffer to screen.
	}
	
	public void addNotify() {
		super.addNotify(); // creates the peer
		startGame(); // start the thread
	}
	
	public void startGame() {
        running = true;
        Thread thread = new Thread(this);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

	@Override
	public void run() {
		while(running) {
			gameAction();
		}
	}
	/**Method which update the game
	*/
	private void gameUpdate() {
		// Do some stuff here, like moving the player or looking at the colliding things
		mouseButtons.setPosition(getMousePosition());
		dT = (System.nanoTime() - currentTime) / 1000000.0;
		currentTime = System.nanoTime();
		
		//mesurer l'ecart de temps entre ici et le prochain ici
		if (level != null) {
			level.update(dT);
		}
		
		if (level != null && level.getPlayer().isWinner())
		{
			//System.out.println(level.getNextLevel());
			if (level.getNextLevel() == null ) {
				TitleMenu titleMenu = new TitleMenu(panelWidth, panelHeight, this);
				gv.setLevel(null);
				level = null;
				addMenu(titleMenu);
			} else {
				level = new Level(level.getNextLevel(),null, this);
				gv.setLevel(level);
			}
			currentTime = System.nanoTime();
		}
		
		if (menuStack.isEmpty() && level !=  null) {
			clearMenus();
		}
		
		if (!menuStack.isEmpty()) {
            menuStack.peek().update(mouseButtons);
			if (menuStack.peek().isClosed()) {
				popMenu();
				//System.out.println("Start Game");
			}
        }
		
		if (level == null && menuStack.isEmpty()) {
			if (nameLevel == null) {
				level = new Level("1.map",null,this);
			} else {
				level = new Level(nameLevel,nextLevel,this);
			}
			gv.setLevel(level);
			clearMenus();
			currentTime = System.nanoTime();
		}
		/*if (Button.getId() == TitleMenu.OPTION_GAME_ID){
			OptionMenu option = new OptionMenu(0,0);
        	addMenu(option);
		}*/
		mouseButtons.tick();
	}
	
	private void gameRender() {
		if(image == null) {
			image = createImage(this.panelWidth, this.panelHeight);
			return;
		}
	    g = (Graphics2D) image.getGraphics();  
		gv.draw(g, panelWidth, panelHeight);
	}
	
	private void paintScreen() {	
		Graphics2D g;
		try {
			g = (Graphics2D) this.getGraphics();
			if ((g != null) && (image != null))  {
				g.drawImage(image, 0, 0, null);
				g.dispose();
			} 
		} catch (Exception e) { System.out.println("Graphics context error: " + e); }
	}
	
	public void keyPressed(KeyEvent e) {
		if (level != null) {
			level.keyPressed(e);
		}
		if (!menuStack.isEmpty()) {
            menuStack.peek().keyPressed(e);
        }
	}

	public void keyReleased(KeyEvent e) {
		if (level != null) {
			level.keyReleased(e);
		}
		if (!menuStack.isEmpty()) {
            menuStack.peek().keyReleased(e);
        }
	}

	public void keyTyped(KeyEvent e) {
		
	} 
	
	public void mouseDragged(MouseEvent arg0) {
        mouseMoved = true;
    }

    public void mouseMoved(MouseEvent arg0) {
        mouseMoved = true;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        mouseButtons.releaseAll();
    }

    public void mousePressed(MouseEvent e) {
        mouseButtons.setNextState(e.getButton(), true);
		//System.out.println("GameControleur.java -> mousePressed");
    }

    public void mouseReleased(MouseEvent e) {
        mouseButtons.setNextState(e.getButton(), false);
    }
	
	public void addMenu(Menu menu) {
        menuStack.add(menu);
		gv.setMenu(menu); // Permet d'afficher le menu dans la vue
		if (level == null) {
			gv.setLevel(null);
		}
        //menu.addButtonListener(this);
    }
	
	public void popMenu() {
        if (!menuStack.isEmpty()) {
            menuStack.pop();
			if (!menuStack.isEmpty()) {
				gv.setMenu(menuStack.peek());
			}
        }
    }
	
	private void clearMenus() {
        while (!menuStack.isEmpty()) {
            menuStack.pop();
        }
		gv.setMenu(null);
    }
	
	public void setNameLevel(String name) {
		nameLevel = name;
	}
	
	public void setNextLevel(String name) {
		nextLevel = name;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}
}
