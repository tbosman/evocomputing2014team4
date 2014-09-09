package org.vu.evocomputing2014team4.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.vu.evocomputing2014team4.algorithms.buildingblocks.*;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.KMeansClustering.Cluster;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.*;
import org.vu.evocomputing2014team4.algorithms.datastructures.Embryo;
import org.vu.evocomputing2014team4.algorithms.datastructures.GenomeCarrier;
import org.vu.evocomputing2014team4.algorithms.datastructures.Individual;
import org.vu.evocomputing2014team4.algorithms.datastructures.Population;

public class FirstEvolutionaryAlgorithm extends AbstractEvolutionaryAlgorithm {

	private int populationSize;
	private int offspringSize; 
	private Initialiser initialiser; 
	private FitnessFunction fitnessFunction;
	private Mutator mutator; 
	private int evalsLeft;


	private int TOURNAMENT_SIZE = 10;

	public FirstEvolutionaryAlgorithm(int populationSize, int offspringSize) {
		super();
		this.populationSize = populationSize;
		this.offspringSize = offspringSize;
		this.mutator = new DefaultMutator();


	}

	@Override
	public void run() {
		Population currentPopulation = initialiser.initialisePopulation(offspringSize);
		this.evalsLeft -= offspringSize;



		Breeder breeder = new DefaultBreeder();
		SurvivorSelector survivorSelector = new TournamentSurvivorSelector(offspringSize/TOURNAMENT_SIZE);
		currentPopulation = survivorSelector.selectSurvivors(currentPopulation, populationSize);
		int iteration = 1;
		while(evalsLeft > 0) {
			
			
			Iterable<Embryo> embryos = breeder.breed(currentPopulation, offspringSize);

			embryos = mutateAll(embryos);
			Population newPopulation = raiseAll(embryos);

			
			
			KMeansClustering clusterer = new KMeansClustering(50);
			
			List<Cluster> clusters = clusterer.findClusters(newPopulation);

//			System.out.println(clusters);
			
			
			int numClusters = 10; 
			
			boolean useHeuristicClusteringNumber = true; 
			if(useHeuristicClusteringNumber) {
				numClusters = 1+(evalsLeft/evals)*offspringSize/2;
			}
			
			if(isMultimodal() || !isRegular()) {
				survivorSelector = new KMeansClusteringSelector(numClusters);
			}
			
			newPopulation = survivorSelector.selectSurvivors(newPopulation, populationSize);

			

			
			currentPopulation = newPopulation;
			evalsLeft -= offspringSize;

			//			System.out.println("Iteration: "+iteration++ +" - best fitness: "+currentPopulation.getMaximumFitness());
		}


	}

	private Population raiseAll(Iterable<Embryo> embryos) {
		Population  newPopulation = new Population();
		for(Embryo current : embryos) {
			//			System.out.println("In raiseall: "+current.getGenome());
			newPopulation.add(current.birth(fitnessFunction));
		}

		return newPopulation;
	}

	private Iterable<Embryo> mutateAll(Iterable<Embryo> embryos) {
		ArrayList<Embryo> outEmbryos = new ArrayList<Embryo>();
		for(Embryo current : embryos) {
			//			current.setGenome(mutator.mutate(current.getGenome()));
			//			System.out.println("In mutateall: "+current.getGenome());
			Embryo newEmbryo = new Embryo(mutator.mutate(current.getGenome()));
			outEmbryos.add(newEmbryo);
			//			System.out.println("In mutateall: "+newEmbryo.getGenome());
		}
		return outEmbryos;
	}
	private void init() {
		this.fitnessFunction = new DefaultFitnessFunction(evaluation_);
		DefaultRandomInitialiser initialiser = new DefaultRandomInitialiser(fitnessFunction);
		this.evalsLeft = this.evals;
	
		
		
		if(isMultimodal()) {
			initialiser.defaultEpsilon0 = 0.1;
		}
		
		this.initialiser = initialiser;
		
	

		//Calc pop/offspring size
		if(isMultimodal()) {
			this.populationSize = getEvals()/2000;
		}else {
			this.populationSize = getEvals()/2000;
		}
		this.offspringSize = 4*this.populationSize;
	}

	@Override
	public void onSetEvaluation() {
		init();		
	}

}
