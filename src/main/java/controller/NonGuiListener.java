package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import lombok.extern.java.Log;
import model.Agent;
import view.GridView;
import view.InputPanel;

@Log
public class NonGuiListener implements ActionListener {
	private GridView gridView;
	private InputPanel inputPanel;

	public NonGuiListener(InputPanel inputPanel, GridView gridView) {
		this.inputPanel = inputPanel;
		this.gridView = gridView;
		inputPanel.addNotGuiListener(this);
	}

	public void action(double x) {
		if (x == 1) {
			AgentActions.moveUp();
		} else if (x == 2) {
			AgentActions.moveDown();
		} else if (x == 3) {
			AgentActions.moveLeft();
		} else if (x == 4) {
			AgentActions.moveRight();
		} else if (x == 5) {
			AgentActions.moveRight();
		} else {
			log.warning("Invalid Input");
		}

		AgentActions.eat();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			log.info("Reading from Console");
			Scanner scan = new Scanner(System.in);
			File file = new File("input1.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			int count = 0;
			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			String line = "";
			while (scan.hasNext()) {
				line = scan.nextLine();
				bw.write(line);
				log.info("Input received from System : " + line);
				String[] str = line.split(" ");
				Double[] input = new Double[str.length];
				for (int i = 0; i < str.length; i++) {
					input[i] = Double.parseDouble(str[i]);
				}

				// If input received from multiple nodes
				if (input.length > 1) {
					Arrays.sort(input, Collections.reverseOrder());
				}
				Agent singleAgent = gridView.getSingleAgent();
				double x = input[0];
				int pos = singleAgent.getPos();
				log.info("Initial agent position " + pos);
				action(x);
				log.info("Final agent position " + pos);
				String content = "";
				content += generateOutput(pos);
				log.info("Content " + content);
				writeOutput(content);
				bw.flush();
				bw.newLine();
				count++;
				if (count == 100) {
					break;
				}
			}
			bw.close();
		}

		catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public String generateOutput(int pos) {

		StringBuilder sb = new StringBuilder();
		// UP
		if (gridView.getFinalizedMazeCells().contains(pos - AgentActions.getMazeWidth()))
			sb.append("1 ");

		// DOWN
		if (gridView.getFinalizedMazeCells().contains(pos + AgentActions.getMazeWidth()))
			sb.append("2 ");
		// LEFT
		if (gridView.getFinalizedMazeCells().contains(pos - 1) && (pos % AgentActions.getMazeWidth() != 0))
			sb.append("3 ");
		// RIGHT

		if (gridView.getFinalizedMazeCells().contains(pos + 1) && (pos + 1) % AgentActions.getMazeWidth() != 0)
			sb.append("4 ");

		if (AgentActions.checkEat()) {
			sb.append("5 ");
		}
		return sb.toString().trim();
	}

	public void writeOutput(String content) {
		try {

			File file = new File("output1.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.newLine();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}