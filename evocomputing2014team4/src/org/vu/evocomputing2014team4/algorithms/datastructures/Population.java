package org.vu.evocomputing2014team4.algorithms.datastructures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Population implements Iterable<Individual> {

	private List<Individual> individuals = new ArrayList<Individual>();
	public Population() {
		// TODO Auto-generated constructor stub
	}
	
	public void add(Individual individual) {
		individuals.add(individual);
	}
	
	public void addAll(Iterable<Individual> individuals){
		//TODO implement
		for(Individual i : individuals) {
			this.individuals.add(i);
		}
	}

	
	
	@Override
	public Iterator<Individual> iterator() {
		return individuals.iterator();
	}
	
	
}
