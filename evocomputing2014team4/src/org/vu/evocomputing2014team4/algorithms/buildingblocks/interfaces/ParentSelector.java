package org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces;

import java.util.Iterator;
import java.util.List;

import org.vu.evocomputing2014team4.algorithms.datastructures.Individual;
import org.vu.evocomputing2014team4.algorithms.datastructures.Population;

public interface ParentSelector {

	public List<Individual> selectParentsFrom(Population population, int numParents);
}
