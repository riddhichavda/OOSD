package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import model.GridCell;
import model.Reward;

public class ShuffleRewards implements EventCommand {
	GridCell gc;

	public ShuffleRewards(GridCell gc) {
		// TODO Auto-generated constructor stub
		this.gc = gc;

	}

	@Override
	public String className() {
		// TODO Auto-generated method stub
		return "ShuffleRewards";
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		List<Reward> valueList = new ArrayList<Reward>(gc.getReward().values());
		Collections.shuffle(valueList);
		Integer valueIt = 0;
		for (Map.Entry<Integer, Reward> e : gc.getReward().entrySet()) {
			e.setValue(valueList.get(valueIt++));
		}
	}

	@Override
	public void register() {
		// TODO Auto-generated method stub
		RegisterEventCommand rec = RegisterEventCommand.getInstance();
	}

}
