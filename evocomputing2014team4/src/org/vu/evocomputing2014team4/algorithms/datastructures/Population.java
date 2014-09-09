package org.vu.evocomputing2014team4.algorithms.datastructures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Population implements  List<Individual>{

	private List<Individual> individuals = new ArrayList<Individual>();
	public Population() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean add(Individual individual) {
		return individuals.add(individual);
	}
	
	public int size() {
		return individuals.size();
	}

	public boolean isEmpty() {
		return individuals.isEmpty();
	}

	public boolean contains(Object o) {
		return individuals.contains(o);
	}

	public Object[] toArray() {
		return individuals.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return individuals.toArray(a);
	}

	public boolean remove(Object o) {
		return individuals.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return individuals.containsAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		return individuals.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return individuals.retainAll(c);
	}

	public void clear() {
		individuals.clear();
	}

	public boolean equals(Object o) {
		return individuals.equals(o);
	}

	public int hashCode() {
		return individuals.hashCode();
	}

	public Individual get(int index) {
		return individuals.get(index);
	}

	public Individual set(int index, Individual element) {
		return individuals.set(index, element);
	}

	public Individual remove(int index) {
		return individuals.remove(index);
	}

	public int indexOf(Object o) {
		return individuals.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return individuals.lastIndexOf(o);
	}

	public ListIterator<Individual> listIterator() {
		return individuals.listIterator();
	}

	public ListIterator<Individual> listIterator(int index) {
		return individuals.listIterator(index);
	}

	public List<Individual> subList(int fromIndex, int toIndex) {
		return individuals.subList(fromIndex, toIndex);
	}

	@Override
	public Iterator<Individual> iterator() {
		return individuals.iterator();
	}
	
	
	public Normalizer getNormalizer() {
		return new Normalizer(getMinimumFitness(), getMaximumFitness());
	}
	
	public double getNormalisedSummedFitness() {
		double summedFitness = 0; 

		Normalizer norm = getNormalizer(); 
		
		for(Individual i : individuals) {			
			summedFitness += norm.normalizedFitness(i);
		}
		return summedFitness;
	}
	public double getMinimumFitness() {
		Collections.sort(individuals);
		return individuals.get(0).fitness;
	}
	
	public double getMaximumFitness() {
		Collections.sort(individuals);
		return individuals.get(individuals.size()-1).fitness;
	}

	@Override
	public boolean addAll(Collection<? extends Individual> c) {
		return individuals.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Individual> c) {
		return individuals.addAll(index, c);
	}

	@Override
	public void add(int index, Individual element) {
		individuals.add(index, element);
	}
	
}
