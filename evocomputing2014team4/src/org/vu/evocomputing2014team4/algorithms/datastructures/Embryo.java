package org.vu.evocomputing2014team4.algorithms.datastructures;

import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.FitnessFunction;


/**
 * Embryo representing a genotype which can still be changed (mutate) 
 * Can be turned into an individual through the birth method,
 * at which genotype is fixed and fitness is definitively evaluated. 
 * @author tbosman
 *
 */
public class Embryo implements GenomeCarrier{
	private Genome genome; 
	public Embryo(Genome genome) {		
		this.setGenome(genome); 
	}
	public Genome getGenome() {
		return genome;
	}
	public void setGenome(Genome genome) {
		this.genome = genome;
	}

	public Individual birth(FitnessFunction fitnessFunction) {
		double fitness = fitnessFunction.fitness(getGenome());
//		System.out.println(getGenome().toString() + " - f: " + fitness);
		return new Individual(getGenome(), fitness);
	}
}
