package org.vu.evocomputing2014team4.algorithms.datastructures;
/**
 * an individual representing a genotype and a fitness, which are immutable
 * @author tbosman
 *
 */
public class Individual implements Comparable<Individual>, GenomeCarrier{

	public final Genome genome; 
	public Genome getGenome() {
		return genome;
	}


	public final double fitness;
	public Individual(Genome genome, double fitness) {
		this.genome = genome; 
		this.fitness = fitness; 
	}
	@Override
	public int compareTo(Individual o) {
		return  (int) Math.signum((this.fitness - o.fitness));
	}
	
	
	public String toString() {
		return genome.toString() + " - f: " + fitness;
	}

}
