package org.vu.evocomputing2014team4.algorithms.datastructures;

public class Normalizer {
	double minFitness;
	double maxFitness; 
	double epsilon = 0.1;//correction so that not all normalizedfitness become zero when min = maxfitness
	public Normalizer(double min, double max) {
		this.minFitness = min;
		this.maxFitness = max;
	}
	
	public double normalizedFitness(Individual i) {
		return (i.fitness - minFitness)/(maxFitness - minFitness) + epsilon;
	}

}
