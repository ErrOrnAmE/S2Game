package beditor;

import beditor.view.*;
import beditor.controller.*;

public class BEditor
{
	public static void main(String args[])
	{
		MainWindow window = new MainWindow();
		ButtonEventsManager buttons = new ButtonEventsManager(window);
	}
}
