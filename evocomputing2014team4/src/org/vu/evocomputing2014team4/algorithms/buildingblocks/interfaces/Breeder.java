package org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces;

import java.util.Set;

import org.vu.evocomputing2014team4.algorithms.datastructures.Embryo;
import org.vu.evocomputing2014team4.algorithms.datastructures.Population;

/**
 * Class that manages the crossover process for a given population  
 * @author tbosman
 *
 */
public interface Breeder {

	public Set<Embryo> breed(Population currentGeneration, int numOffspring);
}
