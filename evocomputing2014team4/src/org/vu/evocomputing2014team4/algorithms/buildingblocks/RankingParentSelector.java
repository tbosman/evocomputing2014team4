package org.vu.evocomputing2014team4.algorithms.buildingblocks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.vu.evocomputing2014team4.algorithms.RandomSampler;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.ParentSelector;
import org.vu.evocomputing2014team4.algorithms.datastructures.Individual;
import org.vu.evocomputing2014team4.algorithms.datastructures.Population;

/**
 * Uses ranking only based parent selection
 * probabilities are linearly proportional to ranking
 * @author tbosman
 *
 */
public class RankingParentSelector implements ParentSelector {

	double p = 0.5;
	public RankingParentSelector() {
		// TODO Auto-generated constructor stub
	}
	public RankingParentSelector(double p) {
		this.p = p;
	}
	

	@Override
	public List<Individual> selectParentsFrom(Population population,
			int numParents) {
		Collections.sort(population);
		double[] randSamples = RandomSampler.getNUniform(numParents);
		Arrays.sort(randSamples);
		
		Population newPopulation = new Population();
		int idx = population.size()-1;
		double normConst = population.size()*(population.size()+1)/2.0;
		double cumProb = (idx+1)/normConst;		
		for(int i=0; i<numParents;i++) {		
			 
			while(cumProb < randSamples[i]) {
				idx--; 
				cumProb += (idx+1)/normConst; 
			}
			newPopulation.add(population.get(idx));
		}
		
		Collections.shuffle(newPopulation, RandomSampler.random);
		return newPopulation;
	}

}
