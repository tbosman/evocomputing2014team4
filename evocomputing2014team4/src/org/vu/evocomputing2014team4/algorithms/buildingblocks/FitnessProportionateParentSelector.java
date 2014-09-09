package org.vu.evocomputing2014team4.algorithms.buildingblocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.vu.evocomputing2014team4.algorithms.RandomSampler;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.ParentSelector;
import org.vu.evocomputing2014team4.algorithms.datastructures.Individual;
import org.vu.evocomputing2014team4.algorithms.datastructures.Normalizer;
import org.vu.evocomputing2014team4.algorithms.datastructures.Population;
/**
 * Selects parents fitness proportionate with replacement 
 * @author tbosman
 *
 */
public class FitnessProportionateParentSelector implements
		ParentSelector {

	public FitnessProportionateParentSelector() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Individual> selectParentsFrom(Population population,
			int numParents) {
		double summedFitness = population.getNormalisedSummedFitness();
		double[] random = RandomSampler.getNUniform(numParents);
		Arrays.sort(random);		
		ArrayList<Individual> parents = new ArrayList<Individual>(numParents);
		
		Iterator<Individual> populationIterator = population.iterator();
		Individual current = populationIterator.next();
		current = population.get(0);
		int popIdx = 0;
		Normalizer norm = population.getNormalizer();
		double cumulativeFitness = norm.normalizedFitness(current);
		for(double rnd : random) {
			double targetFitness = rnd*summedFitness;
			while(cumulativeFitness < targetFitness) {
				current = population.get(++popIdx);
				cumulativeFitness += norm.normalizedFitness(current);
			}
			parents.add(current);
			
		}
		
		Collections.shuffle(parents, RandomSampler.random);
		return parents;
	}

}
