package org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces;

import org.vu.evocomputing2014team4.algorithms.datastructures.Population;

/**
 * Classes should generate an initial population
 * @author tbosman
 *
 */
public interface Initialiser {

	public Population initialisePopulation(int size);
}
