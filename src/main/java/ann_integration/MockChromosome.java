package ann_integration;

import lombok.Getter;
import lombok.Setter;

public class MockChromosome {
	
	private @Setter @Getter double fitnessValue;
	private @Setter @Getter String name;
	
	public MockChromosome(String name) {
		this.name=name;
	}


}
