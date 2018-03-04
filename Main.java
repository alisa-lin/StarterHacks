import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main extends JFrame {
	private GraphicsPanel gp;

	public Main () {
		GraphicsPanel gp = new GraphicsPanel();
		add(gp);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(850, 800);
		setVisible(true);
	}

	public static void main (String[] args) {
		Main m = new Main();
	}
}