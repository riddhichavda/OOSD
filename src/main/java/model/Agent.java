package model;

import lombok.Getter;
import lombok.Setter;

public class Agent {
	private @Getter @Setter int pos = -1;
	private @Getter @Setter double power = -1;

	public Agent() {
	}

	public Agent(int pos) {
		this.pos = pos;
	}
}
