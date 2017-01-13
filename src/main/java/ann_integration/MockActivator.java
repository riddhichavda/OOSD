package ann_integration;

import java.util.Random;

public class MockActivator {

	public static final int INPUT_SIZE = 1;
	private MockChromosome chromosome;
	
	public MockActivator(MockChromosome chromosome) {
		this.chromosome = chromosome;
	}

	public String getName() {
		return chromosome.getName();
	}
	
	public double[] next( double[] sensorValues) {
		Random r = new Random();
		double[] motorValues = new double[INPUT_SIZE];
		for (int i=0; i< INPUT_SIZE; i++) {
			motorValues[i] = r.nextDouble();
		}
		return motorValues;
	}
}
