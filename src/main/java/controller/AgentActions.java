package controller;

import static constants.Constants.FOUR;
import static constants.Constants.ONE;
import static constants.Constants.THREE;
import static constants.Constants.TWO;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import lombok.Getter;
import lombok.Setter;
import model.Agent;
import model.GridCell;
import view.GridView;

public class AgentActions {
	private static AgentActions agentActions;
	private static @Setter GridCell gridCell;
	private static @Setter GridView gridView;
	private static @Setter int mazeLength;
	private static @Setter @Getter int mazeWidth;

	public static boolean checkEat() {
		boolean flag = false;
		Agent singleAgent = gridView.getSingleAgent();

		if (gridCell.getReward().get(singleAgent.getPos()).getValue() >= 0) {
			flag = true;
		}

		return flag;

	}

	public static void eat() {
		// here timeSteps are hardcoded. The experiment should end after a fixed
		// number of time steps
		if (ParametersCalculator.getTimeSteps() < 10) {
			Agent singleAgent = gridView.getSingleAgent();
			if (gridCell.getReward().containsKey(singleAgent.getPos())) {
				singleAgent
						.setPower(singleAgent.getPower() + gridCell.getReward().get(singleAgent.getPos()).getValue());
				gridCell.getReward().get(singleAgent.getPos()).setValue(0);
				ParametersCalculator.updateParameters(gridCell, singleAgent.getPos());

			}
		} else {
			// logic for ending the experiment
			ParametersCalculator.displayParameters();
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(gridView);
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
	}

	public static AgentActions getInstance() {
		if (agentActions == null) {
			agentActions = new AgentActions();
		}
		return agentActions;
	}

	public static int move(int pos, int key) {
		int newPos = -1;

		// UP
		if (key == 1) {
			if (gridView.getFinalizedMazeCells().contains(pos - mazeWidth))
				newPos = pos - mazeWidth;
		}
		// DOWN
		if (key == 2) {
			if (gridView.getFinalizedMazeCells().contains(pos + mazeWidth))
				newPos = pos + mazeWidth;
		}
		// LEFT
		if (key == 3) {
			if (gridView.getFinalizedMazeCells().contains(pos - 1) && (pos % mazeWidth != 0))
				newPos = pos - 1;
		}
		// RIGHT
		if (key == 4) {
			if (gridView.getFinalizedMazeCells().contains(pos + 1) && (pos + 1) % mazeWidth != 0)
				newPos = pos + 1;
		}
		return newPos > -1 ? newPos : pos;
	}

	public static void moveDown() {
		// here timeSteps are hardcoded. The experiment should end after a fixed
		// number of time steps
		if (ParametersCalculator.getTimeSteps() < 10) {
			Agent singleAgent = gridView.getSingleAgent();
			singleAgent.setPos(move(singleAgent.getPos(), TWO));
			if (!gridView.isInUIMode()) {
				RegisterEventCommand.getInstance().checkEvent(singleAgent.getPos());
				gridView.repaint();
			}
			gridView.setSingleAgent(singleAgent);
			ParametersCalculator.updateParameters(gridCell, singleAgent.getPos());

			gridView.repaint();
		} else {
			// logic for ending the experiment
			ParametersCalculator.displayParameters();
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(gridView);
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
	}

	public static void moveLeft() {
		// here timeSteps are hardcoded. The experiment should end after a fixed
		// number of time steps
		if (ParametersCalculator.getTimeSteps() < 10) {
			Agent singleAgent = gridView.getSingleAgent();
			singleAgent.setPos(move(singleAgent.getPos(), THREE));
			if (!gridView.isInUIMode()) {
				RegisterEventCommand.getInstance().checkEvent(singleAgent.getPos());
				gridView.repaint();
			}
			gridView.setSingleAgent(singleAgent);
			ParametersCalculator.updateParameters(gridCell, singleAgent.getPos());

			gridView.repaint();
		} else {
			// logic for ending the experiment
			ParametersCalculator.displayParameters();
		}
	}

	public static void moveRight() {
		// here timeSteps are hardcoded. The experiment should end after a fixed
		// number of time steps
		if (ParametersCalculator.getTimeSteps() < 10) {
			Agent singleAgent = gridView.getSingleAgent();
			singleAgent.setPos(move(singleAgent.getPos(), FOUR));
			if (!gridView.isInUIMode()) {
				System.out.println("Event Triggered");
				RegisterEventCommand.getInstance().checkEvent(singleAgent.getPos());
				gridView.repaint();
			}
			gridView.setSingleAgent(singleAgent);
			ParametersCalculator.updateParameters(gridCell, singleAgent.getPos());

			gridView.repaint();
		} else {
			// logic for ending the experiment
			ParametersCalculator.displayParameters();
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(gridView);
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
	}

	public static void moveUp() {
		// here timeSteps are hardcoded. The experiment should end after a fixed
		// number of time steps
		if (ParametersCalculator.getTimeSteps() < 10) {
			Agent singleAgent = gridView.getSingleAgent();
			singleAgent.setPos(move(singleAgent.getPos(), ONE));
			System.out.println("GVM :" + gridView.isInUIMode());
			if (!gridView.isInUIMode()) {
				System.out.println("Event Triggered");
				RegisterEventCommand.getInstance().checkEvent(singleAgent.getPos());
				gridView.repaint();
			}
			gridView.setSingleAgent(singleAgent);
			ParametersCalculator.updateParameters(gridCell, singleAgent.getPos());

			gridView.repaint();
		}

		else {
			ParametersCalculator.displayParameters();
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(gridView);
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			// logic for ending the experiment
			// file code to write the parameters from ParametersCalculator to a
			// final output file
		}

	}

	private AgentActions() {
	}
}
