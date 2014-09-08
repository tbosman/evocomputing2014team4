package org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces;

import org.vu.evocomputing2014team4.algorithms.datastructures.Embryo;
import org.vu.evocomputing2014team4.algorithms.datastructures.Individual;

/**
 * Local Crossover between two individuals,
 *  any chromosomes not relevant for the crossover should be taken from the maternal genotype 
 * @author tbosman
 *
 */
public interface MonogamousCrossover {

	public Embryo mate(Individual mother, Individual father);
}
