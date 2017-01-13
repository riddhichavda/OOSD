package ann_integration;

import java.util.ArrayList;

public class ANNLauncher {
	
	public static void main(String[] args) {
		
		//Lets run 2 trials with our mock agent
		
		int trials = 2;
		int numberOfAgents = 5;
		int maxTimesteps = 5;
		
		ExperimentCreatorFitnessFunction fitnessFunction = new ExperimentCreatorFitnessFunction(); 
		
		fitnessFunction.setMaxTimesteps( maxTimesteps );
		fitnessFunction.setNumTrials(trials);
		
		ArrayList<MockChromosome> chromosomes = new ArrayList<MockChromosome>();
		for (int i=0; i <numberOfAgents; i++)  {
			chromosomes.add(new MockChromosome("Agent "+i) );
		}
		
		fitnessFunction.evaluate(chromosomes);
		
		System.out.println("---------------------------------------"  );
		System.out.println("           Final Results  "  );
		System.out.println("---------------------------------------"  );
		
		for (MockChromosome chromosome: chromosomes)  {
			System.out.println( chromosome.getName()+"  fitness= "+ chromosome.getFitnessValue() );
		}
		
		
	}
	

}
