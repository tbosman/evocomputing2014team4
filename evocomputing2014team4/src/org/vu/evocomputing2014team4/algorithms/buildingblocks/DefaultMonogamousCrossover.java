package org.vu.evocomputing2014team4.algorithms.buildingblocks;

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
		double[] value = mother; 
		for(int i =0; i<value.length; i++) {
			value[i] += father[i];
			value[i] /= 2;
		}
		return value;
	}
	
	@Override
	public Embryo mate(Individual mother, Individual father) {
		double value[] = averageArrays(mother.genome.value, father.genome.value);
		double sigma[] = averageArrays(mother.genome.sigma, father.genome.sigma);
		Genome newGenome = new Genome.GenomeBuilder(mother.genome).
				setValue(value).
				setSigma(sigma).
				createGenome();
		return new Embryo(newGenome);
	}

}
