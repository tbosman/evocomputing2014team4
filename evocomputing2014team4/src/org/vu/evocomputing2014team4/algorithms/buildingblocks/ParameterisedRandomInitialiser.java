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

public class ParameterisedRandomInitialiser implements Initialiser {
	FitnessFunction fitnessFunction; 
	private Population population;
	public double minValue = -5;
	public double maxValue = 5; 
	
	public int defaultPrecision = 0;
	
	double[][] defaultAlpha = new double[10][10];
	public double _defaultSigma = 0.1;
	double[] defaultSigma; 
	public double defaultEpsilon0 = 0.1; 
	public double defaultEpsilonMax = 0.5;
//	double defaultTau1 = 0.5;
//	double defaultTau2 = 0.01;
//	double defaultTau2 = 0.01;
//	double defaultTauPrime = 0.05; 
	double defaultTau1 = 1.0 / Math.sqrt(2*10);//as on slides
	double defaultTau2 = 2*defaultTau1;//
	double defaultTauPrime = 1.0 / Math.sqrt(2*Math.sqrt(10)); //as on slides
	double defaultBeta = 0.087;
	double defaultPi = Math.PI;
	
	public boolean defaultCrossoverSet = false;
	
	public CrossoverType defaultCrossover = CrossoverType.NONE;
	
	
	public ParameterisedRandomInitialiser(FitnessFunction fitnessFunction) {
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
				value[i] = RandomSampler.getUniform()*(maxValue-minValue)+minValue;
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
			double randSamp = RandomSampler.getUniform();
			if(randSamp < 0.25) {
				crossoverType = CrossoverType.LOCAL_INTERMEDIARY;
			}else if(randSamp < 0.5) {
				crossoverType = CrossoverType.LOCAL_DISCRETE;
			}else {
				crossoverType = CrossoverType.NONE;
			}
			
		}
		
		Genome genome = new Genome.GenomeBuilder().		
				setPrecision(defaultPrecision).
				setValue(value).
				setAlpha(defaultAlpha).
				setBeta(defaultBeta).
				setEpsilon0(defaultEpsilon0).
				setEpsilonMax(defaultEpsilonMax).
				setPi(defaultPi).
				setSigma(defaultSigma).
				setTau(tauUsed).
				setTauPrime(defaultTauPrime).
				setCrossoverType(crossoverType).
				createGenome();
		
		Individual newIndividual =(new Embryo(genome)).birth(fitnessFunction); 
		population.add(newIndividual);
		
	}

}
