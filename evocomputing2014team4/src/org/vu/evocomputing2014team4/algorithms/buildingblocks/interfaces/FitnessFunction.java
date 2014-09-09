package org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces;

import org.vu.contest.ContestEvaluation;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome;

/** 
 * classes implementing this interface must evaluate fitness using some criterion
 * 
 * @author tbosman
 *
 */
public interface FitnessFunction {
	public double fitness(Genome genome);
}
