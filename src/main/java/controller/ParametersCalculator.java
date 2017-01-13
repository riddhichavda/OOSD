package controller;

import java.util.Arrays;

import constants.Constants.REWARD_TYPES;
import lombok.Getter;
import lombok.Setter;
import model.GridCell;

public class ParametersCalculator {
	// the object of this class is created in Controller class

	/*
	 * CURRENT CALCULATIONS OF PARAMETERS: parametersCalculator is a singleton
	 * class object fitnessScore = increases by 0.4 after every agent action =
	 * increases by reward value too if reward is present energyLevel =
	 * decreases by 1 after every agent action = increases by reward value too
	 * if reward is present timeSteps = increases by 1 after every agent action
	 * resourceLevel= is an array of all types of resources(food,water) = types
	 * defined in Constants class = keeps track of current level of each of the
	 * types of resources
	 */

	private static @Setter @Getter double energyLevel;
	private static @Setter @Getter double fitnessScore;
	private static GridCell gridCell;
	private static ParametersCalculator parametersCalculator;
	private static @Setter @Getter double[] resourcesLevel;
	private static @Setter @Getter double timeSteps;

	public static void displayParameters() {

		System.out.println("Fitness Score:" + fitnessScore);
		System.out.println("Energy Level:" + energyLevel);
		System.out.println("Resources Level:" + Arrays.toString(resourcesLevel));
		System.out.println("TimeSteps:" + timeSteps + "\n");
	}

	public static void updateParameters(GridCell gridCell, int pos) {
		gridCell = gridCell;
		if (gridCell.getReward().containsKey(pos)) {
			fitnessScore = fitnessScore + 0.4 + gridCell.getReward().get(pos).getValue();
			energyLevel = energyLevel + gridCell.getReward().get(pos).getValue() - 1;
		}

		for (int i = 0; i < REWARD_TYPES.values().length; i++) {
			if (gridCell.getReward().containsKey(pos)
					&& gridCell.getReward().get(pos).getType().equalsIgnoreCase(REWARD_TYPES.values()[i].toString())) {
				resourcesLevel[i] = resourcesLevel[i] + gridCell.getReward().get(pos).getValue();
			}

		}

		timeSteps = timeSteps + 1.0;

	}

	public ParametersCalculator() {
		resourcesLevel = new double[REWARD_TYPES.values().length];
		fitnessScore = 0.0;
		timeSteps = 0.0;
		energyLevel = 0.0;
	}

}
