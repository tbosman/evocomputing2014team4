package org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces;

import org.vu.evocomputing2014team4.algorithms.datastructures.Genome;

/**
 * interface that should be implement for any mutation strategy 
 * @author tbosman
 *
 */
public interface Mutator {

	public Genome mutate(Genome genome);

	public abstract void cool();

	public abstract void heat();

	public abstract double getTemp();
	

}
