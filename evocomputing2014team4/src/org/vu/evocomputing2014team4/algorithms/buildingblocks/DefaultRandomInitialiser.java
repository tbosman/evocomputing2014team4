package org.vu.evocomputing2014team4.algorithms.buildingblocks;

import org.vu.evocomputing2014team4.algorithms.RandomSampler;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.FitnessFunction;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.Initialiser;
import org.vu.evocomputing2014team4.algorithms.datastructures.Embryo;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome.CrossoverType;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome.GenomeBuilder;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome;
import org.vu.evocomputing2014team4.algorithms.datastructures.Individual;
import org.vu.evocomputing2014team4.algorithms.datastructures.Population;

public class DefaultRandomInitialiser implements Initialiser {
	FitnessFunction fitnessFunction; 
	private Population population;
	double minValue = -5;
	double maxValue = 5; 
	
	double[][] defaultAlpha = new double[10][10];
	double _defaultSigma = 0.5;
	double[] defaultSigma; 
	public double defaultEpsilon0 = 0.01; 
	public double defaultEpsilonMax = 1	;
	double defaultTau1 = 0.5;
	double defaultTau2 = 0.01;
	double defaultTauPrime = 0.05; 
	double defaultBeta = 0.01;
	double defaultPi = 0.01;
	
	public boolean defaultCrossoverSet = false;
	
	public CrossoverType defaultCrossover = CrossoverType.NONE; 
	
	public DefaultRandomInitialiser(FitnessFunction fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
		this.population = new Population();
		defaultSigma = new double[10];
		for(int i =0; i<10 ; i++) {
			defaultSigma[i] = _defaultSigma;
		}
	}

	
	@Override
	public Population initialisePopulation(int size) {
		this.population = new Population();
		for(int j=0; j<size;j++) {
			double[] value = new double[10];
			for(int i=0;i<10;i++) {
				value[i] = RandomSampler.getUniform()*10-5;
			}
			addNewWithValue(value);
		}
		
		
		return this.population;
	}
	
	
	private void addNewWithValue(double[] value) {
		double tauUsed; 
		if(RandomSampler.getUniform() < 0.5) {
			tauUsed = defaultTau1;
		}else {
			tauUsed = defaultTau2;
		}
		
		
		CrossoverType crossoverType;
		if(defaultCrossoverSet) {
			crossoverType = this.defaultCrossover;
		}else {
			if(RandomSampler.getUniform() < 0.25) {
				crossoverType = CrossoverType.LOCAL_INTERMEDIARY;
			}else if(RandomSampler.getUniform() < 0.33) {
				crossoverType = CrossoverType.LOCAL_DISCRETE;
			}else {
				crossoverType = CrossoverType.NONE;
			}
		}
		
		Genome genome = new Genome.GenomeBuilder().			
				setValue(value).
				setAlpha(defaultAlpha).
				setBeta(defaultBeta).
				setEpsilon0(defaultEpsilon0).
				setEpsilonMax(defaultEpsilonMax).
				setPi(defaultPi).
				setSigma(defaultSigma).
				setTau(tauUsed).
				setTauPrime(defaultTauPrime).
				setCrossoverType(CrossoverType.NONE).
				createGenome();
		Individual newIndividual =(new Embryo(genome)).birth(fitnessFunction); 
		population.add(newIndividual);
		
//		System.out.print("Added: ");
//		for(int i=0; i<10; i++) {
//			System.out.print(", "+genome.value[i]);
//		}
//		System.out.println(" - fitness: "+ newIndividual.fitness);
	}

}
