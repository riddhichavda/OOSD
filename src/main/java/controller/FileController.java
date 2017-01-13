package controller;
/*
 * This class is instantiated in BtnCreateController.java on clicking the button 'Launch with UI : File Input'.
 * The purpose of this class is make the agent move and eat based on the double values read from the file 'input.txt'.
 * The constructor calls the method to read a file and stores it in a string.
 * Using the Timer class, the agent performs the respective action picked from this string every 2 seconds.
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.Timer;

import constants.Constants;
import view.GridView;
import view.InputDataPanel;
import view.MazeFrame;

public class FileController {
	private int count;
	private int delay = Constants.DELAY;
	private GridView gridView;
	private InputDataPanel inputDataPanel;
	private String[] lines;
	private MazeFrame mazeFrame;
	private String readString;
	private Timer t;

	public FileController(MazeFrame mazeFrame, final GridView gridView) {
		this.gridView = gridView;
		
		readString = getFile("input.txt");
		this.mazeFrame = mazeFrame;
		this.inputDataPanel = mazeFrame.getInputDataPanel();

		count = 0;
		// store each line in an array named lines
		lines = readString.split("\\r?\\n");
		init();
	}

	// Reads a file and returns the contents of a file as a string
	public String getFile(String filename) {
		
		
		// Get file from resources folder
		StringBuilder result = new StringBuilder("");
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(filename).getFile());
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public void init() {
		ActionListener taskPerformer = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				gridView.repaint();
				performAction(count, lines);
				if (count == lines.length)
					t.stop();
				gridView.repaint();
				count++;
			}
		};

		t = new Timer(delay, taskPerformer);
		t.start();
	}

	public void performAction(int i, String[] lines) {
		if (i < lines.length) {

			//Display the current input based on which agent will perform the required action
			inputDataPanel.getInputLabel().setText(lines[i]);
			// store the move action value and eat value in an array
			String moveAction[] = lines[i].split(",");
			double eatAction = Double.parseDouble(moveAction[1]);
			if (eatAction == 0.0) {
				if (Double.parseDouble(moveAction[0]) <= Constants.BOUND_ONE) {
					AgentActions.moveUp();
				} else if (Double.parseDouble(moveAction[0]) >= Constants.BOUND_ONE
						&& Double.parseDouble(moveAction[0]) <= Constants.BOUND_TWO) {
					AgentActions.moveDown();
				} else if (Double.parseDouble(moveAction[0]) >= Constants.BOUND_TWO
						&& Double.parseDouble(moveAction[0]) <= Constants.BOUND_THREE) {
					AgentActions.moveLeft();
				} else {
					AgentActions.moveRight();
				}
			} else {
				AgentActions.eat();
			}
		}
	}
}
