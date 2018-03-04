import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GraphicsPanel extends JPanel {
	public static final String TRIANGLE_FILE = "triangles.txt";
	public static final String ICON_FILE = "legend_icons.txt";
	public static final int NUM_TRIANGLES = 5;
	public static final int NUM_TRIANGLE_ORIENTATIONS = 2;
	private Color backgroundColour = new Color(255,255,255);
	private SpringLayout layout = new SpringLayout();
	private DataManager dataManager;
	private ImageIcon triangleImages[][];
	private ImageIcon legendIcons[];

	final int LABEL_HEIGHT = 20;
	private Font titleFont = new Font("Trebuchet MS", Font.BOLD, 35);
	private Font labelFont = new Font("Trebuchet MS", Font.BOLD, 25);
	private Font buttonFont = new Font("Trebuchet MS", Font.BOLD, 30);

	JButton backButton, saveButton;
	JPanel sidePanel, legendPanel;
	SpringLayout sidePanelLayout = new SpringLayout();
	DisplayTriangles frame;

	public GraphicsPanel (DataManager dataManager, DisplayTriangles frame) {
		this.dataManager = dataManager;
		this.frame = frame;
		setLayout(layout);
		setBackground(backgroundColour);
		loadTriangleImages();
		loadLegendIcons();
		displayTriangles();
		addSidePanel();
	}

	private void loadTriangleImages () {
		try {
			BufferedReader in = new BufferedReader(new FileReader(TRIANGLE_FILE));

			int numTriangles = Integer.parseInt(in.readLine());
			triangleImages = new ImageIcon[numTriangles][NUM_TRIANGLE_ORIENTATIONS];
			for (int i = 0; i < numTriangles; i++) {
				in.readLine(); //flush blank line
				for (int j = 0; j < NUM_TRIANGLE_ORIENTATIONS; j++) {
					triangleImages[i][j] = new ImageIcon(in.readLine());
				}
			}
			in.close();
		} catch (IOException iox) {
			System.out.println("TriangleManager: Error loading triangle images.");
		}
	}

	public void displayTriangles () {
		final int RIGHT_SIDE_UP = 0;
		final int UPSIDE_DOWN = 1;
		ArrayList<Integer> triangleCount = dataManager.getTriangleCount();

		int triangleColour = 0;
		int orientation = 0;
		int row = 687;
		int col = 20;

		for (int i = 0; i < triangleCount.size() && triangleColour < 5; i++) {
			int trianglesLeft = triangleCount.get(i);
			while (trianglesLeft > 0) {
				JLabel imageLabel = new JLabel();
				imageLabel.setIcon(triangleImages[triangleColour][orientation]);
				add(imageLabel);
				layout.putConstraint(SpringLayout.NORTH, imageLabel, row, SpringLayout.NORTH, this);
				layout.putConstraint(SpringLayout.WEST, imageLabel, col, SpringLayout.WEST, this);

				col += 56.5;
				if (col > 440) {
					row -= 83.8;
					col = 20;
					if (orientation == RIGHT_SIDE_UP) {
						orientation = UPSIDE_DOWN;
					} else {
						orientation = RIGHT_SIDE_UP;
					}
				}

				if (orientation == RIGHT_SIDE_UP) {
					orientation = UPSIDE_DOWN;
				} else {
					orientation = RIGHT_SIDE_UP;
				}

				trianglesLeft--;
			}
			triangleColour++;
		}
	}

	private void loadLegendIcons () {
		try {
			BufferedReader in = new BufferedReader(new FileReader(ICON_FILE));

			int numIcons = Integer.parseInt(in.readLine());
			legendIcons = new ImageIcon[numIcons];
			in.readLine(); //flush blank line
			for (int i = 0; i < numIcons; i++) {
				legendIcons[i] = new ImageIcon(in.readLine());
			}
			in.close();
		} catch (IOException iox) {
			System.out.println("TriangleManager: Error loading legend icons.");
		}
	}

	private void addSidePanel () {
		ArrayList<String> labels = dataManager.getLabelList();

		// add side panel
		sidePanel = new JPanel();
		SpringLayout sidePanelLayout = new SpringLayout();
		sidePanel.setPreferredSize(new Dimension(310, 750));
		sidePanel.setBackground(new Color(255,0,97));
		sidePanel.setLayout(sidePanelLayout);
		add(sidePanel);
		layout.putConstraint(SpringLayout.NORTH, sidePanel, 14, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, sidePanel, 540, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, this, 20, SpringLayout.EAST, sidePanel);

		// add legend panel
		SpringLayout legendLayout = new SpringLayout();
		legendPanel = new JPanel();
		legendPanel.setBackground(new Color(255,255,255));
		legendPanel.setBorder(BorderFactory.createLineBorder(new Color(185,0,58), 10));
		legendPanel.setPreferredSize(new Dimension(250, 120 + 60 * labels.size()));
		legendPanel.setLayout(legendLayout);
		sidePanelLayout.putConstraint(SpringLayout.NORTH, legendPanel, 30, SpringLayout.NORTH, sidePanel);
		sidePanelLayout.putConstraint(SpringLayout.WEST, legendPanel, 25, SpringLayout.WEST, sidePanel);
		sidePanelLayout.putConstraint(SpringLayout.EAST, sidePanel, 25, SpringLayout.EAST, legendPanel);
		sidePanel.add(legendPanel);

		// add all content to legend panel
		JLabel title = new JLabel("Legend");
		title.setFont(titleFont);
		title.setForeground(new Color(185,0,58));
		legendLayout.putConstraint(SpringLayout.NORTH, title, 15, SpringLayout.NORTH, legendPanel);
		legendLayout.putConstraint(SpringLayout.WEST, title, 20, SpringLayout.WEST, legendPanel);
		legendPanel.add(title);
		for (int i = 0; i < labels.size(); i++) {
			JLabel colour = new JLabel();
			colour.setIcon(legendIcons[i]);
			legendLayout.putConstraint(SpringLayout.NORTH, colour, 85 + i*60, SpringLayout.NORTH, legendPanel);
			legendLayout.putConstraint(SpringLayout.WEST, colour, 20, SpringLayout.WEST, legendPanel);
			legendPanel.add(colour);

			JLabel colourLabel = new JLabel(labels.get(i));
			colourLabel.setFont(labelFont);
			colourLabel.setForeground(new Color(185,0,58));
			legendLayout.putConstraint(SpringLayout.NORTH, colourLabel, 90 + i*60, SpringLayout.NORTH, legendPanel);
			legendLayout.putConstraint(SpringLayout.WEST, colourLabel, 70, SpringLayout.WEST, legendPanel);
			legendPanel.add(colourLabel);
		}

		// add save button
		saveButton = new JButton("Save");
		saveButton.addActionListener(__ -> {
			String inputValue = JOptionPane.showInputDialog(null, "Enter a name for your Triangles graphic: ", "Name Your Graphic", JOptionPane.INFORMATION_MESSAGE);
		});
		saveButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				saveButton.setBackground(new Color(238,238,238));
			}
			public void mouseExited(MouseEvent evt) {
				saveButton.setBackground(Color.WHITE);
			}
		});
		saveButton.setFont(buttonFont);
		saveButton.setBackground(new Color (255, 255, 255));
		saveButton.setForeground(new Color(185,0,58));
		saveButton.setBorderPainted(false);
		saveButton.setOpaque(true);
		saveButton.setPreferredSize(new Dimension(250, 70));
		sidePanel.add(saveButton);
		sidePanelLayout.putConstraint(SpringLayout.WEST, saveButton, 0, SpringLayout.WEST, legendPanel);
		sidePanelLayout.putConstraint(SpringLayout.NORTH, saveButton, 40, SpringLayout.SOUTH, legendPanel);
		sidePanelLayout.putConstraint(SpringLayout.EAST, saveButton, 0, SpringLayout.EAST, legendPanel);

		// add back button
		backButton = new JButton("Close");
		backButton.addActionListener(__ -> {
			frame.closeWindow();
		});
		backButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				backButton.setBackground(new Color(238,238,238));
			}
			public void mouseExited(MouseEvent evt) {
				backButton.setBackground(Color.WHITE);
			}
		});
		backButton.setFont(buttonFont);
		backButton.setBackground(new Color (255, 255, 255));
		backButton.setForeground(new Color(185,0,58));
		backButton.setBorderPainted(false);
		backButton.setOpaque(true);
		backButton.setPreferredSize(new Dimension(250, 70));
		sidePanel.add(backButton);
		sidePanelLayout.putConstraint(SpringLayout.WEST, backButton, 0, SpringLayout.WEST, legendPanel);
		sidePanelLayout.putConstraint(SpringLayout.NORTH, backButton, 40, SpringLayout.SOUTH, saveButton);
		sidePanelLayout.putConstraint(SpringLayout.EAST, backButton, 0, SpringLayout.EAST, legendPanel);
	}
}