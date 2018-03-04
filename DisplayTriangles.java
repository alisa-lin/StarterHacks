import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class DisplayTriangles extends JFrame {
	private GraphicsPanel gp;

	public DisplayTriangles (DataManager dm) {
		gp = new GraphicsPanel(dm, this);
		add(gp);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(850, 800);
		setVisible(true);
	}

	public void closeWindow () {
		//dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		setVisible(false);
	}
}