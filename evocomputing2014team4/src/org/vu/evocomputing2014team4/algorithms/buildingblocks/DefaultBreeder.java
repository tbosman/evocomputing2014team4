package org.vu.evocomputing2014team4.algorithms.buildingblocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.Breeder;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.MonogamousCrossover;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.ParentSelector;
import org.vu.evocomputing2014team4.algorithms.datastructures.Embryo;
import org.vu.evocomputing2014team4.algorithms.datastructures.Individual;
import org.vu.evocomputing2014team4.algorithms.datastructures.Population;

public class DefaultBreeder implements Breeder {

	public DefaultBreeder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Embryo> breed(Population currentGeneration, int numOffspring) {
//		ParentSelector parentSelector = new FitnessProportionateParentSelector();
		ParentSelector parentSelector = new RankingParentSelector();
		Iterator<Individual> parents = parentSelector.selectParentsFrom(currentGeneration, numOffspring*2).iterator();//every child has 2 parents
		
		//For each 2 parents do monogamous crossover 
		//Put into list of embryos 
		MonogamousCrossover cross = new DefaultMonogamousCrossover();
		ArrayList<Embryo> embryoPool = new ArrayList<Embryo>();
		while(parents.hasNext()) {
			Individual mother = parents.next();
			Individual father = parents.next();
			embryoPool.add(cross.mate(mother, father));
		}
		
		return embryoPool;
	}

}
