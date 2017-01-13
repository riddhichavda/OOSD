package controller;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JOptionPane;

import constants.Constants;
import model.Agent;
import model.GridCell;
import model.Reward;
import view.ButtonPanel;
import view.GridView;
import view.InputPanel;
import view.MazeFrame;

public class BtnCreateController implements ActionListener {
	private Agent agent;
	private ButtonPanel buttonPanel;
	private GridCell gridCell;
	private GridView gridView;
	private InputPanel inputPanel;
	int mazeLength = 0;
	int mazeWidth = 0;

	public BtnCreateController(InputPanel inputPanel, GridView gridView, ButtonPanel buttonPanel) {
		this.inputPanel = inputPanel;
		this.gridView = gridView;
		this.buttonPanel = buttonPanel;
		inputPanel.addBtnCreateListener(this);
		buttonPanel.addBtnCreateListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Create Grid") {
			if (!inputPanel.getRowInput().getText().equals("") || !inputPanel.getColInput().getText().equals("")) {
				int res[] = new int[2];
				res[0] = Integer.parseInt(inputPanel.getRowInput().getText());
				res[1] = Integer.parseInt(inputPanel.getColInput().getText());
				gridCell = new GridCell(res[0], res[1]);
				Rectangle rectangle[] = new Rectangle[res[0] * res[1]];
				int x = 0;
				int y = 0;
				int count = 0;
				int rowInc = (Constants.RIGHTPANELHEIGHT - 2 * Constants.ROWOFFSET) / res[0];
				int colInc = (Constants.RIGHTPANELWIDTH - 2 * Constants.COLOFFSET) / res[1];
				mazeLength = res[0];
				mazeWidth = res[1];
				AgentActions.setMazeLength(mazeLength);
				AgentActions.setMazeWidth(mazeWidth);
				AgentActions.setGridCell(gridCell);
				for (int i = 0; i < rectangle.length; i++) {
					rectangle[i] = new Rectangle(x, y, colInc, rowInc);
					x = x + colInc;
					count++;
					if (count % res[1] == 0) {
						count = 0;
						x = 0;
						y = y + rowInc;
					}

				}
				gridCell.setMazeCell(rectangle);
				gridView.setMazeCell(rectangle);
				gridView.setGridCell(gridCell);
				gridView.mazeSetFlag = true;
				gridView.setVisibility(gridCell.getVisibility());
				gridView.repaint();

			}
		}
		if (e.getActionCommand() == "Finalize Maze") {
			gridView.mazeFinalized = true;

		}

		if (e.getActionCommand() == "Save XML") {
			String filePath = inputPanel.chooseSaveFile();
			Agent singleAgent = gridView.getSingleAgent();
			XMLController.saveLayoutXML(gridCell, filePath, singleAgent);
		}

		if (e.getActionCommand() == "Load XML") {
			String filePath = inputPanel.chooseOpenFile();
			if (!filePath.equals("")) {
				int status = XMLController.loadLayoutXML(filePath, gridView);
				if (status == 0) {
					gridCell = gridView.getGridCell();
					agent = gridView.getSingleAgent();
					gridView.repaint();
				} else
					JOptionPane.showMessageDialog(inputPanel.getRootPane(), "XMl is not a GasNeat Project");
			}
		}

		if (e.getActionCommand() == "Launch with UI : Manual Input") {
			gridView.setInUIMode(true);
			MazeFrame mazeFrame = new MazeFrame(gridView, buttonPanel);
			System.out.println("Agent set :" + agent);
			RegisterEventCommand.getInstance().setEnvironment(gridCell, agent);
		}
		if (e.getActionCommand() == "Launch with UI : File Input") {
			gridView.setInUIMode(true);
			// MazeFrame mazeFrame = null;
			// try {
			// mazeFrame = new MazeFrame(gridView.getClass().newInstance(),
			// buttonPanel);
			// } catch (InstantiationException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// } catch (IllegalAccessException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			MazeFrame mazeFrame = new MazeFrame(gridView, buttonPanel);
			FileController fileController = new FileController(mazeFrame, gridView);
			RegisterEventCommand.getInstance().setEnvironment(gridCell, agent);
		}
		if (e.getActionCommand() == "Add Reward") {
			if (!inputPanel.getRewardValue().getText().equals("")) {
				String rewardType = inputPanel.getRewardType().getSelectedItem().toString();
				System.out.println(rewardType);
				double reward = Double.parseDouble(inputPanel.getRewardValue().getText());
				System.out.println(reward);
				int pos = gridView.getPos();
				Reward localReward = new Reward(rewardType, reward);
				// System.out.println(gr);
				gridCell.getReward().put(pos, localReward);
				gridView.addGridCell(gridCell);
				gridView.repaint();
			}
		}

		if (e.getActionCommand() == "Clear Selection") {
			// Arrays.fill(gridView.getVisibility(), false);
			gridView.clearSelection();
			gridView.mazeFinalized = false;
			gridView.repaint();
		}

		if (e.getActionCommand() == "Add Agent") {
			int pos = gridView.getPos();
			if (gridView.getVisibility()[pos]) {
				agent = new Agent(pos);
				gridView.addAgent(agent);
				gridView.repaint();
			}
			inputPanel.getBtnSaveXML().setEnabled(true);
		}

		if (e.getActionCommand() == "UP") {
			AgentActions.moveUp();
			gridView.repaint();
		}

		if (e.getActionCommand() == "DOWN") {
			AgentActions.moveDown();
			gridView.repaint();
		}

		if (e.getActionCommand() == "LEFT") {
			AgentActions.moveLeft();
			gridView.repaint();
		}

		if (e.getActionCommand() == "RIGHT") {
			AgentActions.moveRight();
			gridView.repaint();
		}

		if (e.getActionCommand() == "EAT") {
			AgentActions.eat();
			gridView.repaint();
		}

		if (e.getActionCommand().equals("Save Cell Type")) {
			int cellNo = gridView.getSelectedRectangle();
			// System.out.println("Cell No :"+cellNo);
			HashSet<String> cellProperties = new HashSet<String>();
			if (!inputPanel.getCellType().isSelectionEmpty()) {
				cellProperties.addAll((ArrayList<String>) inputPanel.getCellType().getSelectedValuesList());
				if (!gridCell.getCellProperty().containsKey(cellNo))
					gridCell.getCellProperty().put(cellNo, cellProperties);
			}

		}
		if (e.getActionCommand().equals("Save Cell Event")) {
			int cellNo = gridView.getSelectedRectangle();
			System.out.println("Cell No :" + cellNo);
			HashSet<String> cellProperties = new HashSet<String>();
			if (!inputPanel.getCellEvent().isSelectionEmpty()) {
				cellProperties.addAll((ArrayList<String>) inputPanel.getCellEvent().getSelectedValuesList());
				if (gridCell.getCellProperty().containsKey(cellNo)
						&& gridCell.getCellProperty().get(cellNo).contains("Home")) {
					JOptionPane.showMessageDialog(inputPanel.getRootPane(), "Cannot Assign Event to Home Cell");
				}
				gridCell.getCellEvents().put(cellNo, cellProperties);
			}

		}

		if (e.getActionCommand().equals("Test")) {
			for (Integer i : gridCell.getCellProperty().keySet()) {
				System.out.println("Row :" + i + " , val : " + gridCell.getCellProperty().get(i).toString());
			}
			for (Integer i : gridCell.getCellEvents().keySet()) {
				System.out.println("Row :" + i + " , val : " + gridCell.getCellEvents().get(i).toString());
			}
		}
	}

}
