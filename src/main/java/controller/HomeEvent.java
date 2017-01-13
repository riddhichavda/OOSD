package controller;

import model.Agent;
import model.GridCell;

public class HomeEvent implements EventCommand{

	private Agent agent;
	private GridCell gc ;
	private int pos ;
	
	
	public HomeEvent(GridCell gc,Agent agent,int pos) {
		// TODO Auto-generated constructor stub
		this.gc = gc;
		this.agent = agent;
		this.pos = pos;
	}
	
	@Override
	public String className() {
		// TODO Auto-generated method stub
		return "HomeEvent";
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Teleport tele = new Teleport(agent, pos);
		ShuffleRewards sr = new ShuffleRewards(gc);
		sr.execute();
		tele.execute();
	}

	@Override
	public void register() {
		// TODO Auto-generated method stub
		RegisterEventCommand.getInstance().register(this);
	}
	
}
