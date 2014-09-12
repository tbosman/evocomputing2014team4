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
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome.CrossoverType;

public class FirstEvolutionaryAlgorithm extends AbstractEvolutionaryAlgorithm {

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

	public FirstEvolutionaryAlgorithm(int populationSize, int offspringSize) {
		super();
		this.populationSize = populationSize;
		this.offspringSize = offspringSize;
		this.mutator = new DefaultMutator();


	}

	@Override
	public void run() {
		if(instantReturn) {
			return;
		}
		
		Population currentPopulation = initialiser.initialisePopulation(offspringSize);
		this.evalsLeft -= offspringSize;
		bestList.add(currentPopulation.getMaximumFitness());


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
				((DefaultMutator)mutator).heat();
			}else {
				((DefaultMutator)mutator).cool();
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

		
		
		

		if(!isRegular() && !isSeparable()) {
			initialiser.defaultCrossover = CrossoverType.LOCAL_DISCRETE;
			initialiser.defaultCrossoverSet = true;
			muPlusLambda = true;
		}

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
		
		
		int offspringMulti = 4; 
		// TEST for infering evals per function 
//		if(getEvals() <= 5000) {
//			this.instantReturn = true;
//			//throw new RuntimeException();//should return 0
//		}else if(getEvals()<=10000) {
//			offspringMulti = 4;//same as last commit
//		}else if(getEvals()<=100000) {
//			offspringMulti = 7;//same as before last commit 
//		}else {
//			this.populationSize = getEvals()/10000;//something completely different
//		}
//		
		
		if(muPlusLambda) { //Evals at least > 100K 
			if(getEvals()<= 200000) {
				this.instantReturn = true;// gets 0 value
			}else if(getEvals() <= 500000) {
				offspringMulti = 4; // same as two commits back
			}else if(getEvals() <= 1000000) {
				offspringMulti = 7; //same as three commits back
			}else if(getEvals() <= 5000000) {
				this.populationSize = getEvals()/10000; //same as last commit
			}else {
				this.populationSize = getEvals()/100000; // new
			}
		}
		
		
		
		
		this.offspringSize = 4*this.populationSize;
	}

	@Override
	public void onSetEvaluation() {
		init();		
	}

}
