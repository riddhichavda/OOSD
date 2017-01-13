package view;

import javax.swing.JFrame;

import constants.Constants;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private GridView gridView;
	private MazeCreator mazeCreator;

	public MainFrame(MazeCreator mazeCreator, GridView gridView) {

		this.mazeCreator = mazeCreator;
		this.gridView = gridView;
		init();
	}

	public void init() {
		// Setting parameters related to frame
		this.setBounds(100, 20, Constants.MAINFRAMEWIDTH, Constants.MAINFRAMEHEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Maze Maker");
		this.getContentPane().setLayout(null);
		this.add(mazeCreator);
		this.add(gridView);
		this.setVisible(true);
		this.repaint();
	}

}
