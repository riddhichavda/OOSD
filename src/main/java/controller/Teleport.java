package controller;

import model.Agent;

public class Teleport implements EventCommand{
	Agent agent;
	int pos;
	
	Teleport(Agent agent, int i ) {
		// TODO Auto-generated constructor stub
		this.agent = agent;
		this.pos = i;
	}
	
	@Override
	public String className() {
		// TODO Auto-generated method stub
		return "Teleport";
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		agent.setPos(pos);
	}

	@Override
	public void register() {
		// TODO Auto-generated method stub
		RegisterEventCommand rec = RegisterEventCommand.getInstance();
		rec.register(this);
	}

}
