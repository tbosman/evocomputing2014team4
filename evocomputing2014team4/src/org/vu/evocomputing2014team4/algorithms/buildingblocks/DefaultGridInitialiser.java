package org.vu.evocomputing2014team4.algorithms.buildingblocks;

import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.FitnessFunction;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.Initialiser;
import org.vu.evocomputing2014team4.algorithms.datastructures.Embryo;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome.CrossoverType;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome.GenomeBuilder;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome;
import org.vu.evocomputing2014team4.algorithms.datastructures.Population;

public class DefaultGridInitialiser implements Initialiser {
	FitnessFunction fitnessFunction; 
	private Population population;
	double minValue = -5;
	double maxValue = 5; 
	
	double[][] defaultAlpha = new double[10][10];
	double _defaultSigma = 0.5;
	double[] defaultSigma; 
	double defaultEpsilon0 = 0.01; 
	
	double defaultTau = 0.1; 
	double defaultTauPrime = 0.01; 
	double defaultBeta = 0.01;
	double defaultPi = 0.01;
	
	
	public DefaultGridInitialiser(FitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
		this.population = new Population();
		defaultSigma = new double[10];
		for(int i =0; i<10 ; i++) {
			defaultSigma[i] = _defaultSigma;
		}
	}

	
	/**
	 * make sure size is some n^10 for some n \in N
	 * as the number of points per element is going to be floor(10th_root(size))
	 * grid is layed out like this: 
	 * - stepsize = 10 / numPerElement 
	 * - points are layed out in steps of -5 + 0.5*stepsize + i*stepsize 
	 * - for i = 0,..., numPerElement-1 
	 */
	@Override
	public Population initialisePopulation(int size) {
		int numPerElement = (int) Math.floor(Math.pow(size, 0.1));
		double stepSize = 10.0/numPerElement; 
		recursivelyFillPopulation(0, stepSize, numPerElement, null);
		return this.population;
	}
	
	private void recursivelyFillPopulation(int index, double stepSize, int numPerElement, double[] value) {
		if( index == 0 ) {
			value = new double[10];
		}
		for(int i=0; i<numPerElement; i++) {
			value[index] = minValue + 0.5*stepSize + i*stepSize;
			if(index<9) {
				recursivelyFillPopulation(index+1, stepSize, numPerElement, value);
			}else {
				addNewWithValue(value);
			}
		}
	}
	
	private void addNewWithValue(double[] value) {
		Genome genome = new Genome.GenomeBuilder().			
				setValue(value).
				setAlpha(defaultAlpha).
				setBeta(defaultBeta).
				setEpsilon0(defaultEpsilon0).
				setPi(defaultPi).
				setSigma(defaultSigma).
				setTau(defaultTau).
				setTauPrime(defaultTauPrime).
				setCrossoverType(CrossoverType.NONE).
				createGenome();
		population.add((new Embryo(genome)).birth(fitnessFunction));
		
	}

}
