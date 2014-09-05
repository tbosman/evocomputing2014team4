package org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces;

import org.vu.evocomputing2014team4.algorithms.datastructures.Embryo;
import org.vu.evocomputing2014team4.algorithms.datastructures.Individual;
import org.vu.evocomputing2014team4.algorithms.datastructures.Population;

/**
 * Global crossover using entire Population. Any chromosomes not used should be taken from the mother.
 * @author tbosman
 *
 */
public interface PolygamousCrossover {

	public Embryo mate(Individual mother, Population matingPool);
}
