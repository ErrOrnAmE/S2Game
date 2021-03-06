package beditor.view;

import javax.swing.*;
import java.awt.*;

/**
* AboutWindow : class for the window which displays information about BEditor
*
* @author Hugo PIGEON
* @version 1.1
*/

public class AboutWindow extends JFrame
{	
	private MainWindow win;
	private JLabel text;
	private JButton closeButton;

	private final String VERSION = "1.1";	
	private final String YEAR = "2014";
	private final String WINDOW_TITLE = "About BEditor";
	private final String CLOSE_LABEL = "Close";
	private final Dimension WINDOW_SIZE = new Dimension(400, 450);

	
	/** Constructor to make a new window
	* @param win the parent window
	*/
	public AboutWindow(MainWindow win)
	{
		this.win = win;
		this.initWindow();
		this.addContent();
    	
		this.setVisible(true);
	}
	
	/** Initializes the window
	*/
	private void initWindow()
	{
   		this.setTitle(WINDOW_TITLE);
		this.setSize(WINDOW_SIZE);
   		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	/** Adds the content to the window
	*/
	private void addContent()
	{		
		this.getContentPane().setLayout(new BorderLayout());
		
		this.text = new JLabel("<html><center><b>BEditor version " + VERSION + " (" + YEAR + ")</b><br/><br/>BEditor is an editor to create maps working with BEngine<br/><br/>This program is a part of a project for Nantes Institute of Technology<br/><br/>Responsible for the map editor<br/>Hugo PIGEON<br/>Responsible for the view<br/>Dylan GAUTIER<br/>Responsible models<br/>Thomas PICARD<br/>Responsible for the physics engine<br/>Mathieu GRONDIN<br/>Controller responsible<br/>Maël QUÉMARD<br/>Responsible for the internal and external communication<br/>Thibaud COURTOISON<br/>Teacher tutor / Project manager<br/>Adrien BOUGOUIN<br/></center></html>");
		
		JPanel closePan = new JPanel();
		this.closeButton = new JButton(CLOSE_LABEL);
		closePan.add(this.closeButton);
		
		this.getContentPane().add(this.text, BorderLayout.CENTER);
		this.getContentPane().add(closePan, BorderLayout.SOUTH);
	}
	
	/** Gives the close button
	* @return the close button
	*/
	public JButton getCloseButton()
	{
		return this.closeButton;
	}
}
