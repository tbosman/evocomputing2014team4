package org.vu.evocomputing2014team4.algorithms.buildingblocks;

import org.vu.evocomputing2014team4.algorithms.RandomSampler;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.MonogamousCrossover;
import org.vu.evocomputing2014team4.algorithms.datastructures.Embryo;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome.CrossoverType;
import org.vu.evocomputing2014team4.algorithms.datastructures.Individual;

public class DefaultMonogamousCrossover implements MonogamousCrossover {
	CrossoverType type = CrossoverType.LOCAL_INTERMEDIARY;

	public DefaultMonogamousCrossover() {
		//TODO Maybe add crossover type specification
	}

	private double[] averageArrays(double[] mother, double[] father) {
		if(mother.length != father.length) {
			throw new RuntimeException("Trying to average arrays of unequal size.");
		}
		double[] value = new double[10]; 
		for(int i =0; i<value.length; i++) {
			value[i] = (mother[i]+father[i])/2;
			if(value[i] > 5) {
				System.out.println("#DBG avg out of boudn."+mother[i]+"-"+father[i]+"-"+value[i]);
			}
		}
		
		return value;
	}
	
	private Embryo average(Individual mother, Individual father) {
		double value[] = averageArrays(mother.genome.value, father.genome.value);
		double sigma[] = averageArrays(mother.genome.sigma, father.genome.sigma);
		Genome newGenome = new Genome.GenomeBuilder(mother.genome).
				setValue(value).
				setSigma(sigma).
				createGenome();
		return new Embryo(newGenome);
	}
	
	private Embryo discrete(Individual mother, Individual father) {
		double value[] = new double[10];
		double sigma[] = new double[10];
		for(int i=0;i<10;i++) {
			if(RandomSampler.getUniform() < 0.5) {
				value[i] = mother.genome.value[i];
			}else {
				value[i] = father.genome.value[i];
			}
			
			if(RandomSampler.getUniform() < 0.5) {
				sigma[i] = mother.genome.sigma[i];
			}else {
				sigma[i] = father.genome.sigma[i];
			}		
		}
		
		Genome newGenome = new Genome.GenomeBuilder(mother.genome).
				setValue(value).
				setSigma(sigma).
				createGenome();
		return new Embryo(newGenome);
	}
	
	@Override
	public Embryo mate(Individual mother, Individual father) {
				
		switch(mother.genome.crossoverType) {
		case LOCAL_INTERMEDIARY:
			return average(mother, father);
		case LOCAL_DISCRETE:
			return discrete(mother, father);
		default:
			return new Embryo(mother.genome);	
		}
		
		
	}

}
