package beditor;

import javax.swing.*;
import java.awt.*;
import javax.swing.UIManager.*;
import beditor.view.*;
import beditor.controller.*;
import java.util.Locale;

public class BEditor
{
	public static void main(String args[])
	{
		try
		{
		  	UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e)
		{
			try
			{
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			}
			catch(Exception e2)
			{
				System.out.println("ERREUR : impossible d'appliquer un look and feel");
			}
		}
		Locale.setDefault(Locale.ENGLISH);
		MainWindow window = new MainWindow();
	}
}
