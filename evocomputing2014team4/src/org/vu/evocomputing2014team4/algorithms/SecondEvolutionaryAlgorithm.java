package org.vu.evocomputing2014team4.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.vu.evocomputing2014team4.algorithms.buildingblocks.*;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.KMeansClustering.Cluster;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.*;
import org.vu.evocomputing2014team4.algorithms.datastructures.Embryo;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome;
import org.vu.evocomputing2014team4.algorithms.datastructures.GenomeCarrier;
import org.vu.evocomputing2014team4.algorithms.datastructures.Individual;
import org.vu.evocomputing2014team4.algorithms.datastructures.Population;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome.CrossoverType;

public class SecondEvolutionaryAlgorithm extends AbstractEvolutionaryAlgorithm {

	private int populationSize;
	private int offspringSize; 
	private Initialiser initialiser; 
	private FitnessFunction fitnessFunction;
	private Mutator mutator; 
	private int evalsLeft;

	boolean muPlusLambda = false; 

	private int TOURNAMENT_SIZE = 10;


	ArrayList<Double> bestList = new ArrayList<Double>();
	private boolean instantReturn = false;
	private int startSize;
	private boolean addZeroVector = false;

	
	public boolean verbose = false;
	
	private double crossoverTypeMutationChance = 0.1;
	
	public SecondEvolutionaryAlgorithm(int populationSize, int offspringSize) {
		super();
		this.populationSize = populationSize;
		this.offspringSize = offspringSize;
		this.mutator = new ParameterisedMutator(crossoverTypeMutationChance);


	}

	@Override
	public void run() {
		if(instantReturn) {
			return;
		}
		
		
		Population currentPopulation = initialiser.initialisePopulation(startSize);
		this.evalsLeft -= startSize;
		bestList.add(currentPopulation.getMaximumFitness());

		if(addZeroVector ) {
			Genome zeroGenome = new Genome.GenomeBuilder(currentPopulation.get(0).genome).
					setValue(new double[10]).createGenome();
			currentPopulation.add(new Individual(zeroGenome, fitnessFunction.fitness(zeroGenome)));
		}
		
		Breeder breeder = new DefaultBreeder();
//		SurvivorSelector survivorSelector = new TournamentSurvivorSelector(offspringSize/TOURNAMENT_SIZE);
		SurvivorSelector survivorSelector = new KMeansClusteringSelector(populationSize);
		currentPopulation = survivorSelector.selectSurvivors(currentPopulation, populationSize);
		int iteration = 1;
		while(evalsLeft > 0) {


			Iterable<Embryo> embryos = breeder.breed(currentPopulation, offspringSize);

			embryos = mutateAll(embryos, false);
			Population newPopulation = raiseAll(embryos);



			KMeansClustering clusterer = new KMeansClustering(50);

			List<Cluster> clusters = clusterer.findClusters(newPopulation);

			//			System.out.println(clusters);


			int numClusters = 10; 

			boolean useHeuristicClusteringNumber = true; 
			if(useHeuristicClusteringNumber) {
				numClusters = 1+(evalsLeft/evals)*populationSize;
			}

			if(isMultimodal() || !isRegular() || true) {
				survivorSelector = new KMeansClusteringSelector(numClusters);
			}
			
			if(muPlusLambda && false) {
				newPopulation.addAll(currentPopulation);
			}

			bestList.add(newPopulation.getMaximumFitness());

			newPopulation = survivorSelector.selectSurvivors(newPopulation, populationSize);



			currentPopulation = newPopulation;
			evalsLeft -= offspringSize;

			adjustTemperature();

			//			System.out.println("Iteration: "+iteration++ +" - best fitness: "+currentPopulation.getMaximumFitness());
		}
		
		if(verbose) {
			System.out.println("Temp: "+mutator.getTemp());
		}

//		System.out.println("#DBG Best/Temp"+ bestList.get(bestList.size()-1) + " - "+((DefaultMutator)mutator).getTemp());
	}

	private void adjustTemperature() {


		//TODO heat/cool mutator
		int lookback = 50; 
		if(bestList.size() >= lookback+1) {
			boolean worst = true;
			for(int i=2 ; i<=lookback+1;i++) {
				if(bestList.get(bestList.size()-1) > bestList.get(bestList.size()-i)) {
					worst = false;
				}
			}

			if(worst) {
				mutator.heat();
			}else {
				mutator.cool();
			}
		}

		List<Double> sortList = new ArrayList<Double>();
		sortList.addAll(bestList);
		Collections.sort(bestList);
		double best = sortList.get(sortList.size()-1);
		
	
		
		//System.out.println(((DefaultMutator)mutator).getTemp());
		//		System.out.println("#DBG best"+bestList.get(bestList.size()-1));
//				System.out.println("#DBG Best/Temp"+ best + " - "+((DefaultMutator)mutator).getTemp());
	}
	private Population raiseAll(Iterable<Embryo> embryos) {
		Population  newPopulation = new Population();
		for(Embryo current : embryos) {
			//			System.out.println("In raiseall: "+current.getGenome());
			newPopulation.add(current.birth(fitnessFunction));
		}

		return newPopulation;
	}

	private Iterable<Embryo> mutateAll(Iterable<Embryo> embryos, boolean skipCrossover) {
		ArrayList<Embryo> outEmbryos = new ArrayList<Embryo>();
		for(Embryo current : embryos) {
			Embryo newEmbryo;
			if(!skipCrossover || current.getGenome().crossoverType == CrossoverType.NONE) {
				newEmbryo = new Embryo(mutator.mutate(current.getGenome()));
			}else {
				newEmbryo = current;
				System.err.println("#DBG: no x/o "+current.getGenome().crossoverType);
			}
			outEmbryos.add(newEmbryo);
			
		}
		return outEmbryos;
	}
	private void init() {
		this.fitnessFunction = new DefaultFitnessFunction(evaluation_);
		ParameterisedRandomInitialiser initialiser = new ParameterisedRandomInitialiser(fitnessFunction);
		initialiser.minValue = 0; 
		
		this.evalsLeft = this.evals;

		
		
		

//		if(!isRegular() && !isSeparable()) {
//			initialiser.defaultCrossover = CrossoverType.LOCAL_DISCRETE;
//			initialiser.defaultCrossoverSet = true;
//			muPlusLambda = true;
//		}

		if(isMultimodal()) {
			initialiser.defaultEpsilon0 = 0.1;
		}
		

		



		
		
		
		int offspringMulti = 4; 
		
		 
		
//		if(muPlusLambda) {
//			if(getEvals() <= 1000000) {
//				this.populationSize = 5;
//			}else {
//				this.populationSize = 20;
//			}
//			initialiser.minValue = -5;
//		}else {
////			this.addZeroVector  = true;
//		}
		
		this.addZeroVector  = true;
		
		this.populationSize = 20;
		
		initialiser.minValue = 0;
		
		
		this.startSize = getEvals()/10;
		
		this.offspringSize = 4*this.populationSize;
		
		
		this.initialiser = initialiser;
	}

	@Override
	public void onSetEvaluation() {
		init();		
	}

}
