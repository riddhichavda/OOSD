package controller;

import java.util.HashSet;

import model.Agent;
import model.GridCell;

public class RegisterEventCommand {
	
	private volatile static RegisterEventCommand rec;
	public static RegisterEventCommand getInstance() {
		if (rec == null) {
			synchronized (RegisterEventCommand.class) {
				rec = new RegisterEventCommand();
			}
		}

		return rec;
	}
	private Agent agent;
	private HashSet<EventCommand > commandList;
	
	private GridCell gc;
	
	private RegisterEventCommand(){
		commandList = new HashSet<>();
	}
	
	public void checkEvent(int j ){
		EventCommand ec=null ;
		if(gc.getCellEvents().containsKey(j)){
			for (String s : gc.getCellEvents().get(j)){
				if (s.equals("HomeEvent")){
					int pos = -1;
					for (Integer  i : gc.getCellProperty().keySet()){
						if (gc.getCellProperty().get(i).contains("Home")){
							pos = i;
						}
					}
					
					ec = new HomeEvent(gc, agent,pos);
					ec.execute();
					
				}
				if (s.equals("ShuffleRewards")){
					ec = new ShuffleRewards(gc);
					ec.execute();
				}
			}
		}
	}
	public void deRegister(EventCommand ec){
		commandList.remove(ec);
	}
	
	public void register(EventCommand ec){
		commandList.add(ec);
	}
	
	public void setEnvironment(GridCell gc , Agent agent ){
		this.agent = agent;
		this.gc = gc;
	}
}
