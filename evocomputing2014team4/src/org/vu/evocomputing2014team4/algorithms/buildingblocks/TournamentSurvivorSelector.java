package org.vu.evocomputing2014team4.algorithms.buildingblocks;

import org.vu.evocomputing2014team4.algorithms.RandomSampler;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.SurvivorSelector;
import org.vu.evocomputing2014team4.algorithms.datastructures.Individual;
import org.vu.evocomputing2014team4.algorithms.datastructures.Population;

public class TournamentSurvivorSelector implements SurvivorSelector {
	private int tournamentSize;
	public TournamentSurvivorSelector(int tournamentSize) {
		this.tournamentSize = tournamentSize;
	}

	@Override
	public Population selectSurvivors(Population population,
			int desiredPopulationSize) {
	
		Population survivors = new Population(); 
		while(survivors.size() < desiredPopulationSize) {
			
			int[] randPerm = RandomSampler.getPermutation(population.size(), tournamentSize);
			int bestIndex = findBestIndexFrom(population, randPerm);
			survivors.add(population.get(bestIndex));
			population.remove(bestIndex);//Remove from old population to prevent multiple survival
			
		}
		
		//Restore survivors back into old population to not mutate inputdata
		population.addAll(survivors);
		
		return survivors;
	}

	private int findBestIndexFrom(Population population, int[] indices) {
		int best = 0;
		double bestFitness = population.get(indices[0]).fitness;
		for(int i=1; i<indices.length;i++) {
			if(population.get(indices[i]).fitness > bestFitness) {
				bestFitness = population.get(indices[i]).fitness;
				best = i;
			}
		}
		return best;
	}
	
}
