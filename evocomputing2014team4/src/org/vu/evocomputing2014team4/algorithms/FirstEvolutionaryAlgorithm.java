package org.vu.evocomputing2014team4.algorithms;

import org.vu.evocomputing2014team4.algorithms.buildingblocks.DefaultFitnessFunction;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.DefaultGridInitialiser;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.FitnessFunction;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.Initialiser;
import org.vu.evocomputing2014team4.algorithms.datastructures.Population;

public class FirstEvolutionaryAlgorithm extends AbstractEvolutionaryAlgorithm {

	private int populationSize;
	private int offSpringSize; 
	private Initialiser initialiser; 
	private FitnessFunction fitnessFunction;
	
	public FirstEvolutionaryAlgorithm(int populationSize, int offspringSize) {
		super();
		this.populationSize = populationSize;
		this.offSpringSize = offSpringSize;
		
		
	}

	@Override
	public void run() {
		Population initialPopulation = initialiser.initialisePopulation(populationSize);		
	}

	
	private void init() {
		this.fitnessFunction = new DefaultFitnessFunction(evaluation_);
		this.initialiser = new DefaultGridInitialiser(fitnessFunction);
	}
	
	@Override
	public void onSetEvaluation() {
		init();		
	}

}
