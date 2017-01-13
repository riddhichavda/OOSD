package model;

import lombok.Getter;
import lombok.Setter;

public class Reward {
	private @Setter @Getter String type;
	private @Setter @Getter double value;
	
	public Reward(){
		
	}
	public Reward(String type, double value){
		this.type = type;
		this.value = value;
	}
	
	@Override
	public String toString(){
		return "["  + type + "," + value +"]";
		
	}
 
}
