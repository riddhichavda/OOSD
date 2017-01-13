package ann_integration;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

public class ExperimentCreatorFitnessFunction { //implements BulkFitnessFunction, Configurable {

	//TODO use the network output to update
	public static void performAction( double[] motorData) {
		System.out.println(  "   ...Performing action by agent on enviornment" );

	}
	private @Getter @Setter boolean enableDisplay=false;
	private @Getter @Setter int maxTimesteps = 50;
	
	private @Getter @Setter int numTrials = 10;

	
	//TODO use this to set the display mode to be enabled 
	public void enableDisplay() {
		setEnableDisplay(true);
	}


	public void evaluate( List genotypes ) {
		// evaluate each chromosome
		Iterator it = genotypes.iterator();
		while ( it.hasNext() ) {
			MockChromosome c = (MockChromosome) it.next();
			evaluate( c );
		}
	}

	public void evaluate( MockChromosome c ) {
		System.out.println("Evaluating "+ c.getName() );
		try {
			//Activator activator = factory.newActivator( c );
			MockActivator activator = new MockActivator( c );
			
			int fitness = 0;
			for ( int i = 0; i < numTrials; i++ ) {
				int value = singleTrial( activator ); 
				fitness += value;
			}
			c.setFitnessValue( fitness );
		}
		catch ( Throwable e ) {
			//logger.warn( "error evaluating chromosome " + c.toString(), e );
			System.out.println( "error evaluating chromosome " );
			c.setFitnessValue( 0 );
		}
	}

	//TODO REPLACE with actual extraction of fitness
	private int getFinalFitnessValue() {
		Random r= new Random();
		int fitness = r.nextInt(10);
		System.out.println( "   Final fitness value= " + fitness );
		return fitness;
	}


	//TODO  extract the sensor data from the experiment
	private double[] getSensorData() {
		System.out.println( "   ...Extracting sensor data from enviornment" );
		return null;
	}


	//TODO you can use a method like this to load an XML experiment
	public void init( String filepath ) {
		//use this to load experiment from file

	}

	private int singleTrial( MockActivator activator ) {
		int fitness = 0;
		int currentTimestep = 0;
		
		// Network activation SENSOR values
		double[] sensorData;
		
		for ( currentTimestep = 0; currentTimestep < maxTimesteps; currentTimestep++ ) {
			//read the sensor information from experiment
			sensorData = getSensorData();

			// Activate the network.
			double[] motorData = activator.next( sensorData );

			//Use the motor data to update the enviornment
			performAction( motorData );
			
			if ( enableDisplay ) {
				updateDisplay();
			}
		}
		
		fitness = getFinalFitnessValue();		
		
		return fitness;
	}


	//TODO implement means of updating GUI
	//This can be used to repaint the GUI, 
	//this should only be called if it is enabled
	private void updateDisplay() {
		
	}


}
