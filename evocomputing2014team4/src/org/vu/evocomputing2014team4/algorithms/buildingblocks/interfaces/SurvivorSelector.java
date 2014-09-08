package org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces;

import org.vu.evocomputing2014team4.algorithms.datastructures.Population;

/**
 * Implementing classes should define a strategy for selecting survivors, 
 * which may depend on the population (genotypes)
 * @author tbosman
 *
 */
public interface SurvivorSelector {
	public Population selectSurvivors(Population population, int desiredPopulationSize);
}
